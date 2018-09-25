package no.kalli;


import java.nio.charset.StandardCharsets;
import java.util.Vector;

import static no.kalli.Matrix.createKeyMatrix;
import static no.kalli.Matrix.createTuples;
import static no.kalli.Matrix.multiplyMatrix;

public class Utility {

    private static int a = 5;
    private static int b = 7;
    private static int m = 26;

    public static byte[] affineEncrypt(byte[] input) {

        byte[] tmp = new byte[input.length];

        for (int i = 0; i < input.length; i++)
            tmp[i] = (byte) encryptThis(input[i]);

        return tmp;
    }

    public static byte[] affineDecrypt(byte[] input) {

        byte[] tmp = new byte[input.length];

        for (int i = 0; i < input.length; i++)
            tmp[i] = (byte) decryptThis(input[i]);

        return tmp;
    }

    private static int encryptThis(int i) {
        return ('A' + (a * (i + 'A') + b) % m);
    }

    private static int decryptThis(int i) {
        int x = modInverse(a, m) * ((i - 'A') - b) % m;
        return x < 0 ? ((x + m) % m) + 'A' : x + 'A';
    }

    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1;
    }

    public static int gcd(int a, int b) {
        // Everything divides 0
        if (a == 0 || b == 0)
            return 0;

        // base case
        if (a == b)
            return a;

        // a is greater
        if (a > b)
            return gcd(a - b, b);
        return gcd(a, b - a);
    }


    public static byte[] hillEncrypt(byte[] msg, byte[] key) {
        var keyMatrix = createKeyMatrix(key);
        var tuplets = createTuples(msg);

        var m_res = multiplyMatrix(keyMatrix, tuplets);
        m_res.modulo(m);

        return m_res.toBytes();
    }

    public static byte[] hillDencrypt(byte[] msg, byte[] key) {
        var keyMatrix = createKeyMatrix(key);
        var key_inv = keyMatrix.inverse2x2();
        key_inv.modulo(m);

        var m_res = createTuples(msg);

        var c = multiplyMatrix(key_inv, m_res);
        c.modulo(m);
        c.fixNegatives();

        return c.toBytes();
    }


    public static void main(String[] args) {
        byte[] test = "ILOVEPIZZA".getBytes();
        byte[] encrypted = affineEncrypt(test);
        byte[] decrypted = affineDecrypt(encrypted);

        System.out.println(new String(encrypted));
        System.out.println(new String(decrypted));


        byte[] key = "PATH".getBytes();
        byte[] msg = "CIPHER".getBytes();

        // Encrypt
        encrypted = hillEncrypt(msg, key);
        System.out.println(new String(encrypted, StandardCharsets.UTF_8));


        // Decrypt
        decrypted = hillDencrypt(encrypted, key);
        System.out.println(new String(decrypted, StandardCharsets.UTF_8));

    }
}
