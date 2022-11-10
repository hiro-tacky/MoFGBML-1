package cilabo.fuzzy.rule.antecedent.factory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.utility.Random;

class RandomInitializationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random.getInstance().initRandom(2022);
	}

	@Test
	void test() {
		fail("まだ実装されていません");
	}

}
