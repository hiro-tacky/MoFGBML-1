package cilabo.gbml.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uma.jmetal.component.termination.Termination;
import org.uma.jmetal.component.termination.impl.TerminationByEvaluations;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import cilabo.data.DataSet;
import cilabo.fuzzy.classifier.operator.classification.factory.SingleWinnerRuleSelection;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.factory.HeuristicRuleGenerationMethod;
import cilabo.gbml.operator.crossover.HybridGBMLcrossover;
import cilabo.gbml.operator.crossover.MichiganOperation;
import cilabo.gbml.operator.crossover.PittsburghCrossover;
import cilabo.gbml.operator.mutation.PittsburghMutation;
import cilabo.gbml.problem.impl.pittsburgh.MOP1;
import cilabo.main.Consts;
import cilabo.utility.Input;
import cilabo.utility.Random;
import xml.XML_manager;

class HybridMoFGBMLwithNSGAIITest {

	static DataSet train;
	static HeuristicRuleGenerationMethod factory;
	static int dimension;
	static Antecedent antecedent;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random.getInstance().initRandom(2022);

		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "a0_0_iris-10tra.dat";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		/* MOP: Multi-objective Optimization Problem */
		MOP1<IntegerSolution> problem = new MOP1<>(train);
		problem.setClassification(new SingleWinnerRuleSelection());

		/* Crossover: Hybrid-style GBML specific crossover operator. */
		double crossoverProbability = 1.0;
		/* Michigan operation */
		CrossoverOperator<IntegerSolution> michiganX = new MichiganOperation(Consts.MICHIGAN_CROSS_RT,
																			Knowledge.getInstace(),
																			problem.getConsequentFactory());
		/* Pittsburgh operation */
		CrossoverOperator<IntegerSolution> pittsburghX = new PittsburghCrossover(Consts.PITTSBURGH_CROSS_RT);
		/* Hybrid-style crossover */
		CrossoverOperator<IntegerSolution> crossover = new HybridGBMLcrossover(crossoverProbability, Consts.MICHIGAN_OPE_RT,
																				michiganX, pittsburghX);
		/* Mutation: Pittsburgh-style GBML specific mutation operator. */
		MutationOperator<IntegerSolution> mutation = new PittsburghMutation(Knowledge.getInstace(), train);

		/* Termination: Number of total evaluations */
		Termination termination = new TerminationByEvaluations(Consts.terminateEvaluation);

		//knowlwdge出力用
		XML_manager.addElement(XML_manager.getRoot(), Knowledge.getInstace(). knowledgeToElement());

		/* Algorithm: Hybrid-style MoFGBML with NSGA-II */
		HybridMoFGBMLwithNSGAII<IntegerSolution> algorithm
			= new HybridMoFGBMLwithNSGAII<>(problem,
											Consts.populationSize,
											Consts.offspringPopulationSize,
											Consts.outputFrequency,
											Consts.EXPERIMENT_ID_DIR,
											crossover,
											mutation,
											termination,
											problem.getConsequentFactory()
											);
	}

	@Test
	void test() {
		fail("まだ実装されていません");
	}

}
