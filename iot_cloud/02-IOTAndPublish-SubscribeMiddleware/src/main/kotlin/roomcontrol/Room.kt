package roomcontrol

class Room(startTemp: Double) {

    private var tempState: Int = -1
    private var temperature: Double = startTemp
    private var lastSense: Long = System.currentTimeMillis()

    @Synchronized
    fun sense(): Double {

        val timeNow = System.currentTimeMillis()
        val timeInterval = timeNow - lastSense

        temperature += RATE * tempState * timeInterval

        return temperature
    }

    @Synchronized
    fun actuate(newState: Boolean) {
        sense()
        tempState = if (newState) 1 else -1
    }

    companion object {
        const val RATE: Double = .0001
    }
}