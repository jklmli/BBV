package edu.uiuc.cs598.project.bbv.data;

public interface SymmetricEncryptionCodec {

	public Data encode(Data data);
	
	public Data decode(Data data);
}
