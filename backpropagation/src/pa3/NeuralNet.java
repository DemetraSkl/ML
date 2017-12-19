package pa3;
import java.util.*;

public class NeuralNet {
  WeightCache weightCache;
  int width;
  int depth;
  double learningRate;
  int numTargets;
  int numFeatures;
  int maxIterations;
  public NeuralNet(ARFFReader reader, int d, int w, double learningRate, int iterations) {
    this.learningRate = learningRate;
    this.depth = d;
    this.width = w;
    this.numTargets = reader.targets.length;
    this.numFeatures = reader.features.length;
    if (this.depth == 0) {
      this.width = this.numTargets;
    }

    // Generate the weights
    this.weightCache = new WeightCache(this.depth, this.width, this.numFeatures, this.numTargets);

    // Train the network
    this.maxIterations = iterations;
    train(reader.samples, this.maxIterations);
  }

  public void train(DataSample[] samples, int iterations) {
    for (int iteration = 0; iteration < iterations; iteration++ ) {
      for (int sampleIndex = 0; sampleIndex < samples.length; sampleIndex++ ) {
        //int sampleIndex = 0;
        DataSample sample = samples[sampleIndex];
        backPropagation(sample);
      }
    }
  }

  public void backPropagation(DataSample sample) {

    // Build the network
    NeuralLayer[] nnet = new NeuralLayer[this.depth + 2];

    NeuralLayer inputLayer = new NeuralLayer(sample);
    nnet[0] = inputLayer;

    for (int layer = 1; layer < nnet.length - 1; layer++) {
      NeuralLayer tmpLayer = new NeuralLayer(this.width);
      for (int i = 0; i < this.width; i++) {
        tmpLayer.neurons[i] = new Neuron(0.0, this.weightCache.get(layer - 1).weights[i], nnet[layer - 1].neurons);
      }
      nnet[layer] = tmpLayer;
    }
    NeuralLayer outputLayer = new NeuralLayer(this.numTargets);
    for (int i = 0; i < this.numTargets ; i++ ) {
      outputLayer.neurons[i] = new Neuron(0.0, this.weightCache.get(this.depth).weights[i], nnet[this.depth].neurons, sample.label, i);
    }
    nnet[this.depth + 1] = outputLayer;

    // Backpropagate and calculate Deltas
    for (int layerT = nnet.length - 2; layerT > 0 ; layerT--) {
      for (int i = 0; i < nnet[layerT].neurons.length ; i++ ) {
        Neuron hidden_neuron = nnet[layerT].neurons[i];
        double sig = hidden_neuron.value;
        double sigDev = hidden_neuron.sigDerivative(sig);
        double weighted_delta_sum = 0.0;
        double[] wc = this.weightCache.get(layerT).weights[i];
        for (int j = 0; j < wc.length ; j++ ) {
          weighted_delta_sum += wc[j] * nnet[layerT + 1].neurons[j].getDelta();
        }
        double delta = sigDev * weighted_delta_sum;
        hidden_neuron.setDelta(delta);
      }
    }

    // Update Weights
    for (Map.Entry<Integer, WeightCache.WeightMatrix> entry : this.weightCache.cache.entrySet()) {
      Integer layerC = entry.getKey();
      double[][] layerCache = entry.getValue().weights;
      for (int h = 0; h < layerCache.length ; h++ ) {
        for (int k = 0; k < layerCache[h].length ; k++ ) {
          double weight = layerCache[h][k];
          double delta = nnet[layerC + 1].neurons[h].getDelta();
          double layerCVal = nnet[layerC].neurons[k].getValue();
          double newWeight = weight - this.learningRate * delta * layerCVal;
          layerCache[h][k] = newWeight;
        }
      }
    }
  }

  public void evaluate(ARFFReader reader) {
    DataSample[] samples = reader.samples;
    double acc = 0.0;

    for (int sampleIndex = 0; sampleIndex < samples.length; sampleIndex++ ) {
      DataSample sample = samples[sampleIndex];

      NeuralLayer[] nnet = new NeuralLayer[this.depth + 2];
      NeuralLayer inputLayer = new NeuralLayer(sample);
      nnet[0] = inputLayer;

      for (int layer = 1; layer < nnet.length - 1; layer++) {
        NeuralLayer tmpLayer = new NeuralLayer(this.width);
        for (int i = 0; i < this.width; i++) {
          tmpLayer.neurons[i] = new Neuron(0.0, this.weightCache.get(layer - 1).weights[i], nnet[layer - 1].neurons);
        }
        nnet[layer] = tmpLayer;
      }
      NeuralLayer outputLayer = new NeuralLayer(this.numTargets);
      for (int i = 0; i < this.numTargets ; i++ ) {
        outputLayer.neurons[i] = new Neuron(0.0, this.weightCache.get(this.depth).weights[i], nnet[this.depth].neurons, sample.label, i);
      }
      nnet[this.depth + 1] = outputLayer;
      // Find max sigmoid entry
      double maxSigmoid = -9999.0;
      int maxSigmoidIndex = 0;
      for (int i = 0; i < outputLayer.neurons.length ; i++ ) {
        Neuron neuron = outputLayer.neurons[i];
        if (maxSigmoid < neuron.value) {
          maxSigmoid = neuron.value;
          maxSigmoidIndex = i;
        }
      }
      Neuron luckyNeuron = outputLayer.neurons[maxSigmoidIndex];
      int sampleTarget = Integer.parseInt(sample.label);
      if (sampleTarget == maxSigmoidIndex) {
        acc++;
      }
    }
    double accuracy = acc / samples.length * 100;
    Utils.info("depth: " + this.depth + "\t, width: " + this.width + "\t, learninRate: " + this.learningRate + ", iterations: " + this.maxIterations + ", accuracy: " + accuracy + " %\t-> classified " + acc + "\tout of " + samples.length + " samples correctly");
  }
}
