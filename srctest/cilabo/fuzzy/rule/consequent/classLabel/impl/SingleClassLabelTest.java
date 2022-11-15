package cilabo.fuzzy.rule.consequent.classLabel.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SingleClassLabelTest {
	static SingleClassLabel singleClassLabel;
	static Integer classLabel;

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		classLabel = 0;
		singleClassLabel = new SingleClassLabel(classLabel);
	}

	@Test
	void testSingleClassLabel() {
		assertNotNull(singleClassLabel);
	}

	@Test
	void testDeepcopy() {
		SingleClassLabel singleClassLabelCopy = singleClassLabel.deepcopy();
		//値は同値だがインスタンスは別であることの検証
		assertFalse(singleClassLabel == singleClassLabelCopy);
		assertTrue(singleClassLabel.equals(singleClassLabelCopy));
	}

	@Test
	void testGetClassLabel() {
		assertEquals(classLabel, singleClassLabel.getClassLabel());
	}

	@Test
	void testSetClassLabel() {
		singleClassLabel.setClassLabel(1);
		assertEquals(1, (int)singleClassLabel.getClassLabel());
		//代入元の値変更が影響を及ぼさない
		classLabel = 2;
		assertEquals(1, (int)singleClassLabel.getClassLabel());
	}

	@Test
	void testEqualsClassLabel() {
		SingleClassLabel singleClassLabelBuf1 = new SingleClassLabel(0);
		assertTrue(singleClassLabel.equals(singleClassLabelBuf1));

		SingleClassLabel singleClassLabelBuf2 = new SingleClassLabel(1);
		assertFalse(singleClassLabel.equals(singleClassLabelBuf2));
	}

	@Test
	void testToString() {
		String str = " 0";
		assertEquals(str, singleClassLabel.toString());
	}

}
