package cilabo.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.utility.Random;

public class PatternTest {

	static InputVector inputVector;
	static SingleClassLabel classLabel;
	static Pattern pattern;

	static double[] inputVectorExpected;
	static Integer[] classLabelExpected;

    @BeforeAll
    public static void setUpBeforeClass(){
    	Random.getInstance().initRandom(2022);
    	inputVectorExpected = new double[] {0d, 1/3d, 2/3d, 1d};
		inputVector = new InputVector(inputVectorExpected);

		classLabelExpected = new Integer[] {1, 0, 1};
		classLabel = new SingleClassLabel(classLabelExpected);


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
		Integer expected = 2;
		classLabel = new SingleClassLabel(expected);

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
