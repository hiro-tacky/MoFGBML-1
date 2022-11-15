package utility;

import cilabo.data.DataSet;
import cilabo.fuzzy.knowledge.factory.HomoTriangleKnowledgeFactory;
import cilabo.utility.Input;

public class Utility {

	static public DataSet makeDataSet(String dataSetFilePath) {
		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, dataSetFilePath);
		return train;
	}

	static public void makeKnowladge(int dimension, float[][] params) {
		HomoTriangleKnowledgeFactory.builder()
			.dimension(dimension)
			.params(params)
			.build()
			.create();
	}

}
