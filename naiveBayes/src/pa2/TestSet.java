package pa2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

public class TestSet {
	DataSet testData;
	DataSet trainData;
	double m;
	public TestSet(String testfilename, String trainfilename, double smoothingFactor, long nlines, String dataDirectoryPrefix) {
		this.testData = new DataSet(testfilename, -1, dataDirectoryPrefix);
		this.trainData = new DataSet(trainfilename, nlines, dataDirectoryPrefix);
		this.m = smoothingFactor;
		// Utils.info(getAccuracy("type1"));
	}

	public double getAccuracy(String type) {
		int counter = 0;
		Iterator<Document> it = this.testData.documents.iterator();
		while (it.hasNext()) {

			Document document = it.next();
			// Utils.info(document);
			if (document.label.equals(getBayesPrediction(document, type))) {
				counter ++;
			}
		}
		return (double) counter/this.testData.documents.size();
	}
	// Returns score of each class - i.e. sum of log probabilities 
	public double getLabelBayesPrediction(Document document, String label, String variantType) {
		double cumulativeProbability = 0;
		if (label.equals("yes")) {

			double trainPriorProbability = this.trainData.getPriorProbability("yes");
			cumulativeProbability += Math.log(trainPriorProbability);
			for (String word : document.features) {
				if (this.trainData.vocabulary.contains(word)) {
					if (variantType.equals("type1")) {

						cumulativeProbability += Math.log(this.trainData.getConditionalProbabilityType1(word, "yes", this.m));
						// Utils.info(word);
						// Utils.info(this.trainData.getConditionalProbabilityType1(word, "yes", this.m));
					} else if (variantType.equals("type2")) {
						cumulativeProbability += Math.log(this.trainData.getConditionalProbabilityType2(word, "yes", this.m));
					}
				}
			}
		} else {
			double trainPriorProbability = this.trainData.getPriorProbability("no");
			cumulativeProbability += Math.log(trainPriorProbability);
			for (String word : document.features) {
				if (this.trainData.vocabulary.contains(word)) {
					if (variantType.equals("type1")) {
						cumulativeProbability += Math.log(this.trainData.getConditionalProbabilityType1(word, "no", this.m));
						// Utils.info(word);
						// Utils.info(this.trainData.getConditionalProbabilityType1(word, "no", this.m));
					} else if (variantType.equals("type2")) {
						cumulativeProbability += Math.log(this.trainData.getConditionalProbabilityType2(word, "no", this.m));
					}
				}
			}
		}
		return cumulativeProbability;
	}
	// Compare scores and get prediction
	public String getBayesPrediction(Document document, String variantType) {
		double predictionYes = getLabelBayesPrediction(document, "yes", variantType);
		double predictionNo = getLabelBayesPrediction(document, "no", variantType);
		//Utils.info(predictionYes);
		//Utils.info(predictionNo);
		if (predictionYes >= predictionNo) {
			return "yes";
		} else {
			return "no";
		}
	}
}