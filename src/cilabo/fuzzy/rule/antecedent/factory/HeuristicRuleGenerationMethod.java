package cilabo.fuzzy.rule.antecedent.factory;

import java.util.Arrays;

import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.AntecedentFactory;
import cilabo.main.Consts;
import cilabo.utility.Random;

/**ヒューリスティックに基づいた前件部生成
 * @author hirot
 *
 */
public class HeuristicRuleGenerationMethod implements AntecedentFactory {

	/**  学習用データ*/
	DataSet train;
	/** Internal parameter */
	int[] antecedents;
	/** 選択時の学習に用いるパターンのidリスト*/
	Integer[] samplingIndex;

	/** Internal parameter head番目の前件部を生成する*/
	int head = 0;

	/**コンストラクタ
	 * @param train 学習用データ
	 * @param samplingIndex 選択時の学習に用いるパターンのidリスト
	 */
	public HeuristicRuleGenerationMethod(DataSet train, Integer[] samplingIndex) {
		this.train = train;
		this.samplingIndex = samplingIndex;
	}

	/** 前件部のファジィ集合のidを決定する
	 * @param head 生成する前件部の番目
	 * @return 決定された前件部のFuzzyTermTypeのidリスト
	 */
	private int[] selectAntecedentPart(int head) {
		int dimension = train.getNdim();
		Pattern pattern = train.getPattern(samplingIndex[head]);
		double[] vector = pattern.getInputVector().getVector();

		double dcRate;
		if(Consts.IS_PROBABILITY_DONT_CARE) {
			dcRate = Consts.DONT_CARE_RT;
		}
		else {
			// (Ndim - const) / Ndim
			dcRate = (double)(((double)dimension - (double)Consts.ANTECEDENT_LEN)/(double)dimension);
		}

		int[] antecedentIndex = new int[dimension];
		for(int n = 0; n < dimension; n++) {
			// don't care
			if(Random.getInstance().getGEN().nextDouble() < dcRate) {
				antecedentIndex[n] = 0;
				continue;
			}

			// Categorical Judge
			if(vector[n] < 0) {
				antecedentIndex[n] = (int)vector[n];
				continue;
			}

			// Numerical
			int fuzzySetNum = Knowledge.getInstance().getFuzzySetNum(n)-1;
			double[] membershipValueRoulette = new double[fuzzySetNum];
			double sumMembershipValue = 0;
			membershipValueRoulette[0] = 0;
			for(int h = 0; h < fuzzySetNum; h++) {
				sumMembershipValue += Knowledge.getInstance().getMembershipValue(vector[n], n, h+1);
				membershipValueRoulette[h] = sumMembershipValue;
			}

			double arrow = Random.getInstance().getGEN().nextDouble() * sumMembershipValue;
			for(int h = 0; h < fuzzySetNum; h++) {
				if(arrow < membershipValueRoulette[h]) {
					antecedentIndex[n] = h+1;
					break;
				}
			}
		}
		return antecedentIndex;
	}

	@Override
	public Antecedent create() {
		if(head >= samplingIndex.length) return null;


		int[] antecedentIndex = selectAntecedentPart(head);
		head++;

		return Antecedent.builder()
				.antecedentIndex(antecedentIndex)
				.build();
	}

	public void setSamplingIndex(Integer[] samplingIndex) {
		this.samplingIndex = Arrays.copyOf(samplingIndex, samplingIndex.length);
		this.head = 0;
	}


	/** ヒューリスティックに基づいた前件部のcreatorを生成
	 * @return 生成されたcreator(factory design)
	 */
	public static HeuristicRuleGenerationMethod.HeuristicRuleGenerationMethodBuilder builder() {
		return new HeuristicRuleGenerationMethodBuilder();
	}

	/**ヒューリスティックに基づいた前件部のcreator
	 * @author hirot
	 *
	 */
	public static class HeuristicRuleGenerationMethodBuilder {
		private DataSet train;
		private Integer[] samplingIndex;

		HeuristicRuleGenerationMethodBuilder() {}

		/**生成する前件部で使用するパターンのリストを設計図に書き込む
		 * @param samplingIndex
		 * @return 記入済み設計図
		 */
		public HeuristicRuleGenerationMethod.HeuristicRuleGenerationMethodBuilder samplingIndex(Integer[] samplingIndex) {
			this.samplingIndex = samplingIndex;
			return this;
		}

		/** 生成する前件部の選択時に使用する学習用データを設計図に書き込む
		 * @param train
		 * @return 記入済み設計図
		 */
		public HeuristicRuleGenerationMethod.HeuristicRuleGenerationMethodBuilder train(DataSet train) {
			this.train = train;
			return this;
		}

		/** 設計図を基にproductを生成する.インスタンス自体はcreate()で生成．
		 * @param train : DataSet
		 * @param samplingIndex : Integer[]
		 * @see cilabo.fuzzy.rule.antecedent.factory.HeuristicRuleGenerationMethod
		 */
		public HeuristicRuleGenerationMethod build() {
			return new HeuristicRuleGenerationMethod(train, samplingIndex);
		}
	}
}
