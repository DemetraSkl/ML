package pa4;
import java.util.*;

public class Utils {
  public static void info(Object str) {
    System.out.println(str);
  }

  public static void error(Object str) {
    System.err.println(str);
  }

  public static double euclideanDistance(double[] vecA, double[] vecB) {
    double edist = 0.0;
    for (int i = 0; i < vecA.length; i ++) {
      double tmp = vecA[i] - vecB[i];
      edist += tmp * tmp;
    }
    return edist;
  }

  // Create function for calculating inner product of 2 vectors
  public static double innerProduct(double[] vecA, double[] vecB) {
    if (vecA.length != vecB.length) {
      Utils.error("Cant multiply unequal sized vectors");
    }
    double sum = 0.0;
    for (int i = 0; i < vecA.length; i ++) {
      sum += vecA[i] * vecB[i];
    }
    return sum;
  }

  public static double[] initEmptyArray(int n) {
   return new double[n];
  }

  // Create function for signum ( 1 if >= 0 or -1 < 0)
  public static double signum(double num) {
    if (num >= 0)
      return 1.0;
    return -1.0;
  }
}
