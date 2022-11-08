package cilabo.fuzzy.classifier.operator.classification;

import cilabo.data.InputVector;
import cilabo.fuzzy.classifier.Classifier;
import cilabo.fuzzy.rule.InterfaceRule;

public interface Classification {

	/**
	 * パターンに対する識別を行う
	 *
	 * @param classifier 識別器
	 * @param vector 入力パターン
	 * @return 勝利ルール
	 */
	public InterfaceRule classify(Classifier classifier, InputVector vector);
}
