package pa4;
import java.io.*;
import java.util.*;

// Describes a data set after its parsed from ARFF
public class ARFFReader {
  String name;
  String RelationStr = "@Relation";
  String relationStr = "@relation";
  DataSample[] samples;
  DataAttribute[] features;
  String [] targets;

  public ARFFReader(String fileName) {
    try {
      // Open the file
      FileInputStream fstream = new FileInputStream(fileName);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String line;
      boolean dataHit = false;
      ArrayList<DataSample> samples = new ArrayList<DataSample>();
      ArrayList<DataAttribute> features = new ArrayList<DataAttribute>();
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
          // We are in the features section
          // lets grab them
          String[] arr  = line.split(" ");
          String attrName = arr[1];
          String attrType = arr[2];
          DataAttribute attribute = new DataAttribute(attrName, attrType, null, attributeCounter);
          features.add(attribute);
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

      DataAttribute fileLabels = features.get(features.size() - 1);
      this.targets = fileLabels.type.replace("{", "").replace("}", "").split(",");

      // Set the data samples values of this DataSet
      this.samples = new DataSample[samples.size()];
      for (int i = 0; i < this.samples.length; i++) {
        this.samples[i] = samples.get(i);
      }

      // Set the features values of this DataSet
      // Remove the class from the features
      this.features = new DataAttribute[features.size() - 1];
      for (int i = 0; i < this.features.length; i++) {
        this.features[i] = features.get(i);
      }
    } catch (Exception e) { //Catch exception if any
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
  // Describes an attribute within the ARFF file
  public class DataAttribute {
    String name;
    String type;
    DataPoint[] values;
    int index;


    public DataAttribute(String name, String type, DataPoint[] values, int index) {
      this.name = name;
      this.type = type;
      // Refers to the column data
      this.values = values;
      this.index = index;

    }

    // Allow setting the data values of this attribute
    // Changes the column data
    public void setValues(DataPoint[] values) {
      this.values = values;
    }

    public String toString() {
      return "{ name: " + this.name +
        ", type: " + this.type +
        ", values: " + Arrays.toString(this.values) +
        ", index: " + this.index +" }";
    }
  }
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
}
