package main.java;

import java.io.IOException;
import java.security.SecureRandom;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

public class SymmetricEncryptionCodec
{
   public static final int DEFAULT_KEY_SIZE = 256;
   
   
   private BufferedBlockCipher cipher;
   
   private KeyParameter keyParam;

   
   public SymmetricEncryptionCodec(byte[] key)
   {
      this( new AESEngine(), new KeyParameter( key ) );
   }

   public SymmetricEncryptionCodec( BlockCipher engine, KeyParameter keyParam )
   {
      this.cipher = new PaddedBufferedBlockCipher( new CBCBlockCipher( engine ) );
      this.keyParam = keyParam;
   }
   
   public byte[] encode( byte[] data ) throws IOException
   {
      cipher.init( true, keyParam );
      return callCipher( data );
   }

   public byte[] decode( byte[] data ) throws IOException
   {
      cipher.init( false, keyParam );
      return callCipher( data );
   }
   
   public byte[] getKey()
   {
      return keyParam.getKey();
   }

   private byte[] callCipher( byte[] data ) throws IOException
   {
      try
      {
         int size = cipher.getOutputSize( data.length );
         byte[] result = new byte[size];
         int olen = cipher.processBytes( data, 0, data.length, result, 0 );
         olen += cipher.doFinal( result, olen );
   
         if ( olen < size )
         {
            byte[] tmp = new byte[olen];
            System.arraycopy( result, 0, tmp, 0, olen );
            result = tmp;
         }
   
         return result;
      } catch ( Exception e )
      {
         throw new IOException( e );
      }
   }

   
   public static byte[] generateKey() 
   {
      return generateKey( DEFAULT_KEY_SIZE ); 
   }

   public static byte[] generateKey( int keySize ) 
   {
      SecureRandom random = new SecureRandom();
      byte[] key = new byte[keySize / 8];
      random.nextBytes( key );
      
      return key;
   }
}
