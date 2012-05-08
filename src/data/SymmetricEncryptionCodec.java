package data;

public interface SymmetricEncryptionCodec {
	public byte[] encode(byte[] data, Data key);
	public byte[] decode(byte[] data, Data key);

}
