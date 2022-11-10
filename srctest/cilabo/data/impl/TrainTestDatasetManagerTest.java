package cilabo.data.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import cilabo.data.DataSet;
import cilabo.data.Pattern;
import cilabo.utility.Random;

public class TrainTestDatasetManagerTest {

	private static String trainFile;
	private static String testFile;
	private static TrainTestDatasetManager TDM;
	static ArrayList<DataSet> trains;
	static ArrayList<DataSet> tests;


    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    	Random.getInstance().initRandom(2022);
    	trainFile = "dataset\\cilabo\\test_Dtra.dat";
    	testFile = "dataset\\cilabo\\test_Dtst.dat";
    	TDM = new TrainTestDatasetManager();
    	TDM.loadTrainTestFiles(trainFile, testFile);
    	trains = TDM.getTrains();
    	tests = TDM.getTests();
    }

	@Test
	public void loadTrainTestFiles() {

		assertEquals(2, trains.get(0).getNdim());
		assertEquals(4, trains.get(0).getCnum());

		ArrayList<Pattern> pattern_actual = trains.get(0).getPatterns();
		assertEquals(100, pattern_actual.size());
		for(int i=0; i<pattern_actual.size(); i++) {
			Pattern pattern_i = pattern_actual.get(i);

			assertEquals(i, pattern_i.getID());

			//属性値
			double[] vectorBuf = {i/100d, Math.pow(i/100d, 2)};
			assertArrayEquals(vectorBuf, pattern_i.getInputVector().getVector(), 1e-5);

			//クラスラベルマルチ
			Integer[] classLabelMultiBuf = {i/25};
			assertArrayEquals(classLabelMultiBuf, pattern_i.getTrueClass().getClassVector());

			//クラスラベル
			Integer classLabelBuf = i/25;
			assertEquals(classLabelBuf, pattern_i.getTrueClass().getClassLabel());
		}

		pattern_actual = tests.get(0).getPatterns();
		assertEquals(100, pattern_actual.size());
		for(int i=0; i<pattern_actual.size(); i++) {
			Pattern pattern_i = pattern_actual.get(i);

			assertEquals(i, pattern_i.getID());

			//属性値
			double[] vectorBuf = {Math.pow(i/100d, 2), 1-Math.pow(i/100d, 2)};
			assertArrayEquals(vectorBuf, pattern_i.getInputVector().getVector(), 1e-5);

			//クラスラベルマルチ
			Integer[] classLabelMultiBuf = {i/25};
			assertArrayEquals(classLabelMultiBuf, pattern_i.getTrueClass().getClassVector());

			//クラスラベル
			Integer classLabelBuf = i/25;
			assertEquals(classLabelBuf, pattern_i.getTrueClass().getClassLabel());
		}
	}
}
