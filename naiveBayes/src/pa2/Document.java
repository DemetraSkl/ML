package pa2;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// class Document will have as input arguments the text file index name and its classification


public class Document {
	String name;
	String label;
	String[] features;
	String[] vocabulary;
	HashMap<String, Integer> featureCount;
	public Document(String docname, String label, String dataDirectoryPrefix) {
		this.name = docname;
		this.label = label;

		String fileName = dataDirectoryPrefix + "/" + this.name + ".clean";

		// Read text file, create a concatanated version 
		// create a vocabulary associated with the text file - contains each word once
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			String allText = "";
			while ((line = br.readLine()) != null) {
				//Utils.info(line);
				allText += " " + line;
			}
			//Utils.info(allText);
			String[] features_0 = allText.split(" ");
			ArrayList<String> features_list = new ArrayList<String>();
			for (int i = 0; i < features_0.length; i++) {
				String feature = features_0[i];
				if (!feature.equals("")) {
					features_list.add(feature.trim());
				}

			}
			this.features = new String[features_list.size()];
			this.features = features_list.toArray(this.features);

			this.featureCount = Utils.countStringOccurences(this.features);

			this.vocabulary = new String[this.featureCount.size()];
			int acc = 0;
			for (String feature : this.featureCount.keySet()) {
				this.vocabulary[acc] = feature;
				acc++;
			}
			//Utils.info(Arrays.toString(vocabulary));


		} catch (Exception e) {
			Utils.error(e.getMessage());
		}

	}

	public String toString() {
		return "Name: " + this.name +
		       "\nLabel: " + this.label;
	}
}