package no.kalli

import java.security.KeyPair
import java.security.PublicKey

class Wallet(var id: String, var utxo: UTXO)
{

    //A refererence to the "global" complete utxo-set
    private var utxoMap: Map<Input, Output> = emptyMap()

    private val keyPair: KeyPair = DSAUtil.generateRandomDSAKeyPair()

    val address: String
        get() = HashUtil.addressFromPublicKey(keyPair.public)

    val publicKey: PublicKey
        get() = keyPair.public

    val balance: Long
        get() = utxoMap.values.stream()
                .filter {
                    it.address == address
                }.map {
                    calculateBalance(listOf(it))
                }.count()

    @Throws(Exception::class)
    fun createTransaction(value: Long, address: String): Transaction? {

        //TODO - This is a big one

        // 1. Collect all UTXO for this wallet and calculate balance
        // 2. Check if there are sufficient funds --- Exception?
        // 3. Choose a number of UTXO to be spent --- Strategy?
        // 4. Calculate change
        // 5. Create an "empty" transaction
        // 6. Add chosen inputs
        // 7. Add 1 or 2 outputs, depending on change
        // 8. Sign the transaction
        // 9. Calculate the hash for the transaction
        // 10. return
        return null

        // PS! We have not updated the UTXO yet. That is normally done
        // when appending the block to the blockchain, and not here!
        // Do that manually from the Application-no.kalli.main.
    }

    private fun calculateBalance(outputs: Collection<Output>): Long = outputs.stream().mapToLong(Output::value).sum()

    private fun collectMyUtxo(): Map<Input, Output>? {
        return null
    }

}
