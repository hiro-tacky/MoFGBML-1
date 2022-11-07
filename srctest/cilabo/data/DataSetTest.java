package cilabo.data;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import cilabo.utility.Input;

public class DataSetTest {

	private static DataSet dataset;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	dataset = new DataSet();
    	String fileName = "dataset\\iris\\a0_0_iris-10tra.dat";;
		Input.inputSingleLabelDataSet(dataset, fileName);
    }

    @Test
	public void pattern() {
    	Pattern pattern_sapmle = dataset.getPattern(0);

    	//属性値
    	double[] vector = {0.222222222222222,0.625,0.0677966101694915,0.0416666666666667};
    	InputVector inputVector = new InputVector(vector);
    	assertArrayEquals(inputVector.getVector(), pattern_sapmle.getInputVector().getVector(), 1e-10);

    	//結論部クラスラベル
    	ClassLabel classLabel = new ClassLabel();
    	classLabel.addClassLabel(0);
    	assertArrayEquals(classLabel.getClassVector(), pattern_sapmle.getTrueClass().getClassVector());
    	assertEquals(classLabel.getClassLabel(), pattern_sapmle.getTrueClass().getClassLabel());
    }

}
