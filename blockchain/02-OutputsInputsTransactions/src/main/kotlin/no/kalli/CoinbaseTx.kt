package no.kalli

/**
 * This class represents a [CoinbaseTx], it takes a [message] as a [String], [value] as an [Long],
 * and a [address] as a [String].
 */
data class CoinbaseTx(var message: String, private var value: Long, private var address: String) {

    val txHash: String
        get() = HashUtil.base64Encode(HashUtil.sha256Hash(message + value))

    val output: Output = Output(value, address)

    override fun toString(): String {
        return "CoinbaseTx($txHash)\n\tmessage='$message', output=$output)"
    }


}