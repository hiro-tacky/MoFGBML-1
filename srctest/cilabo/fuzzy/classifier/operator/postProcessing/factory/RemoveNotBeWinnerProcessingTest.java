package cilabo.fuzzy.classifier.operator.postProcessing.factory;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.StaticFuzzyClassifierForTest;
import cilabo.fuzzy.classifier.impl.RuleBasedClassifier;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.classifier.operator.classification.impl.SingleWinnerRuleSelection;
import cilabo.fuzzy.classifier.operator.preProcessing.PreProcessing;
import cilabo.fuzzy.classifier.operator.preProcessing.factory.NopPreProcessing;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.Rule;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;
import cilabo.utility.Input;

class RemoveNotBeWinnerProcessingTest {

	@Test
	void test() {
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		float[][] params = HomoTriangle_3.getParams();
		StaticFuzzyClassifierForTest.makeKnowledge(params, train.getNdim());

		RuleBasedClassifier classifier = new RuleBasedClassifier();
		Classification classification = new SingleWinnerRuleSelection();
		classifier.setClassification(classification);
		// Pre Processing
		PreProcessing preProcessing = new NopPreProcessing();
		preProcessing.preProcess(classifier);

		for(int i=1; i<4; i++) {
			for(int j=1; j<4; j++) {
				//falseでルール重みの必ず勝てないルールを追加
				for(boolean k: new boolean[]{true, false}) {
					int[] buf = {i, j};
					Antecedent antecedent = new Antecedent(buf);

					Integer[] buf2;
					if(k) {buf2 = new Integer[]{i, j};}else {buf2 = new Integer[]{-1};}
					SingleClassLabel classLabel = new SingleClassLabel(buf2);

					double ruleWeightBuf;
					if(k) { ruleWeightBuf = 0.5; }else { ruleWeightBuf = 0.0; }
					SingleRuleWeight ruleWeight = new SingleRuleWeight(ruleWeightBuf);
					Consequent consequent = new Consequent(classLabel, ruleWeight);

					Rule rule = new Rule(antecedent, consequent);
					classifier.addRule(rule);
				}
			}
		}

		// Post Processing
		RemoveNotBeWinnerProcessing postProcessing = new RemoveNotBeWinnerProcessing(train);
		postProcessing.postProcess(classifier);

		for(Rule rile_i: classifier.getRuleSet()) {
			assertNotEquals(-1, (int)rile_i.getConsequent().getClassLabel().getClassLabel());
		}
	}

}
