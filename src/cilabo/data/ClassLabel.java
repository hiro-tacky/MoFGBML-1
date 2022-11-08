package cilabo.data;

import java.util.ArrayList;

import utility.Methods;

/** クラスラベルを持つクラス
 * @author hirot
 */
public class ClassLabel {
	/** クラスラベルのArrayList */
	ArrayList<Integer> classLabel = new ArrayList<>();

	/**空のインスタンスを生成します <br> Constructs an empty instance of class */
	public ClassLabel() {}

	/** deepcopy用
	 * @param classLabel コピー元のclassLabel
	 */
	private ClassLabel(ArrayList<Integer> classLabel) {
		this.classLabel.addAll(classLabel);
	}


	/** deepcopy
	 * @return このインスタンスのクローン
	 */
	public ClassLabel deepcopy() {
		return new ClassLabel(this.classLabel);
	}

	/** 単一のクラスラベルを取得
	 * @return クラスラベルのArrayListの先頭要素
	 */
	public Integer getClassLabel() {
		Methods.nullCheck(this.classLabel, "classLabel is null");
		return this.classLabel.get(0);
	}

	/** 単一のクラスラベルのArrayListを取得
	 * @return クラスラベルのArrayList
	 */
	public Integer[] getClassVector() {
		Methods.nullCheck(this.classLabel, "classLabel is null");
		return this.classLabel.toArray(new Integer[0]);
	}

	/**
	 * @param classLabel
	 */
	public void addClassLabels(Integer[] classLabel) {
		for(int i = 0; i < classLabel.length; i++) {
			this.classLabel.add(classLabel[i]);
		}
	}

	/**
	 *
	 */
	public void addClassLabel(Integer classLabel) {
		this.classLabel.add(classLabel);
	}

	@Override
	public String toString() {
		if(this.classLabel.size() == 0) {
			return null;
		}

		String str = String.valueOf(classLabel.get(0));
		if(this.classLabel.size() > 1) {
			for(int i = 1; i < this.classLabel.size(); i++) {
				str += ", " + this.classLabel.get(i);
			}
		}
		return str;
	}

}
