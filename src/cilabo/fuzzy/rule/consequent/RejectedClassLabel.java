package cilabo.fuzzy.rule.consequent;

import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;

public class RejectedClassLabel extends SingleClassLabel {

	public RejectedClassLabel(Integer classLabel) {
		super(classLabel);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	private static RejectedClassLabel instance;

	public static RejectedClassLabel getInstance() {
		if(instance == null) {
			instance = new RejectedClassLabel(null);
		}
		return instance;
	}

	@Override
	public String toString() {
		return "rejected";
	}

}
