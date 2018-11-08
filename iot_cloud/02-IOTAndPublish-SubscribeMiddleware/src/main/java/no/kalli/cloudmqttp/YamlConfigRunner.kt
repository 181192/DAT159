package no.kalli.cloudmqttp

import no.kalli.cli.Cli
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
        commandLine.parse(*args)
        if (commandLine.isUsageHelpRequested) {
            commandLine.usage(System.out)
            System.exit(1)
        }

        try {
            Files.newInputStream(Paths.get(cli.configFile)).use { `in` ->
                config = yaml.loadAs(`in`, CloudMQTTConfiguration::class.java)
                println(config.toString())
                return config
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return config
    }
}


fun main(args: Array<String>) {
    val configRunner = YamlConfigRunner()
    configRunner.setup(args)
}