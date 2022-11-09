package cilabo.fuzzy.classifier;

import java.util.ArrayList;

import org.w3c.dom.Element;

import cilabo.data.InputVector;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.rule.Rule;
import xml.XML_manager;

/**ルールベース識別器
 * @author hirot
 *
 */
public class RuleBasedClassifier implements Classifier {

	/**  ルール集合*/
	ArrayList<Rule> ruleSet = new ArrayList<>();

	/** 識別方式
	 * @see cilabo.fuzzy.classifier.operator.classification
	 *  */
	Classification classification;

	/** 空のインスタンスを生成します <br> Constructs an empty instance of class */
	public RuleBasedClassifier() {}

	/**  Copy constructor */
	public RuleBasedClassifier(RuleBasedClassifier classifier) {
		ruleSet = new ArrayList<>(classifier.getRuleNum());
		for(int i = 0; i < ruleSet.size(); i++) {
			ruleSet.add(i, classifier.getRule(i).deepcopy());
		}
		classification = classifier.getClassification();
	}

	/* 識別を行う
	 * @see cilabo.fuzzy.classifier.Classifier#classify(cilabo.data.InputVector)
	 */
	@Override
	public Rule classify(InputVector vector) {
		return (Rule)this.classification.classify(this, vector);
	}

	/** ルール数を取得する */
	public int getRuleNum() {
		return this.ruleSet.size();
	}

	/**	ルール長を取得する */
	public int getRuleLength() {
		int length = 0;
		for(int i = 0; i < ruleSet.size(); i++) {
			length += ruleSet.get(i).getAntecedent().getRuleLength();
		}
		return length;
	}

	/** ルールを追加する
	 * @param rule 追加ルール
	 */
	public void addRule(Rule rule) {
		this.ruleSet.add(rule);
	}

	/**  id指定でルールを取得<br>Returns the element at the specified position in this list.
	 * @param index id
	 * @return ルール
	 */
	public Rule getRule(int index) {
		return this.ruleSet.get(index);
	}

	/** ルール集合を取得
	 * @return ルール集合
	 */
	public ArrayList<Rule> getRuleSet() {
		return this.ruleSet;
	}


	/** 指定したルールを削除
	 * @param index 指定するid
	 * @return 削除されたルール
	 */
	public Rule popRule(int index) {
		return this.ruleSet.remove(index);
	}


	/**識別方法を設定
	 * @param classification 識別方法
	 */
	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	/**識別方式を取得
	 * @return 識別方式
	 */
	public Classification getClassification() {
		return this.classification;
	}

	public RuleBasedClassifier copy() {
		return new RuleBasedClassifier(this);
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		String ln = System.lineSeparator();
		String str = "@classification: " + classification.toString() + ln;
		for(int i = 0; i < ruleSet.size(); i++) {
			str += ruleSet.get(i).toString() + ln;
		}
		return str;
	}

	public Element ClassifierToElemnt() {
		XML_manager xml_manager = XML_manager.getInstance();
		Element individualElement = XML_manager.createElement(xml_manager.classifierName);
		for(Rule rule: this.ruleSet) {
			Element singleRule = XML_manager.addChildNode(individualElement, xml_manager.ruleName);
			//前件部
			Element antecedent = XML_manager.addChildNode(singleRule, xml_manager.antecedentName);
			int[] AntecedentIndex = rule.getAntecedent().getAntecedentIndex();
			for(int i=0; i<AntecedentIndex.length; i++) {
				XML_manager.addChildNode(antecedent, xml_manager.fuzzyTermIDName, String.valueOf(AntecedentIndex[i]),
						xml_manager.dimentionIDName, String.valueOf(i));
			}

			//後件部
			Element consequent = XML_manager.addChildNode(singleRule, xml_manager.consequentName);

			//結論部クラス
			Element consequentClass = XML_manager.addChildNode(consequent, xml_manager.consequentClassVectorName);
			Integer[] classLabelVector = rule.getConsequent().getClassLabel().getClassVector();
			for(int i=0; i<classLabelVector.length; i++) {
				XML_manager.addChildNode(consequentClass, xml_manager.consequentClassName, String.valueOf(classLabelVector[i]),
						xml_manager.consequentClassIndexName, String.valueOf(i));
			}

			//ルール重み
			Element ruleWeight = XML_manager.addChildNode(consequent, xml_manager.ruleWeightVectorName);
			Double[] ruleWeightVector = rule.getConsequent().getRuleWeight().getRuleWeightVector();
			for(int i=0; i<ruleWeightVector.length; i++) {
				XML_manager.addChildNode(ruleWeight, xml_manager.ruleWeightName, String.valueOf(ruleWeightVector[i]),
						xml_manager.ruleWeightIndexName, String.valueOf(i));
			}

		}
		return individualElement;
	}

}
