package no.kalli.ssl;

import no.kalli.IParent;
import picocli.CommandLine;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import static no.kalli.ssl.SslUtility.*;


/**
 * @author Kristoffer-Andre Kalliainen
 */
public class SslServer implements IParent {

    /**
     * Main Method
     *
     * @param args
     */
    public static void main(String args[]) {
        var commandLine = new CommandLine(new SslUtility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        var server = new SslServer();

        // Wait for requests
        while (true) server.receiveAndSend();
    }

    private void receiveAndSend() {
        ServerSocket server;
        Socket client;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            server = getSocket();
            System.out.println("Waiting for requests from client...");
            client = server.accept();
            System.out.println("Connected to client at the address: " + client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            // Receive message from the client
            byte[] clientMsg = (byte[]) ois.readObject();

            // Print the message in UTF-8 format
            System.out.println("Message from DesClient: " + new String(clientMsg, StandardCharsets.UTF_8));

            // Send the plaintext response message to the client
            oos.writeObject(clientMsg);
            oos.flush();

            // Close DesClient and DesServer sockets
            client.close();
            server.close();
            oos.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static SSLServerSocket getSocket() {
        setServerSystemProperties();
        var ssf = getSslServerSocketFactory();

        return getSslServerSocket(ssf);
    }

    private static SSLServerSocket getSslServerSocket(SSLServerSocketFactory ssf) {
        SSLServerSocket sslServerSocket = null;
        try {
            sslServerSocket = (SSLServerSocket) ssf.createServerSocket(SSL_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sslServerSocket;
    }

    private static SSLServerSocketFactory getSslServerSocketFactory() {
        SSLContext ctx = getSslContext();
        return ctx.getServerSocketFactory();
    }

}
