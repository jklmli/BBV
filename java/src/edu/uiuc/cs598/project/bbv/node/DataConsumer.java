package edu.uiuc.cs598.project.bbv.node;

import java.util.UUID;

import edu.uiuc.cs598.project.bbv.data.Data;

public interface DataConsumer {

	public static interface DataDownloadTask extends Runnable {
		public Data getData();
	}

	public static enum DataImportance {
		HIGH, MEDIUM, LOW
	};

	public DataDownloadTask createDownloadTask(UUID dataId, DataImportance importance);
}
