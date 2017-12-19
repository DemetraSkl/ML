package pa3;
import java.util.Arrays;

// Describes a row of data within the arff file
public class DataSample {
	int index;
	String label;
	int length;
	double[] values;
	public DataSample (int index, String line)  {
		this.index = index;
		String[] lineArray = line.split(",");
		// Grab the last value and set the label of this Sample
		// Each Data Sample has 1 class
		this.label = lineArray[lineArray.length - 1];
		// The row data
		this.values = this.getValues(lineArray);
		this.length = this.values.length;
	}

	public DataSample (double[] values)  {
		this.values = values;
		this.length = this.values.length;
	}

	// Returns the values of this Sample
	// Refers to the row data
	private double[] getValues(String[] values) {
		// Grab all values except the class value
		double[] v = new double[values.length - 1];
		for (int i = 0; i < v.length; i++) {
			v[i] = Float.parseFloat(values[i]);
		}
		return v;
	}

	public String toString() {
		return "\n{ index: " + this.index +
		       ", values: " + Arrays.toString(this.values) +
		       ", label: " + this.label +
		       ", length: " + this.length + " }";
	}
}
