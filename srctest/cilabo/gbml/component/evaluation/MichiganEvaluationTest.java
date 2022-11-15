package cilabo.gbml.component.evaluation;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uma.jmetal.solution.integersolution.IntegerSolution;

import cilabo.data.DataSet;
import cilabo.fuzzy.rule.antecedent.Antecedent;
import cilabo.fuzzy.rule.antecedent.factory.HeuristicRuleGenerationMethod;
import cilabo.gbml.problem.impl.michigan.ProblemMichiganFGBML;
import cilabo.utility.Input;
import cilabo.utility.Random;

class MichiganEvaluationTest {

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
		ProblemMichiganFGBML<IntegerSolution> problem = new ProblemMichiganFGBML<>(2022, train);
	}

	@Test
	void test() {
//		fail("まだ実装されていません");
	}

}
