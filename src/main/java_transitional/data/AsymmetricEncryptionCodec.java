package data;

public interface AsymmetricEncryptionCodec {

	public Data encode(Data data);
	
	public Data decode(Data data);
}
