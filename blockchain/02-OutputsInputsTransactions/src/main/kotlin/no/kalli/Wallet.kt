package no.kalli

import java.security.KeyPair
import java.security.PublicKey


class Wallet(var id: String, utxo: Map<Input, Output>) {

    //A refererence to the "global" complete utxo-set
    private var utxoMap: Map<Input, Output> = utxo

    private val keyPair: KeyPair = DSAUtil.generateRandomDSAKeyPair()

    val address: String
        get() = HashUtil.addressFromPublicKey(keyPair.public)

    val publicKey: PublicKey
        get() = keyPair.public

    val balance: Long
        get() = calculateBalance(utxoMap.filterValues { it.address == address }.values)

    private fun calculateBalance(outputs: Collection<Output>): Long = outputs.stream().mapToLong(Output::value).sum()

    private fun collectMyUtxo(): Map<Input, Output>? = utxoMap.filterValues { it.address == address }

    @Throws(Exception::class)
    fun createTransaction(value: Long, address: String): Transaction {

        // 1. Collect all UTXO for this wallet and calculate balance
        val myUTXO = collectMyUtxo() ?: throw Exception("No transactions")
        val myBalance = calculateBalance(myUTXO.values)

        // 2. Check if there are sufficient funds --- Exception?
        if (myBalance < value) throw Exception("No sufficient funds")

        // 3. Choose a number of UTXO to be spent --- Strategy?
        var collected = 0L
        val res = ArrayList<Input>()
        myUTXO.entries
                .stream()
                .takeWhile { collected < value }
                .forEach {
                    collected += it.value.value
                    res.add(it.key)
                }

        // 4. Calculate change
        val change = collected - value

        // 5. Create an "empty" transaction
        val transaction = Transaction(publicKey)

        // 6. Add chosen inputs
        res.forEach { transaction.addInput(it) }

        // 7. Add 1 or 2 outputs, depending on change
        transaction.addOutput(Output(value, address))
        if (change > 0) transaction.addOutput(Output(change, this.address))

        // 8. Sign the transaction
        transaction.signTxUsing(keyPair.private)

        // 9. Calculate the hash for the transaction
        transaction.calculateTxHash()

        // 10. return
        return transaction

        // PS! We have not updated the UTXO yet. That is normally done
        // when appending the block to the blockchain, and not here!
        // Do that manually from the Application-no.kalli.main.
    }

    override fun toString(): String {
        return "Wallet(id='$id', address=$address, balance=$balance)"
    }


}