package cilabo.fuzzy.example;

import java.io.File;

import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.fuzzy.classifier.factory.ClassifierFactory;
import cilabo.fuzzy.classifier.factory.impl.FuzzyClassifierFactory;
import cilabo.fuzzy.classifier.impl.RuleBasedClassifier;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.classifier.operator.classification.impl.SingleWinnerRuleSelection;
import cilabo.fuzzy.classifier.operator.postProcessing.PostProcessing;
import cilabo.fuzzy.classifier.operator.postProcessing.factory.SimplePostProcessing;
import cilabo.fuzzy.classifier.operator.preProcessing.PreProcessing;
import cilabo.fuzzy.classifier.operator.preProcessing.factory.NopPreProcessing;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.Rule;
import cilabo.fuzzy.rule.antecedent.AntecedentFactory;
import cilabo.fuzzy.rule.antecedent.factory.AllCombinationAntecedentFactory;
import cilabo.fuzzy.rule.consequent.ConsequentFactory;
import cilabo.fuzzy.rule.consequent.factory.MoFGBML_Learning;
import cilabo.utility.Input;

/**
 * For student belonging in CILAB,
 * this example is implementation of the programming exercise 5.
 */

public class FuzzyClassifierMain {

	public static void main(String[] args) {
		String sep = File.separator;

		// Dataset
		String dataName = "dataset" + sep + "cilabo" + sep + "kadai5_pattern1.txt";
		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		// Initialization Knowledge Base
		HomoTriangleKnowledgeFactory.builder().dimension(train.getNdim())
												.params(HomoTriangle_3.getParams())
												.build()
												.create();

		// Learning Fuzzy Classifier
		RuleBasedClassifier classifier = makeSingleLabelClassifier(train);

		// Evaluation
		int numberOfRules = classifier.getRuleNum();
		System.out.println("Number of rules = " + numberOfRules);

		int numberOfRuleLength = classifier.getRuleLength();
		System.out.println("Total length = " + numberOfRuleLength);

		double correctRate = evaluateCorrectRate(train, classifier);
		System.out.println("Accuracy = " + correctRate);

		return;
	}

	public static RuleBasedClassifier makeSingleLabelClassifier(DataSet train) {
		PreProcessing preProcessing = new NopPreProcessing();

		AntecedentFactory antecedentFactory = AllCombinationAntecedentFactory.builder()
												.build();
		int initRuleNum = ((AllCombinationAntecedentFactory)antecedentFactory).getRuleNum();

		ConsequentFactory consequentFactory = MoFGBML_Learning.builder()
												.train(train)
												.build();

		PostProcessing postProcessing = new SimplePostProcessing();

		Classification classification = new SingleWinnerRuleSelection();

		ClassifierFactory factory = FuzzyClassifierFactory.builder()
										.preProcessing(preProcessing)
										.antecedentFactory(antecedentFactory)
										.consequentFactory(consequentFactory)
										.postProcessing(postProcessing)
										.classification(classification)
										.train(train)
										.ruleNum(initRuleNum)
										.build();

		RuleBasedClassifier classifier = (RuleBasedClassifier)factory.create();
		return classifier;
	}

	public static double evaluateCorrectRate(DataSet dataset, RuleBasedClassifier classifier) {
		double correct = 0;
		for(int i = 0; i < dataset.getDataSize(); i++) {
			Pattern pattern = dataset.getPattern(i);
			Rule winnerRule = classifier.classify(pattern.getInputVector());
			if(winnerRule == null) continue;
			if(pattern.getTrueClass().toString().equals(winnerRule.getConsequent().getClassLabel().toString())) {
				correct += 1;
			}
		}
		double rate = 100 * (correct / (double)dataset.getDataSize());
		return rate;
	}


}
