package no.kalli

import java.util.*


/**
 * This class [Blockchain] represents a blockchain. The argument is the
 * number of leading zeroes in the block hashes
 */
class Blockchain(miningDifficulty: Int) {

    var miningTarget: String = "^0{5}.*"
    var listOfBlocks: List<Block> = ArrayList()

    /**
     * This method [hashLastBlock] returns the hash of the last block appended to the chain
     * If the chain is empty, "0" is returned.
     */
    fun hashLastBlock(): String {
        return if (listOfBlocks.isEmpty()) "0" else listOfBlocks.last().hash
    }

    /**
     * This method [isValidChain] validate the entire chain
     */
    fun isValidChain(): Boolean {
        return false;
    }

    /**
     * Validate and append [b] to chain of valid
     * @return wheter everything went OK and [b] was appended
     */
    fun validateAndAppendNewBlock(b: Block): Boolean {
        return false
    }
}
