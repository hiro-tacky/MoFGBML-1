package cilabo.data;


public class Pattern {
	// ************************************************************
	// Fields

	/** Identification number */
	final int id;

	/** 属性値 */
	final private InputVector inputVector;

	/** 結論部クラス*/
	final private ClassLabel trueClass;

	/** コンストラクタ <br> Constructs an instance of class
	 * @param id パターンのID
	 * @param inputVector 属性値クラス
	 * @param trueClass 結論部クラス
	 */
	public Pattern(int id, InputVector inputVector, ClassLabel trueClass) {
		this.id = id;
		this.inputVector = inputVector;
		this.trueClass = trueClass;
	}

	/** 指定したidの属性値を取得<br>Returns the element at the specified position in this list.
	 * @param index id
	 * @return 指定したidの属性値
	 */
	public double getDimValue(int index) {
		return this.inputVector.getDimValue(index);
	}

	/** idを取得
	 * @return id
	 */
	public int getID() {
		return this.id;
	}

	/** 属性値クラスを取得
	 * @return 属性値クラス
	 */
	public InputVector getInputVector() {
		return this.inputVector;
	}

	/** 結論部クラスを取得
	 * @return 結論部クラス
	 */
	public ClassLabel getTrueClass() {
		return this.trueClass;
	}

	@Override
	public String toString() {
		if(this.inputVector == null || this.trueClass == null) {
			return null;
		}

		String str = "id:" + String.valueOf(this.id);
		str += "," + "input:[" + this.inputVector.toString() + "]";
		str += "," + "class:[" + this.trueClass.toString() + "]";
		return str;
	}


	/** patternインスタンスをFactory Methodを作成
	 * @return Factory Method
	 */
	public static PatternBuilder builder() {
		return new PatternBuilder();
	}

	/**
	 * patternインスタンスのFactory Method
	 * @author hirot
	 *
	 */
	public static class PatternBuilder {
		private int id = -1;
		private InputVector inputVector;
		private ClassLabel trueClass;

		/**  空のインスタンスを生成します <br> Constructs an empty instance of class*/
		PatternBuilder() {}

		public Pattern.PatternBuilder id(int id) {
			this.id = id;
			return this;
		}

		/** patternインスタンスの属性値クラスを設計図に書き込む
		 * @param inputVector 属性値クラス
		 * @return 記入済みpatternインスタンスのFactory Method
		 */
		public Pattern.PatternBuilder inputVector(InputVector inputVector) {
			this.inputVector = inputVector;
			return this;
		}

		/** patternインスタンスの結論部クラスを設計図に書き込む
		 * @param trueClass 結論部クラス
		 * @return 記入済みpatternインスタンスのFactory Method
		 */
		public Pattern.PatternBuilder trueClass(ClassLabel trueClass) {
			this.trueClass = trueClass;
			return this;
		}

		/**設計図を基にpatternインスタンスを生成
		 * @return 生成されたpatternインスタンス
		 */
		public Pattern build() {
			return new Pattern(id, inputVector, trueClass);
		}
	}


}
