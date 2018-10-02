package no.kalli;

import picocli.CommandLine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Kristoffer-Andre Kalliainen
 */
public class Server implements IParent {
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

        var server = new Server();
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
            //byte[] decrypted = decryptMessage(clientMsg);

            // Print the message in UTF-8 format
            //System.out.println("Message from Client: " + new String(decrypted, StandardCharsets.UTF_8));

            //byte[] encrypted = encryptMessage(decrypted);

            // Send the plaintext response message to the client
            //oos.writeObject(encrypted);
            oos.flush();

            // Close Client and Server sockets
            client.close();
            server.close();
            oos.close();
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
