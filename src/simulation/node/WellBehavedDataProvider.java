package simulation.node;

import java.util.UUID;

import node.DataProvider;

public class WellBehavedDataProvider implements DataProvider {

	@Override
	public void registerDataRequest(DataRequest dataRequest) {
	}

	@Override
	public UUID getId() {
		return null;
	}

}
