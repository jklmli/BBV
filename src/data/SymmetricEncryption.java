package data;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class SymmetricEncryption implements SymmetricEncryptionCodec {

	@Override
	public byte[] encode(byte[] data, Data key) {
		Cipher c = null;
		byte[] encryptedData = null;
		try {
			c = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		}
		SecretKeySpec k = new SecretKeySpec(key.getBytes(), "AES");
		try {
			c.init(Cipher.ENCRYPT_MODE, k);
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		}
		try {
			encryptedData = c.doFinal(data);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		return encryptedData;
	}

	@Override
	public byte[] decode(byte[] data, Data key) {
		byte[] encryptedData = null;
		Cipher c = null;
		try {
			c = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		SecretKeySpec k = new SecretKeySpec(key.getBytes(), "AES");
		try {
			c.init(Cipher.DECRYPT_MODE, k);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		try {
			data = c.doFinal(encryptedData);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return data;
	}

}
