package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import simulation.node.MemoryNodeManager;
import simulation.node.SimulationNode;
import data.Data;

public class Simulation {

	public static class Parameters
	{	
		private int dataChunkCount = 100;
		private int nodeCount = 2;
		
		public int getDataChunkCount()
		{
			return dataChunkCount;
		}
		
		public int getNodeCount()
		{
			return nodeCount;
		}
	}
	
	public static class Statistics
	{
		private AtomicLong dataTransferCount = new AtomicLong(0);
		private AtomicLong failedDataTransferCount = new AtomicLong(0);
		
		public long getDataTransferCount()
		{
			return dataTransferCount.get();
		}
		
		public void incrementDataTransferCount() {
			dataTransferCount.incrementAndGet();
		}
		
		public void incrementFailedDataTransferCount()
		{
			failedDataTransferCount.incrementAndGet();
		}

		public long getFailedDataTransferCount() {
			return failedDataTransferCount.get();
		}
	}
	
	public static final Simulation instance = new Simulation();
	
	private Parameters parameters = new Parameters();
	private Statistics statistics = new Statistics();

	// Contains the id of every data chunk in the system.  
	public Set<UUID> dataIds = new HashSet<UUID>();
	
	private List<SimulationNode> nodes = new ArrayList<SimulationNode>();

	private Simulation()
	{
		for(int cnt = 1; cnt < parameters.getDataChunkCount(); cnt++)
		{
			dataIds.add(UUID.randomUUID());
		}
	}	
		
	public static Simulation getInstance()
	{
		return instance;
	}
	
	public Parameters getParameters()
	{
		return parameters;
	}
	
	public Statistics getStatistics()
	{
		return statistics;
	}
	
	public void run()
	{
		MemoryNodeManager nodeManager = new MemoryNodeManager();
		
		// Create nodes
		for(int cnt = 1; cnt <= parameters.getNodeCount(); cnt++)
		{
			nodes.add(nodeManager.addNode());					
		}
			
		// Make node 0 a provider for all data
		SimulationNode node0 = nodes.get(0);
		for(UUID dataId : dataIds)
		{
			Data data = new Data("".getBytes());
			node0.addData(dataId, data);
		}

		// Perform operations
		
		for(SimulationNode node : nodes)
		{
			node.performOperation();
		}

		// Print statistics
		
		System.out.println("dataTransferCount: " + statistics.getDataTransferCount());
		System.out.println("failedDataTransferCount: " + statistics.getFailedDataTransferCount());
	}
	
	public Set<UUID> getDataIds() {
		return Collections.unmodifiableSet(dataIds);
	}

	public static void main(String[] args) {
		Simulation simulation = Simulation.getInstance();	
		simulation.run();		
	}

}
