package no.kalli.des;

import no.kalli.IParent;
import no.kalli.Utility;
import picocli.CommandLine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


/**
 * @author Kristoffer-Andre Kalliainen
 */
public class DesServer implements IParent {

    private static DesEncryption des;

    /**
     * Main Method
     *
     * @param args
     */
    public static void main(String args[]) {
        CommandLine commandLine = new CommandLine(new Utility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        des = DesEncryption.getInstance();

        var server = new DesServer();
        // Wait for requests
        while (true) {
            server.receiveAndSend();
        }
    }

    private void receiveAndSend() {
        ServerSocket server;
        Socket client;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Waiting for requests from client...");
            client = server.accept();
            System.out.println("Connected to client at the address: " + client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            // Receive message from the client
            byte[] clientMsg = (byte[]) ois.readObject();
            byte[] decrypt = des.decrypt(clientMsg);

            // Print the message in UTF-8 format
            System.out.println("Message from DesClient: " + new String(decrypt, StandardCharsets.UTF_8));

            // Send the plaintext response message to the client
            byte[] encrypt = des.encrypt(decrypt);
            oos.writeObject(encrypt);
            oos.flush();

            // Close DesClient and DesServer sockets
            client.close();
            server.close();
            oos.close();
            ois.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
