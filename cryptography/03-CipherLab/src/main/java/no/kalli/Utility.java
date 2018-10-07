package no.kalli;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


@CommandLine.Command(name = "Basic",
        description = "Send message with given argument")
public class Utility {

    @CommandLine.Option(names = {"-m", "--message"}, paramLabel = "MESSAGE", description = "Message to encrypt")
    private static String message;
    public static byte[] msg;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;


    /**
     * Little hack to assign the message to an byte array
     *
     * @param args args
     */
    public static void configure(String[] args) {
        msg = message.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Get the password from the resource file password.txt
     *
     * @return
     */
    public static String getPassword() {
        return getStringFromInputStream(getFile("password.txt"));
    }

    /**
     * Get the password as a char array
     *
     * @return
     */
    public static char[] getPasswordAsCharArray() {
        return getPassword().toCharArray();
    }

    /**
     * Get the keystore
     *
     * @return
     */
    public static InputStream getKeyStore(String keystore) {
        return getFile(keystore);
    }

    /**
     * Get a resource file
     *
     * @param filename filename
     * @return Inputstream from the file
     */
    private static InputStream getFile(String filename) {
        return Utility.class.getClassLoader().getResourceAsStream(filename);
    }


    /**
     * Create a string from input stream
     *
     * @param is
     * @return
     */
    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) sb.append(line);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
