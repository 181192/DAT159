package no.kalli.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import no.kalli.Heating
import no.kalli.HeatingActuator
import no.kalli.Response

class HeaterClient(thingName: String, val actuator: HeatingActuator? = null) : ClientAPI(thingName), Runnable {
    fun getLatestHeatState(): Heating {
        val response: Response = Gson().fromJson(get(), Response::class.java)
        return Heating(response.with.content.asJsonObject.get("heat").asString)
    }

    fun publish(heat: String): Boolean = JsonObject()
            .apply { addProperty("heat", heat) }
            .let { publishHelper(it) }

    override fun run() {
        while (true) {
            val state = getLatestHeatState().heat
            if (state == "ON") actuator!! write true
            if (state == "OFF") actuator!! write false
            Thread.sleep(10000)
        }
    }
}