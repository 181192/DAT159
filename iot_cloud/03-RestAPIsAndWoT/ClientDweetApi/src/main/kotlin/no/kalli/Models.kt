package no.kalli

import com.google.gson.JsonElement

data class Temperature(var temperature: Double = 0.0)

data class Heating(var heat: String = "OFF")

data class With(val thing: String, val created: String, val content: JsonElement, val transaction: String)

data class Response(val _this: String, val by: String, val the: String, val with: With)

class Room(startTemp: Double) {

    private var tempState: Int = -1
    private var temperature: Double = startTemp
    private var lastSense: Long = System.currentTimeMillis()

    @Synchronized
    fun sense(): Double {

        val timeNow = System.currentTimeMillis()
        val timeInterval = timeNow - lastSense

        temperature += RATE * tempState * timeInterval

        return temperature
    }

    @Synchronized
    fun actuate(newState: Boolean) {
        sense()
        tempState = if (newState) 1 else -1
    }

    companion object {
        const val RATE: Double = .0001
    }
}

class TemperatureSensor(private val room: Room) {
    fun read(): Double = room.sense()
}

class HeatingActuator(private val room: Room) {
    infix fun write(newValue: Boolean) = room.actuate(newValue)
}