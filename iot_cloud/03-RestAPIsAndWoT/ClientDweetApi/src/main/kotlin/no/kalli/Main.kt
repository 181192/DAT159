package no.kalli

fun main(args: Array<String>) {
    val client = DweetClient()
    client.publish(31)
    println(client.get())
}