package pa3;
import java.util.*;

public class WeightCache {
  HashMap<Integer, WeightMatrix> cache = new HashMap<Integer, WeightMatrix>(); // this.cache;
  public WeightCache(int d, int w, int numFeatures, int numLabels) {
    for (int i = 0; i <= d; i++) {
      WeightMatrix wmatrix;
      if (i == 0) {
        wmatrix = new WeightMatrix(w, numFeatures); // first matrix is w by 64
      } else if (i == d) {
        wmatrix = new WeightMatrix(numLabels, w); // last matrix is 10 by w
      } else {
        wmatrix = new WeightMatrix(w, w); // hidden layers get the same widths
      }
      this.cache.put(i, wmatrix);
    }
  }

  public WeightMatrix get(int i) {
    return this.cache.get(i);
  }

  public String toString() {
    String tmp = "";
    for (Map.Entry<Integer, WeightMatrix> entry : this.cache.entrySet()) {
      tmp += "\n" + entry.getKey() + " : " + entry.getValue();
    }
    return tmp;
  }

  public class WeightMatrix {
    private Random random = new Random();
    double[][] weights;
    public WeightMatrix(int row, int col) {
      this.weights = new double[row][col];
      for (int i = 0; i < row; i++ ) {
        for (int j = 0; j < col ; j++ ) {
          weights[i][j] = randomInRange(-0.1, 0.1);
        }
      }
    }

    public double randomInRange(double min, double max) {
      double range = max - min;
      double scaled = random.nextDouble() * range;
      double shifted = scaled + min;
      return shifted; // == (rand.nextDouble() * (max-min)) + min;
    }

    public String toString() {
      return Arrays.deepToString(this.weights);
    }
  }
}
