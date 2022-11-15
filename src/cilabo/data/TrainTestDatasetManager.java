package cilabo.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import cilabo.labo.developing.fan2021.CommandLineArgs;
import cilabo.main.Consts;
import cilabo.utility.Input;

/** 学習用データ1つ，評価用データ1つのシンプルなデータ分割を保持するクラス.<br>
 * 	this class has one training dataset and one test dataset.
 *  */
public class TrainTestDatasetManager {
	private static TrainTestDatasetManager instance = new TrainTestDatasetManager();
	// ** 学習用データセット <br>training dataset*/
	final ArrayList<DataSet> trains = new ArrayList<>();
	// ** 評価用データセット <br>test dataset*/
	final ArrayList<DataSet> tests = new ArrayList<>();

	private TrainTestDatasetManager() {}

	/** 空のインスタンスを生成します <br> Constructs an empty instance of class */
	public static TrainTestDatasetManager getInstance() {
		return instance;
	}


	/** 学習用データセットを追加
	 * @param train 学習用データセット
	 */
	protected void addTrains(DataSet train) {
		this.trains.add(train);
	}

	/** 評価用データセットを追加
	 * @param test 評価用データセット
	 */
	protected void addTests(DataSet test) {
		this.tests.add(test);
	}


	/** 学習用データセットを取得
	 * @return 学習用データセット
	 */
	public ArrayList<DataSet> getTrains() {
		if(Objects.isNull(this.trains)) {System.err.println("TrainTestDatasetManager is null");}
		return this.trains;
	}

	/** 評価用データセットを取得
	 * @return 評価用データセット
	 */
	public ArrayList<DataSet> getTests() {
		if(Objects.isNull(this.tests)) {System.err.println("TrainTestDatasetManager is null");}
		return this.tests;
	}

	/**
	 * ファイル名を指定してデータセットをロードする関数
	 * @param trainFile 学習用データセットのパス
	 * @param testFile 評価用データセットのパス
	 * @return 生成されたTrainTestDatasetManagerインスタンス
	 */
	public TrainTestDatasetManager loadTrainTestFiles(String trainFile, String testFile) {
		if(Objects.isNull(trainFile) || Objects.isNull(testFile)) {System.err.println("fileNameString is null");}

		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, trainFile);
		addTrains(train);

		DataSet test = new DataSet();
		Input.inputSingleLabelDataSet(test, testFile);
		addTests(test);

		return this;
	}

	/**
	 * irisのtrial00をロードする関数.
	 * @return irisのtrial00の学習用及び評価用データセットを持つTrainTestDatasetManagerインスタンス
	 */
	public TrainTestDatasetManager loadIrisTrial00() {
		String sep = File.separator;
		String fileName;

		// Training dataset
		fileName = Consts.DATASET;
		fileName += sep + CommandLineArgs.dataName;
		fileName += sep + "a0_0_" + CommandLineArgs.dataName + "-10tra.dat";
		DataSet train = new DataSet();
		Input.inputSingleLabelDataSet(train, fileName);
		addTrains(train);

		// Test dataset
		fileName = Consts.DATASET;
		fileName += sep + CommandLineArgs.dataName;
		fileName += sep + "a0_0_" + CommandLineArgs.dataName + "-10tst.dat";
		DataSet test = new DataSet();
		Input.inputSingleLabelDataSet(test, fileName);
		addTests(test);

		return this;
	}

}
