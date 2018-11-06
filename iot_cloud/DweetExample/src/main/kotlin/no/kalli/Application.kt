package no.kalli

import io.swagger.client.ApiClient
import io.swagger.client.api.DweetsApi

fun main(args: Array<String>) {

    val client = ApiClient()
    val dweet = DweetsApi()
    dweet.apiClient = client
    dweet.dweetForThingPost("kalli-test", "message", "Nice")
    dweet.getLatestDweet("kalli-test", "")

}