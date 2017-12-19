package pa4;
import java.util.*;

public class Primal {
  public Primal(ARFFReader train, ARFFReader test, int I) {
    double[] w = Utils.initEmptyArray(train.features.length + 1);
    // Account for the extra feature we are going to add
    double tau = calculateTauPrimal(train);
    // For each Iteration I (0 -> 50)
    for (int iteration = 0; iteration < I; iteration++) {
      // For each sample
      for(int i = 0; i < train.samples.length; i++) {
        DataSample sample = train.samples[i];
        double[] arr = new double[w.length];
        for(int m = 0; m < sample.values.length; m++) {
          arr[m] = sample.values[m];
        }
        arr[w.length - 1] = 1.0;
        double label = Double.parseDouble(sample.label);
        double weightedSum = Utils.innerProduct(w, arr);
        if (label * weightedSum < tau) {
          w = updateWeights(label, w, arr);
        }
      }
    }

    double acc = 0;
    // For each sample in test set
    for(int i = 0; i < test.samples.length; i++) {
      DataSample sample = test.samples[i];
      double label = Double.parseDouble(sample.label);
      double[] arr = new double[w.length];
      for(int m = 0; m < sample.values.length; m++) {
        arr[m] = sample.values[m];
      }
      arr[w.length - 1] = 1.0;
      double weightedSum = Utils.innerProduct(w, arr);
      double prediction = Utils.signum(weightedSum);
      if (prediction == label) {
        acc++;
      }
    }
    double accuracy = acc / test.samples.length * 100;
    Utils.info("primal accuracy: " + accuracy + " %");
  }

  private static double[] updateWeights(double label, double[] weights, double[] features) {
    double[] newWeights = new double[weights.length];
    for(int i = 0; i < weights.length; i++) {
      newWeights[i] = weights[i] + label * features[i];
    }
    return newWeights;
  }

  private static double calculateTauPrimal(ARFFReader train) {
    //tau = 0.1 * A
    //A = 0.0;
    //for each sample s
    //  A += sqrt(Sum(feature^2) + 1)
    //end
    // A =/ numSamples
    double tau;
    double A = 0.0;
    for (int i = 0; i < train.samples.length; i++) {
      DataSample sample = train.samples[i];
      double sum = 0.0;
      for (int j = 0; j < sample.values.length; j++) {
        double feature = sample.values[j];
        double featureSquared = feature * feature;
        sum += featureSquared;
      }
      // Account for the extra feature
      sum += 1;
      A += Math.sqrt(sum);
    }
    return 0.1 * A / train.samples.length;
  }
}
