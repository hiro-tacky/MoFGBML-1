package cilabo.fuzzy.knowledge.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.utility.Random;
import jfml.term.FuzzyTermType;

public class HomoTriangleKnowledgeFactoryTest {

    @BeforeAll
    public static void setUpBeforeClass(){
    	Random.getInstance().initRandom(2022);
    }

	@Test
	public void testCreate() {
		int dimension = 3;
		float[][] params = HomoTriangle_2_3_4_5.getParams();

		//actual
		FuzzyTermType[][] fuzzySets = new FuzzyTermType[dimension][params.length+1];
		for(int i = 0; i < dimension; i++) {
			//Don't care
            fuzzySets[i][0] = new FuzzyTermType(" 0", FuzzyTermType.TYPE_rectangularShape, new float[] {0f, 1f});
            for(int j = 0; j < params.length; j++) {
                fuzzySets[i][j+1] = new FuzzyTermType(String.format("%2s", String.valueOf(j+1)), FuzzyTermType.TYPE_triangularShape, params[j]);
            }
		}

		HomoTriangleKnowledgeFactory.builder()
							.dimension(dimension)
							.params(params)
							.build()
							.create();

		float[] actual_MembershipValue = {1f,  0.5f, 0.5f,  0f, 1f, 0f,  0f, 0.5f, 0.5f, 0f,  0f, 0f, 1f, 0f, 0f};

		for(int i=0; i<fuzzySets.length; i++) {
			for(int j=0; j<fuzzySets[i].length; j++) {
				testFuzzyTerm(fuzzySets[i][j], Knowledge.getInstace().getFuzzySet(i, j));
				float buf = Knowledge.getInstace().getFuzzySet(i, j).getMembershipValue((float) 0.5);
				assertEquals(actual_MembershipValue[j], buf, 1e-5);
			}
		}
	}

	public void testFuzzyTerm(FuzzyTermType expected, FuzzyTermType actual) {
		assertEquals(expected.getName(), actual.getName());
		assertArrayEquals(expected.getParam(), actual.getParam());
		assertEquals(expected.getType(), actual.getType());
	}
}
