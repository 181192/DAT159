package no.kalli.des;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

import static no.kalli.Utility.getKeyStore;
import static no.kalli.Utility.getPasswordAsCharArray;


class DesEncryption {

    public static final String DES_KEYSTORE = "des_keystore.jceks";

    private static DesEncryption utility;

    private Cipher ecipher;

    private Cipher dcipher;

    static synchronized DesEncryption getInstance() {
        if (utility == null) {
            try {
                utility = new DesEncryption(getSecretKey());
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        return utility;
    }

    DesEncryption(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    public String encrypt(String str) throws Exception {
        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return new String(Base64.getEncoder().encode(enc), StandardCharsets.UTF_8);
    }

    public String decrypt(String str) throws Exception {
        // Decode base64 to get bytes
        byte[] dec = Base64.getDecoder().decode(str);
        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, StandardCharsets.UTF_8);
    }

    /**
     * Get the Secretkey
     *
     * @return SSL Context
     */
    private static SecretKey getSecretKey() {
        KeyStore ks;
        SecretKey secretKey = null;
        try {
            ks = KeyStore.getInstance("JCEKS");
            ks.load(getKeyStore(DES_KEYSTORE), getPasswordAsCharArray());
            KeyStore.SecretKeyEntry entry = (KeyStore.SecretKeyEntry) ks.getEntry("securekey", new KeyStore.PasswordProtection(getPasswordAsCharArray()));

            secretKey = entry.getSecretKey();

        } catch (NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public static void main(String[] args) throws Exception {
        SecretKey key = getSecretKey();
        DesEncryption encrypter = null;

        try {
            encrypter = new DesEncryption(key);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        String encrypted = encrypter.encrypt("Don't tell anybody!");
        String decrypted = encrypter.decrypt(encrypted);

        System.out.println(encrypted);
        System.out.println(decrypted);
    }
}
