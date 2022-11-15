package cilabo.fuzzy.rule.antecedent;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.factory.HeuristicRuleGenerationMethod;
import cilabo.utility.Input;
import cilabo.utility.Random;

public class AntecedentTest {


	static DataSet train;
	static int[] antecedentIndex;
	static HeuristicRuleGenerationMethod factory;
	static int dimension;
	static Antecedent antecedent;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random.getInstance().initRandom(2022);
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "a0_0_iris-10tra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		dimension = train.getNdim();
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
		.dimension(dimension)
		.params(params)
		.build()
		.create();

		antecedentIndex = new int[] {0, 1, 2, 3};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
	}

	@Test
	public void testDeepCopy() {

		Antecedent antecedentCopy = antecedent.deepcopy();
		assertNotEquals(antecedent, antecedentCopy);
		assertNotEquals(antecedent.getAntecedentIndex(), antecedentCopy.getAntecedentIndex());
		assertNotEquals(antecedent.getAntecedentFuzzySets(), antecedentCopy.getAntecedentFuzzySets());

	}

	@Test
	public void testGetCompatibleGrade() {
		antecedentIndex = new int[] {0, 1, 2, 3};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();

		double[] input = new double[]{0.5, 0.25, 0.5, 0.75};
		double actual = antecedent.getCompatibleGrade(input);
		double expected = 1.0 * 0.5 * 1.0 * 0.5;
		assertEquals(expected, actual, 1e-5);

		input = new double[]{0.0, 1/3d, 2/3d, 1.0};
		actual = antecedent.getCompatibleGrade(input);
		expected = 1.0 * 1/3d * 2/3d * 1.0;
		assertEquals(expected, actual, 1e-5);
	}

	@Test
	public void testGetCompatibleGrade2() {
		antecedentIndex = new int[] {0, -1, 2, -3};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();

		double[] input = new double[]{-1, -1, 2/3d, -3};
		double actual = antecedent.getCompatibleGrade(input);
		double expected = 1.0 * 1.0 * 2/3d * 1.0;
		assertEquals(expected, actual, 1e-5);

		input = new double[]{-2, -1, 2/3d, -3};
		actual = antecedent.getCompatibleGrade(input);
		expected = 1.0 * 1.0 * 2/3d * 1.0;
		assertEquals(expected, actual, 1e-5);

		input = new double[]{-2, -1, 2/3d, -2};
		actual = antecedent.getCompatibleGrade(input);
		expected = 1.0 * 1.0 * 2/3d * 0.0;
		assertEquals(expected, actual, 1e-5);
	}

	@Test
	public void testGetRuleLength() {
		antecedentIndex = new int[] {0, 1, 2, 3};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
		assertEquals(3, antecedent.getRuleLength());
		antecedentIndex = new int[] {0, 1, 0, 3};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
		assertEquals(2, antecedent.getRuleLength());
	}

	@Test
	public void testCategorical() {
		int dimension = 3;
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(HomoTriangle_2_3_4_5.getParams())
								.build()
								.create();

		int[] antecedentIndex = new int[] {-1, 0, -2};
		Antecedent antecedent = Antecedent.builder()
									.antecedentIndex(antecedentIndex)
									.build();

		double[] x1 = new double[] {-1, 0.5, -2};
		double[] x2 = new double[] {-3, 0.5, -2};
		double[] x3 = new double[] {-1, -1, -2};

		double diff = 0.00001;
		assertEquals(antecedent.getCompatibleGrade(x1), 1.0, diff);
		assertEquals(antecedent.getCompatibleGrade(x2), 0.0, diff);
		assertEquals(antecedent.getCompatibleGrade(x3), 1.0, diff);
	}


}
