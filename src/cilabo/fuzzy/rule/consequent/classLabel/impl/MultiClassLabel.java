package cilabo.fuzzy.rule.consequent.classLabel.impl;

import java.util.Objects;

import cilabo.fuzzy.rule.consequent.classLabel.ClassLabel;

/**
 * 複数の結論部クラスラベル用のクラス
 * @author hirot
 */
public final class MultiClassLabel implements ClassLabel {

	/** クラスラベルの配列 */
	private Integer[] classLabel;

	/**
	 * 入力されたクラスラベルを持つインスタンスを生成する
	 * @param classLabel classLabelの配列
	 */
	public MultiClassLabel(Integer[] classLabel) {
		this.classLabel = classLabel.clone();
	}


	/** クラスラベルの配列を取得
	 * @return クラスラベルの配列
	 */
	public Integer[] getClassLabels() {
		if( Objects.isNull(this.classLabel) ) {
			throw new NullPointerException();
		}
		return this.classLabel;
	}

	/** 指定したインデックスのクラスラベルを取得
	 * @param index ClassLabelList上のインデックス
	 * @return クラスラベル
	 */
	public Integer getClassLabelAt(int index) {
		if( Objects.isNull(this.classLabel) ) {
			throw new NullPointerException();
		}
		return this.classLabel[index];
	}

	 /** クラスラベルを代入
	 * @param index ClassLabelList上のインデックス
	 * @param value 変更後クラスラベルの値
	 */
	public void setClassLabels(Integer[] value) {
		this.classLabel = value.clone();
	}

	 /** 指定したインデックスにクラスラベルを代入
	 * @param index ClassLabelList上のインデックス
	 * @param value 変更後クラスラベルの値
	 */
	public void setClassLabelAt(int index, Integer value) {
		this.classLabel[index] = value;
	}

	@Override
	public MultiClassLabel deepcopy() {
		return new MultiClassLabel(this.classLabel);
	}

	@Override
	public boolean equals(ClassLabel x) {
		//同じクラスのオブィエクトか調べる
		if(!(x instanceof MultiClassLabel)) {return false;}
		//配列長の検証
		if(((MultiClassLabel) x).getClassLabels().length != this.classLabel.length) {return false;}
		for(int i=0; i<this.classLabel.length; i++) {
			//クラスラベルの値が同値か調べる
			if(!((MultiClassLabel) x).getClassLabels()[i].equals(this.getClassLabels()[i])){return false;}
		}
		return true;
	}

	@Override
	public String toString() {
		//null check
		if( Objects.isNull(this.classLabel) ) {
			throw new NullPointerException();
		}

		String str = String.format("%2s", this.classLabel[0]);
		if(this.classLabel.length < 2) {
			return str;
		}else {
			for(int i=1; i<this.classLabel.length; i++) {
				str += String.format(", %2s", this.classLabel[i]);
			}
			return str;
		}
	}

}
