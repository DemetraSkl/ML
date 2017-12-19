package pa1;
import java.util.Arrays;

// Describes a row of data within the arff file
public class DataSample {
	int index;
	String klass;
	int length;
	DataPoint[] values;
	public DataSample (int index, String line)  {
		this.index = index;
		String[] lineArray = line.split(",");
		// Grab the last value and set the klass of this Sample
		// Each Data Sample has 1 class
		this.klass = lineArray[lineArray.length - 1];
    // The row data
		this.values = this.getValues(lineArray);
		this.length = this.values.length;
	}

	// Returns the values of this Sample
	// Refers to the row data
	private DataPoint[] getValues(String[] values) {
		// Grab all values except the class value
		DataPoint[] v = new DataPoint[values.length - 1];
		for (int i = 0; i < v.length; i++) {
			double value = Float.parseFloat(values[i]);
			v[i] = new DataPoint("", value, this.klass, i, 1.0);
		}
		return v;
	}

	// Set the weight of an attribute of a particular data_point
	// at index
	public void setWeight(int index, double weight) {
		this.values[index].setWeight(weight);
	}

	public String toString() {
		return "{ index: " + this.index +
			", values: " + Arrays.toString(this.values) +
			", class: " + this.klass +
			", length: " + this.length + " }";
	}
}
