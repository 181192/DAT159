package no.kalli.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import no.kalli.Response

class TemperatureClient(thingName: String) : ClientAPI(thingName) {
    fun getLatestTemperature(): Double {
        val response: Response = Gson().fromJson(get(), Response::class.java)
        return response.with.content.asJsonObject.get("temperature").asDouble
    }

    fun publish(temperature: Double): Boolean = JsonObject()
            .apply { addProperty("temperature", temperature) }
            .let { publishHelper(it) }
}
