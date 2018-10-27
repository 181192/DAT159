package no.kalli

/**
 * This class represents a [UTXO]
 */
class UTXO {

    /**
     * Why is this a Map and not a Set?
     * The values in this map are the UTXOs (unspent Outputs)
     * When removing UTXOs, we need to identify which to remove.
     * Since the Inputs are references to UTXOs, we can use those
     * as keys.
     */
    val map = HashMap<Input, Output>()

    /**
     * [printUTXO] prints out the UTXO [map]
     */
    fun printUTXO() = map.forEach { t: Input, u: Output -> println("$t\n\t-->\t$u") }

    /**
     * [addOutputFrom] add an [Output] from the [CoinbaseTx]
     */
    fun addOutputFrom(ctx: CoinbaseTx) {
        map[Input(ctx.txHash, 0)] = ctx.output
    }

    /**
     * [addAndRemoveOutputsFrom] add the [Output]'s in the [Transaction] to the UTXO [map],
     * and removes the [Input]'s from the [map].
     */
    fun addAndRemoveOutputsFrom(tx: Transaction) {
        tx.outputs.stream().forEach { map[Input(tx.txHash, tx.outputs.indexOf(it))] = it }
        tx.inputs.stream().forEach { map.remove(it) }
    }

    fun validateSumInputAndOutput(tx: Transaction): Boolean =
            tx.inputs.stream().mapToLong { map[it]!!.value }.sum() == tx.outputs.stream().mapToLong { it.value }.sum()

    fun verifyUnpentTxOwner(tx: Transaction): Boolean =
            tx.inputs.stream().anyMatch { map[it]!!.address == HashUtil.addressFromPublicKey(tx.senderPublicKey) }
}