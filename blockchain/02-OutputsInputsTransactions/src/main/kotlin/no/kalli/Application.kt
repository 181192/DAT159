package no.kalli

@Throws(Exception::class)
fun main(args: Array<String>) {

    val globalUTXO = UTXO()

    // 0. To get started, we need a few (single address) Wallets. Create 2 wallets.
    //    Think of one of them as the "miner" (the one collecting "block rewards").
    val minerWallet = Wallet("Miner Wallet", globalUTXO.map)
    val myWallet = Wallet("Kalli's Wallet", globalUTXO.map)

    // 1. The first "block" (= round of transactions) contains only a message
    //    transaction. Create a message transaction that adds a certain
    //    amount to the "miner"'s address. Update the UTXO-set (add only).
    val genesis = CoinbaseTx("Hello Genesis", 100, minerWallet.address)
    globalUTXO.addOutputFrom(genesis)
    println("Block1:\n$genesis")

    // 2. The second "block" contains two transactions, the mandatory message
    //    transaction and a regular transaction. The regular transaction shall
    //    send ~20% of the money from the "miner"'s address to the other address.
    var coinbaseTx = CoinbaseTx("Hello from the other side", 100, minerWallet.address)

    try {
        val regularTx = minerWallet.createTransaction(20, myWallet.address)

        if (!regularTx.isValid()
                && globalUTXO.validateSumInputAndOutput(regularTx)
                && globalUTXO.verifyUnpentTxOwner(regularTx))
            throw Exception("Ayy lmao, transaction ${regularTx.txHash} is not valid")

        //    Update the UTXO-set (both add and remove).
        globalUTXO.addAndRemoveOutputsFrom(regularTx)
        println("Block2:\n$coinbaseTx\n$regularTx")
    } catch (e: Exception) {
        e.printStackTrace()
    }

    globalUTXO.addOutputFrom(coinbaseTx)


    // 3. Do the same once more. Now, the "miner"'s address should have two or more
    //    unspent outputs (depending on the strategy for choosing inputs) with a
    //    total of 2.6 * block reward, and the other address should have 0.4 ...
    coinbaseTx = CoinbaseTx("Hello darkness, my old friend", 100, minerWallet.address)

    try {
        val regularTx = minerWallet.createTransaction(20, myWallet.address)

        if (!regularTx.isValid()
                && globalUTXO.validateSumInputAndOutput(regularTx)
                && globalUTXO.verifyUnpentTxOwner(regularTx))
            throw Exception("Ayy lmao, transaction ${regularTx.txHash} is not valid")

        globalUTXO.addAndRemoveOutputsFrom(regularTx)
        println("Block2:\n$coinbaseTx\n$regularTx")
    } catch (e: Exception) {
        e.printStackTrace()
    }

    globalUTXO.addOutputFrom(coinbaseTx)


    // 4. Make a nice print-out of all that has happened, as well as the end status.
    println("UTXO:")
    globalUTXO.printUTXO()
    println("\nThe miner's wallet:\n$minerWallet")
    println("\nMy wallet:\n$myWallet")
}
