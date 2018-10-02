package no.kalli.ssl;

import no.kalli.IParent;
import picocli.CommandLine;

import java.util.Properties;

@CommandLine.Command(name = "SSL",
        description = "Send message with given argument")
class SslUtility {

    @CommandLine.Option(names = {"-m", "--message"}, paramLabel = "MESSAGE", description = "Message to encrypt")
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
        systemProps.put("javax.net.ssl.trustStore", IParent.CERTIFICATES + "cacerts.jks");
        systemProps.put("javax.net.ssl.trustStorePassword", IParent.PASSWORD);
        System.setProperties(systemProps);
    }
}
