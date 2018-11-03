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
        return "CloudMQTTPConfiguration(server=$server, name='$name', user='$user', password='$password', api='$api', subscriber=$subscriber, publisher=$publisher)"
    }


}
