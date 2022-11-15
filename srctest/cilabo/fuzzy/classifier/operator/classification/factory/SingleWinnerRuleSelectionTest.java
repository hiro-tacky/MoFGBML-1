package cilabo.fuzzy.classifier.operator.classification.factory;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.fuzzy.StaticFuzzyClassifierForTest;
import cilabo.fuzzy.classifier.impl.RuleBasedClassifier;
import cilabo.fuzzy.rule.Rule;
import cilabo.utility.Input;
import cilabo.utility.Random;

class SingleWinnerRuleSelectionTest {

    @BeforeAll
    public static void setUpBeforeClass(){
    	Random.getInstance().initRandom(2022);
    }

	@Test
	void test() {
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "test2_Dtra.dat";
		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		RuleBasedClassifier classifier = StaticFuzzyClassifierForTest.simpleClassifier(train.getNdim());
		ArrayList<Pattern> patterns = train.getPatterns();
		for(Pattern pattern_i: patterns) {
			double[] inputVector = pattern_i.getInputVector().getVector();
			int x_1 = func(inputVector[0]), x_2 = func(inputVector[1]);
			Rule rule_actual = classifier.classify(pattern_i.getInputVector());
			Integer[] expected = {x_1, x_2};
			assertArrayEquals(expected, rule_actual.getConsequent().getClassLabel().getClassVector());
		}
	}


	/** 3均等分割三角形型ファジィでどのラベルが与えられるか
	 * @param x 属性値
	 * @return クラスラベル r = {1, 2, 3}のいずれか
	 */
	private int func(double x) {
		int r;
		if(x < 1/4d) {r = 1;}else if(x < 3/4d) {r = 2;} else {r = 3;}
		return r;
	}
}
