package node;

import java.util.UUID;

import data.Data;

public interface DataConsumer {

	public static interface DataDownloadTask extends Runnable {
		public Data getData();
	}

	public static enum DataImportance {
		HIGH, MEDIUM, LOW
	};

	public DataDownloadTask createDownloadTask(UUID dataId, DataImportance importance);
}
