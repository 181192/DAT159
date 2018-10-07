package no.kalli.des;

import no.kalli.IParent;
import no.kalli.Utility;
import picocli.CommandLine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static no.kalli.Utility.configure;
import static no.kalli.Utility.msg;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class DesClient implements IParent {

    private static DesEncryption des;

    public static void main(String[] args) {
        var commandLine = new CommandLine(new Utility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        configure(args);

        des = DesEncryption.getInstance();

        var client = new DesClient();
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
            oos.writeObject(des.encrypt(msg));
            oos.flush();

            // receive response from server
            var response = (byte[]) ois.readObject();
            var decrypt = des.decrypt(response);

            System.out.println("Response from server: " + new String(decrypt, StandardCharsets.UTF_8));

            // close cliet socket
            client.close();
            ois.close();
            oos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
