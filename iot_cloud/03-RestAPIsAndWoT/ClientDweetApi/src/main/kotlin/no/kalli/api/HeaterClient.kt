package no.kalli.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import no.kalli.HeatingActuator
import no.kalli.Response

class HeaterClient(thingName: String, val actuator: HeatingActuator? = null) : ClientAPI(thingName), Runnable {
    fun getLatestHeatState(): String {
        val response: Response = Gson().fromJson(get(), Response::class.java)
        return response.with.content.asJsonObject.get("heat").asString
    }

    fun publish(heat: String): Boolean = JsonObject()
            .apply { addProperty("heat", heat) }
            .let { publishHelper(it) }

    override fun run() {
        val state = getLatestHeatState()
        if (state == "ON") actuator!! write true
        if (state == "OFF") actuator!! write false
    }
}