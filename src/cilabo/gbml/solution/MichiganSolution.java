package cilabo.gbml.solution;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.uma.jmetal.solution.integersolution.IntegerSolution;
import org.uma.jmetal.solution.integersolution.impl.DefaultIntegerSolution;

import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.Rule;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.ConsequentFactory;

public class MichiganSolution extends DefaultIntegerSolution implements IntegerSolution {
	// ************************************************************
	// Fields
	protected Rule rule;

	// ************************************************************
	// Constructor
	public MichiganSolution(List<Pair<Integer, Integer>> bounds,
							int numberOfObjectives,
							int numberOfConstraints,
							Antecedent antecedent,
							Consequent consequent)
	{
		super(bounds, numberOfObjectives, numberOfConstraints);
		// antecedent to variables
		for(int i = 0; i < antecedent.getDimension(); i++) {
			this.setVariable(i, antecedent.getAntecedentIndexAt(i));
		}
		this.rule = Rule.builder()
						.antecedent(antecedent)
						.consequent(consequent)
						.build();
	}

	public MichiganSolution(List<Pair<Integer, Integer>> bounds,
							int numberOfObjectives,
							int numberOfConstraints,
							Rule rule) {
		super(bounds, numberOfObjectives, numberOfConstraints);
		// antecedent to variables
		for(int i = 0; i < rule.getAntecedent().getDimension(); i++) {
			this.setVariable(i, rule.getAntecedent().getAntecedentIndexAt(i));
		}
		this.rule = rule.deepcopy();
	}

	public MichiganSolution(MichiganSolution solution) {
		super(solution);
		this.rule = solution.getRule().deepcopy();
	}

	// ************************************************************
	// Methods

	/**
	 *
	 */
	@Deprecated
	public Antecedent getAntecedentFromVariables(Knowledge knowledge) {
		int[] antecedentIndex = new int[this.getNumberOfVariables()];
		for(int i = 0; i < antecedentIndex.length; i++) {
			antecedentIndex[i] = this.getVariable(i);
		}
		Antecedent antecedent = Antecedent.builder()
								.antecedentIndex(antecedentIndex)
								.build();
		return antecedent;
	}

	/**
	 *
	 */
	public void learning(Knowledge knowledge, ConsequentFactory consequentFactory) {
		// variables to antecedent
		int[] antecedentIndex = new int[this.getNumberOfVariables()];
		for(int i = 0; i < antecedentIndex.length; i++) {
			antecedentIndex[i] = this.getVariable(i);
		}
		Antecedent antecedent = Antecedent.builder()
								.antecedentIndex(antecedentIndex)
								.build();

		// Learning consequent
		Consequent consequent = consequentFactory.learning(antecedent);

		this.rule = Rule.builder()
				.antecedent(antecedent)
				.consequent(consequent)
				.build();
	}



	/**
	 *
	 */
	public Rule getRule() {
		return this.rule.deepcopy();
	}

	public List<Pair<Integer, Integer>> getBounds() {
		return this.bounds;
	}

	@Override
	/**
	 * setVariable(int index, Integer value, Knowledge knowledge)に移行予定
	 */
	public void setVariable(int index, Integer value) {
		// TODO 自動生成されたメソッド・スタブ
//		System.out.println("using a wrong \"setVariable\" function.");
		if(this.rule != null) {
			this.rule.getAntecedent().setAntecedentIndexAt(index, value);
			this.rule.getAntecedent().setAntecedentFuzzySets(index, value, Knowledge.getInstace());
		}
		super.setVariable(index, value);
	}

	/**
	 *
	 */
	@Override
	public MichiganSolution copy() {
		return new MichiganSolution(this);
	}

}
