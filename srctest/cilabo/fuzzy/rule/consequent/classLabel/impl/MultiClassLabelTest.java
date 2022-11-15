package cilabo.fuzzy.rule.consequent.classLabel.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MultiClassLabelTest {
	static MultiClassLabel multiClassLabel;
	static Integer[] classLabels;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		classLabels = new Integer[] {0, 1, 2};
		multiClassLabel = new MultiClassLabel(classLabels);
	}

	@Test
	void testMultiClassLabel() {
		assertNotNull(multiClassLabel);
	}

	@Test
	void testGetClassLabels() {
		assertArrayEquals(classLabels, multiClassLabel.getClassLabels());
		//異なるインスタンスが返ってくるか
		assertFalse(multiClassLabel.getClassLabels() == classLabels);
	}

	@Test
	void testGetClassLabelAt() {
		for(int i=0; i<classLabels.length; i++) {
			assertEquals(classLabels[i], multiClassLabel.getClassLabelAt(i));
		}
	}

	@Test
	void testSetClassLabels() {
		Integer[] classLabelsBuf = new Integer[] {3, 4, 5}, classLabelsBuf2 = new Integer[] {3, 4, 5};
		multiClassLabel.setClassLabels(classLabelsBuf);
		for(int i=0; i<classLabelsBuf.length; i++) {
			assertEquals(classLabelsBuf[i], multiClassLabel.getClassLabelAt(i));
		}
		//代入元の値変更が影響を及ぼさない
		classLabelsBuf = new Integer[] {6, 7, 8};
		for(int i=0; i<classLabelsBuf.length; i++) {
			assertEquals(classLabelsBuf2[i], multiClassLabel.getClassLabelAt(i));
		}
	}

	@Test
	void testSetClassLabelAt() {
		multiClassLabel.setClassLabelAt(1, 4);
		assertEquals(0, (int)multiClassLabel.getClassLabelAt(0));
		assertEquals(4, (int)multiClassLabel.getClassLabelAt(1));
		assertEquals(2, (int)multiClassLabel.getClassLabelAt(2));
	}

	@Test
	void testDeepcopy() {
		MultiClassLabel multiClassLabelCopy = multiClassLabel.deepcopy();
		//値は同値だがインスタンスは別であることの検証
		assertFalse(multiClassLabel == multiClassLabelCopy);
		assertTrue(multiClassLabel.equals(multiClassLabelCopy));
	}

	@Test
	void testEqualsClassLabel() {
		MultiClassLabel multiClassLabelBuf1 = new MultiClassLabel(new Integer[] {0, 1, 2});
		assertTrue(multiClassLabel.equals(multiClassLabelBuf1));

		MultiClassLabel multiClassLabelBuf2 = new MultiClassLabel(new Integer[] {3, 4, 5});
		assertFalse(multiClassLabel.equals(multiClassLabelBuf2));
	}

	@Test
	void testToString() {
		String str = " 0,  1,  2";
		assertEquals(str, multiClassLabel.toString());
	}

}
