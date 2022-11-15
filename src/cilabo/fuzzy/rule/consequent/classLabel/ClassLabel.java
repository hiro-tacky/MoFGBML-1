package cilabo.fuzzy.rule.consequent.classLabel;

public interface ClassLabel {

	/**
	 * このインスタンスのディープコピーを取得する
	 * @return ディープコピーされたインスタンス
	 */
	public ClassLabel deepcopy();

	/**
	 * 入力された ClassLabel インスタンスが同値か返す
	 * @param x 比較したい ClassLabel インスタンス
	 * @return 同値である場合:true 同値でない場合:false
	 */
	public boolean equals(ClassLabel x);
}
