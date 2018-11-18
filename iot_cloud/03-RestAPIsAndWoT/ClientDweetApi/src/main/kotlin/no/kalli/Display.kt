package no.kalli

import no.kalli.api.ClientAPI

class Display {
    infix fun write(message: String) = println(message)
}

fun main(args: Array<String>) {
    val tempClient = ClientAPI(thingName = "kalli-temperature")
    val display = Display()

    while (true) {
        display write tempClient.get()

        Thread.sleep(10000)
    }
}