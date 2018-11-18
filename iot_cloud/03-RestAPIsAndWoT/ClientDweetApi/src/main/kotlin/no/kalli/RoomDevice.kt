package no.kalli

import no.kalli.api.HeaterClient
import no.kalli.api.TemperatureClient

fun main(args: Array<String>) {

    val room = Room(20.0)
    val sensor = TemperatureSensor(room)
    val actuator = HeatingActuator(room)

    try {
        val temperatureClient = Thread(TemperatureClient(thingName = "kalli-temperature", sensor = sensor))

        val heaterClient = Thread(HeaterClient(thingName = "kalli-heater", actuator = actuator))

        temperatureClient.start()
        heaterClient.start()

        temperatureClient.join()
        heaterClient.join()

    } catch (e: Exception) {
        println("RoomDevice: " + e.message)
        e.printStackTrace()
    }
}