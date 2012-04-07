package node;

import java.util.UUID;

import data.Data;

public interface DataProviderNode extends Node {

	public Data getData(UUID consumerNodeId, UUID dataId, int currencyUnitsOffered);

}
