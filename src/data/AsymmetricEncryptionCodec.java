package edu.uiuc.cs598.project.bbv.data;

public interface AsymmetricEncryptionCodec {

	public Data encode(Data data);
	
	public Data decode(Data data);
}
