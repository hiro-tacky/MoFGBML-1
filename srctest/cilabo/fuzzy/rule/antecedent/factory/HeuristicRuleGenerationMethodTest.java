package cilabo.fuzzy.rule.antecedent.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.utility.Input;
import cilabo.utility.Random;

public class HeuristicRuleGenerationMethodTest {

	static DataSet train;
	static Integer[] samplingIndex;
	static HeuristicRuleGenerationMethod factory;
	static int dimension;

	@BeforeAll
	public static void beforeAll() {
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

		samplingIndex = new Integer[] {0, 5, 60, 120};
		// dataset[0] = {0, 0}; dataset[5] = {0, 0.5}; dataset[60] = {0.5, 0.5}; dataset[120] = {1 1};
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
        		{1, 1}, {1, 2}, {2, 2}, {3, 3}
        };
        for(int i=0; i<samplingIndex.length; i++) {
        	// メソッド呼び出し
        	int[] selectedAntecedentPart = (int[]) method.invoke(factory, i);
        	// 結果をアサーション
        	assertArrayEquals(actual[i], selectedAntecedentPart);
        }
	}

	@Test
	public void testHeuristicRuleGeneration2() {
        int[][] actual = {
        		{1, 1}, {1, 2}, {2, 2}, {3, 3}
        };
        for(int i=0; i<samplingIndex.length; i++) {
        	Antecedent antecedent = factory.create();
        	assertArrayEquals(actual[i], antecedent.getAntecedentIndex());
        	// 結果をアサーション
        	for(int j=0; j<dimension; j++) {
        		assertEquals(Knowledge.getInstance().getFuzzySet(j, actual[i][j]), antecedent.getAntecedentFuzzySetAt(j));
        	}
        }
	}

	@Test
	public void testHeuristicRuleGeneration() {
		Random.getInstance().initRandom(2022);
		String sep = File.separator;
		String dataName = "dataset" + sep + "iris" + sep + "a0_0_iris-10tra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		dimension = train.getNdim();
		float[][] params = HomoTriangle_2_3_4_5.getParams();
		HomoTriangleKnowledgeFactory.builder()
			.dimension(dimension)
			.params(params)
			.build()
			.create();

		samplingIndex = new Integer[]{0, 1, 2, 3};

		factory = HeuristicRuleGenerationMethod.builder()
			.train(train)
			.samplingIndex(samplingIndex)
			.build();


		int[][]actual = {
				{4, 1, 4, 6}, {7, 11, 11, 4}, {6, 1, 3, 10}, {6, 4, 4, 3}
		};
		for(int i=0; i<samplingIndex.length; i++) {
			Antecedent antecedent = factory.create();
        	assertArrayEquals(actual[i], antecedent.getAntecedentIndex());
			for(int j=0; j<dimension; j++) {
				assertEquals(Knowledge.getInstance().getFuzzySet(j, actual[i][j]), antecedent.getAntecedentFuzzySetAt(j));
			}
		}
	}
}
