package pa4;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    // final String home = System.getenv("HOME");
    final String home = ".";
    final String dataDir = home + "/data";
    if (args.length > 0) {
      String dataSetName = "additional";
      Utils.info("=======================");
      Utils.info("DATA SET: " + dataSetName);
      Utils.info("=======================");
      String trainFile = dataDir + "/" + dataSetName + "Training.arff";
      String testFile = dataDir + "/" + dataSetName + "Test.arff";
      ARFFReader train = new ARFFReader(trainFile);
      ARFFReader test = new ARFFReader(testFile);
      run(train, test);
    } else {
      final String[] dataSets = {"sonar", "breast", "back", "A", "B", "C"};
      // Put your data in your home folder
      // /home/<user>/data
      for (int i = 0; i < dataSets.length; i++) {
        String dataSetName = dataSets[i];
        Utils.info("=======================");
        Utils.info("DATA SET: " + dataSetName);
        Utils.info("=======================");
        String trainFile = dataDir + "/" + dataSetName + "Train.arff";
        String testFile = dataDir + "/" + dataSetName + "Test.arff";
        ARFFReader train = new ARFFReader(trainFile);
        ARFFReader test = new ARFFReader(testFile);
        run(train, test);
      }
    }
  }

  private static void run(ARFFReader train, ARFFReader test) {
    Utils.info("TRAIN SET DATA:");
    Utils.info("NUM TARGETS: " + train.targets.length);
    Utils.info("NUM FEATURES: " + train.features.length);
    Utils.info("NUM SAMPLES: " + train.samples.length);

    Utils.info("TEST SET DATA:");
    Utils.info("NUM TARGETS: " + test.targets.length);
    Utils.info("NUM FEATURES: " + test.features.length);
    Utils.info("NUM SAMPLES: " + test.samples.length);

    int I = 50; // 50
    //double s = 10;
    // Calculate tau and any other constant.
    Utils.info("ITERATIONS: " + I);
    Utils.info("\nRunning primal algorithm:\n");
    Primal p = new Primal(train, test, I);
    Utils.info("\nRunning polynomial kernel algorithm:\n");
    PolyKernel pKernel = new PolyKernel(train, test, I, 1);
    pKernel = new PolyKernel(train, test, I, 2);
    pKernel = new PolyKernel(train, test, I, 3);
    pKernel = new PolyKernel(train, test, I, 4);
    pKernel = new PolyKernel(train, test, I, 5);
    //radialBasisFunctionKernel(train, test, I, s);
    Utils.info("\nRunning rbf kernel algorithm:\n");
    RbfKernel rKernel = new RbfKernel(train, test, I, 0.1);
    rKernel = new RbfKernel(train, test, I, 0.5);
    rKernel = new RbfKernel(train, test, I, 1);
    rKernel = new RbfKernel(train, test, I, 2);
    rKernel = new RbfKernel(train, test, I, 5);
    rKernel = new RbfKernel(train, test, I, 10);
  }
}
