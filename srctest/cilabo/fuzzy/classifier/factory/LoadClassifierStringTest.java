package cilabo.fuzzy.classifier.factory;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import cilabo.data.DataSet;
import cilabo.fuzzy.StaticFuzzyClassifierForTest;
import cilabo.fuzzy.classifier.RuleBasedClassifier;
import cilabo.fuzzy.knowledge.Knowledge;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.fuzzy.knowledge.membershipParams.HomoTriangle_3;
import cilabo.utility.Input;

public class LoadClassifierStringTest {

	private static DataSet train;

	@BeforeClass
    public static void setUpBeforeClass(){
		String sep = File.separator;
		String dataName = "dataset" + sep + "cilabo" + sep + "kadai5_pattern1.txt";
		train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataName);

		int dimension = train.getNdim();
		float[][] params = HomoTriangle_3.getParams();
		HomoTriangleKnowledgeFactory.builder()
								.dimension(dimension)
								.params(params)
								.build()
								.create();
    }

	@Test
	public void testLoadCreate() {
		RuleBasedClassifier classifier = StaticFuzzyClassifierForTest.makeSingleLabelClassifier(train);

		RuleBasedClassifier newClassifier = LoadClassifierString.builder()
											.classifierString(classifier.toString())
											.knowledge(Knowledge.getInstace())
											.build()
											.create();

		assertEquals(newClassifier.toString(), classifier.toString());
	}

}
