package no.kalli

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern
import javax.xml.bind.DatatypeConverter

/**
 * This class [Block] represents a block in the [Blockchain]. It takes the hash of the last block as
 * input to the constructor.
 */
class Block(hashLastBlock: String, // The data for this block.
            val data: String) {

    var hash: String
    var nonce: Long = 0     // The nonce for this block.
    val prev: String = hashLastBlock // The hash of the previous block in the blockchain.

    init {
        this.hash = calculateHash()
    }

    /**
     * Given the [miningTarget] the method [mine], mine until the calculated hash matches the target.
     * The target is a regular expression, for example "^0{5}.*" which implies that
     * the hash must start with 5 zeroes.
     */
    fun mine(miningTarget: String) {
        while (!Pattern.compile(miningTarget).matcher(hash).matches()) {
            nonce++
            hash = calculateHash()
        }
    }

    /**
     * A complete validation of the block, including prev matching the hash of the last
     * block in the blockchain, and that the block is mined according to the mining target.
     * The method [isValidAsNextBlock] takes [hashLastBlock] the hash of the last block, and
     * the [miningTarget] mining target.
     */
    fun isValidAsNextBlock(hashLastBlock: String, miningTarget: String): Boolean =
            hashLastBlock  == prev && Pattern.compile(miningTarget).matcher(hash).matches()

    /**
     * The method [calculateHash] calculates the hash for this block based on the other instance variables.
     * The hash is returned as a HEX-String
     */
    fun calculateHash(): String = DatatypeConverter.printHexBinary(createSha256Hash(prev + nonce + data))

    /**
     * Amazing to-string method
     */
    override fun toString(): String {
        return "Block [data=$data\t nonce=$nonce\t hash=$hash\t prev=$prev]"
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    private fun createSha256Hash(s: String): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(s.toByteArray(charset("UTF-8")))
    }
}
