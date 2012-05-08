package data;

import java.util.List;

public interface SharedSecretCodec {
	public Share decode(List<Share> fragments);
	public List<Share> encode(Data secret);
}
