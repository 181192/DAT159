package cloudmqttp

import cli.Cli
import org.yaml.snakeyaml.Yaml
import picocli.CommandLine
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


class YamlConfigRunner {
    fun setup(args: Array<String>): CloudMQTTConfiguration {
        val cli = Cli()
        val yaml = Yaml()
        var config = CloudMQTTConfiguration()

        val commandLine = CommandLine(cli)
        try {
            commandLine.parse(*args)
        } catch (e: CommandLine.MissingParameterException) {
            println(e.message)
            System.exit(1)
        }

        if (commandLine.isUsageHelpRequested) {
            commandLine.usage(System.out)
            System.exit(1)
        }

        try {
            Files.newInputStream(Paths.get(cli.configFile)).use { `in` ->
                config = yaml.loadAs(`in`, CloudMQTTConfiguration::class.java)
                return config
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return config
    }
}