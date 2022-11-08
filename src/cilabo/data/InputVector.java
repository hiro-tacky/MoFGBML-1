package cilabo.data;

import java.util.Arrays;

/**属性値クラス
 * @author hirot
 *
 */
public class InputVector {

	double[] inputVector; // 属性値配列

	/**  コンストラクタ <br> Constructs an instance of class
	 * @param inputVector 属性値配列
	 */
	public InputVector(double[] inputVector) {
		this.inputVector = Arrays.copyOf(inputVector, inputVector.length);
	}

	/** 指定したidの属性値を取得<br>Returns the element at the specified position in this list.
	 * @param index id
	 * @return 指定したidの属性値
	 */
	public double getDimValue(int index) {
		return this.inputVector[index];
	}

	/** 属性値配列を取得
	 * @return 属性値配列
	 */
	public double[] getVector() {
		return this.inputVector;
	}

	@Override
	public String toString() {
		if(this.inputVector == null) {
			return null;
		}
		String str = String.valueOf(this.inputVector[0]);
		for(int i = 1; i < this.inputVector.length; i++) {
			str += ", " + this.inputVector[i];
		}
		return str;
	}

}
