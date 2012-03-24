import java.util.UUID;

public class Transaction {
  int currencyAmount;
  int senderNodeID;
  int receiverNodeID;
  int signature;
 
  public Transaction(int currencyAmount, int senderNodeID, int receiverNodeID){
    this.currencyAmount = currencyAmount;
    this.senderNodeID = senderNodeID;
    this.receiverNodeID = receiverNodeID;
  }
  
}
