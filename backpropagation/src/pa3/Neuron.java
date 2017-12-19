package pa3;

public class Neuron {
	double[] weights;
	Neuron[] values;

	double value;
	double target;
	double delta;
  double sigmoid;
  double weightedSum;
  double sigmoidDerivative;

	int index;
  public String label;
  private String type;

  // Input Neuron
	public Neuron(double value) {
    this.type = "input";
		this.value = value;
  }

  // Hidden Neuron
	public Neuron(double value, double[] previousLayerWeights, Neuron[] previousLayerValues) {
    this.type = "hidden";
		this.weights = previousLayerWeights;
		this.values = previousLayerValues;
		this.value = this.calculateValue();
	}

  // Output Neuron
	public Neuron(double value, double[] previousLayerWeights, Neuron[] previousLayerValues, String target, int index) {
    this.type = "output";
		this.weights = previousLayerWeights;
		this.values = previousLayerValues;
		this.value = this.calculateValue();
		this.index = index;
    this.label = target;
		this.target = targetsAgree(target, index);
		this.delta = (-1.0) * (this.target - this.value) * this.sigDerivative(this.value);
	}

	public double getDelta() {
		return this.delta;
	}

	public void setTarget(double target) {
		this.target = target;
		this.delta = (-1.0) * (this.target - this.value) * this.sigDerivative(this.value);
	}

	public void setSigmoidDerivative(double sigDev) {
		this.sigmoidDerivative = sigDev;
	}

	public void setDelta(double delta) {
		this.delta = delta;
	}

	public double getSigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-1.0 * x));
	}

	public double sigDerivative(double sig) {
		this.sigmoidDerivative = (1.0 - sig) * sig;
    return this.sigmoidDerivative;
	}

	public double getWeightedSum() {
		double sum = 0.0;
		for (int i = 0; i < this.values.length; i++) {
			double val = this.values[i].value;
			double weight = this.weights[i];
			double prod = val * weight;
       //System.out.println("INDEX: " + i);
       //System.out.println("VAL: " + val);
       //System.out.println("WEIGHT: " + weight);
       //System.out.println("PROD: " + prod);
			sum += prod;
		}
		return sum;
	}

	public double targetsAgree(String target, int index) {
		if (target.equals(Integer.toString(index))) {
			return 1.0;
		}
		return 0.0;
	}

	public double calculateValue() {
    this.weightedSum = getWeightedSum();
    this.sigmoid = getSigmoid(this.weightedSum);
		return this.sigmoid;
	}

	public double getValue() {
		return this.value;
	}

	public String toString() {
		return "{value: " + this.value
				+ ", type: " + this.type
				+ ", label: " + this.label
				+ ", target: " + this.target
				+ ", weighted sum: " + this.weightedSum
        + ", sigmoid: " + this.sigmoid
        + ", sigmoid deriv: " + this.sigmoidDerivative
				+ ", delta: " + this.delta + "}";
	}

	public void print() {
    Utils.info(this.toString());
	}
}
