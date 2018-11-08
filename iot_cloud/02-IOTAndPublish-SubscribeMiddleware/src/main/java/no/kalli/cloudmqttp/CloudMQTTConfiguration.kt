package no.kalli.cloudmqttp

class CloudMQTTConfiguration {
    var server = Server()
    var name = ""
    var user = ""
    var password = ""
    var api = ""
    var temperature = Temperature()
    var heat = Heat()
    override fun toString(): String {
        return "CloudMQTTConfiguration(\n\tserver=$server,\nname='$name',\nuser='$user',\npassword='$password',\napi='$api',\nheat=$heat,\ntemp=$temperature)"
    }


}
