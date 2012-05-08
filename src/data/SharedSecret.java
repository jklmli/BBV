package data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class SharedSecret implements SharedSecretCodec {

	public Share decode(List<Share> fragments) {
	  List<Share> someShares = new ArrayList<Share>();
      someShares.add(fragments.get(2));
      someShares.add(fragments.get(7));
      ShareCombiner combiner = new ShareCombiner(someShares);
      System.err.println(new String(combiner.combine()));
	return null;
	}

	public List<Share> encode(Data secret) {
		SecureRandom random = new SecureRandom();	
		ShareBuilder builder = new ShareBuilder(secret.getBytes(), 2, 512, random);
		List<Share> shares = builder.build(10);
		
		return shares;
	}
	
}
