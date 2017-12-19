package pa4;
import java.util.*;

public class PolyKernel {
  public PolyKernel(ARFFReader train, ARFFReader test, int I, int d) {
    double[] a = Utils.initEmptyArray(train.samples.length);
    double tau = calculateTau(train, d);
    // For each Iteration I (0 -> 50)
    for (int iteration = 0; iteration < I; iteration++) {
      // For each sample
      for(int i = 0; i < train.samples.length; i++) {
        DataSample sample = train.samples[i];
        double label = Double.parseDouble(sample.label);
        double weightedSum = weightedSum(train.samples, sample, a, d);
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
      double weightedSum = weightedSum(train.samples, sample, a, d);
      double prediction = Utils.signum(weightedSum);
      if (prediction == label) {
        acc++;
      }
    }
    double accuracy = acc / test.samples.length * 100;
    Utils.info("d: " + d + ", accuracy: " + accuracy + " %");
  }

  private static double weightedSum(DataSample[] samples, DataSample sample_i, double[] a, int d) {
    double sum = 0.0;
    for(int k = 0; k < samples.length; k++) {
      DataSample sample_k = samples[k];
      double label_k = Double.parseDouble(sample_k.label);
      double a_k = a[k];
      double kernel = polyKernel(sample_k.values, sample_i.values, d);
      sum += label_k * a_k * kernel;
    }
    return sum;
  }

  private static double calculateTau(ARFFReader train, int d) {
    double tau;
    double sum = 0.0;
    for (int i = 0; i < train.samples.length; i++) {
      DataSample sample = train.samples[i];
      double kernel = polyKernel(sample.values, sample.values, d);
      sum += Math.sqrt(kernel);
    }
    return 0.1 * sum / train.samples.length;
  }

  private static double polyKernel(double[] vecA, double[] vecB, int d) {
    double dotProduct = Utils.innerProduct(vecA, vecB);
    return Math.pow(dotProduct + 1.0, d);
  }
}
