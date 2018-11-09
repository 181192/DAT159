package no.kalli.roomcontrol

import no.kalli.cloudmqttp.YamlConfigRunner
import no.kalli.publish.MQTTPubHeating
import no.kalli.subscribe.MQTTSubTemperature

fun main(args: Array<String>) {
    val config = YamlConfigRunner().setup(args)
    val room = Room(20.0)
    val sensor = TemperatureSensor(room)
    val heating = Heating(room)
    val sensorSub = MQTTSubTemperature(config, heating)
    val heatPub = MQTTPubHeating(config, heating)

    try {
        val tempPublisher = Thread(sensorSub)
        val heatSubscriber = Thread(heatPub)

        heatSubscriber.start()
        tempPublisher.start()

        heatSubscriber.join()
        tempPublisher.join()

    } catch (ex: Exception) {
        println("RoomDevice: " + ex.message)
        ex.printStackTrace()
    }
}