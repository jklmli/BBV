package edu.uiuc.cs598.project.bbv.node;

import java.util.UUID;

public interface DataProvider {

	public static class DataRequest {
		private UUID consumerNodeId;
		private UUID requestedDataId;
		private int currencyUnitsOffered;
	}

	public void registerDataRequest(DataRequest dataRequest);
}
