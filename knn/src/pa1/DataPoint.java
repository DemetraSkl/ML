package pa1;

// Describes a sing data point in the ARFF file
public class DataPoint {
	String attribute;
	double value;
	String klass;
	int index;
	double weight = 0.0;

	public DataPoint(String attribute, double value, String klass, int index, double weight) {
		this.attribute = attribute;
		this.value = value;
		// Class will be the same across a sample
		this.klass = klass;
		this.index = index;
		this.weight = weight;
	}

	public void setAttributeName(String name) {
		this.attribute = name;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String toString() {
		return "\n{ attribute: " + this.attribute +
			", value: " + this.value +
			", class: " + this.klass +
			", index: " + this.index +
			", weight: " + this.weight + " }";
	}
}
