import java.util.ArrayList;

class NeuralNetwork {

    private ArrayList<InputLayer> inputLayers = new ArrayList<>();              //the input layer (duh)
    private ArrayList<double[][]> bestWeightArray = new ArrayList<>();          //the weight array for the best weights

    private int hidden_neurons;
    private double learning_rate;
    private double error_tolerance;
    private int inputs = 4097;                                                  //64 * 64 (size of the square) + 1
    private int outputs = 10;                                                   //10 outputs

    NeuralNetwork(int hidden_neurons, double learning_rate, double error_tolerance) {
        this.hidden_neurons = hidden_neurons;
        this.learning_rate = learning_rate;
        this.error_tolerance = error_tolerance;
        bestWeightArray.add(initializeInputWeights());                  //initializing the best weight array
        bestWeightArray.add(initializeHiddenWeights());
        bestWeightArray.add(initializeOutputWeights());
    }

    void addSample(InputLayer new_inputLayer) {
        this.inputLayers.add(new_inputLayer);
    }

    private void backPropagation() {

        ArrayList<double[][]> weightArray = new ArrayList<>();

        weightArray.add(initializeInputWeights());                      //first hidden layer
        weightArray.add(initializeHiddenWeights());
        weightArray.add(initializeOutputWeights());                     //output layer

        int total_error = 1;                                            //the total error
        int epochs = 0;                                                 //number of epochs
        while (epochs < 1000 && total_error > error_tolerance) {

            for (InputLayer inputLayer : inputLayers) {

                double[] input_v = new double[inputs];                  //the v1 for the input layer
                double[] input_y = new double[inputs];                  //the y1 for the input layer
                double[] hidden_v = new double[hidden_neurons + 1];     //the v for the hidden layer
                double[] hidden_y = new double[hidden_neurons + 1];     //the y for the hidden layer
                double[] output_v = new double[outputs];                //the v for the output layer
                double[] output = new double[outputs];                  //the output

                int[] desired_output = new int[outputs];                //initialize the desired output
                desired_output[inputLayer.getOutput()] = 1;

                ///////// FORWARD

                for (int i = 1; i <= hidden_neurons; i++) {
                    double weight_sum = 0;
                    for (int j = 1; j < inputs; j++) {
                        weight_sum += weightArray.get(0)[i][j] * inputLayer.getInput(j);
                    }
                    input_v[i] = weightArray.get(0)[i][0] * (-1) + weight_sum;
                    input_y[i] = 1 / (1 + Math.exp(-input_v[i]));
                }

                for (int i = 1; i <= hidden_neurons; i++) {
                    double weight_sum = 0;
                    for (int j = 1; j <= hidden_neurons; j++) {
                        weight_sum += weightArray.get(1)[i][j] * inputLayer.getInput(j);
                    }
                    hidden_v[i] = weightArray.get(1)[i][0] * (-1) + weight_sum;
                    hidden_y[i] = 1 / (1 + Math.exp(-hidden_v[i]));
                }

                for (int i = 0; i < outputs; i++) {
                    double weight_sum = 0;
                    for (int j = 1; j <= hidden_neurons; j++) {
                        weight_sum += weightArray.get(2)[i][j] * input_y[j];
                    }

                    output_v[i] = weightArray.get(2)[i][0] * (-1) + weight_sum;
                    output[i] = 1 / (1 + Math.exp(-output_v[i]));
                }

                ////////calculating the gradients

                double[] out_delta = new double[outputs];
                double[] hidden_delta = new double[hidden_neurons + 1];
                double[] input_delta = new double[hidden_neurons + 1];

                for (int i = 0; i < outputs; i++) {
                    out_delta[i] = output[i] * (1 - output[i]) * (desired_output[i] - output[i]);
                }

                for (int i = 0; i < outputs; i++) {
                    double delta_sum = 0;
                    for (int j = 1; j <= hidden_neurons; j++) {
                        delta_sum += weightArray.get(2)[i][j] * out_delta[i];
                    }
                    input_delta[i] = hidden_y[i] * (1 - hidden_y[i]) * delta_sum;
                }

                for (int i = 1; i <= hidden_neurons; i++) {
                    double delta_sum = 0;
                    for (int j = 0; j <= hidden_neurons; j++) {
                        delta_sum += weightArray.get(1)[i][j] * hidden_delta[i];
                    }
                    hidden_delta[i] = input_y[i] * (1 - input_y[i]) * delta_sum;
                }

                ///////////////updating the weights

                for (int i = 0; i < outputs; i++) {
                    for (int j = 1; j <= hidden_neurons; j++) {
                        weightArray.get(2)[i][j] += learning_rate * out_delta[i] * input_y[j];
                    }
                }

                for (int i = 1; i <= hidden_neurons; i++) {
                    for (int j = 1; j <= hidden_neurons; j++) {
                        weightArray.get(1)[i][j] += learning_rate * hidden_delta[i] * hidden_y[j];
                    }
                }

                for (int i = 1; i <= hidden_neurons; i++) {
                    for (int j = 1; j < inputs; j++) {
                        weightArray.get(0)[i][j] += learning_rate * input_delta[i] * inputLayer.getInput(j);
                    }
                }

                for (int i = 1; i <= hidden_neurons; i++) {
                    weightArray.get(0)[i][0] += learning_rate * input_delta[i] * (-1);
                }

                for (int i = 0; i < outputs; i++) {
                    double temp_error = Math.pow(output[i] - desired_output[i], 2) / 2;
                    total_error++;
                }
            }
            epochs++;
        }

        for (int i = 0; i <= 2; i++) {
            bestWeightArray.set(i, weightArray.get(i));
        }

        bestWeightArray = weightArray;
    }

