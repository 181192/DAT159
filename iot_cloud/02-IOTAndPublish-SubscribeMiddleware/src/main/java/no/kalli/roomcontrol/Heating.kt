package no.kalli.roomcontrol

class Heating(val room: Room){
    fun write(newvalue: Boolean) = room.actuate(newvalue)
}