package cilabo.fuzzy;

import cilabo.data.ClassLabel;
import cilabo.data.DataSet;
import cilabo.fuzzy.classifier.ClassifierFactory;
import cilabo.fuzzy.classifier.RuleBasedClassifier;
import cilabo.fuzzy.classifier.factory.FuzzyClassifierFactory;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.classifier.operator.classification.factory.CFmeanClassification;
import cilabo.fuzzy.classifier.operator.classification.factory.SingleWinnerRuleSelection;
import cilabo.fuzzy.classifier.operator.postProcessing.PostProcessing;
import cilabo.fuzzy.classifier.operator.postProcessing.factory.NopPostProcessing;
import cilabo.fuzzy.classifier.operator.postProcessing.factory.SimplePostProcessing;
import cilabo.fuzzy.classifier.operator.preProcessing.PreProcessing;
import cilabo.fuzzy.classifier.operator.preProcessing.factory.NopPreProcessing;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_2_3_4_5;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.Rule;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.AntecedentFactory;
import cilabo.fuzzy.rule.antecedent.factory.AllCombinationAntecedentFactory;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.ConsequentFactory;
import cilabo.fuzzy.rule.consequent.RuleWeight;
import cilabo.fuzzy.rule.consequent.factory.MoFGBML_Learning;
import cilabo.fuzzy.rule.consequent.factory.MultiLabel_MoFGBML_Learning;

public class StaticFuzzyClassifierForTest {

	public static RuleBasedClassifier makeSingleLabelClassifier(DataSet train) {
		int dimension = train.getNdim();
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		PreProcessing preProcessing = new NopPreProcessing();

		AntecedentFactory antecedentFactory = AllCombinationAntecedentFactory.builder()
												.build();
		int ruleNum = ((AllCombinationAntecedentFactory)antecedentFactory).getRuleNum();

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
										.ruleNum(ruleNum)
										.build();

		RuleBasedClassifier classifier = (RuleBasedClassifier)factory.create();
		return classifier;
	}

	public static RuleBasedClassifier makeMultiLabelClassifier(DataSet train) {
		int dimension = train.getNdim();
		float[][] params = HomoTriangle_2_3_4_5.getParams();
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		PreProcessing preProcessing = new NopPreProcessing();

		AntecedentFactory antecedentFactory = AllCombinationAntecedentFactory.builder()
												.build();
		int ruleNum = ((AllCombinationAntecedentFactory)antecedentFactory).getRuleNum();

		ConsequentFactory consequentFactory = MultiLabel_MoFGBML_Learning.builder()
												.train(train)
												.build();

		PostProcessing postProcessing = new NopPostProcessing();

		Classification classification = new CFmeanClassification();

		ClassifierFactory factory = FuzzyClassifierFactory.builder()
										.preProcessing(preProcessing)
										.antecedentFactory(antecedentFactory)
										.consequentFactory(consequentFactory)
										.postProcessing(postProcessing)
										.classification(classification)
										.train(train)
										.ruleNum(ruleNum)
										.build();

		RuleBasedClassifier classifier = (RuleBasedClassifier)factory.create();
		return classifier;
	}

	/**
	 * 3均等分割三角形型ファジィ集合(Don't careを含まない)の全ての組み合わせを有するファジィ識別器
	 * @param train 学習用データセット
	 * @return ファジィ識別器
	 */
	public static RuleBasedClassifier simpleClassifier(int dim) {
		float[][] params = HomoTriangle_3.getParams();
		makeKnowledge(params, dim);

		RuleBasedClassifier classifier = new RuleBasedClassifier();
		Classification classification = new SingleWinnerRuleSelection();
		classifier.setClassification(classification);
		// Pre Processing
		PreProcessing preProcessing = new NopPreProcessing();
		preProcessing.preProcess(classifier);

		makeRules(classifier);

		// Post Processing
		PostProcessing postProcessing = new SimplePostProcessing();
		postProcessing.postProcess(classifier);

		return classifier;
	}

	/** knowledgeを生成
	 * @param params 分割区間のパラメータ
	 * @param dim データセットの次元数
	 */
	public static void makeKnowledge(float[][] params, int dim) {
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dim)
								.params(params)
								.build()
								.create();
	}

	/**
	 * classifierにルールを追加
	 * @param classifier 識別器
	 */
	public static void makeRules(RuleBasedClassifier classifier) {
		for(int i=1; i<Knowledge.getInstace().getFuzzySetNum(0); i++) {
			for(int j=1; j<Knowledge.getInstace().getFuzzySetNum(0); j++) {
				int[] buf = {i, j};
				Antecedent antecedent = new Antecedent(buf);

				ClassLabel classLabel = new ClassLabel();
				Integer[] buf2= {i, j};
				classLabel.addClassLabels(buf2);

				RuleWeight ruleWeight = new RuleWeight();
				ruleWeight.addRuleWeight(0.5d);
				Consequent consequent = new Consequent(classLabel, ruleWeight);

				Rule rule = new Rule(antecedent, consequent);
				classifier.addRule(rule);
			}
		}
	}
}
