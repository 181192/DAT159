package no.kalli

import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import java.util.stream.Collectors

class Transaction(var senderPublicKey: PublicKey) {

    val inputs = ArrayList<Input>()
    val outputs = ArrayList<Output>()

    private var signature: ByteArray = byteArrayOf(0)

    var txHash: String = ""

    //TODO Complete validation of the transaction. Called by the Application.

    //    Validate the regular transaction created by the "miner"'s wallet:
    //      - All the content must be valid (not null++)!!! - OK
    //      - All the inputs are unspent and belongs to the sender
    //      - There are no repeating inputs!!!
    //      - All the outputs must have a value > 0 - OK
    //      - The sum of inputs equals the sum of outputs - OK
    //      - The transaction is correctly signed by the sender - OK
    //      - The transaction hash is correct
    fun isValid(): Boolean {
        return inputs.isNotEmpty()
                && outputs.isNotEmpty()
                && inputs.size == outputs.size
                && outputs.stream().allMatch { it.value < 21000000 && it.value > 0 }
                && DSAUtil.verifyWithDSA(senderPublicKey, inputsToString() + outputsToString(), signature)
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