package cilabo.fuzzy.rule.consequent.ruleWeight.impl;

import java.util.Objects;

import cilabo.fuzzy.rule.consequent.ruleWeight.RuleWeight;

/**
 * 複数のルール重み用のクラス
 * @author hirot
 */
public class MultiRuleWeight implements RuleWeight{

	/**	ルール重みの配列 */
	private Double[] ruleWeights;

	/**
	 * 入力されたルール重みを持つインスタンスを生成する
	 * @param ruleWeight ルール重み
	 */
	public MultiRuleWeight(Double[] ruleWeight) {
		this.ruleWeights = ruleWeight.clone();
	}

	/**ルール重みの配列を取得する
	 * @return ルール重みの配列
	 */
	public Double[] getRuleWeights() {
		return ruleWeights;
	}

	/**ルール重みの配列を代入する
	 * @param ruleWeight
	 */
	public void setRuleWeight(Double[] ruleWeight) {
		this.ruleWeights = ruleWeight;
	}

	/**指定したインデックスのルール重みを取得
	 * @param index ruleWeights上のインデックス
	 * @return ルール重み
	 */
	public Double getRuleWeightsAt(int index) {
		return this.ruleWeights[index];
	}

	/**指定したインデックスにルール重みを取得
	 * @param index ruleWeights上のインデックス
	 * @param ruleWeight 代入するruleWeights
	 */
	public void setRuleWeight(int index, Double ruleWeight) {
		this.ruleWeights[index] = ruleWeight;
	}

	@Override
	public MultiRuleWeight deepcopy() {
		return new MultiRuleWeight(this.ruleWeights);
	}

	@Override
	public boolean equals(RuleWeight x) {
		//同じクラスのオブィエクトか調べる
		if(!(x instanceof MultiRuleWeight)) {return false;}
		//配列長の検証
		if(((MultiRuleWeight) x).getRuleWeights().length != this.ruleWeights.length) {return false;}
		for(int i=0; i<this.ruleWeights.length; i++) {
			//クラスラベルの値が同値か調べる
			if(!((MultiRuleWeight) x).getRuleWeights()[i].equals(this.ruleWeights[i])){return false;}
		}
		return true;
	}

	@Override
	public String toString() {
		if( Objects.isNull(this.ruleWeights) ) {
			throw new NullPointerException();
		}


		String str = String.format("%2s", this.ruleWeights[0]);
		if(this.ruleWeights.length < 2) {
			return str;
		}else {
			for(int i=1; i<this.ruleWeights.length; i++) {
				str += String.format(", %2s", this.ruleWeights[i]);
			}
			return str;
		}
	}
}
