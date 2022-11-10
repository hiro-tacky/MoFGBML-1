package cilabo.fuzzy.rule.antecedent.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.utility.Input;

public class HeuristicRuleGenerationMethodTest {


	static DataSet train;
	static Integer[] samplingIndex;
	static HeuristicRuleGenerationMethod factory;

	@BeforeAll
	public static void beforeAll() {
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		int dimension = train.getNdim();
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
		.dimension(dimension)
		.params(params)
		.build()
		.create();

		samplingIndex = new Integer[] {0, 60, 120};
		// dataset[0] = {0, 0}; dataset[60] = {0.5, 0.5}; dataset[120] = {1 1};
		factory = HeuristicRuleGenerationMethod.builder()
				.train(train)
				.samplingIndex(samplingIndex)
				.build();
	}

	@Test
	public void testSelectAntecedentPart() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = HeuristicRuleGenerationMethod.class.getDeclaredMethod("selectAntecedentPart", int.class);
        // メソッドをアクセス制限を解除
        method.setAccessible(true);
        int[][] actual = {
        		{1, 1}, {2, 2}, {3, 3}
        };
        for(int i=0; i<samplingIndex.length; i++) {
        	// メソッド呼び出し
        	int[] selectedAntecedentPart = (int[]) method.invoke(factory, i);
        	// 結果をアサーション
        	assertArrayEquals(actual[i], selectedAntecedentPart);
        }
	}

	@Test
	public void testHeuristicRuleGeneration() {

		String sep = File.separator;
		String dataName = "dataset" + sep + "iris" + sep + "a0_0_iris-10tra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		int dimension = train.getNdim();
		float[][] params = HomoTriangle_2_3_4_5.getParams();
		HomoTriangleKnowledgeFactory.builder()
		.dimension(dimension)
		.params(params)
		.build()
		.create();

		Antecedent antecedent = factory.create();
		String actual = " 7,  8,  6, 10";
		String expected = antecedent.toString();
		assertEquals(expected, actual);

		antecedent = factory.create();
		actual = " 3,  7, 10,  1";
		expected = antecedent.toString();
		assertEquals(expected, actual);

		antecedent = factory.create();
		assertEquals(null, antecedent);

		antecedent = factory.create();
		assertEquals(null, antecedent);

	}
}
