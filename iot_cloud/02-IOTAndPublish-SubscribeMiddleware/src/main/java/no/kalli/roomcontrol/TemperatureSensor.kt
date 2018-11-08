package no.kalli.roomcontrol

class TemperatureSensor(private val room: Room) {
    fun read(): Double = room.sense()
}