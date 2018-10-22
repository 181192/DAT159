package no.kalli

data class CoinbaseTx(var coinbase: String, private var value: Long, private var address: String) {

    private val txHash: String
        get() = HashUtil.base64Encode(HashUtil.sha256Hash(coinbase))

    val output: Output = Output(value, address)
}