package no.kalli.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import static no.kalli.Utility.*;


class SslUtility {

    private static final String SSL_KEYSTORE = "ssl_keystore.jceks";

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
     * Get the url of the cacerts
     *
     * @return
     */
    private static URL getCaCert() {
        return SslClient.class.getClassLoader().getResource("cacerts.jceks");
    }

    /**
     * Get the SSL Context
     *
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
    private static TrustManager[] dummyTrustManager = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
    }};

}
