package no.kalli;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class HashUtil {

    public static String addressFromPublicKey(PublicKey pk) {
        byte[] pkBytes = pk.getEncoded();
        byte[] hash = sha256Hash(pkBytes);
        return DatatypeConverter.printBase64Binary(hash);
    }

    public static byte[] sha256Hash(String s) {
        try {
            return sha256Hash(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha256Hash(byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Encode(byte[] bytes) {
        return DatatypeConverter.printBase64Binary(bytes);
    }

    public static byte[] base64Decode(String s) {
        return DatatypeConverter.parseBase64Binary(s);
    }

}
