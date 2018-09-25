package no.kalli;


import java.util.Vector;

public class Utility2 {

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

    public static byte[] hillEncrypt(byte[] input) {

        return null;
    }

    public static byte[] hillDecrypt(byte[] input) {
        return null;
    }


    public static void main(String[] args) {
        byte[] test = "ILOVEPIZZA".getBytes();
        byte[] encrypted = affineEncrypt(test);
        byte[] decrypted = affineDecrypt(encrypted);

        System.out.println(new String(encrypted));
        System.out.println(new String(decrypted));



    }
}
