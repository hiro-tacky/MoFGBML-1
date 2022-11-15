package cilabo.fuzzy.rule.consequent.ruleWeight.impl;

import java.util.Objects;

import cilabo.fuzzy.rule.consequent.ruleWeight.RuleWeight;

/**
 * 単一のルール重み用のクラス
 * @author hirot
 */
public class SingleRuleWeight implements RuleWeight{

	/** ルール重み */
	private Double ruleWeight;

	/**
	 * 入力されたルール重みを持つインスタンスを生成する
	 * @param ruleWeight ルール重み
	 */
	public SingleRuleWeight(Double ruleWeight) {
		this.ruleWeight = ruleWeight;
	}


	/**
	 * ルール重みを取得する
	 * @return ルール重み
	 */
	public Double getRuleWeight() {
		if( Objects.isNull(this.ruleWeight) ) {
			throw new NullPointerException();
		}
		return this.ruleWeight;
	}

	/**
	 * ルール重みを代入する
	 * @param ruleWeight ルール重み
	 */
	public void setRuleWeight(Double ruleWeight) {
		this.ruleWeight = ruleWeight;
	}

	@Override
	public SingleRuleWeight deepcopy() {
		return new SingleRuleWeight(this.ruleWeight);
	}

	@Override
	public boolean equals(RuleWeight x) {
		//同じクラスのオブィエクトか調べる
		if(!(x instanceof SingleRuleWeight)) {return false;}
		//クラスラベルの値が同値か調べる
		if(!((SingleRuleWeight) x).getRuleWeight().equals(this.ruleWeight)){return false;}
		return true;
	}

	@Override
	public String toString() {
		if( Objects.isNull(this.ruleWeight) ) {
			throw new NullPointerException();
		}

		return String.format("%2s", this.ruleWeight);
	}
}
