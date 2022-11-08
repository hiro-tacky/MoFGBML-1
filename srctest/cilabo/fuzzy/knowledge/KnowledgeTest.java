package cilabo.fuzzy.knowledge;

import static org.junit.Assert.*;

import org.junit.Test;

import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;

public class KnowledgeTest {

	@Test
	public void testHomoTriangle_2_3_4_5() {
		int dimension = 3;
		float[][] params = HomoTriangle_2_3_4_5.getParams();
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		double diff = 1e-5;

		float[] actual_MembershipValue_1_2 = {1f,  0.5f, 0.5f,  0f, 1f, 0f,  0f, 0.5f, 0.5f, 0f,  0f, 0f, 1f, 0f, 0f};

		float[] actual_MembershipValue_1_3 = {1f,  2/3f, 1/3f,  1/3f, 2/3f, 0f,  0f, 1f, 0f, 0f,  0f, 2/3f, 1/3f, 0f, 0f};
		float[] actual_MembershipValue_2_3 = {1f,  1/3f, 2/3f,  0f, 2/3f, 1/3f,  0f, 0f, 1f, 0f,  0f, 0f, 1/3f, 2/3f, 0f};

		for(int i=0; i<params[0].length; i++) {
			assertEquals(actual_MembershipValue_1_2[i], Knowledge.getInstace().getMembershipValue(0.5, 0, i), diff);

			assertEquals(actual_MembershipValue_1_3[i], Knowledge.getInstace().getMembershipValue(1/3d, 0, i), diff);
			assertEquals(actual_MembershipValue_2_3[i], Knowledge.getInstace().getMembershipValue(2/3d, 0, i), diff);
		}
	}
}
