package cilabo.data;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PatternTest {

	static InputVector inputVector;
	static ClassLabel classLabel;
	static Pattern pattern;

	static double[] inputVectorExpected;
	static Integer[] classLabelExpected;

    @BeforeClass
    public static void setUpBeforeClass(){
    	inputVectorExpected = new double[] {0d, 1/3d, 2/3d, 1d};
		inputVector = new InputVector(inputVectorExpected);

		classLabel = new ClassLabel();
		classLabelExpected = new Integer[] {1, 0, 1};
		classLabel.addClassLabels(classLabelExpected);


		pattern = Pattern.builder()
				.id(0)
				.inputVector(inputVector)
				.trueClass(classLabel)
				.build();
    }
	@Test
	public void testGetDimValue() {

		for(int i = 0; i < inputVectorExpected.length; i++) {
			Double actual = inputVectorExpected[i];
			Double expected = pattern.getDimValue(i);
			assertEquals(expected, actual);
		}
	}


	@Test
	public void testGetTrueClassSingel() {
		classLabel = new ClassLabel();
		Integer expected = 2;
		classLabel.addClassLabel(expected);

		pattern = Pattern.builder()
				.id(0)
				.inputVector(inputVector)
				.trueClass(classLabel)
				.build();

		assertEquals(expected, pattern.getTrueClass().getClassLabel());
	}


	@Test
	public void testGetTrueClassMulti() {
		assertArrayEquals(classLabelExpected, pattern.getTrueClass().getClassVector());
	}
}
