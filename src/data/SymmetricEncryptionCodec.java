package data;

public interface SymmetricEncryptionCodec {

	public Data encode(Data data);
	
	public Data decode(Data data);
}
