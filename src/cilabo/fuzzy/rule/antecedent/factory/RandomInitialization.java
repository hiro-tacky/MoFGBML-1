package cilabo.fuzzy.rule.antecedent.factory;

import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.AntecedentFactory;
import cilabo.main.Consts;
import random.MersenneTwisterFast;

public class RandomInitialization implements AntecedentFactory {
	/**	乱数生成器 */
	MersenneTwisterFast uniqueRnd;

	/** 学習用データ */
	DataSet train;


	// ************************************************************
	// Constructor
	public RandomInitialization(int seed, DataSet train) {
		this.uniqueRnd = new MersenneTwisterFast(seed);
		this.train = train;
	}

	// ************************************************************
	// Methods

	@Override
	public Antecedent create() {
		int dimension = Knowledge.getInstace().getDimension();
		double dcRate;
		if(Consts.IS_PROBABILITY_DONT_CARE) {
			// Constant Value
			dcRate = Consts.DONT_CARE_RT;
		}
		else {
			// (dimension - Const) / dimension
			dcRate = (double)(((double)dimension - (double)Consts.ANTECEDENT_LEN)/(double)dimension);
		}

		int[] antecedentIndex = new int[dimension];

		Pattern randomPattern = train.getPattern(uniqueRnd.nextInt(train.getDataSize()));

		for(int n = 0; n < dimension; n++) {
			if(uniqueRnd.nextDoubleIE() < dcRate) {
				// don't care
				antecedentIndex[n] = 0;
			}
			else {
				// Judge which dimension n is categorical or numerical.
				if(randomPattern.getDimValue(n) < 0) {
					// Categorical
					antecedentIndex[n] = (int)randomPattern.getDimValue(n);
				}
				else {
					// Numerical
					antecedentIndex[n] = uniqueRnd.nextInt(Knowledge.getInstace().getFuzzySetNum(n));
				}
			}
		}

		return Antecedent.builder()
						.antecedentIndex(antecedentIndex)
						.build();
	}

	public static RandomInitialization.RandomInitializationBuilder builder(){
		return new RandomInitializationBuilder();
	}

	public static class RandomInitializationBuilder {
		private int seed = -1;
		private DataSet train;

		RandomInitializationBuilder() {}

		public RandomInitialization.RandomInitializationBuilder seed(int seed) {
			this.seed = seed;
			return this;
		}

		public RandomInitialization.RandomInitializationBuilder train(DataSet train) {
			this.train = train;
			return this;
		}

		/**
		 * @param seed : int
		 * @param train : DataSet
		 */
		public RandomInitialization build() {
			return new RandomInitialization(seed, train);
		}
	}

}
