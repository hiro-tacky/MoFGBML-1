package cilabo.fuzzy.rule.consequent.ruleWeight;

public interface RuleWeight {

	/**
	 * このインスタンスのディープコピーを取得する
	 * @return ディープコピーされたインスタンス
	 */
	public RuleWeight deepcopy();

	/**
	 * 入力された RuleWeight インスタンスが同値か返す
	 * @param x 比較したい RuleWeight インスタンス
	 * @return 同値である場合:true 同値でない場合:false
	 */
	public boolean equals(RuleWeight x);
}
