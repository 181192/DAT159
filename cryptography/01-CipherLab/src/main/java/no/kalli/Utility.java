package no.kalli;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import static no.kalli.Matrix.*;


/**
 * @author Kristoffer-Andre Kalliainen
 */
@CommandLine.Command(name = "CipherUltimate", footer = "Copyright(c) 2018",
        description = "Encrypt and decrypt message with provided cipher algorithm as argument")
public class Utility {

    private static final int m = 26;

    @Option(names = {"-c", "--cipher"}, required = true, paramLabel = "affine|hill", description = "Select cipher type (affine or hill)")
    private static String type;

    @Option(names = {"-a", "--keyA"}, paramLabel = "keyA", description = "Affine key A")
    private static int a;

    @Option(names = {"-b", "--keyB"}, paramLabel = "keyB", description = "Affine key B")
    private static int b;

    @Option(names = {"-k", "--keyHill"}, paramLabel = "KEY", description = "Hill key. A four letter key.")
    public static String hill;

    public static byte[] key;

    @Option(names = {"-m", "--message"}, paramLabel = "MESSAGE", description = "Message to encrypt")
    public static String message;

    public static byte[] msg;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    /**
     * Affine encrypt method
     *
     * @param input Byte array to be encrypted
     * @return A affine encrypted byte array
     */
    public static byte[] affineEncrypt(byte[] input) {

        byte[] tmp = new byte[input.length];

        for (int i = 0; i < input.length; i++)
            tmp[i] = (byte) encryptThis(input[i]);

        return tmp;
    }

    /**
     * Affine decrypt
     *
     * @param input Byte array to be decrypted
     * @return A affine decrypted byte array
     */
    public static byte[] affineDecrypt(byte[] input) {

        byte[] tmp = new byte[input.length];

        for (int i = 0; i < input.length; i++)
            tmp[i] = (byte) decryptThis(input[i]);

        return tmp;
    }

    /**
     * Helper method the encrypt one letter
     *
     * @param i The letter as byte format
     * @return The encrypted letter as int
     */
    private static int encryptThis(int i) {
        return ('A' + (a * (i + 'A') + b) % m);
    }

    /**
     * Helper method the decrypt one letter
     *
     * @param i The letter as byte format
     * @return The decrypted letter as int
     */
    private static int decryptThis(int i) {
        int x = modInverse() * ((i - 'A') - b) % m;
        return x < 0 ? ((x + m) % m) + 'A' : x + 'A';
    }

    /**
     * Modulo inverse of the key a
     *
     * @return
     */
    private static int modInverse() {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    /**
     * Hill encrypt
     *
     * @param input Byte array to be encrypted
     * @return Encrypted byte array
     */
    public static byte[] hillEncrypt(byte[] input) {
        var keyMatrix = createKeyMatrix(key);
        var tuplets = createTuples(input);

        var m_res = multiplyMatrix(keyMatrix, tuplets);
        m_res.modulo(m);

        return m_res.toBytes();
    }

    /**
     * Hill decrypt
     *
     * @param input Byte array to be encrypted
     * @return Decrypted byte array
     */
    public static byte[] hillDecrypt(byte[] input) {
        var keyMatrix = createKeyMatrix(key);
        var key_inv = keyMatrix.inverse2x2();
        key_inv.modulo(m);

        var m_res = createTuples(input);

        var c = multiplyMatrix(key_inv, m_res);
        c.modulo(m);
        c.fixNegatives();

        return c.toBytes();
    }


    /**
     * Encrypt a message based on first commandline argument
     *
     * @param plaintext byte array to encrypt
     * @return encrypted byte array
     */
    public static byte[] encryptMessage(byte[] plaintext) {
        byte[] res = new byte[0];
        switch (Utility.type) {
            case "affine":
                res = Utility.affineEncrypt(plaintext);
                break;
            case "hill":
                res = Utility.hillEncrypt(plaintext);
                break;
        }
        return res;
    }

    /**
     * Decrypt a message based on first commandline argument 1
     *
     * @param ciphertext byte array to decrypt
     * @return decrypted byte array
     */
    public static byte[] decryptMessage(byte[] ciphertext) {
        byte[] res = new byte[0];
        switch (Utility.type) {
            case "affine":
                res = Utility.affineDecrypt(ciphertext);
                break;
            case "hill":
                res = Utility.hillDecrypt(ciphertext);
                break;
        }
        return res;
    }

    /**
     * Commandline arguments checker
     *
     * @param args     Commandline arguments
     * @param isServer Is it a client or server?
     */
    static void checkArguments(String[] args, boolean isServer) {
        if (type.equals("hill"))
            key = hill.getBytes();

        if (!isServer)
            msg = message.getBytes();
    }
}
