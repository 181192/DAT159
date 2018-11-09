package roomcontrol

import cloudmqttp.YamlConfigRunner
import no.kalli.subscribe.MQTTSubDisplay

class Display {
    fun write(message: String) = println(message)
}


fun main(args: Array<String>) {
    val display = Display()
    val config = YamlConfigRunner().setup(args)

    try {
        Thread(MQTTSubDisplay(config, display)).start()
    } catch (ex: Exception) {
        println("Display: " + ex.message)
        ex.printStackTrace()
    }
}