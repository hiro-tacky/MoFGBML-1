package cilabo.fuzzy.rule.consequent.classLabel.impl;

import java.util.Objects;

import cilabo.fuzzy.rule.consequent.classLabel.ClassLabel;

/**
 * 単一のクラスラベル用のクラス
 * @author hirot
 */
public final class SingleClassLabel implements ClassLabel {
	/** クラスラベル */
	private Integer classLabel;

	/**
	 * 入力されたクラスラベルを持つインスタンスを生成する
	 * @param classLabel
	 */
	public SingleClassLabel(Integer classLabel) {
		this.classLabel = classLabel;
	}

	@Override
	public SingleClassLabel deepcopy() {
		return new SingleClassLabel(this.classLabel);
	}

	/**
	 * クラスラベルを取得
	 * @return クラスラベルのインデックス
	 */
	public Integer getClassLabel() {
		if( Objects.isNull(this.classLabel) ) {
			throw new NullPointerException();
		}
		return this.classLabel;
	}

	/**
	 * クラスラベルを代入
	 * @return クラスラベルのインデックス
	 */
	public void setClassLabel(Integer classLabel) {
		this.classLabel = classLabel;
	}

	@Override
	public boolean equals(ClassLabel x) {
		//同じクラスのオブィエクトか調べる
		if(!(x instanceof SingleClassLabel)) {return false;}
		//クラスラベルの値が同値か調べる
		if(!((SingleClassLabel) x).getClassLabel().equals(this.getClassLabel())){return false;}
		return true;
	}

	@Override
	public String toString() {
		if( Objects.isNull(this.classLabel) ) {
			throw new NullPointerException();
		}
		String str = String.format("%2s", this.classLabel);
		return str;
	}
}
