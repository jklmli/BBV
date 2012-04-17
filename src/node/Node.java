package node;

import java.util.UUID;

public interface Node{
	public UUID id();

  public void link(Node other);
  public void unlink(Node other);

  public void share(Block block);
}
