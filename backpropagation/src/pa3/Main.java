package pa3;
import java.util.*;

public class Main {
  public static void main(String[] args) {

    final double learningRate = 0.1;
    final int iterations = 200;
    String dataSetName = "optdigits";
    // Put your data in your home folder
    // /home/<user>/data
    // String home = System.getenv("HOME");
    String home = ".";
    String dataDir = home + "/data";
    String trainFile = dataDir + "/" + dataSetName + "_train.arff";
    String testFile = dataDir + "/" + dataSetName + "_test.arff";
    ARFFReader train = new ARFFReader(trainFile);
    ARFFReader test = new ARFFReader(testFile);
    Utils.info("TRAIN SET DATA:");
    Utils.info("NUM TARGETS: " + train.targets.length);
    Utils.info("NUM FEATURES: " + train.features.length);
    Utils.info("NUM SAMPLES: " + train.samples.length);

    Utils.info("TEST SET DATA:");
    Utils.info("NUM TARGETS: " + test.targets.length);
    Utils.info("NUM FEATURES: " + test.features.length);
    Utils.info("NUM SAMPLES: " + test.samples.length);

    // Init layers
    int[] dlayers = {1, 2, 3, 4};
    int[] nWidths = {1, 2, 5, 10};

    // Error out if our train and test sets are irregular
    if (train.targets.length != test.targets.length) {
      Utils.error("Test Set Labels are not the same as the ones in the Train Set");
    }
    if (train.features.length != test.features.length) {
      Utils.error("Test Set Features are not the same as the ones in the Train Set");
    }

    // Run the zero case independently only once
    //runExperiment(0, 0, train, test, learningRate, iterations);
    (new Thread(new NeuralNetRunner(0, 0, train, test, learningRate, iterations))).start();

    // Run the rest of the case combinations
    for(int i = 0; i < dlayers.length; i++) {
      for(int j = 0; j < nWidths.length; j++) {
        int d = dlayers[i];
        int w = nWidths[j];
        // Make sure we only run the network for
        // numTargets >= width
        if (train.targets.length >= w) {
          (new Thread(new NeuralNetRunner(d, w, train, test, learningRate, iterations))).start();
          //runExperiment(d, w, train, test, learningRate);
        }
      }
    }
  }

  public static void runExperiment(int depth, int width, ARFFReader trainData, ARFFReader testData, double learningRate, int iterations) {
    //Utils.info("============================");
    //Utils.info("Running Experiment");
    //Utils.info("============================");
    NeuralNet net = new NeuralNet(trainData, depth, width, learningRate, iterations);
    net.evaluate(testData);
  }

  public static class NeuralNetRunner implements Runnable {
    ARFFReader trainData;
    ARFFReader testData;
    int depth;
    int width;
    double learningRate;
    int iterations;
    public NeuralNetRunner(int depth, int width, ARFFReader trainData, ARFFReader testData, double learningRate, int iterations) {
      this.trainData = trainData;
      this.testData = testData;
      this.depth = depth;
      this.width = width;
      this.learningRate = learningRate;
      this.iterations = iterations;
    }

    public void run() {
      runExperiment(this.depth, this.width, this.trainData, this.testData, this.learningRate, this.iterations);
    }
  }
}
