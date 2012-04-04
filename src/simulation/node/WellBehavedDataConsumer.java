package simulation.node;

import java.util.UUID;

import node.DataConsumer;

public class WellBehavedDataConsumer implements DataConsumer {

	@Override
	public DataDownloadTask createDownloadTask(UUID dataId,
			DataImportance importance) {
		return null;
	}

}
