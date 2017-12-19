package pa2;
import java.io.FileWriter;
import java.util.Arrays;

public class Main {
	public static void main(String[] args) {
		varyingSmoothingFactor();
		varyingDocumentSizes();
	}

	private static void varyingDocumentSizes() {
		String[] dataSets = {"ibmmac", "sport"};
		for (int m = 0; m < dataSets.length; m++) {
			String setName = dataSets[m];
			Utils.info(setName + "===============================");
			// Put your data folder in your home directory
      // String home = System.getenv("HOME");
		String home = ".";
		  String dataDirectoryPrefix  = home + "/data/" + setName;
			String trainingSet = dataDirectoryPrefix + "/index_train";
			String testingSet = dataDirectoryPrefix + "/index_test";
			System.out.println("=============================================");
			System.out.println("With Varying Training Data Sizes");
			System.out.println("=============================================");
			String csvFile = dataDirectoryPrefix + "/" + setName + "-varying-train-size.csv";
			try {
				FileWriter writer = new FileWriter(csvFile);
				double[] sFactors = {0.0, 1.0};
				String[] types = {"type1", "type2"};
				int max_length = new DataSet(trainingSet, -1, dataDirectoryPrefix).documents.size();
				double weight = 1.0;
				long[] nlines = {
					Math.round(0.1 * max_length),
					Math.round(0.2 * max_length),
					Math.round(0.3 * max_length),
					Math.round(0.4 * max_length),
					Math.round(0.5 * max_length),
					Math.round(0.6 * max_length),
					Math.round(0.7 * max_length),
					Math.round(0.8 * max_length),
					Math.round(0.9 * max_length),
					Math.round(1.0 * max_length),
				};
				CSVUtils.writeLine(writer, Arrays.asList("Data", "variant_type", "number_of_training_data_included", "smoothing_factor", "accuracy"));

				for (int j = 0; j < types.length; j++) {
					String type = types[j];
					for (int i = 0; i < sFactors.length; i++) {
						double smoothingFactor = sFactors[i];
						for (int l = 0; l < nlines.length; l++) {
							TestSet testSet = new TestSet(testingSet, trainingSet, smoothingFactor, nlines[l], dataDirectoryPrefix);
							double accuracy = testSet.getAccuracy(type);
							Utils.info("Data: " + setName + ", variant type: " + type + ", N: " + Long.toString(nlines[l]) + ", smoothing factor: " + Double.toString(smoothingFactor) + ", accuracy : " + accuracy);
							CSVUtils.writeLine(writer, Arrays.asList(setName, type, Long.toString(nlines[l]), Double.toString(smoothingFactor), Double.toString(accuracy)));
						}
					}
				}
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void varyingSmoothingFactor() {
		String[] dataSets = {"ibmmac", "sport"};


		for (int m = 0; m < dataSets.length; m++) {
			String setName = dataSets[m];
			Utils.info(setName + "===============================");
			String home = ".";
      //String home = System.getenv("HOME");
			String dataDirectoryPrefix  = home + "/data/" + setName;
			String trainingSet = dataDirectoryPrefix + "/index_train";
			String testingSet = dataDirectoryPrefix + "/index_test";
			System.out.println("=============================================");
			System.out.println("With Varying Smoothing Factors");
			System.out.println("=============================================");
			String csvFile = dataDirectoryPrefix + "/" + setName + "-varying-smoothing-factors.csv";
			try {
				FileWriter writer = new FileWriter(csvFile);

				double[] sFactors = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
				String[] types = {"type1", "type2"};
				int nlines = -1;
				CSVUtils.writeLine(writer, Arrays.asList("Data", "variant_type", "number_of_training_data_included", "smoothing_factor", "accuracy"));
				for (int j = 0; j < types.length; j++) {
					String type = types[j];
					for (int i = 0; i < sFactors.length; i++) {
						double smoothingFactor = sFactors[i];
						TestSet testSet = new TestSet(testingSet, trainingSet, smoothingFactor, nlines, dataDirectoryPrefix);
						double accuracy = testSet.getAccuracy(type);
						Utils.info("Data: " + setName + ", variant type: " + type + ", N: " + Integer.toString(testSet.trainData.documents.size()) + ", smoothing factor: " + Double.toString(smoothingFactor) + ", accuracy: " + Double.toString(accuracy));
						CSVUtils.writeLine(writer, Arrays.asList(setName, type, Integer.toString(testSet.trainData.documents.size()), Double.toString(smoothingFactor), Double.toString(accuracy)));

					}
				}
				writer.flush();
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
}
