package cilabo.fuzzy.rule.antecedent.factory;

import static org.junit.Assert.*;

import org.junit.Test;

import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.Antecedent;

public class AllCombinationAntecedentFactoryTest {
	@Test
	public void testAllCombination() {
		int dimension = 2;
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		AllCombinationAntecedentFactory factory = AllCombinationAntecedentFactory.builder()
													.build();

		int[][] expected = new int[][]
		{	{0,  0}, {0,  1}, {0,  2}, {0,  3},
			{1,  0}, {1,  1}, {1,  2}, {1,  3},
			{2,  0}, {2,  1}, {2,  2}, {2,  3},
			{3,  0}, {3,  1}, {3,  2}, {3,  3},
		};

		assertEquals(expected.length, factory.getRuleNum());

		for(int i = 0; i < expected.length; i++) {
			Antecedent antecedent = factory.create();
			assertArrayEquals(expected[i], antecedent.getAntecedentIndex());
			for(int dim_i=0; dim_i<dimension; dim_i++) {
				assertEquals(Knowledge.getInstace().getFuzzySet(dim_i, expected[i][dim_i]),
						antecedent.getAntecedentFuzzySetAt(dim_i));
			}
		}
		assertEquals(factory.create(), null);
 	}
}
