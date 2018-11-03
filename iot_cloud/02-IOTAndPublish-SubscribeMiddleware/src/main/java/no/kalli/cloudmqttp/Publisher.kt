package no.kalli.cloudmqttp

class Publisher {
    var topic: String = ""
    var qos: Int = 0
    override fun toString(): String {
        return "Publisher(topic='$topic', qos='$qos')"
    }


}