/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio.Utilidad;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec; 

/**
 *
 * @author desar
 */
public class cifrado {

    private static byte[] llave = {
        0x2d, 0x2a, 0x2d, 0x45, 0x44, 0x4f, 0x43, 0x41, 0x44, 0x4c, 0x49, 0x55, 0x42, 0x2d, 0x2a, 0x2d
    };

    public static String cifra(String xx) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(llave, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherText = cipher.doFinal(xx.getBytes("UTF8"));
            String xxCifrado = new String(Base64.getEncoder().encode(cipherText), "UTF-8"); 
            return xxCifrado;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            Logger.getLogger(cifrado.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public static String descifra(String xx) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(llave, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] cipherText = Base64.getDecoder().decode(xx.getBytes("UTF8"));
            String xxDescifrado = new String(cipher.doFinal(cipherText), "UTF-8");
            return xxDescifrado;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            Logger.getLogger(cifrado.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
