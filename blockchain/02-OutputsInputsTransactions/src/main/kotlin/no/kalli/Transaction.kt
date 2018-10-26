package no.kalli

import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import java.util.stream.Collectors

class Transaction(var senderPublicKey: PublicKey) {

    //Simplified compared to Bitcoin
    val inputs = ArrayList<Input>()
    val outputs = ArrayList<Output>()

    //If we make the assumption that all the inputs belong to the
    //same key, we can have one signature for the entire transaction,
    //and not one for each input. This simplifies things a lot
    //(more than you think)!
    private var signature: ByteArray = byteArrayOf(0)

    var txHash: String = ""

    //TODO Complete validation of the transaction. Called by the Application.
    fun isValid(): Boolean {
        return inputs.isNotEmpty()
                && outputs.isNotEmpty()
                && outputs.stream().allMatch { it.value < 21000000 && it.value > 0 }

    }

    fun addInput(input: Input) = inputs.add(input)

    fun addOutput(output: Output) = outputs.add(output)

    override fun toString(): String =
            "Trasaction( $txHash )\n\tInputs:\n\t\t${inputsToString()}\n\tOutputs:\n\t\t${outputsToString()}"

    fun signTxUsing(privateKey: PrivateKey) {
        signature = DSAUtil.signWithDSA(
                privateKey, inputsToString() + outputsToString())
    }

    fun calculateTxHash() {
        txHash = HashUtil.base64Encode(
                HashUtil.sha256Hash(inputsToString() + outputsToString()))
    }

    private fun inputsToString(): String = inputs.stream().map(Input::toString).collect(Collectors.joining("\n\t\t"))

    private fun outputsToString(): String = outputs.stream().map(Output::toString).collect(Collectors.joining("\n\t\t"))

}