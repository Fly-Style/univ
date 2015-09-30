import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Created by MakeYouHappy on 24.09.2015.
 */

public class Rijndael
{

    private SecretKey secretKey;
    private PublicKey publicKey;
    private KeyGenerator generator;
    private Cipher encryptCipher;

    ByteArrayOutputStream byteArray;
    CipherOutputStream cipherArray;
    Cipher decryptCipher;


    private String inputMessage;

    public void Initiate() {
        try {
            generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            secretKey = generator.generateKey();

            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(secretKey.getEncoded());
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        } catch (NoSuchAlgorithmException noAlgo) {
            System.err.println("No algorithm AES in memory");
        } catch (NoSuchPaddingException noPadding) {
            System.err.println("No such padding exeption");
        } catch (GeneralSecurityException anyOther) {
            System.err.println("General Exeption");
        }
    }

    public byte[] Encrypt (String toEncrypt) {
        try {
                byteArray = new ByteArrayOutputStream();
                cipherArray = new CipherOutputStream(byteArray, encryptCipher);

                byte[] encryptBytes = toEncrypt.getBytes();
                cipherArray.write(encryptBytes);
                cipherArray.flush();
                cipherArray.close();

                byte [] decryptBytes = byteArray.toByteArray();

                return decryptBytes;
            }
            catch (Exception e) {
                System.err.println("Something went wrong");
            }
        return null;

        }

    public String Decrypt (byte [] encryptedBytes) {

        try {
            byteArray = new ByteArrayOutputStream();
            ByteArrayInputStream inStream = new ByteArrayInputStream(encryptedBytes);
            CipherInputStream cipherInputStream = new CipherInputStream(inStream, decryptCipher);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buf)) >= 0) {
                byteArray.write(buf, 0, bytesRead);
            }
            return new String(byteArray.toString());
        }
        catch (Exception e) {
            System.err.println("Some shit happens");
        }
        return null;
    }

    public static void main(String[] args) {
        String someText = "I take it deeper, I break it down, I turned your knife around, " +
                          "I made stronger I keeped free, All the dreams I will be see";
        Rijndael algo = new Rijndael();
        algo.Initiate();

        byte [] ars = algo.Encrypt(someText);
        String res = algo.Decrypt(ars);

        System.out.println(res);

    }

}
