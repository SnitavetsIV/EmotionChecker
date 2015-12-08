package com.siv.detecton;

import com.siv.detecton.FormatResult;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

public class NeuralNetworkBackPropagation
        extends NeuralNetworkParent
        implements INeuralNetwork {
    // The actual neural network.
    BasicNetwork m_network;

    public void postConfigure() {
        m_trainingVectors = new double[numTestVecs()][numFeatures()];
        m_emotions = new double[numTestVecs()][numEmotions()];
        m_trainVecCounter = 0;

        // Create the actual neural network.
        m_network = new BasicNetwork();
        m_network.addLayer(
                new BasicLayer(null, true, numFeatures()));
        m_network.addLayer(
                new BasicLayer(new ActivationTANH(), true, numNodes()));
        m_network.addLayer(
                new BasicLayer(new ActivationTANH(), false, numEmotions()));
        m_network.getStructure().finalizeStructure();
        m_network.reset();
    }

    public void finishTraining() {
        MLDataSet trainingSet =
                new BasicMLDataSet(m_trainingVectors, m_emotions);
        Backpropagation train =
                new Backpropagation(m_network, trainingSet);

        int epoch = 1;
        do {
            train.iteration();
            epoch++;
        }
        while ((train.getError() > m_desiredError && epoch < m_maxEpochs) ||
                epoch < m_minEpochs);
    }

    public FormatResult test(float[] reduced) {
        double[] inputArray = new double[reduced.length];
        for (int i = 0; i < reduced.length; i++) {
            inputArray[i] = (double) reduced[i];
        }

        BasicMLData input = new BasicMLData(inputArray);
        MLData output = m_network.compute(input);
        FormatResult format = new FormatResult(numEmotions());

        for (int i = 0; i < output.size(); i++) {
            // Format the output to be between 0.0 and 1.0.
            float val = (float) output.getData(i);
            val = (val + 1.0f) / 2.0f;
            format.set(i, val);
        }

        return format;
    }
}
