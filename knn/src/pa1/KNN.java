package pa1;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// K nearest neighbor implementation
public class KNN {

	int k;
	FeatureSelector ft;
	String[] igdt;
	int numFeatures = 0;
	DataSet train;
	DataSet test;

	public KNN (int k, DataSet train, DataSet test, int numFeatures) {
    // neighbor number
		this.k = k;
    // train set
		this.train = train;
    // test set
		this.test = test;
    // Number of features to be included
    // if 0 all features are included
		this.numFeatures = numFeatures;
		if (this.numFeatures > 0) {
			this.ft = new FeatureSelector(train);
			this.igdt = this.ft.getNFirstIGAttributes(this.numFeatures);
		}
	}

  // Returns an array of Euclidean distances
  // of a test sample to all train samples
	public EuclideanDataType[] getListofEuclideanDistances(DataSample testSample) {
		int trainSetLength = this.train.samples.length;
		EuclideanDataType[] buffer = new EuclideanDataType[trainSetLength];
		for(int i = 0; i < trainSetLength; i++) {
			DataSample trainSample = this.train.samples[i];
			String trainClass = trainSample.klass;
			String testClass = testSample.klass;
      // If we want to exclude features
			if (this.numFeatures > 0) {
				// Filter out the unwanted features
				for (int m = 0; m < testSample.values.length; m++) {
					String trainAttr = trainSample.values[m].attribute;
          if (Arrays.asList(this.igdt).contains(trainAttr)) {
							trainSample.setWeight(m, 1.0);
						} else {
							trainSample.setWeight(m, 0.0);
						}
				}
			}
			double d = Euclidean.euclideanDistance(testSample.values, trainSample.values);
			EuclideanDataType dt = new EuclideanDataType(d, trainClass, testClass);
			buffer[i] = dt;
		}
		return buffer;
	}

  // Sort the array of euclidean distances by distance
	public EuclideanDataType[] sortDistances(DataSample testSample) {
		EuclideanDataType[] dts = this.getListofEuclideanDistances(testSample);
		Arrays.sort(dts, new Comparator<EuclideanDataType>() {
			@Override
			public int compare(EuclideanDataType o1, EuclideanDataType o2) {
				if (o1.distance > o2.distance) {
					return 1;
				} else if (o1.distance < o2.distance) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		return dts;
	}

  // Get the K nearest neighbors of a test sample
	public EuclideanDataType[] getKNearestNeighbors(DataSample testSample) {
		EuclideanDataType[] dts = this.sortDistances(testSample);
		EuclideanDataType[] kneighbors = new EuclideanDataType[this.k];
    // Return only k of the sorted distances
		for (int i = 0; i < kneighbors.length; i++) {
			kneighbors[i] = dts[i];
		}
		return kneighbors;
	}

  // Returns the class with the highest occurence within the
  // k neighborhood
	public String getKNeighborPrediction(DataSample testSample) {
		EuclideanDataType[] dts = this.getKNearestNeighbors(testSample);
		String[] trainClasses = new String[dts.length];
		for(int i = 0; i < trainClasses.length; i++) {
			trainClasses[i] = dts[i].classTrain;
		}

		HashMap<String, Integer> counts = Utils.countStringOccurences(trainClasses);
		Map.Entry<String, Integer> maxEntry = null;

    // Grab the max value of the occurences
		for (Map.Entry<String, Integer> entry : counts.entrySet())
		{
			if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
			{
				maxEntry = entry;
			}
		}
    // and return it
		return maxEntry.getKey();
	}

  // Checks that the sample class and the most prevalent k neighborhood class
  // are in agreement
	public boolean getPredictionAgreement(DataSample testSample) {
		EuclideanDataType[] dts = this.getKNearestNeighbors(testSample);
		String classTest= dts[0].classTest;
		String kneighborPrediction = this.getKNeighborPrediction(testSample);
		return (classTest.equals(kneighborPrediction));
	}

  // Return the accuracy of the test set based on the knn algorithm
  // as percentage of agreements over the whole data set
	public double getAccuracy() {
		int testSetLength = this.test.samples.length;
		boolean[] predictions = new boolean[testSetLength];
		int agreements = 0;
    // Loop through all the data samples and get their agreement
    // with the neighborhood
		for(int i = 0; i < testSetLength; i++) {
			DataSample test_instance = this.test.samples[i];
			boolean prediction = this.getPredictionAgreement(test_instance);
			predictions[i] = prediction;
      // If we agree we are accurate
			if (prediction == true) {
				agreements++;
			}
		}
    // Return the accuracy as a percentage
		return ((double) agreements) / testSetLength * 100;
	}

  // Helper method to prin out the euclidean distance array
	public String printEuclideanDistanceArray(DataSample testSample) {
		EuclideanDataType[] dts = this.getKNearestNeighbors(testSample);
		String str = "";
		for (int i = 0; i < dts.length; i++) {
			EuclideanDataType elm = dts[i];
			str += elm.toString() + "\n";
		}
		return str;
	}
}
