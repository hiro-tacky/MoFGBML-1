package cilabo.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.utility.Random;

public class ClassLabelTest {

    @BeforeAll
    public static void setUpBeforeClass(){
    	Random.getInstance().initRandom(2022);
    }

	@Test
	public void testSingleLabel() {
		Integer expected = 7;

		SingleClassLabel classLabel = new SingleClassLabel(expected);

		assertEquals(expected, classLabel.getClassLabel());
	}

	@Test
	public void testMultiLabel() {
		Integer[] expected = new Integer[] {1, 0, 1};

		SingleClassLabel classLabel = new SingleClassLabel(expected);

		assertArrayEquals(expected, classLabel.getClassVector());

	}

	@Test
	public void testMultiLabel2() {
		Integer[] expected = new Integer[] {1, 0, 1};

		SingleClassLabel classLabel = new SingleClassLabel(expected);

		assertArrayEquals(expected, classLabel.getClassVector());
	}
}
