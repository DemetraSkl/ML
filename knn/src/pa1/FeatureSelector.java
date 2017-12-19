package pa1;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// Feature selector class
// Calculates information gain for every attribute
// Sorts all attributes by information gain
// and returns the first N attributes to be considered
public class FeatureSelector {
	DataSet trainSet = null;

  // Constructor takes the train set
	public FeatureSelector(DataSet trainSet) {
		this.trainSet = trainSet;
	}

  // Returns a specific attribute at an index
	public DataAttribute getAttribute(int index) {
		return this.trainSet.attributes[index];
	}

  // Returns the values of an attributes
  // (column vector)
	public DataPoint[] getAttributeValues(int index) {
		return this.getAttribute(index).values;
	}

  // Sorts the values of an attribute (a column vector)
	public void sortAttributeValues(int index) {
		Arrays.sort(this.getAttributeValues(index), new Comparator<DataPoint>() {
			@Override
			public int compare(DataPoint o1, DataPoint o2) {
				if (o1.value > o2.value) {
					return 1;
				} else if (o1.value < o2.value) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

  // Partitions an Array of data points by a chunkSize
	public DataPoint[][] partitionAttributeValues(DataPoint[] array, int chunkSize) {
		int numOfChunks = (int)Math.ceil((double)array.length / chunkSize);
		DataPoint[][] output = new DataPoint[numOfChunks][];

		for(int i = 0; i < numOfChunks; ++i) {
			int start = i * chunkSize;
			int length = Math.min(array.length - start, chunkSize);

      // Grab the chunk to partition
			DataPoint[] temp = new DataPoint[length];
      // Copy it
			System.arraycopy(array, start, temp, 0, length);
			output[i] = temp;
		}

		return output;
	}

  // Calculates the entropy influence of each partition
	public double calculateEntropy(DataPoint[] values) {
    // Get the counts of classes
		HashMap<String, Integer> counts = Utils.countStringOccurences(values);

		int numValues = values.length;
		double log2 = Math.log(2);
		double entropy = 0;
    // For every class calculate its entropy influence
		for (Map.Entry<String, Integer> entry : counts.entrySet())
		{
			String klass = entry.getKey();
			int count = entry.getValue();
			double probability = ((double) count) / numValues;
			double logarithm = Math.log(probability) / log2;
			double mult = probability * logarithm;
			entropy += mult;
		}
    // Everything is multiplied by -1
		entropy *= -1;
		if (Double.isNaN(entropy)) {
			return 0;
		}
    // Return the accumulation
		return entropy;
	}

  // Returns the info gain for a given attribute at an index
	public double calculateAttributeIG(int index) {
		this.sortAttributeValues(index);
		DataAttribute attr = this.getAttribute(index);
		DataPoint[] allAttributes = this.getAttributeValues(index);
    // Get the whole set entropy
		double entropy = this.calculateEntropy(allAttributes);
		int parts = 5;
		int part_size = allAttributes.length / (parts - 1);
    // Partition the set
		DataPoint[][] partitions = this.partitionAttributeValues(allAttributes, part_size);
		double partition_entropy = 0.0;
    // Get the entropy for each partition
		for (int i = 0; i < partitions.length -1; i++) {
			DataPoint[] partition = partitions[i];
			double pEntropy = this.calculateEntropy(partition);
			double pct = ((double) partition.length) / ((double) allAttributes.length);
			partition_entropy += pct * pEntropy;
		}
    // And calculate the info gain
		return entropy - partition_entropy;
	}

  // Get the info Gain for all attributes and return the
  // attribute with infoGains set
	public DataAttribute[] getAllAttributesInfoGain() {
		int numAttributes = this.trainSet.attributes.length;
		for (int index = 0; index < numAttributes; index++) {
      // Calculate the info gain of an attribute
			double infoGain = this.calculateAttributeIG(index);
      // Set the info gain
			this.getAttribute(index).setInfoGain(infoGain);
		}
    // return the attributes
		return this.trainSet.attributes;
	}

  // Sorts the attributes Array by Info gain
	public DataAttribute[] sortIG() {
		Arrays.sort(this.getAllAttributesInfoGain(), new Comparator<DataAttribute>() {
			@Override
			public int compare(DataAttribute o1, DataAttribute o2) {
				if (o1.infoGain > o2.infoGain) {
					return -1;
				} else if (o1.infoGain < o2.infoGain) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		return this.trainSet.attributes;
	}

  // Return the first N elements of the attributes array
	public String[] getNFirstIGAttributes(int n) {
		DataAttribute[] igdtAll = this.sortIG();
		String[] igdt = new String[n];
		for (int i = 0; i < n; i++) {
		  igdt[i] = igdtAll[i].name;
		}
		return igdt;
	}
}
