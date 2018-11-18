package no.kalli

import no.kalli.api.ClientAPI

fun main(args: Array<String>) {
    val tempClient = ClientAPI(thingName = "kalli-temperature")

    while (true) {
        println(tempClient.get())
        Thread.sleep(10000)
    }
}