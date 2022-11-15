package cilabo.fuzzy.rule;

import static org.junit.Assert.*;

import org.junit.Test;

import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;

public class RuleWeightTest {

	@Test
	public void testRuleWeight() {
		Double w = 0.5;
		String actual = "0.5";

		SingleRuleWeight ruleWeight = new SingleRuleWeight(w);

		String expected = ruleWeight.toString();

		assertEquals(expected, actual);
	}

	@Test
	public void testRuleWeightVector() {
		Double[] wVec = new Double[] {0.5, 0.8, 0.9};
		String actual = "0.5, 0.8, 0.9";

		SingleRuleWeight ruleWeight = new SingleRuleWeight(wVec);

		String expected = ruleWeight.toString();

		assertEquals(expected, actual);
	}
}
