package no.kalli

import java.util.*
import java.util.regex.Pattern


/**
 * This class [Blockchain] represents a blockchain. The argument is the
 * number of leading zeroes in the block hashes
 */
class Blockchain(miningDifficulty: Int) {

    var miningTarget = "^0{$miningDifficulty}(\\d|\\D){59}$"
    var listOfBlocks = ArrayList<Block>()

    /**
     * This method [hashLastBlock] returns the hash of the last block appended to the chain
     * If the chain is empty, "0" is returned.
     */
    fun hashLastBlock(): String = if (listOfBlocks.isEmpty()) "0".repeat(64) else listOfBlocks.last().hash

    /**
     * This method [isValidChain] validate the entire chain
     */
    fun isValidChain(): Boolean {
        for (i in 1 until listOfBlocks.size) {
            val currentBlock = listOfBlocks[i]
            val previousBlock = listOfBlocks[i - 1]
            val isMatch = Pattern.compile(miningTarget).matcher(currentBlock.hash).matches()

            if (!(currentBlock.hash == currentBlock.calculateHash()
                            && previousBlock.hash == currentBlock.prev
                            && isMatch)) return false
        }
        return true
    }

    /**
     * Validate and append [b] to chain of valid
     * @return wheter everything went OK and [b] was appended
     */
    fun validateAndAppendNewBlock(b: Block): Boolean =
            if (b.isValidAsNextBlock(hashLastBlock(), miningTarget))
                listOfBlocks.add(b)
            else false
}