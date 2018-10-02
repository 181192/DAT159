package no.kalli.des;

import picocli.CommandLine;

@CommandLine.Command(name = "DES",
        description = "Send message with given argument")
class DesUtility {

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
        CommandLine commandLine = new CommandLine(new DesUtility());
        commandLine.parse(args);
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        msg = message.getBytes();
    }
}
