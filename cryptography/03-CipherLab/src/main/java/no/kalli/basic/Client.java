package no.kalli.basic;

import no.kalli.IParent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class Client implements IParent {

    public static void main(String[] args) {
        BasicUtility.configure(args);

        var client = new Client();
        client.sendAndReceice();
    }

    private void sendAndReceice() {
        Socket client;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        try {
            client = new Socket("localhost", PORT);

            System.out.println("Connected to DesServer on " + client.getInetAddress());

            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());

            // send message to server
            oos.writeObject(BasicUtility.msg);
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
}
