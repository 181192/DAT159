package no.kalli.cloudmqttp

class Server {
    var url: String = ""
    var port: Int = 0
    var sslPort: Int = 0
    var websocketPort: Int = 0
    override fun toString(): String {
        return "Server(url='$url', port=$port, sslPort=$sslPort, websocketPort=$websocketPort)"
    }
}