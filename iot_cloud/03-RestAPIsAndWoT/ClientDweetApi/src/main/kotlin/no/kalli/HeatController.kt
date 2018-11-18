package no.kalli

import no.kalli.api.HeaterClient
import no.kalli.api.TemperatureClient

fun main(args: Array<String>) {
    val tempClient = TemperatureClient(thingName = "kalli-temperature")
    val heatClient = HeaterClient(thingName = "kalli-heater")

    while (true) {
        val temp = tempClient.getLatestTemperature()

        if (temp < 20) heatClient.publish("ON") else heatClient.publish("OFF")

        println("Temp = $temp, Heater = ${heatClient.getLatestHeatState()}")

        Thread.sleep(10000)
    }
}


