package cilabo.data;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import cilabo.utility.Input;

public class DataSetTest {

	private static DataSet dataset;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	dataset = new DataSet();
    	String fileName = "dataset\\cilabo\\test_Dtra.dat";
		Input.inputSingleLabelDataSet(dataset, fileName);
    }

    @Test
	public void pattern() {

		assertEquals(2, dataset.getNdim());
		assertEquals(4, dataset.getCnum());

		ArrayList<Pattern> pattern_actual = dataset.getPatterns();
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

    }

}
