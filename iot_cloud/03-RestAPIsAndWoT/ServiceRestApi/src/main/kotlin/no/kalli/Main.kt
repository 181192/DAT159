package no.kalli

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import spark.kotlin.after
import spark.kotlin.get
import spark.kotlin.port
import spark.kotlin.put
import java.time.LocalDateTime
import java.util.*

fun main(args: Array<String>) {

    var temperature = Temperature()
    var heater = Heating()

    val tempName = "kalli-temperature"
    val heaterName = "kalli-heater"

    port(8080)

    after { response.type("application/json") }

    get("get/latest/dweet/for/$tempName") {
        Gson().toJson(
                Response(
                        `this` = "succeeded",
                        by = "dweeting",
                        the = "dweet",
                        with = With(
                                thing = tempName,
                                created = LocalDateTime.now().toString(),
                                content = Gson().toJsonTree(temperature),
                                transaction = UUID.randomUUID().toString()
                        )
                )
        )
    }

    put("/dweet/for/$tempName") {
        temperature = Gson().fromJson(request.body(), Temperature::class.java)
        println("Updated temperature with ${temperature.temperature}")
        GsonBuilder()
                .create()
                .toJson(temperature)
    }

    get("get/latest/dweet/for/$heaterName") {
        Gson().toJson(
                Response(
                        `this` = "succeeded",
                        by = "dweeting",
                        the = "dweet",
                        with = With(
                                thing = heaterName,
                                created = LocalDateTime.now().toString(),
                                content = Gson().toJsonTree(heater),
                                transaction = UUID.randomUUID().toString()
                        )
                )
        )
    }

    put("/dweet/for/$heaterName") {
        heater = Gson().fromJson(request.body(), Heating::class.java)
        println("Updated heater with ${heater.heat}")
        GsonBuilder()
                .create()
                .toJson(heater)
    }

}

data class Temperature(var temperature: Double = 0.0)

data class Heating(var heat: String = "OFF")

data class With(val thing: String, val created: String, val content: JsonElement, val transaction: String)

data class Response(val `this`: String, val by: String, val the: String, val with: With)