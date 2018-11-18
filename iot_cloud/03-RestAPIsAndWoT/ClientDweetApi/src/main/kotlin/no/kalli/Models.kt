package no.kalli

import com.google.gson.JsonElement

data class Temperature(var temperature: Double = 0.0)

data class Heating(var heat: String = "OFF")

data class With(val thing: String, val created: String, val content: JsonElement, val transaction: String)

data class Response(val _this: String, val by: String, val the: String, val with: With)