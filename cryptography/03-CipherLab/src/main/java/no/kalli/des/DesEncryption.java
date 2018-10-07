package no.kalli.des;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Base64;

import static no.kalli.Utility.getKeyStore;
import static no.kalli.Utility.getPasswordAsCharArray;


class DesEncryption {

    private static final String DES_KEYSTORE = "des_keystore.jceks";
    private static DesEncryption utility;
    private Cipher ecipher;
    private Cipher dcipher;

    /**
     * Singleton creator for managing instances of Cipher while persist static methods, and loading the secretkey from a
     * keystore.
     *
     * @return A DesEncryption instance
     */
    static synchronized DesEncryption getInstance() {
        if (utility == null) {
            try {
                utility = new DesEncryption(getSecretKey());
            } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchProviderException e) {
                e.printStackTrace();
            }
        }
        return utility;
    }

    /**
     * Private constructor for DesEncryption. Setting up instances for encryption and decryption with a SecretKey
     *
     * @param key The secretkey
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     */
    private DesEncryption(SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        ecipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
        dcipher = Cipher.getInstance("DES/ECB/PKCS7Padding", "BC");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * Encrypt a byte array
     *
     * @param input the input
     * @return encrypted base64 byte array
     * @throws Exception
     */
    byte[] encrypt(byte[] input) throws Exception {
        // Encrypt
        byte[] enc = ecipher.doFinal(input);

        // Encode bytes to base64 to get a string
        return Base64.getEncoder().encode(enc);
    }

    /**
     * Decrypt a byte array
     *
     * @param input the input
     * @return decrypted base64 byte array
     * @throws Exception
     */
    byte[] decrypt(byte[] input) throws Exception {
        // Decode base64 to get bytes
        byte[] dec = Base64.getDecoder().decode(input);
        return dcipher.doFinal(dec);
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
}
