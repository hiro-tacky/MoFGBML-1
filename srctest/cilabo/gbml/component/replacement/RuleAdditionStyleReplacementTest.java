package cilabo.gbml.component.replacement;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uma.jmetal.component.replacement.Replacement;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import cilabo.data.DataSet;
import cilabo.fuzzy.classifier.operator.classification.Classification;
import cilabo.fuzzy.classifier.operator.classification.impl.SingleWinnerRuleSelection;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.factory.AllCombinationAntecedentFactory;
import cilabo.fuzzy.rule.consequent.Consequent;
import cilabo.fuzzy.rule.consequent.classLabel.impl.SingleClassLabel;
import cilabo.fuzzy.rule.consequent.ruleWeight.impl.SingleRuleWeight;
import cilabo.gbml.problem.impl.pittsburgh.MOP1;
import cilabo.gbml.solution.MichiganSolution;
import cilabo.gbml.solution.PittsburghSolution;
import cilabo.main.Consts;
import cilabo.utility.Input;
import cilabo.utility.Random;

class RuleAdditionStyleReplacementTest {
	static DataSet train;
	static PittsburghSolution solutionParent;
	static PittsburghSolution solutionOffspring;
	static int ruleNum = 14;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random.getInstance().initRandom(2022);

		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		int dimension = 2;
		float[][] params = HomoTriangle_3.getParams();

		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();

		//Problem
		Classification classification = new SingleWinnerRuleSelection();

		AllCombinationAntecedentFactory antecedentFactory = AllCombinationAntecedentFactory.builder().build();

		SingleRuleWeight ruleWeight = new SingleRuleWeight(0.7);

		List<Integer> lowerBoundsMichigan = new ArrayList<>();
		List<Integer> upperBoundsMichigan = new ArrayList<>();
		for(int i = 0; i < dimension; i++) {
			lowerBoundsMichigan.add(0);
			upperBoundsMichigan.add(params.length);
		}
		List<Pair<Integer, Integer>> michiganBbounds =
		        IntStream.range(0, lowerBoundsMichigan.size())
		            .mapToObj(i -> new ImmutablePair<>(lowerBoundsMichigan.get(i), upperBoundsMichigan.get(i)))
		            .collect(Collectors.toList());

		List<IntegerSolution> michiganPopulationParent = new ArrayList<>(),
				michiganPopulationOffspring = new ArrayList<>();

		for(int i=0; i<antecedentFactory.getRuleNum(); i++) {
			Antecedent antecedent = antecedentFactory.create();

			SingleClassLabel classLabelParent= new SingleClassLabel(i), classLabelOffspring= new SingleClassLabel(0);

			Consequent consequentParent = Consequent.builder()
					.consequentClass(classLabelParent)
					.ruleWeight(ruleWeight)
					.build();
			Consequent consequentOffsprin = Consequent.builder()
					.consequentClass(classLabelOffspring)
					.ruleWeight(ruleWeight)
					.build();

			MichiganSolution solution1 = new MichiganSolution(michiganBbounds,
					1,	// Number of objectives for Michigan solution
					0,	// Number of constraints for Michigan solution
					antecedent, consequentParent);
			MichiganSolution solution2 = new MichiganSolution(michiganBbounds,
					1,	// Number of objectives for Michigan solution
					0,	// Number of constraints for Michigan solution
					antecedent, consequentOffsprin);
			michiganPopulationParent.add(solution1);
			michiganPopulationOffspring.add(solution2);
		}

		List<Integer> lowerBoundsPittsburgh = new ArrayList<>();
		List<Integer> upperBoundsPittsburgh = new ArrayList<>();
		for(int i = 0; i < dimension*michiganPopulationParent.size(); i++) {
			lowerBoundsPittsburgh.add(0);
			upperBoundsPittsburgh.add(params.length);
		}
		List<Pair<Integer, Integer>> pittsburghBbounds =
		        IntStream.range(0, lowerBoundsPittsburgh.size())
		            .mapToObj(i -> new ImmutablePair<>(lowerBoundsPittsburgh.get(i), upperBoundsPittsburgh.get(i)))
		            .collect(Collectors.toList());

		solutionParent = new PittsburghSolution(pittsburghBbounds,
						 							2,
						 							michiganPopulationParent.subList(8, ruleNum),
						 							classification);
		solutionOffspring = new PittsburghSolution(pittsburghBbounds,
													2,
													michiganPopulationOffspring.subList(8, ruleNum),
													classification);
}

	@Test
	void testReplace() {
		Replacement<IntegerSolution> replacement = new RuleAdditionStyleReplacement();
		MOP1<IntegerSolution> problem = new MOP1<>(train);
		problem.evaluate(solutionParent);
		problem.evaluate(solutionOffspring);

		Consts.MAX_RULE_NUM = ruleNum;
		int ruleNumRemain = ruleNum - solutionOffspring.getMichiganPopulation().size();
		List<IntegerSolution> solutionReplaced = replacement.replace(solutionParent.getMichiganPopulation(), solutionOffspring.getMichiganPopulation());
		for(int i=0; i< solutionReplaced.size(); i++) {
			MichiganSolution solution_i = (MichiganSolution) solutionReplaced.get(i);
			if(i>=ruleNumRemain) {
				assertEquals(-1, (int)solution_i.getRule().getConsequent().getClassLabel().getClassLabel());
			}else {
				assertNotEquals(-1, (int)solution_i.getRule().getConsequent().getClassLabel().getClassLabel());
			}
		}
	}

}
