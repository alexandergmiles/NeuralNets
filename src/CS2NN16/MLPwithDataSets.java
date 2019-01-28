/**
 * 
 */
package CS2NN16;

/**
 * @author shsmchlr
 * Class of a multi layer perceptron network with training, unseen and validation data sets
 * MLP has hidden layer of sigmnoidally activated neurons and then output layer(s)
 * Such a network can learn using the training set, and be tested on teh unseen set
 * In addition, it can use the validation set to decide when to stop learning
 */
public class MLPwithDataSets extends MultiLayerNetwork {

	// HINT you may need extra variables here
	
	protected DataSet unseenData;			// unseen data set
	protected DataSet validationData;		// validation set : is set to null if that set is not being used
	protected double sumOfSSEs = 0;
	protected double oldSSE = 0;
	/**
	 * Constructor for the MLP
	 * @param numIns			number of inputs	of hidden layer
	 * @param numOuts			number of outputs	of hidden layer
	 * @param data				training data set used
	 * @param nextL				next layer		
	 * @param unseen			unseen data set
	 * @param valid				validation data set
	 */
	MLPwithDataSets (int numIns, int numOuts, DataSet data, LinearLayerNetwork nextL,
						DataSet unseen, DataSet valid) {
		super(numIns, numOuts, data, nextL);	// create the MLP
												// and store the data sets
		unseenData = unseen;
		validationData = valid;
	}

	/** 
	 * initialise network before learning ...
	 */
	public void doInitialise() {
		super.doInitialise();
		// you may need extra initialisation here, both of the other data and any other variables
	}
	/**
	 * present the data to the set and return a String describing results
	 * Here it returns the performance when the training, unseen (and if available) validation
	 * sets are passed - typically responding with SSE and if appropriate % correct classification
	 */
	public String doPresent() {
		String S;
		presentDataSet(trainData);
		S = "Train: " +  trainData.dataAnalysis();
		presentDataSet(unseenData);
		S = S + " Unseen: " + unseenData.dataAnalysis();
		if (validationData != null) {
			presentDataSet(validationData);
			S = S + " Valid: " + validationData.dataAnalysis();
		}
		return S;
	}

	/**
	 * learn training data, printing SSE at 10 of the epochs, evenly spaced
	 * if a validation set available, learning stops when SSE on validation set rises
	 * this check is done by summing SSE over 10 epochs
	 * @param numEpochs		number of epochs
	 * @param lRate			learning rate
	 * @param momentum		momentum
	 * @return				String with data about learning eg SSEs at relevant epoch
	 */
	public String doLearn (int numEpochs, double lRate, double momentum) {
		String s = "";
		if (validationData==null) s = super.doLearn(numEpochs, lRate, momentum);
					// if no validation set, just use normal doLearn
		else {
			if(validationData.getSSE() == unseenData.getSSE())
			{
				return s;
			}
			else
			{
				int epochhsSoFar = unseenData.sizeSSELog();
				for(int i = 0; i < numEpochs; i++)
				{
					sumOfSSEs = validationData.getTotalSSE();
					learnDataSet(unseenData, lRate, momentum);
					presentDataSet(validationData);
					validationData.addToSSELog();
					sumOfSSEs += validationData.getTotalSSE();
					if(i % (numEpochs/10) == 0)
						if(sumOfSSEs > oldSSE)
						{
							s = s + addEpochString(i+epochhsSoFar) + " : " + validationData.dataAnalysis()+"\n";
						}
						else
						{
							oldSSE = sumOfSSEs;
							sumOfSSEs = 0;
						}
				}
			}
		}
		return s;											// return string showing learning
	}
}
