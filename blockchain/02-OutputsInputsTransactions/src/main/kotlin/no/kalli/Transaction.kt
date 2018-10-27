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

    fun isValid(): Boolean {
        return inputs.isNotEmpty()
                && outputs.isNotEmpty()
                && signature.isNotEmpty()
                && outputs.stream().allMatch { it.value < 21000000 && it.value > 0 }
                && DSAUtil.verifyWithDSA(senderPublicKey, inputsToString() + outputsToString(), signature)
                && HashUtil.base64Encode(HashUtil.sha256Hash(inputsToString() + outputsToString() + signature)) == txHash
    }

    fun addInput(input: Input) =
            if (!inputs.contains(input)) inputs.add(input)
            else throw Exception("Input already exists in the lists")

    fun addOutput(output: Output) = outputs.add(output)

    override fun toString(): String =
            "Trasaction( $txHash )\n\tInputs:\n\t\t${inputsToString()}\n\tOutputs:\n\t\t${outputsToString()}"

    fun signTxUsing(privateKey: PrivateKey) {
        signature = DSAUtil.signWithDSA(
                privateKey, inputsToString() + outputsToString())
    }

    fun calculateTxHash() {
        txHash = HashUtil.base64Encode(
                HashUtil.sha256Hash(inputsToString() + outputsToString() + signature))
    }

    private fun inputsToString(): String = inputs.stream().map(Input::toString).collect(Collectors.joining("\n\t\t"))

    private fun outputsToString(): String = outputs.stream().map(Output::toString).collect(Collectors.joining("\n\t\t"))

}