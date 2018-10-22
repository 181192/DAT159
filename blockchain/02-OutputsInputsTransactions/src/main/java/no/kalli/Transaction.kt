package no.kalli

import java.security.PrivateKey
import java.security.PublicKey
import java.util.*

class Transaction(var senderPublicKey: PublicKey) {
    //Simplified compared to Bitcoin
    private val inputs = ArrayList<Input>()
    private val outputs = ArrayList<Output>()

    //If we make the assumption that all the inputs belong to the
    //same key, we can have one signature for the entire transaction,
    //and not one for each input. This simplifies things a lot
    //(more than you think)!
    private val signature: ByteArray? = null

    private val txHash: String? = null

    //TODO Complete validation of the transaction. Called by the Application.
    fun isValid(): Boolean {
        return false
    }

    fun addInput(input: Input) = inputs.add(input)

    fun addOutput(output: Output) = outputs.add(output)

    override fun toString(): String {
        //TODO
        return ""
    }

    fun signTxUsing(privateKey: PrivateKey) {
        //TODO
    }

    fun calculateTxHash() {
        //TODO
    }
}
