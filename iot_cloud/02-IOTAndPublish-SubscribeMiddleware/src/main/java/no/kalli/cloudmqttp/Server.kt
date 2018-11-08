package no.kalli.cloudmqttp

data class Server(var url: String = "", var port: Int = 0, var sslPort: Int = 0, var websocketPort: Int = 0)