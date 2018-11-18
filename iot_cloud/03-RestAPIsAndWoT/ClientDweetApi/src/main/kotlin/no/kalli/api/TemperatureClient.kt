package no.kalli.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import no.kalli.Response
import no.kalli.TemperatureSensor

class TemperatureClient(thingName: String, val sensor: TemperatureSensor? = null) : ClientAPI(thingName), Runnable {
    fun getLatestTemperature(): Double {
        val response: Response = Gson().fromJson(get(), Response::class.java)
        return response.with.content.asJsonObject.get("temperature").asDouble
    }

    fun publish(temperature: Double): Boolean = JsonObject()
            .apply { addProperty("temperature", temperature) }
            .let { publishHelper(it) }

    override fun run() {
        while (true) {
            val temp = sensor!!.read()
            println("Publish temperature $temp")
            publish(temp)
            Thread.sleep(10000)
        }
    }
}
