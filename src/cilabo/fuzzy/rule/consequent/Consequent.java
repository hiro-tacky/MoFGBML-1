package cilabo.fuzzy.rule.consequent;

import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;

public class Consequent {
	// ************************************************************
	// Fields

	/**  */
	SingleClassLabel consequentClass;

	/**  */
	SingleRuleWeight ruleWeight;

	// ************************************************************
	// Constructor

	/**
	 * Shallow Copy
	 * @param C
	 * @param weight
	 */
	public Consequent(SingleClassLabel C, SingleRuleWeight weight) {
		this.consequentClass = C;
		this.ruleWeight = weight;
	}

	// ************************************************************
	// Methods

	/**
	 *
	 */
	public Consequent deepcopy() {
		return new Consequent(this.consequentClass.deepcopy(), this.ruleWeight.deepcopy());
	}

	/**
	 *
	 */
	public SingleClassLabel getClassLabel() {
		return this.consequentClass;
	}

	/**
	 *
	 */
	public SingleRuleWeight getRuleWeight() {
		return this.ruleWeight;
	}

	@Override
	public String toString() {
		String str = "";

		// ClassLabel
		str += "class:[";
		str += this.consequentClass.toString();
		str += "]";

		str += " ";

		// RuleWeight
		str += "weight:[";
		str += this.ruleWeight.toString();
		str += "]";

		return str;
	}

	/**
	 *
	 */
	public static ConsequentBuilder builder() {
		return new ConsequentBuilder();
	}

	public static class ConsequentBuilder {
		private SingleClassLabel consequentClass;
		private SingleRuleWeight ruleWeight;

		ConsequentBuilder() {}

		public Consequent.ConsequentBuilder consequentClass(SingleClassLabel consequentClass) {
			this.consequentClass = consequentClass;
			return this;
		}

		public Consequent.ConsequentBuilder ruleWeight(SingleRuleWeight ruleWeight) {
			this.ruleWeight = ruleWeight;
			return this;
		}

		/**
		 * @param consequentClass : ClassLabel
		 * @param RuleWeight : RuleWeight
		 */
		public Consequent build() {
			return new Consequent(consequentClass, ruleWeight);
		}
	}
}
