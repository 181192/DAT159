package cli


import picocli.CommandLine

@CommandLine.Command(name = "MQTTP Temperature", description = ["02-IOT and Publish-Subscribe Middleware"])
class Cli {

    @CommandLine.Option(names = ["-f", "--file"], required = true, paramLabel = "filename", description = ["Name of config file for CloudMQTTP"])
    var configFile: String = ""

    @CommandLine.Option(names = ["-h", "--help"], usageHelp = true, description = ["Display a help message"])
    var helpRequested = false

}
