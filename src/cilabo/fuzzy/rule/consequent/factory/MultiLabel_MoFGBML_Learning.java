package cilabo.fuzzy.rule.consequent.factory;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import cilabo.data.DataSet;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.ConsequentFactory;
import cilabo.fuzzy.rule.consequent.RuleWeightVector;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.utility.Parallel;

public class MultiLabel_MoFGBML_Learning extends MoFGBML_Learning implements ConsequentFactory {
	// ************************************************************
	// Fields

	// ************************************************************
	// Constructor
	public MultiLabel_MoFGBML_Learning(DataSet train) {
		super(train);
	}

	// ************************************************************
	// Methods

	/**
	 *
	 */
	@Override
	public Consequent learning(Antecedent antecedent) {
		double[][] confidence = this.calcConfidenceMulti(antecedent);

		SingleClassLabel classLabel = this.calcClassLabel(confidence);
		RuleWeightVector ruleWeight = this.calcRuleWeightVector(classLabel, confidence);

		Consequent consequent = Consequent.builder()
								.consequentClass(classLabel)
								.ruleWeight(ruleWeight)
								.build();
		return consequent;
	}

	/**
	 *
	 */
	public double[][] calcConfidenceMulti(Antecedent antecedent) {
		int Cnum = train.getCnum();
		double[][] confidence = new double[Cnum][2];

		for(int c = 0; c < Cnum; c++) {
			for(int i = 0; i < 2; i++) {
				final int CLASS = c;
				final int ASSOCIATE = i;
				Optional<Double> partSum = null;
				try {
					partSum = Parallel.getInstance().getLearningForkJoinPool().submit( () ->
						train.getPatterns().parallelStream()
						// 結論部クラスベクトルのCLASS番目の要素がASSOCIATEであるパターンを抽出
						.filter(pattern -> pattern.getTrueClass().getClassVector()[CLASS] == ASSOCIATE)
						// 各パターンの入力ベクトルを抽出
						.map(pattern -> pattern.getInputVector().getVector())
						// 各入力ベクトルとantecedentのcompatible gradeを計算
						.map(x -> antecedent.getCompatibleGrade(x))
						// compatible gradeを総和する
						.reduce((sum, grade) -> sum+grade)
					).get();
				}
				catch (InterruptedException | ExecutionException e) {
					System.out.println(e);
					return null;
				}

				confidence[CLASS][ASSOCIATE] = partSum.orElse(0.0);
			}

			double sumAll = confidence[c][0] + confidence[c][1];
			for(int i = 0; i < 2; i++) {
				if(sumAll != 0) {
					confidence[c][i] /= sumAll;
				}
				else {
					confidence[c][i] = 0.0;
				}
			}
		}

		return confidence;
	}

	/**
	 *
	 */
	public SingleClassLabel calcClassLabel(double[][] confidence) {
		Integer[] classLabelBuf = new Integer[confidence.length];
		for(int c = 0; c < confidence.length; c++) {
			if(confidence[c][0] > confidence[c][1]) {
				classLabelBuf[c] = 0;
			}
			else if(confidence[c][0] < confidence[c][1]) {
				classLabelBuf[c] = 1;
			}
			else {
				classLabelBuf[c] = -1;
			}
		}
		SingleClassLabel classLabel = new SingleClassLabel(classLabelBuf);
		return classLabel;
	}

	/**
	 *
	 */
	public RuleWeightVector calcRuleWeightVector(SingleClassLabel classLabel, double[][] confidence) {
		Double[] ruleWeightBuf = new Double[confidence.length];
		for(int c = 0; c < confidence.length; c++) {
			if(classLabel.getClassVector()[c] == -1) {
				ruleWeightBuf[c] = 0.0;
			}
			else {
				ruleWeightBuf[c] = Math.abs(confidence[c][0] - confidence[c][1]);
			}
		}
		RuleWeightVector ruleWeightVector = new RuleWeightVector(ruleWeightBuf);
		return ruleWeightVector;
	}

	public static MultiLabel_MoFGBML_Learning.MultiLabel_MoFGBML_LearningBuilder builder() {
		return new MultiLabel_MoFGBML_LearningBuilder();
	}

	public static class MultiLabel_MoFGBML_LearningBuilder extends MoFGBML_LearningBuilder {
		/**
		 * @param train : DataSet
		 */
		@Override
		public MultiLabel_MoFGBML_Learning build() {
			return new MultiLabel_MoFGBML_Learning(train);
		}
	}
}
