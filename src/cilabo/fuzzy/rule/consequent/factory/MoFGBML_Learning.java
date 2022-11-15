package cilabo.fuzzy.rule.consequent.factory;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import cilabo.data.DataSet;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.ConsequentFactory;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;
import cilabo.utility.Parallel;

/**後件部
 * @author hirot
 *
 */
public class MoFGBML_Learning implements ConsequentFactory {
	/**  */
	DataSet train;

	public MoFGBML_Learning(DataSet train) {
		this.train = train;
	}

	@Override
	public DataSet getTrain() {
		return this.train;
	}

	/** 後件部を算出 */
	@Override
	public Consequent learning(Antecedent antecedent) {
		double[] confidence = this.calcConfidence(antecedent);
		SingleClassLabel classLabel = this.calcClassLabel(confidence, 0.5);
		SingleRuleWeight ruleWeight = this.calcRuleWeight(classLabel, confidence);

		Consequent consequent = Consequent.builder()
								.consequentClass(classLabel)
								.ruleWeight(ruleWeight)
								.build();
		return consequent;
	}

	/**
	 * 各クラスへの信頼度を返す
	 * @return 信頼度
	 */
	public double[] calcConfidence(Antecedent antecedent) {
		int Cnum = train.getCnum();
		double[] confidence = new double[Cnum];

		// 各クラスのパターンに対する適合度の総和
		double[] sumCompatibleGradeForEachClass = new double[Cnum];

		for(int c = 0; c < Cnum; c++) {
			final Integer CLASSNUM = c;
			Optional<Double> partSum = null;
			try {
				partSum = Parallel.getInstance().getLearningForkJoinPool().submit( () ->
					train.getPatterns().parallelStream()
						// 正解クラスが「CLASS == c」のパターンを抽出
						.filter(pattern -> pattern.getTrueClass().getClassLabel() == CLASSNUM)
						// 各パターンの入力ベクトルを抽出
						.map(pattern -> pattern.getInputVector().getVector())
						// 各入力ベクトルとantecedentのcompatible gradeを計算
						.map(x -> antecedent.getCompatibleGrade(x))
						// compatible gradeを総和する
						.reduce( (sum, grade) -> sum+grade)
				).get();
			}
			catch (InterruptedException | ExecutionException e) {
				System.out.println(e);
				return null;
			}

			sumCompatibleGradeForEachClass[c] = partSum.orElse(0.0);
		}

		// 全パターンに対する適合度の総和
		double allSum = Arrays.stream(sumCompatibleGradeForEachClass).sum();
		if(allSum != 0) {
			for(int c = 0; c < Cnum; c++) {
				confidence[c] = sumCompatibleGradeForEachClass[c] / allSum;
			}
		}
		return confidence;
	}

	/**
	 * <h1>結論部クラス</h1></br>
	 * 信頼度から結論部クラスを決定する</br>
	 * confidence[]が最大となるクラスを結論部クラスとする</br>
	 * </br>
	 * もし、同じ値をとるクラスが複数存在する場合、
	 * もしくは最大の信頼度が0.5以下になる場合は生成不可能なルール(-1)とする．</br>
	 * </br>
	 *
	 * @param confidence
	 * @return
	 */
	public SingleClassLabel calcClassLabel(double[] confidence, double limit) {
		double max = Double.MIN_VALUE;
		int consequentClass = -1;

		for(int i = 0; i < confidence.length; i++) {
			if(max < confidence[i]) {
				max = confidence[i];
				consequentClass = i;
			}
			else if(max == confidence[i]) {
				consequentClass = -1;
			}
		}

		if(max <= limit) { consequentClass = -1; }

		SingleClassLabel classLabel = new SingleClassLabel(consequentClass);

		return classLabel;
	}

	/**
	 * ルール重みを算出
	 * @param consequentClass 結論部クラス
	 * @param confidence クラス別の信頼度
	 * @return
	 */
	public SingleRuleWeight calcRuleWeight(SingleClassLabel consequentClass, double[] confidence) {
		// 生成不可能ルール判定
		if(consequentClass.getClassLabel() == -1) {
			SingleRuleWeight zeroWeight = new SingleRuleWeight(0.0);
			return zeroWeight;
		}

		int C = consequentClass.getClassLabel();
		double sumConfidence = Arrays.stream(confidence).sum();
		double CF = confidence[C] - (sumConfidence - confidence[C]);
		SingleRuleWeight ruleWeight = new SingleRuleWeight(CF);
		return ruleWeight;
	}

	public static MoFGBML_Learning.MoFGBML_LearningBuilder builder() {
		return new MoFGBML_LearningBuilder();
	}

	public static class MoFGBML_LearningBuilder {
		protected DataSet train;

		MoFGBML_LearningBuilder() {}

		public MoFGBML_Learning.MoFGBML_LearningBuilder train(DataSet train) {
			this.train = train;
			return this;
		}

		/**
		 * @param train : DataSet
		 */
		public MoFGBML_Learning build() {
			return new MoFGBML_Learning(train);
		}
	}
}
