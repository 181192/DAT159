package no.kalli.cloudmqttp

class Subscriber {
    var topic: String = ""
    var qos: Int = 0
    override fun toString(): String {
        return "Subscriber(topic='$topic', qos='$qos')"
    }

}