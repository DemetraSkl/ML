package pa1;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Instances;

// Basec ARFF Reader for simple J48 evalutions
// Uses the weka API
public class ARFFReader {
	Instances instances = null;

	public ARFFReader(String fileName){

		try{
			DataSource source = new DataSource(fileName);
			this.instances = source.getDataSet();
			int classIndex = this.instances.numAttributes() - 1;
			this.instances.setClassIndex(classIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
