package no.kalli.ssl;

import picocli.CommandLine;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

@CommandLine.Command(name = "SSL",
        description = "Send message with given argument over SSL/TLS")
class SslUtility {

    @CommandLine.Option(names = {"-m", "--message"}, paramLabel = "MESSAGE", description = "Message to send securely")
    private static String message;
    static byte[] msg;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    private final static String SSL_KEYSTORE = "ssl_keystore.jceks";

    /**
     * Little hack to assign the message to an byte array
     *
     * @param args args
     */
    static void configure(String[] args) {
        msg = message.getBytes();
    }

    /**
     * Set the server system properties
     */
    static void setServerSystemProperties() {
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.keyStore", getCaCert());
        systemProps.put("javax.net.ssl.keyStorePassword", getPassword());
        System.setProperties(systemProps);
    }

    /**
     * Set the client system properties
     */
    static void setClientSystemProperties() {
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.trustStore", getCaCert());
        systemProps.put("javax.net.ssl.trustStorePassword", getPassword());
        System.setProperties(systemProps);
    }

    /**
     * Get the password from the resource file password.txt
     * @return
     */
    private static String getPassword() {
        return getStringFromInputStream(getFile("password.txt"));
    }

    /**
     * Get the password as a char array
     * @return
     */
    private static char[] getPasswordAsCharArray() {
        return getPassword().toCharArray();
    }

    /**
     * Get the url of the cacerts
     * @return
     */
    private static URL getCaCert() {
        return SslClient.class.getClassLoader().getResource("cacerts.jceks");
    }

    /**
     * Get the keystore
     * @param keystore keystore file name
     * @return Inputstream of keystore
     */
    private static InputStream getKeyStore(String keystore) {
        return getFile(keystore);
    }

    /**
     * Get a resource file
     * @param filename filename
     * @return Inputstream from the file
     */
    private static InputStream getFile(String filename) {
        return SslUtility.class.getClassLoader().getResourceAsStream(filename);
    }

    /**
     * Create a string from input stream
     * @param is
     * @return
     */
    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) sb.append(line);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Get the SSL Context
     * @return SSL Context
     */
    static SSLContext getSslContext() {
        SSLContext ctx = null;
        KeyManagerFactory kmf;
        KeyStore ks;
        try {
            ctx = SSLContext.getInstance("TLS");
            kmf = KeyManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JCEKS");
            ks.load(getKeyStore(SSL_KEYSTORE), getPasswordAsCharArray());
            kmf.init(ks, getPasswordAsCharArray());
            ctx.init(kmf.getKeyManagers(), dummyTrustManager, null);
        } catch (NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }
        return ctx;
    }

    /**
     * Getting tired of Java TrustStore made a dummy to accept everything
     */
    static TrustManager[] dummyTrustManager = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }};

}
