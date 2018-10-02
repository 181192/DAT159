package no.kalli.ssl;

import no.kalli.IParent;
import picocli.CommandLine;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Properties;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class SslClient implements IParent {

    public static void main(String[] args) {
        CommandLine commandLine = new CommandLine(new SslUtility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        SslUtility.configure(args);

        var client = new SslClient();
        client.sendAndReceice();
    }

    private void sendAndReceice() {
        Socket client;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            client = getClientSSLSocket();

            System.out.println("Connected to DesServer on " + client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            // send message to server
            oos.writeObject(SslUtility.msg);
            oos.flush();

            // receive response from server
            byte[] response = (byte[]) ois.readObject();

            System.out.println("Response from server: " + new String(response, StandardCharsets.UTF_8));

            // close cliet socket
            client.close();
            ois.close();
            oos.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }

    private static SSLSocket getClientSSLSocket() {
        SslUtility.setSystemProperties();
        var factory = getSslSocketFactory();

        return getSslSocket(factory);
    }

    private static SSLSocket getSslSocket(SSLSocketFactory factory) {
        SSLSocket socket = null;
        try {
            socket = (SSLSocket) factory.createSocket("localhost", SSL_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }

    private static SSLSocketFactory getSslSocketFactory() {
        SSLSocketFactory factory = null;
        SSLContext ctx;
        KeyManagerFactory kmf;
        KeyStore ks;
        char[] passphrase = PASSWORD.toCharArray();

        try {
            ctx = SSLContext.getInstance("TLS");
            kmf = KeyManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream(CERTIFICATES + "keystore.jks"), passphrase);

            kmf.init(ks, passphrase);
            ctx.init(kmf.getKeyManagers(), null, null);

            factory = ctx.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

}
