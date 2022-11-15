package cilabo.fuzzy.rule.consequent.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.factory.HeuristicRuleGenerationMethod;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;
import cilabo.utility.Input;
import cilabo.utility.Random;

class MoFGBML_LearningTest {

	static DataSet train;
	static int[] antecedentIndex;
	static HeuristicRuleGenerationMethod factory;
	static int dimension;
	static Antecedent antecedent;
	static MoFGBML_Learning moFGBML_Learning;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random.getInstance().initRandom(2022);
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		dimension = train.getNdim();
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
		.dimension(dimension)
		.params(params)
		.build()
		.create();

		antecedentIndex = new int[] {0, 0};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();

		moFGBML_Learning = MoFGBML_Learning.builder()
				.train(train)
				.build();
	}

	@Test
	void testCalcConfidence() {
		double[] actual, expected;
		//Don't Care
		actual = moFGBML_Learning.calcConfidence(antecedent);
		expected = new double[] {
				9/121d, 15/121d, 9/121d,
				15/121d, 25/121d, 15/121d,
				9/121d, 15/121d, 9/121d
			};
		assertArrayEquals(expected, actual);

		//均等分割三角形左端
		antecedentIndex = new int[] {1, 0};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
		actual = moFGBML_Learning.calcConfidence(antecedent);
		double left = 1.0 + 0.8 + 0.6, center = 0.4 + 0.2, right=0;
		expected = new double[] {
			left/(left+center)*3/11d, left/(left+center)*5/11d, left/(left+center)*3/11d,
			center/(left+center)*3/11d, center/(left+center)*5/11d, center/(left+center)*3/11d,
			0, 0, 0
		};
		assertArrayEquals(expected, actual, 1e-5);

		//均等分割三角形中央
		antecedentIndex = new int[] {2, 0};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
		actual = moFGBML_Learning.calcConfidence(antecedent);
		left = 0.2 + 0.4; center = 0.6 + 0.8 + 1.0 + 0.8 + 0.6; right = 0.2 + 0.4;
		expected = new double[] {
			left/(left+center+right)*3/11d, left/(left+center+right)*5/11d, left/(left+center+right)*3/11d,
			center/(left+center+right)*3/11d, center/(left+center+right)*5/11d, center/(left+center+right)*3/11d,
			right/(left+center+right)*3/11d, right/(left+center+right)*5/11d, right/(left+center+right)*3/11d,
		};
		assertArrayEquals(expected, actual, 1e-5);

		//均等分割三角形右端
		antecedentIndex = new int[] {3, 0};
		antecedent = Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
		actual = moFGBML_Learning.calcConfidence(antecedent);
		left = 0; center = 0.2 + 0.4; right = 0.6 + 0.8 + 1.0;
		expected = new double[] {
			0, 0, 0,
			center/(center+right)*3/11d, center/(center+right)*5/11d, center/(center+right)*3/11d,
			right/(center+right)*3/11d, right/(center+right)*5/11d, right/(center+right)*3/11d,
		};
		assertArrayEquals(expected, actual, 1e-5);
	}

	@Test
	void testCalcClassLabel() {
		double[] buf = new double[] { 0.1, 0.1, 0.1, 0.7 };
		SingleClassLabel actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		assertEquals(3, (int)actual.getClassLabel());

		buf = new double[] { 0.1, 0.2, 0.3, 0.4 };
		actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		assertEquals(-1, (int)actual.getClassLabel());

		buf = new double[] { 0.0, 0.2, 0.4, 0.4 };
		actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		assertEquals(-1, (int)actual.getClassLabel());
	}

	@Test
	void testCalcRuleWeight() {

		double[] buf = new double[] { 0.1, 0.1, 0.1, 0.7 };
		SingleClassLabel actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		SingleRuleWeight ruleWeight = moFGBML_Learning.calcRuleWeight(actual, buf);
		assertEquals(0.4, ruleWeight.getRuleWeight(), 1e-5);

		buf = new double[] { 0.1, 0.2, 0.3, 0.4 };
		actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		ruleWeight = moFGBML_Learning.calcRuleWeight(actual, buf);
		assertEquals(0.0, ruleWeight.getRuleWeight(), 1e-5);

		buf = new double[] { 0, 0.2, 0.4, 0.4 };
		actual = moFGBML_Learning.calcClassLabel(buf, 0.5);
		ruleWeight = moFGBML_Learning.calcRuleWeight(actual, buf);
		assertEquals(0.0, ruleWeight.getRuleWeight(), 1e-5);
	}
}
