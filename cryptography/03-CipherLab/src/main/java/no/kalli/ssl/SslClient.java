package no.kalli.ssl;

import no.kalli.IParent;
import picocli.CommandLine;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;

import static no.kalli.ssl.SslUtility.*;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class SslClient implements IParent {

    public static void main(String[] args) {
        var commandLine = new CommandLine(new SslUtility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        configure(args);

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
            oos.writeObject(msg);
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
        setClientSystemProperties();
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
        SSLContext ctx = getSslContext();
        return ctx.getSocketFactory();
    }
}
