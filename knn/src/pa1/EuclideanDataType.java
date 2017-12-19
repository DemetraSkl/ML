package pa1;

// Basic data type to help manipulate lists
// of Euclidean distances
public class EuclideanDataType {

	double distance;
	boolean agreement;
	String classTrain;
	String classTest;

	public EuclideanDataType(double distance, String classTrain, String classTest) {
		this.distance = distance;
		// The class of the train sample
		this.classTrain = classTrain;
		// The class of the test sample
		this.classTest = classTest;
		// Check if the classes agree
		this.agreement = (this.classTrain.equals(this.classTest));
	}

	public String toString() {
		return "distance: " + this.distance +
			" class_train: " + this.classTrain +
			" class_test: " + this.classTest +
			" agreement: " + this.agreement;
	}
}
