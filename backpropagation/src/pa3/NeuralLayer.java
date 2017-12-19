package pa3;

public class NeuralLayer {
	Neuron[] neurons;
	public NeuralLayer(int size) {
		this.neurons = new Neuron[size];
	}

	public NeuralLayer(DataSample sample) {
		this.neurons = new Neuron[sample.values.length];
		for (int i = 0; i < sample.values.length ; i++ ) {
			this.neurons[i] = new Neuron(sample.values[i]);
		}
	}

  public void addNeuron(Neuron neuron, int index) {
    this.neurons[index] = neuron;
  }

  public String toString() {
    String str = "NEURAL LAYER:";
		for (int i = 0; i < this.neurons.length ; i++ ) {
			str += "\n" + i + ": " + this.neurons[i].toString();
		}
    return str;
  }
}
