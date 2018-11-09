package cloudmqttp

data class CloudMQTTConfiguration(
        var server: Server = Server(),
        var name: String = "",
        var user: String = "",
        var password: String = "",
        var api: String = ""
)