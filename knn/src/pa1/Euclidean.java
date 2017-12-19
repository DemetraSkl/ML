package pa1;

public class Euclidean {

	// Distance squared
	public static double distance_squared(double x, double y, double w) {
		return w * Math.pow((x - y), 2);
	}

	// Calculates the Euclidean distance of 2 row vector of DataPoints
	public static double euclideanDistance(DataPoint[] test, DataPoint[] train) {
		double distance = 0;
		for (int i= 0; i < test.length; i++) {
			distance += distance_squared(test[i].value, train[i].value, train[i].weight);
		}
		return Math.sqrt(distance);
	}
}
