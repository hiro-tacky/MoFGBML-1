package cilabo.fuzzy.rule.antecedent.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.utility.Input;
import cilabo.utility.Random;

class RandomInitializationTest {

	static DataSet train;
	static RandomInitialization factory;
	static int dimension;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
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


		factory = RandomInitialization.builder()
			.train(train)
			.seed(2022)
			.build();
	}


	@Test
	public void testRandomInitialization() {
		int[][]actual = {
				{3, 7, 3, 8}, {7, 13, 3, 6}, {6, 5, 12, 4}, {9, 11, 1, 0}
		};
		for(int i=0; i<4; i++) {
			Antecedent antecedent = factory.create();
        	assertArrayEquals(actual[i], antecedent.getAntecedentIndex());
			for(int j=0; j<dimension; j++) {
				assertEquals(Knowledge.getInstance().getFuzzySet(j, actual[i][j]), antecedent.getAntecedentFuzzySetAt(j));
			}
		}
	}

}
