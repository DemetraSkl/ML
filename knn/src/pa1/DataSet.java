package pa1;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

// Describes a data set after its parsed from ARFF
public class DataSet {
	String name;
	String RelationStr = "@Relation";
	String relationStr = "@relation";
	DataSample[] samples;
	DataAttribute[] attributes;

	public DataSet(String fileName) {
		try{
			// Open the file
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			boolean dataHit = false;
			ArrayList<DataSample> samples = new ArrayList<DataSample>();
			ArrayList<DataAttribute> attributes = new ArrayList<DataAttribute>();
			int sampleCounter = 0;
			int attributeCounter = 0;
			//Read File Line By Line
			while ((line = br.readLine()) != null)   {
				// If we are in the data section
				if (dataHit) {
					// Everything until the end is a sample
					DataSample sample = new DataSample(sampleCounter, line);
					samples.add(sample);
					sampleCounter++;
				} else if (this.isRelation(line)) {
					// This is the relation line
					this.name = line.replace(RelationStr, "")
						.replace(relationStr, "")
						.trim();
				} else if (isAttribute(line)) {
					// We are in the attributes section
					// lets grab them
					String[] arr  = line.split(" ");
					String attrName = arr[1];
					String attrType = arr[2];
					DataAttribute attribute = new DataAttribute(attrName, attrType, null, attributeCounter, 1.0, 0.0);
					attributes.add(attribute);
					attributeCounter++;
				} else if (isData(line)) {
					// We have hit the data section so
					// everything from here on until
					// the end is a data sample
					dataHit = true;
				}
			}
			//Close the input stream
			in.close();

			// Do some maintanance
			// Fill out the attribute key of each
			// Data point
			for (int i = 0; i < samples.size(); i++) {
				DataSample sample = samples.get(i);
				DataPoint[] values = sample.values;
				for(int j =0; j< values.length; j++) {
					DataPoint point = values[j];
					DataAttribute attribute = attributes.get(j);
					point.setAttributeName(attribute.name);
				}
			}

			// Loop through attributes not including the class
			for (int i = 0; i < attributes.size() - 1; i++) {
        // Build the data point values for each attribute (the column vector)
        ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
        // Pick attribute
				DataAttribute attribute = attributes.get(i);
				// Loop through all data samples
				for (int j = 0; j < samples.size(); j++) {
					//Get the sample
					DataSample sample = samples.get(j);
					//Pick the data point on the same index as the attribute
					DataPoint point = sample.values[i];
					dataPoints.add(point);
				}
				DataPoint[] points = dataPoints.toArray(new DataPoint[dataPoints.size()]);
				// Set the column vector for each attribute
				attribute.setValues(points);
			}

			// Set the data samples values of this DataSet
			this.samples = new DataSample[samples.size()];
			for (int i = 0; i < this.samples.length; i++) {
				this.samples[i] = samples.get(i);
			}

			// Set the attributes values of this DataSet
			// Remove the class from the attributes
			this.attributes = new DataAttribute[attributes.size() - 1];
			for (int i = 0; i < this.attributes.length; i++) {
				this.attributes[i] = attributes.get(i);
			}
		} catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	// Checks if we are in the relation line
	private boolean isRelation(String line) {
		return this.contains(line, "@Relation");
	}

	// Checks if we are in at attribute line
	private boolean isAttribute(String line) {
		return this.contains(line, "@Attribute");
	}

	// Checks if weve hit the data section
	private boolean isData(String line) {
		return this.contains(line, "@Data");
	}

	// Basic substring contain clause
	// Checks if the line weve encountered contains a
	// specific string
	private boolean contains(String line, String string) {
		return line.toLowerCase().contains(string.toLowerCase());
	}
}
