package roomcontrol

import cloudmqttp.YamlConfigRunner
import no.kalli.publish.MQTTPubHeating
import no.kalli.subscribe.MQTTSubTemperature

fun main(args: Array<String>) {
    val config = YamlConfigRunner().setup(args)
    val heatPub = MQTTPubHeating(config)
    val sensorSub = MQTTSubTemperature(config, heatPub)

    try {
        Thread(sensorSub).apply {
            start()
            join()
        }
    } catch (ex: Exception) {
        println("HeatController: " + ex.message)
        ex.printStackTrace()
    }
}