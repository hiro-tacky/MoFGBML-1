package cilabo.fuzzy.classifier.operator.classification.impl;

import java.util.List;

import cilabo.data.InputVector;
import cilabo.fuzzy.classifier.Classifier;
import cilabo.fuzzy.classifier.impl.RuleBasedClassifier;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.rule.RejectedRule;
import cilabo.fuzzy.rule.Rule;

/**
 * 未実装
 *
 * 一時的にSingleWinnerRuleSelectionの内容をコピーしています．(2022/11/15)<br>
 * (実装完了時は上のコメントを削除してください)
 * @author hirot
 *
 */
@Deprecated
public final class CFmeanClassification implements Classification {

	@Override
	public Rule classify(Classifier classifier, InputVector vector) {
		if(!(classifier instanceof RuleBasedClassifier)) {
//			return null;
			System.err.println("Parameter's instance must be instance of RuleBasedClassifier.");
		}

		List<Rule> ruleSet = ((RuleBasedClassifier)classifier).getRuleSet();

		boolean canClassify = true; //識別可能か
		double max = -Double.MAX_VALUE; //適用度最大値
		int winner = 0;
		for(int q = 0; q < ruleSet.size(); q++) {
			Rule rule = ruleSet.get(q);
			double membership = rule.getAntecedent().getCompatibleGrade(vector.getVector()); //メンバシップ値
			double CF = rule.getConsequent().getRuleWeight().getRuleWeight(); //ルール重み

			double value = membership * CF;
			//適用度最大値更新
			if(value > max) {
				max = value;
				winner = q;
				canClassify = true;
			}
			//適用度最大値が同値を取る場合
			else if(value == max) {
				Rule winnerRule = ruleSet.get(winner);
				// "membership*CF"が同値 かつ 結論部クラスが異なる
				if(!rule.getConsequent().getClassLabel().toString().equals(winnerRule.getConsequent().getClassLabel().toString())) {
					canClassify = false;
				}
			}
		}

		if(canClassify && max > 0) {
			return ruleSet.get(winner);
		}
		else {
			return RejectedRule.getInstance();
		}
	}
}