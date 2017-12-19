package pa1;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.J48;
import weka.classifiers.Evaluation;
import java.io.FileWriter;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    String[] dataSets = {"ionosphere", "irrelevant", "mfeat-fourier", "spambase"};
    for (int l = 0; l < dataSets.length; l++) {
      String dataName = dataSets[l];
      String arff_ext = ".arff";
      // Put your data in a data folder in your home directory
      //String home = System.getenv("HOME");
      String home = ".";

      String prefix = home + "/data/";
      String fileTrain = prefix + dataName + "_train" + arff_ext;
      String fileTest = prefix + dataName + "_test" + arff_ext;
      System.out.println("Data set: " + dataName);
      try {
        DataSet trainSet = new DataSet(fileTrain);
        DataSet testSet = new DataSet(fileTest);
        System.out.println("Attribute number test: " + testSet.attributes.length);
        System.out.println("Samples number test: " + testSet.samples.length);
        System.out.println("Attribute number train: " + trainSet.attributes.length);
        System.out.println("Samples number train: " + trainSet.samples.length);

        // Train and evaluate J48 using the Weka API
        ARFFReader readerTrain = new ARFFReader(fileTrain);
        ARFFReader readerTest = new ARFFReader(fileTest);
        // Calculate J48 performance
        J48 cls = new J48();
        // train the model
        cls.buildClassifier(readerTrain.instances);
        Evaluation eval = new Evaluation(readerTrain.instances);
        // Evaluate the testSet
        eval.evaluateModel(cls, readerTest.instances);

        System.out.println("=============================================");
        String csvFile = prefix + dataName +"-knn.csv";
        FileWriter writer = new FileWriter(csvFile);

        System.out.println("k,\tknn_accuracy,\t\tj48_accuracy");
        CSVUtils.writeLine(writer, Arrays.asList("k", "knn_accuracy", "j48_accuracy"));
        for (int k = 1; k < 26; k++) {
          KNN knn = new KNN(k, trainSet, testSet, 0);
          double accuracy = knn.getAccuracy();
          String istr = Integer.toString(k);
          String astr = Double.toString(accuracy);
          String jstr = Double.toString(eval.pctCorrect());
          System.out.println(istr + "\t" + astr + "\t" + jstr);
          CSVUtils.writeLine(writer, Arrays.asList(istr, astr, jstr));
        }
        writer.flush();
        writer.close();

        System.out.println("=============================================");
        int k = 5;
        csvFile = prefix + dataName +"-knn-feature-selection.csv";
        writer = new FileWriter(csvFile);

        System.out.println("n\tknn_accuracy\t\tj48_accuracy");
        CSVUtils.writeLine(writer, Arrays.asList("n", "knn_accuracy", "j48_accuracy"));
        int max = testSet.attributes.length;
        for (int n = 0; n < max; n++) {
          KNN knn_ft = new KNN(k, trainSet, testSet, n);
          double accuracy_ft = knn_ft.getAccuracy();
          String istr_ft = Integer.toString(n);
          String astr_ft = Double.toString(accuracy_ft);
          String jstr_ft = Double.toString(eval.pctCorrect());
          System.out.println(istr_ft + "\t" + astr_ft + "\t" + jstr_ft);
          CSVUtils.writeLine(writer, Arrays.asList(istr_ft, astr_ft, jstr_ft));
        }
        writer.flush();
        writer.close();


      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
