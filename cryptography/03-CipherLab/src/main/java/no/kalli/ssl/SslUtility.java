package no.kalli.ssl;

import picocli.CommandLine;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.Properties;

@CommandLine.Command(name = "SSL",
        description = "Send message with given argument over SSL/TLS")
class SslUtility {

    @CommandLine.Option(names = {"-m", "--message"}, paramLabel = "MESSAGE", description = "Message to send securely")
    private static String message;
    static byte[] msg;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;


    /**
     * Little hack to assign the message to an byte array
     *
     * @param args args
     */
    static void configure(String[] args) {
        msg = message.getBytes();
    }

    static void setSystemProperties() {
        Properties systemProps = System.getProperties();
        systemProps.put("javax.net.ssl.trustStore", getCaCert());
        systemProps.put("javax.net.ssl.trustStorePassword", getPassword());
        System.setProperties(systemProps);
    }

    private static String getPassword() {
        String password = null;
        try {
            password = getStringFromInputStream(getFile("password.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return password;
    }

    static char[] getPasswordAsCharArray() {
        return getPassword().toCharArray();
    }

    private static String getCaCert() {
        String cacert = null;
        try {
            cacert = getStringFromInputStream(getFile("cacerts.jks"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacert;
    }

    static FileInputStream getKeyStore() {
        FileInputStream fi = null;
        try {
            fi = getFile("keystore.jks");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fi;
    }

    private static FileInputStream getFile(String filename) throws IOException {
        ClassLoader classLoader = SslUtility.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        return new FileInputStream(file);
    }

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

    static SSLContext getSslContext() {
        SSLContext ctx = null;
        KeyManagerFactory kmf;
        KeyStore ks;
        try {
            ctx = SSLContext.getInstance("TLS");
            kmf = KeyManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JKS");
            ks.load(getKeyStore(), getPasswordAsCharArray());
            kmf.init(ks, getPasswordAsCharArray());
            ctx.init(kmf.getKeyManagers(), null, null);
        } catch (NoSuchAlgorithmException | IOException | CertificateException | UnrecoverableKeyException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
        return ctx;
    }
}
