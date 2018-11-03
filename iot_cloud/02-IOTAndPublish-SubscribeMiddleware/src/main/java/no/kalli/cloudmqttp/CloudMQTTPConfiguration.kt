package no.kalli.cloudmqttp

class CloudMQTTPConfiguration {
    var server: Server = Server()
    var name: String = ""
    var user: String = ""
    var password: String = ""
    var api: String = ""
    var subscriber: Subscriber = Subscriber()
    var publisher: Publisher = Publisher()
    override fun toString(): String {
        return "CloudMQTTPConfiguration(\n\tserver=$server,\nname='$name',\nuser='$user',\npassword='$password',\napi='$api',\nsubscriber=$subscriber,\npublisher=$publisher)"
    }


}
