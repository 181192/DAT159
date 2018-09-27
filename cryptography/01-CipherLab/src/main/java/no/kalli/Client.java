package no.kalli;

import jdk.jshell.execution.Util;
import picocli.CommandLine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static no.kalli.Utility.decryptMessage;
import static no.kalli.Utility.encryptMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class Client implements IParent {


    public static void main(String[] args) {

        CommandLine commandLine = new CommandLine(new Utility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        Utility.checkArguments(args, false);

        var client = new Client();
        client.sendAndReceice();
    }

    private void sendAndReceice() {
        Socket client;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            client = new Socket("localhost", PORT);

            System.out.println("Connected to Server on " + client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            // send message to server
            oos.writeObject(encryptMessage(Utility.msg));
            oos.flush();

            // receive response from server
            byte[] response = (byte[]) ois.readObject();
            response = decryptMessage(response);

            System.out.println("Response from server: " + new String(response, StandardCharsets.UTF_8));

            // close cliet socket
            client.close();
            ois.close();
            oos.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

    }
}
