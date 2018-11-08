package no.kalli.roomcontrol

import no.kalli.cloudmqttp.YamlConfigRunner
import no.kalli.publish.MQTTPubTemperature
import no.kalli.subscribe.MQTTSubHeating

fun main(args: Array<String>) {
    val room = Room(20.0)
    val sensor = TemperatureSensor(room)
    val config = YamlConfigRunner().setup(args)
    val sensorPub = MQTTPubTemperature(config, sensor)
    val heating = Heating(room)
    val heatPub = MQTTSubHeating(config, heating)


    try {
        val tempPublisher = Thread(sensorPub)
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