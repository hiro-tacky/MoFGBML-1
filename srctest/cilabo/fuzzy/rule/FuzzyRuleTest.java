package cilabo.fuzzy.rule;

import static org.junit.Assert.*;

import org.junit.Test;

import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3_4_5;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;

public class FuzzyRuleTest {
	@Test
	public void testFuzzyRule() {
		int[] antecedentIndex = new int[] {0, 2, 1};
		int dimension = 3;
		float[][] params = HomoTriangle_3_4_5.getParams();

		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		Antecedent antecedent = Antecedent.builder()
								.antecedentIndex(antecedentIndex)
								.build();

		SingleClassLabel classLabel = new SingleClassLabel(7);

		SingleRuleWeight ruleWeight = new SingleRuleWeight(0.5);

		Consequent consequent = Consequent.builder()
								.consequentClass(classLabel)
								.ruleWeight(ruleWeight)
								.build();

		Rule rule = Rule.builder()
						.antecedent(antecedent)
						.consequent(consequent)
						.build();

        String actual = "If [ 0,  2,  1] Then class:[7] weight:[0.5]";
		String expected = rule.toString();

		assertEquals(expected, actual);
	}
}
