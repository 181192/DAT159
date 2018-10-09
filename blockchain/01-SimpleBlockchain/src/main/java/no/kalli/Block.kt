package no.kalli

/**
 * This class [Block] represents a block in the [Blockchain]. It takes the hash of the last block as
 * input to the constructor.
 */
class Block(hashLastBlock: String) {

    var hash: String
    var nonce: Long = 0     // The nonce for this block.
    var data: String = "" // The data for this block.
    var prev: String = hashLastBlock // The hash of the previous block in the blockchain.

    init {
        this.hash = calculateHash()
    }

    /**
     * Setting the data for this block
     */
    fun setData() {
    }

    /**
     * Given the [miningTarget] the method [mine], mine until the calculated hash matches the target.
     * The target is a regular expression, for example "^0{5}.*" which implies that
     * the hash must start with 5 zeroes.
     */
    fun mine(miningTarget: String) {
    }

    /**
     * A complete validation of the block, including prev matching the hash of the last
     * block in the blockchain, and that the block is mined according to the mining target.
     * The method [isValidAsNextBlock] takes [hashLastBlock] the hash of the last block, and
     * the [miningTarget] mining target.
     */
    fun isValidAsNextBlock(hashLastBlock: String, miningTarget: String): Boolean {
        return false
    }

    /**
     * The method [calculateHash] calculates the hash for this block based on the other instance variables.
     * The hash is returned as a HEX-String
     */
    private fun calculateHash(): String {
        return ""
    }

    /**
     * Amazing to-string method
     */
    override fun toString(): String {
        return super.toString()
    }
}