package node;

import java.util.UUID;

import data.Data;

public interface DataConsumerNode extends Node {

	public static enum DataImportance {
		HIGH, MEDIUM, LOW
	};

	
	/**
	 * Get the data with the given id
	 */
	public Data getData(UUID dataId);

	/**
	 * Get the data with the given id. The importance parameter adjusts the 
	 * maximum amount of currency that will be offered 
	 */
	public Data getData(UUID dataId, DataImportance importance);
}
