package no.kalli

fun main(args: Array<String>) {
    val tempClient = DweetClient(thingName = "kalli-temperature")
    val heatClient = DweetClient(thingName = "kalli-heater")

    tempClient.publish(temperature = 31.0)
    heatClient.publish(heat = "OFF")

    println(tempClient.get())
    println(heatClient.get())
}