package proyecto03;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Aes implements Serializable {

    //Sirve para proporcionar mayor seguridad a la llave
    private static final String salt = "Modelado";
    //Llave después de cifrar con Hash-256 
    protected static String llave;

    
    public Aes() {
    }
    
    public String getAES(String data) {
        System.out.print("Ingrese la llave para poder cifrar el texto: ");
        Scanner sc = new Scanner(System.in);
        // entrada de una cadena
        String llaveUsuario = sc.nextLine();
        
        try {
            byte[] iv = new byte[16];
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(llaveUsuario.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            
            llave =  Base64.getEncoder().encodeToString(secretKey.getEncoded());
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
            
        } catch (UnsupportedEncodingException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return null;
    }
    
    public String getAESDecrypt(String data){
        //Es la que se ingresa por con consola
        System.out.print("Ingrese la llave para poder descifrar el texto: ");
        Scanner sc = new Scanner(System.in);
        // entrada de una cadena
        String llaveUsuario = sc.nextLine();
        
        byte[] iv = new byte[16];
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec keySpec = new PBEKeySpec(llaveUsuario.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
            
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return null;
    }
}
