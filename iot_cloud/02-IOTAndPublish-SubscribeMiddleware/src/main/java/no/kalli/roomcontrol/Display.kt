package no.kalli.roomcontrol

import no.kalli.cloudmqttp.YamlConfigRunner
import no.kalli.subscribe.MQTTSubDisplay

class Display {
    fun write(message: String) = println(message)
}


fun main(args: Array<String>) {
    val display = Display()
    val config = YamlConfigRunner().setup(args)
    val sensorSub = Thread(MQTTSubDisplay(config, display)).start()
}