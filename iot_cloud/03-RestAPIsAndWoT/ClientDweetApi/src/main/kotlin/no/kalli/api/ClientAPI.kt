package no.kalli.api


import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.InputStream
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

open class ClientAPI(var thingName: String) {
    private var API_DWEET_END_POINT = "localhost:8080"
    private var jsonParser = JsonParser()

    fun publishHelper(content: JsonObject): Boolean {
        thingName = URLEncoder.encode(thingName, "UTF-8")

        val connection = URL("http://$API_DWEET_END_POINT/dweet/for/$thingName")
                .let { it.openConnection() as HttpURLConnection }
                .apply {
                    setRequestProperty("Content-Type", "application/json; charset=utf-8")
                    requestMethod = "PUT"
                    doInput = true
                    doOutput = true
                }

        PrintWriter(connection.outputStream)
                .apply {
                    println(content.toString())
                    flush()
                    close()
                }

        readResponse(connection.inputStream)
                .also { connection.disconnect() }
                .apply { return has("_this") && get("_this").asString == "succeeded" }
    }

    fun get(): String {

        thingName = URLEncoder.encode(thingName, "UTF-8")

        val connection = URL("http://$API_DWEET_END_POINT/get/latest/dweet/for/$thingName")
                .let { it.openConnection() as HttpURLConnection }
                .apply {
                    setRequestProperty("Content-Type", "application/json; charset=utf-8")
                    setRequestProperty("Accept", "application/json")
                    requestMethod = "GET"
                    doInput = true
                    doOutput = true
                }

        readResponse(connection.inputStream)
                .also { connection.disconnect() }
                .apply {
                    return if (has("_this")
                            && get("_this").asString == "succeeded") toString() else ""
                }
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