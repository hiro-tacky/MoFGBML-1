package cilabo.fuzzy.rule.consequent;

import static org.junit.Assert.*;

import org.junit.Test;

import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;

public class ConsequentTest {
	@Test
	public void testSingleLabel() {
		Integer C = 7;
		Double w = 0.5;
		String actual = "class:[7] weight:[0.5]";

		SingleClassLabel classLabel = new SingleClassLabel(C);
		SingleRuleWeight ruleWeight = new SingleRuleWeight(w);

		Consequent consequent = Consequent.builder()
									.consequentClass(classLabel)
									.ruleWeight(ruleWeight)
									.build();

		String expected = consequent.toString();

		assertEquals(expected, actual);
	}

	@Test
	public void testMultiLabel() {
		Integer[] cVec = new Integer[] {1, 0, 1};
		Double[] wVec = new Double[] {0.5, 0.8, 0.9};
		String actual = "class:[1, 0, 1] weight:[0.5, 0.8, 0.9]";

		SingleClassLabel classLabel = new SingleClassLabel(cVec);
		SingleRuleWeight ruleWeight = new SingleRuleWeight(wVec);

		Consequent consequent = Consequent.builder()
									.consequentClass(classLabel)
									.ruleWeight(ruleWeight)
									.build();

		String expected = consequent.toString();

		assertEquals(expected, actual);
	}

}
