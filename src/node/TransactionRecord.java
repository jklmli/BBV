package node;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransactionRecord {

	public static class SatisfactionStatement
	{
		private UUID nodeId;
		private boolean complaint;

		public UUID getNodeId() {
			return nodeId;
		}
		public boolean isComplaint() {
			return complaint;
		}

		public SatisfactionStatement(UUID nodeId, boolean complaint) {
			super();
			this.nodeId = nodeId;
			this.complaint = complaint;
		}
	}

	private UUID id;
	private Map<UUID, NodeRole> nodeRoles;
	
	private List<SatisfactionStatement> satisfactionStatements;

	public TransactionRecord(UUID id, Map<UUID, NodeRole> nodeRoles,
			List<SatisfactionStatement> satisfactionStatements) {
		super();
		this.id = id;
		this.nodeRoles = nodeRoles;
		this.satisfactionStatements = satisfactionStatements;
	}

	public UUID getId() {
		return id;
	}

	public Map<UUID, NodeRole> getNodeRoles() {
		return nodeRoles;
	}

	public List<SatisfactionStatement> getSatisfactionStatements() {
		return satisfactionStatements;
	}
}
