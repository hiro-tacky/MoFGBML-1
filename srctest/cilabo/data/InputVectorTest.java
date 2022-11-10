package cilabo.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.utility.Random;

public class InputVectorTest {

	private static InputVector inputVector;
	private static double[] vector;

	@BeforeAll
	public static void setUpBeforeClass() {
		Random.getInstance().initRandom(2022);
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
