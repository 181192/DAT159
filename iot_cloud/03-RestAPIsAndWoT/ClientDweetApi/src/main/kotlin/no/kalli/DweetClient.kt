package no.kalli


import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.InputStream
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class DweetClient(var thingName: String) {
    private var API_DWEET_END_POINT = "dweet.io"
    private var jsonParser = JsonParser()

    fun publish(temperature: Double): Boolean {
        val content = JsonObject()

        content.addProperty("Temperature", temperature)

        return publishHelper(content)
    }

    fun publish(heat: String): Boolean {
        val content = JsonObject()

        content.addProperty("Heat", heat)

        return publishHelper(content)
    }

    private fun publishHelper(content: JsonObject): Boolean {
        thingName = URLEncoder.encode(thingName, "UTF-8")

        val url = URL("http://$API_DWEET_END_POINT/dweet/for/$thingName")

        val connection = url.openConnection() as HttpURLConnection

        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
        connection.requestMethod = "POST"
        connection.doInput = true
        connection.doOutput = true

        val out = PrintWriter(connection.outputStream)

        println(content.toString())

        out.flush()
        out.close()

        val response = readResponse(connection.inputStream)

        connection.disconnect()

        return (response.has("this") && response.get("this").asString == "succeeded")
    }

    fun get(): String {
        // http://dweet.io/get/latest/dweet/for/dat159-sensor

        thingName = URLEncoder.encode(thingName, "UTF-8")

        val url = URL("http://$API_DWEET_END_POINT/get/latest/dweet/for/$thingName")

        val connection = url.openConnection() as HttpURLConnection

        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
        connection.setRequestProperty("Accept", "application/json")
        connection.requestMethod = "GET"
        connection.doInput = true
        connection.doOutput = true

        val response = readResponse(connection.inputStream)

        return if (response.has("this")
                && response.get("this").asString == "succeeded") response.toString() else ""
    }

    private fun readResponse(input: InputStream): JsonObject {

        val scan = Scanner(input)
        val sn = StringBuilder()

        while (scan.hasNext())
            sn.append(scan.nextLine()).append('\n')
        scan.close()

        return jsonParser.parse(sn.toString()).asJsonObject
    }
}