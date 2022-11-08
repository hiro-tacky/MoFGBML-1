package cilabo.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassLabelTest {

	@Test
	public void testSingleLabel() {
		Integer expected = 7;

		ClassLabel classLabel = new ClassLabel();
		classLabel.addClassLabel(expected);

		assertEquals(expected, classLabel.getClassLabel());
	}

	@Test
	public void testMultiLabel() {
		Integer[] expected = new Integer[] {1, 0, 1};

		ClassLabel classLabel = new ClassLabel();
		for(int i = 0; i < expected.length; i++) {
			classLabel.addClassLabel(expected[i]);
		}

		assertArrayEquals(expected, classLabel.getClassVector());

	}

	@Test
	public void testMultiLabel2() {
		Integer[] expected = new Integer[] {1, 0, 1};

		ClassLabel classLabel = new ClassLabel();
		classLabel.addClassLabels(expected);

		assertArrayEquals(expected, classLabel.getClassVector());
	}
}
