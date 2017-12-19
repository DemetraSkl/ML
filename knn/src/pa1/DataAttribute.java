package pa1;
import java.util.Arrays;

// Describes an attribute within the ARFF file
public class DataAttribute {
	String name;
	String type;
	DataPoint[] values;
	int index;
	double weight;
	double infoGain = 0.0;

	public DataAttribute(String name, String type, DataPoint[] values, int index, double weight, double infoGain) {
		this.name = name;
		this.type = type;
		// Refers to the column data
		this.values = values;
		this.index = index;
		this.weight = weight;
		this.infoGain = infoGain;
	}

	// Allow setting the data values of this attribute
	// Changes the column data
	public void setValues(DataPoint[] values) {
		this.values = values;
	}

	// Allow setting the infoGain of this Attribute
	public void setInfoGain(double infoGain) {
		this.infoGain = infoGain;
	}

	// Allow setting the weight of this attribute
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String toString() {
		return "{ name: " + this.name +
			", type: " + this.type +
			", values: " + Arrays.toString(this.values) +
			", index: " + this.index +
			", infoGain: " + this.infoGain +
			", weight: " + this.weight + " }";
	}
}
