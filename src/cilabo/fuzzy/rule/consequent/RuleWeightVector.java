package cilabo.fuzzy.rule.consequent;

import java.util.ArrayList;

import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;

public class RuleWeightVector extends SingleRuleWeight {
	// ************************************************************
	// Fields
	Double CFmean = null;

	public RuleWeightVector(Double ruleWeight) {
		super(ruleWeight);
	}

	public RuleWeightVector(Double[] ruleWeight) {
		super(ruleWeight);
	}

	private RuleWeightVector(ArrayList<Double> ruleWeight, Double CFmean) {
		super(ruleWeight);
		this.CFmean = CFmean;
	}

	// ************************************************************
	// Methods

	/**
	 *
	 */
	@Override
	public RuleWeightVector deepcopy() {
		return new RuleWeightVector(this.ruleWeight, this.CFmean);
	}

	/**
	 * @return CFmean : Average of rule weight vector
	 */
	@Override
	public Double getRuleWeight() {
		if(this.CFmean == null) {
			this.CFmean = 0.0;
			double length = (double)this.ruleWeight.size();
			for(int i = 0; i < length; i++) {
				this.CFmean += this.ruleWeight.get(i);
			}
			this.CFmean /= length;
		}

		return this.CFmean;
	}

	/**
	 * @param index
	 */
	public Double getRuleWeightForClass(int index) {
		return this.ruleWeight.get(index);
	}

	@Override
	protected void addRuleWeight(Double weight) {
		this.CFmean = null;
		this.ruleWeight.add(weight);
	}

	/**
	 *
	 */
	@Override
	protected void addRuleWeightVector(Double[] weight) {
		this.CFmean = null;
		for(int i = 0; i < weight.length; i++) {
			this.ruleWeight.add(weight[i]);
		}
	}


}
