package pa4;
import java.util.*;

public class RbfKernel {
  public RbfKernel(ARFFReader train, ARFFReader test, int I, double s) {
    double[] a = Utils.initEmptyArray(train.samples.length);
    double tau = calculateTau(train, s);
    // For each Iteration I (0 -> 50)
    for (int iteration = 0; iteration < I; iteration++) {
      // For each sample
      for(int i = 0; i < train.samples.length; i++) {
        DataSample sample = train.samples[i];
        double label = Double.parseDouble(sample.label);
        double weightedSum = weightedSum(train.samples, sample, a, s);
        if (label * weightedSum < tau) {
          a[i] += 1;
        }
      }
    }

    double acc = 0.0;
    // For each sample in test set
    for(int i = 0; i < test.samples.length; i++) {
      DataSample sample = test.samples[i];
      double label = Double.parseDouble(sample.label);
      double weightedSum = weightedSum(train.samples, sample, a, s);
      double prediction = Utils.signum(weightedSum);
      if (prediction == label) {
        acc++;
      }
    }
    double accuracy = acc / test.samples.length * 100;
    Utils.info("s: " + s + ", accuracy: " + accuracy + " %");
  }

  private static double weightedSum(DataSample[] samples, DataSample sample_i, double[] a, double s) {
    double sum = 0.0;
    for(int k = 0; k < samples.length; k++) {
      DataSample sample_k = samples[k];
      double label_k = Double.parseDouble(sample_k.label);
      double a_k = a[k];
      double kernel = rbfKernel(sample_k.values, sample_i.values, s);
      sum += label_k * a_k * kernel;
    }
    return sum;
  }

  private static double calculateTau(ARFFReader train, double s) {
    double tau;
    double sum = 0.0;
    for (int i = 0; i < train.samples.length; i++) {
      DataSample sample = train.samples[i];
      double kernel = rbfKernel(sample.values, sample.values, s);
      sum += Math.sqrt(kernel);
    }
    return 0.1 * sum / train.samples.length;
  }

  private static double rbfKernel(double[] vecA, double[] vecB, double s) {
    double euclidsDistance = Utils.euclideanDistance(vecA, vecB);
    double nom = -1.0 * (euclidsDistance * euclidsDistance);
    double denom = 2.0 * s * s;
    return Math.exp(nom / denom);
  }
}
