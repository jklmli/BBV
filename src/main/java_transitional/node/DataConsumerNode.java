package node;

import java.util.UUID;

import main.scala.data.Data;

public interface DataConsumerNode extends Node {

	public static enum DataImportance {
		HIGH, MEDIUM, LOW
	};

	public Data getData(UUID dataId);

	public Data getData(UUID dataId, DataImportance importance);

}
