package cilabo.fuzzy.rule.antecedent;

import java.util.Arrays;

import cilabo.fuzzy.knowledge.Knowledge;
import jfml.term.FuzzyTermType;

/**前件部クラス
 * @author hirot
 *
 */
/**
 * @author hirot
 *
 */
public class Antecedent {
	/**ファジィセットのインデックス */
	protected int[] antecedentIndex;

	/** ファジィセット */
	protected FuzzyTermType[] antecedentFuzzySets;

	/**
	 * private constructor for deepcopy method
	 * @param antecedentIndex
	 * @param antecedentFuzzySets
	 */
	private Antecedent(int[] antecedentIndex, FuzzyTermType[] antecedentFuzzySets) {
		this.antecedentIndex = antecedentIndex;
		this.antecedentFuzzySets = antecedentFuzzySets;
	}

	/**コンストラクタ(Shallow copy)
	 * @param antecedentIndex : Shallow copy
	 */
	public Antecedent(int[] antecedentIndex) {
		this.antecedentIndex = antecedentIndex;
		this.antecedentFuzzySets = new FuzzyTermType[antecedentIndex.length];
		for(int i = 0; i < antecedentIndex.length; i++) {
			if(antecedentIndex[i] < 0) {
				// Categorical
				antecedentFuzzySets[i] = null;
			}
			else {
				// Numerical
				antecedentFuzzySets[i] = Knowledge.getInstance().getFuzzySet(i, antecedentIndex[i]);
			}
		}
	}

	/** deepcopy */
	public Antecedent deepcopy() {
		int[] antecedentIndex = Arrays.copyOf(this.antecedentIndex, this.antecedentIndex.length);
		return new Antecedent(antecedentIndex, this.antecedentFuzzySets.clone());
	}

	/** 適用度を算出する
	 * @param x 入力パターンの属性値
	 * @return 適用度
	 */
	public double getCompatibleGrade(double[] x) {
		double grade = 1;
		for(int i = 0; i < x.length; i++) {
			if(antecedentIndex[i] < 0 && x[i] < 0) {
				// categorical
				if(antecedentIndex[i] == (int)x[i]) grade *= 1.0;
				else grade *= 0.0;
			}
			else if(antecedentIndex[i] >= 0 && x[i] >= 0){
				// numerical
				grade *= antecedentFuzzySets[i].getMembershipValue((float)x[i]);
			}else{
				grade *= 1.0;
			}
		}
		return grade;
	}

	/**
	 *
	 */
	public int getDimension() {
		return this.antecedentIndex.length;
	}

	/**
	 *
	 */
	public int getAntecedentIndexAt(int dimension) {
		return this.antecedentIndex[dimension];
	}


	/**
	 *
	 */
	public int[] getAntecedentIndex() {
		return this.antecedentIndex;
	}

	public FuzzyTermType getAntecedentFuzzySetAt(int index) {
		return this.antecedentFuzzySets[index];
	}

	public FuzzyTermType[] getAntecedentFuzzySets() {
		return this.antecedentFuzzySets;
	}


	/** ルール長を算出．但しDon't careは含まない
	 * @return ルール長
	 */
	public int getRuleLength() {
		int length = 0;
		for(int i = 0; i < antecedentIndex.length; i++) {
			if(antecedentIndex[i] != 0) {
				length++;
			}
		}
		return length;
	}

	public void setAntecedentFuzzySets(int dimension, FuzzyTermType antecedentFuzzySet) {
		this.antecedentFuzzySets[dimension] = antecedentFuzzySet;
	}

	public void setAntecedentFuzzySets(int dimension, int antecedentIndex, Knowledge knowledge) {
		if(antecedentIndex < 0) {
			// Categorical
			this.antecedentFuzzySets[dimension] = null;
		}
		else {
			// Numerical
			this.antecedentFuzzySets[dimension] = knowledge.getFuzzySet(dimension, antecedentIndex);
		}
	}

	public void setAntecedentIndexAt(int dimension, int antecedentIndex) {
		this.antecedentIndex[dimension] = antecedentIndex;
	}

	@Override
	public String toString() {
		if(antecedentFuzzySets == null) return null;

		String str = antecedentFuzzySets[0].getName();
		for(int i = 1; i < antecedentFuzzySets.length; i++) {
			str += ", " + antecedentFuzzySets[i].getName();
		}
		return str;
	}


	public static AntecedentBuilder builder() {
		return new AntecedentBuilder();
	}

	public static class AntecedentBuilder {
		private int[] antecedentIndex;

		AntecedentBuilder() {}

		public Antecedent.AntecedentBuilder antecedentIndex(int[] antecedentIndex) {
			this.antecedentIndex = antecedentIndex;
			return this;
		}

		/**
		 * @param knowledge : Knowledge
		 * @param antecedentIndex : int[]
		 */
		public Antecedent build() {
			return new Antecedent(antecedentIndex);
		}
	}

}