    int guessNumber(InputLayer inputLayer) {

        backPropagation();

        double[] input_v = new double[inputs];
        double[] input_y = new double[inputs];
        double[] hidden_v = new double[hidden_neurons + 1];
        double[] hidden_y = new double[hidden_neurons + 1];
        double[] output_v = new double[outputs];                                     //output layer
        double[] output = new double[outputs];

        int[] desired_output = new int[outputs];                                     //initialize the desired output
        desired_output[inputLayer.getOutput()] = 1;

        for (int i = 1; i <= hidden_neurons; i++) {
            double weight_sum = 0;
            for (int j = 1; j < inputs; j++) {
                weight_sum += bestWeightArray.get(0)[i][j] * inputLayer.getInput(j);
            }
            input_v[i] = bestWeightArray.get(0)[i][0] * (-1) + weight_sum;
            input_y[i] = 1 / (1 + Math.exp(-input_v[i]));
        }

        for (int i = 1; i <= hidden_neurons; i++) {
            double weight_sum = 0;
            for (int j = 1; j <= hidden_neurons; j++) {
                weight_sum += bestWeightArray.get(1)[i][j] * inputLayer.getInput(j);
            }
            hidden_v[i] = bestWeightArray.get(1)[i][0] * (-1) + weight_sum;
            hidden_y[i] = 1 / (1 + Math.exp(-hidden_v[i]));
        }

        for (int i = 0; i < outputs; i++) {
            double weight_sum = 0;
            for (int j = 1; j <= hidden_neurons; j++) {
                weight_sum += bestWeightArray.get(2)[i][j] * input_y[j];
            }

            output_v[i] = bestWeightArray.get(2)[i][0] * (-1) + weight_sum;
            output[i] = 1 / (1 + Math.exp(-output_v[i]));
        }

        /// finding out the result based on the maximum output
        int result = 0;
        double max = 0;
        for (int i = 0; i < outputs; i++) {
            if (max <= output[i]) {
                max = output[i];
                result = i;
            }
        }

        return result;
    }

    private double[][] initializeInputWeights() {

        double[][] weights = new double[hidden_neurons + 1][inputs];

        for (int i = 1; i <= hidden_neurons; i++) {
            for (int j = 1; j < inputs; j++) {
                weights[i][j] = 0.5 - Math.random();                                   //random values from 0.5 to -0.5
            }
        }
        return weights;
    }

    private double[][] initializeHiddenWeights() {

        double[][] weights = new double[hidden_neurons + 1][hidden_neurons + 1];

        for (int i = 1; i <= hidden_neurons; i++) {
            for (int j = 1; j <= hidden_neurons; j++) {
                weights[i][j] = 0.5 - Math.random();
            }
        }
        return weights;
    }

    private double[][] initializeOutputWeights() {

        double[][] weights = new double[outputs][hidden_neurons + 1];

        for (int i = 0; i < outputs; i++) {
            for (int j = 1; j <= hidden_neurons; j++) {
                weights[i][j] = 0.5 - Math.random();
            }
        }
        return weights;
    }
}
