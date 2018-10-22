package no.kalli

/**
 * This class represents a [CoinbaseTx], it takes a [coinbase] as a [String], [value] as an [Long],
 * and a [address] as a [String].
 */
data class CoinbaseTx(var coinbase: String, private var value: Long, private var address: String) {

    val txHash: String
        get() = HashUtil.base64Encode(HashUtil.sha256Hash(coinbase))

    val output: Output = Output(value, address)
}