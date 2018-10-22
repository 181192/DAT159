package no.kalli

import java.util.*

class UTXO {

    //Why is this a Map and not a Set?
    //  The values in this map are the UTXOs (unspent Outputs)
    //  When removing UTXOs, we need to identify which to remove.
    //  Since the Inputs are references to UTXOs, we can use those
    //  as keys.
    private val map = HashMap<Input, Output>()

    fun printUTXO() {
        //TODO
    }

    fun addOutputFrom(ctx: CoinbaseTx) {
        //TODO
    }

    fun addAndRemoveOutputsFrom(tx: Transaction) {
        //TODO
    }
}
