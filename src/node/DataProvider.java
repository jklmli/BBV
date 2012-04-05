package node;

import java.util.UUID;

public interface DataProvider extends Node{

	public static class DataRequest {
		private UUID consumerNodeId;
		private UUID requestedDataId;
		private int currencyUnitsOffered;
	}

	public void registerDataRequest(DataRequest dataRequest);
}
