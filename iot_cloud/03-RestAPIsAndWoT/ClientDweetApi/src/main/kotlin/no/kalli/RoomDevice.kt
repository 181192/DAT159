package no.kalli

import no.kalli.api.HeaterClient
import no.kalli.api.TemperatureClient

fun main(args: Array<String>) {

    val room = Room(20.0)
    val sensor = TemperatureSensor(room)
    val temperatureClient = TemperatureClient(thingName = "kalli-temperature", sensor = sensor)

    val actuator = HeatingActuator(room)
    val heaterClient = HeaterClient(thingName = "kalli-heater", actuator = actuator)

    try {
        val temp = Thread(temperatureClient)
        val heat = Thread(heaterClient)

        heat.start()
        temp.start()

        heat.join()
        temp.join()

    } catch (e: Exception) {
        println("RoomDevice: " + e.message)
        e.printStackTrace()
    }
}