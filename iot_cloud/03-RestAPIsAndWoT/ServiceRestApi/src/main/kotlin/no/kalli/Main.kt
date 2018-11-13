package no.kalli

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import spark.kotlin.after
import spark.kotlin.get
import spark.kotlin.port
import spark.kotlin.put

fun main(args: Array<String>) {

    var temperature = Temperature()
    var heater = Heating()

    port(8080)

    after {
        response.type("application/json")
    }

    get("/tempsensor/current") {
        GsonBuilder()
                .create()
                .toJson(temperature)
    }

    put("/tempsensor/current") {
        temperature = Gson().fromJson(request.body(), Temperature::class.java)
        Gson().toJson(
                StandardResponse(
                        StatusResponse.SUCCESS,
                        Gson().toJsonTree(temperature)))
    }

    get("/heatactuator/current") {
        GsonBuilder()
                .create()
                .toJson(heater)
    }

    put("/heatactuator/current") {
        heater = Gson().fromJson(request.body(), Heating::class.java)
        Gson().toJson(
                StandardResponse(
                        StatusResponse.SUCCESS,
                        Gson().toJsonTree(heater)))
    }

}

data class Temperature(var temperature: Double = 0.0)

data class Heating(var heat: String = "OFF")

data class StandardResponse(val status: StatusResponse?, val message: String?, val data: JsonElement?) {
    constructor(status: StatusResponse) : this(status, null, null)
    constructor(status: StatusResponse, message: String) : this(status, message, null)
    constructor(status: StatusResponse, data: JsonElement) : this(status, null, data)
}

enum class StatusResponse {
    SUCCESS,
    ERROR
}