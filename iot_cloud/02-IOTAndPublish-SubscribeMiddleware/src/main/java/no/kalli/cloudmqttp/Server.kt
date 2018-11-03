package no.kalli.cloudmqttp

class Server {
    var url: String = ""
    var port: Int = 0
    var sslPort: Int = 0
    var websocketPort: Int = 0
    override fun toString(): String {
        return "Server(\n\t\turl='$url',\n\t\tport=$port,\n\t\tsslPort=$sslPort,\n\t\twebsocketPort=$websocketPort)"
    }
}