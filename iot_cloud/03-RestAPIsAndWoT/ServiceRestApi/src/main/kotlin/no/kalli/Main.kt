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

    port(8080)

    after {
        response.type("application/json")
    }

    val tempName = "kalli-temperature"
    get("get/latest/dweet/for/$tempName") {
        Gson().toJson(
                StandardResponse(
                        _this = "succeeded",
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
        GsonBuilder()
                .create()
                .toJson(temperature)
    }

    val heaterName = "kalli-heater"

    get("get/latest/dweet/for/$heaterName") {
        Gson().toJson(
                StandardResponse(
                        _this = "succeeded",
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
        GsonBuilder()
                .create()
                .toJson(heater)
    }

}

data class Temperature(var temperature: Double = 0.0)

data class Heating(var heat: String = "OFF")

data class With(val thing: String, val created: String, val content: JsonElement, val transaction: String?)

data class StandardResponse(val _this: String, val by: String, val the: String, val with: With)