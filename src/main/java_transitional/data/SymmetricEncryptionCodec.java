package data;

public interface SymmetricEncryptionCodec {

	public Data encode(Data data, Data key);
	
	public Data decode(Data data, Data key);
}
