package cilabo.fuzzy.classifier;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.ClassLabel;
import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.fuzzy.StaticFuzzyClassifierForTest;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.Rule;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.RuleWeight;
import cilabo.utility.Input;

class RuleBasedClassifierTest extends RuleBasedClassifier {

	static RuleBasedClassifier classifier = new RuleBasedClassifier();
	static DataSet train;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		classifier = StaticFuzzyClassifierForTest.simpleClassifier(train.getNdim());
	}

	@Test
	void testClassify() {
		ArrayList<Pattern> patterns = train.getPatterns();
		for(Pattern pattern_i: patterns) {
			double[] inputVector = pattern_i.getInputVector().getVector();
			int x_1 = func(inputVector[0]), x_2 = func(inputVector[1]);
			Rule rule_actual = classifier.classify(pattern_i.getInputVector());
			Integer[] expected = {x_1, x_2};
			assertArrayEquals(expected, rule_actual.getConsequent().getClassLabel().getClassVector());
		}
	}

	@Test
	void testGetRuleNum() {
		int ruleNum = 1;
		for(int i=0; i<Knowledge.getInstace().getDimension(); i++) {
			ruleNum *= Knowledge.getInstace().getFuzzySetNum(i);
		}
		assertEquals(ruleNum, classifier.getRuleNum());
	}

	@Test
	void testGetRuleLength() {
		int ruleLength = 1;
		for(int i=0; i<Knowledge.getInstace().getDimension(); i++) {
			ruleLength *= Knowledge.getInstace().getFuzzySetNum(i);
		}
		ruleLength *= Knowledge.getInstace().getDimension();
		assertEquals(ruleLength, classifier.getRuleLength());
	}

	@Test
	void testAddRule() {
		int[] antecedent_expected = {0, 0};
		Antecedent antecedent = new Antecedent(antecedent_expected);

		ClassLabel classLabel = new ClassLabel();
		Integer classLabel_expected = -1;
		classLabel.addClassLabel(classLabel_expected);

		RuleWeight ruleWeight = new RuleWeight();
		double ruleWeight_expected = 0.5d;
		ruleWeight.addRuleWeight(ruleWeight_expected);
		Consequent consequent = new Consequent(classLabel, ruleWeight);

		Rule rule = new Rule(antecedent, consequent);
		classifier.addRule(rule);

		int ruleNum = classifier.getRuleNum();

		assertEquals(antecedent_expected, classifier.getRule(ruleNum-1).getAntecedent().getAntecedentIndex());
		assertEquals(classLabel_expected, classifier.getRule(ruleNum-1).getConsequent().getClassLabel().getClassLabel());
		assertEquals(ruleWeight_expected, classifier.getRule(ruleNum-1).getConsequent().getRuleWeight().getRuleWeight(), 1e-5);
	}

	/** 3均等分割三角形型ファジィでどのラベルが与えられるか
	 * @param x 属性値
	 * @return クラスラベル r = {1, 2, 3}のいずれか
	 */
	private int func(double x) {
		int r;
		if(x < 1/4d) {r = 1;}else if(x < 3/4d) {r = 2;} else {r = 3;}
		return r;
	}

}
