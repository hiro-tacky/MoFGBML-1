package cilabo.data;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class InputVectorTest {

	private static InputVector inputVector;
	private static double[] vector;

	@BeforeClass
	public static void setUpBeforeClass() {
		vector = new double[] {0d, 1/3d, 2/3d, 1d};
		inputVector = new InputVector(vector);
	}

	@Test
	public void testInputVector() {

		assertArrayEquals(vector, inputVector.getVector(), 1e-5);
		for(int i=0; i<vector.length; i++) {
			assertEquals(vector[i], inputVector.getDimValue(i), 1e-5);
		}
	}

}
