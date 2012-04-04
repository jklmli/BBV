package data;

import java.util.List;

public interface SharedSecretCodec 
{
	public Data decode(List<Data> fragments);
	public List<Data> encode(Data secret);	
}
