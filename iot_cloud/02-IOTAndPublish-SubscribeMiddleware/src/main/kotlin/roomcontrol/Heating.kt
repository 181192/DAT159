package roomcontrol

class Heating(private val room: Room) {
    fun write(newValue: Boolean) = room.actuate(newValue)
}