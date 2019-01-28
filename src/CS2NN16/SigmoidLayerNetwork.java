/**
 * 
 */
package CS2NN16;

import java.util.ArrayList;


/**
 * @author shsmchlr
 * This is a class for a layer of neurons with sigmoidal activation
 * All such neurons share the same inputs.
 */
public class SigmoidLayerNetwork extends LinearLayerNetwork {

	/**
	 * Constructor for neuron
	 * @param numIns	how many inputs there are (hence how many weights needed)
	 * @param numOuts	how many outputs there are (hence how many neurons needed)
	 * @param data		the data set used to train the network
	 */
	public SigmoidLayerNetwork(int numIns, int numOuts, DataSet data) {
		super(numIns, numOuts, data);
	}
	
	/**
	 * calcOutputs of neuron
	 * @param nInputs	
	 * 
	 */
	protected void calcOutputs(ArrayList<Double> nInputs) {
		//Calling the super method
		super.calcOutputs(nInputs);
		//Iterating over the outputs to assign sigmoid values
		for(int i = 0; i < outputs.size(); i++)
		{
			//Setting the current index to the result of the calculation
			//Uses Math.exp to return e raised to power of the output
			outputs.set(i, 1.0/(1.0 + Math.exp(-outputs.get(i))));
		}
	}

	/**
	 * find deltas
	 *	@param errors	
	 */
	protected void findDeltas(ArrayList<Double> errors) {
		//Iterating over the errors to calculate delta
		for(int i = 0; i < errors.size(); i++)
		{
			//Declaring local variables to improve readability
			double error, output;
			error = errors.get(i);
			output = outputs.get(i);
			//Setting the delta values to the result of the calculation
			deltas.set(i, (error * output * (1.0 - output)));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// test with and or xor
		DataSet AndOrXor = new DataSet("2 3 %.0f %.0f %.3f;x1 x2 AND OR XOR;0 0 0 0 0;0 1 0 1 1;1 0 0 1 1;1 1 1 1 0");
		SigmoidLayerNetwork SN = new SigmoidLayerNetwork(2, 3, AndOrXor);
		SN.setWeights("0.2 0.5 0.3 0.3 0.5 0.1 0.4 0.1 0.2");
		SN.doInitialise();
		System.out.println(SN.doPresent());
		System.out.println("Weights " + SN.getWeights());
		System.out.println(SN.doLearn(1000,  0.15,  0.4));
		System.out.println(SN.doPresent());
		System.out.println("Weights " + SN.getWeights());

	}

}
