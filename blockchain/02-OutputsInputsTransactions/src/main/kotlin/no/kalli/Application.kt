package no.kalli


private val utxo = UTXO()

@Throws(Exception::class)
fun main(args: Array<String>) {

    /*
     * In this assignment, we are going to look at how to represent and record
     * monetary transactions. We will use Bitcoin as the basis for the assignment,
     * but there will be some simplifications.
     *
     * We are skipping the whole blockchain this time, and instead focus on the
     * transaction details, the UTXOs and how money movements are represented.
     *
     * (If you want to, you can of course extend the assignment by collecting the
     * individual transactions into blocks, create a Merkle tree for the block
     * header, validate, mine and add the block to a blockchain.)
     *
     */

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
    globalUTXO.addOutputFrom(coinbaseTx)

    try {
        val regularTx = minerWallet.createTransaction((minerWallet.balance * 0.20).toLong(), myWallet.address)

        //    Validate the regular transaction created by the "miner"'s wallet:
        //      - All the content must be valid (not null++)!!!
        //      - All the inputs are unspent and belongs to the sender
        //      - There are no repeating inputs!!!
        //      - All the outputs must have a value > 0
        //      - The sum of inputs equals the sum of outputs
        //      - The transaction is correctly signed by the sender
        //      - The transaction hash is correct
        if (!regularTx.isValid()) throw Exception("Ayy lmao, transaction ${regularTx.txHash} is not valid")

        //    Update the UTXO-set (both add and remove).
        globalUTXO.addAndRemoveOutputsFrom(regularTx)
        println("Block2:\n$coinbaseTx\n$regularTx")
    } catch (e: Exception) {
        e.printStackTrace()
    }


    // 3. Do the same once more. Now, the "miner"'s address should have two or more
    //    unspent outputs (depending on the strategy for choosing inputs) with a
    //    total of 2.6 * block reward, and the other address should have 0.4 ...
    coinbaseTx = CoinbaseTx("Hello darkness, my old friend", 100, minerWallet.address)
    globalUTXO.addOutputFrom(coinbaseTx)

    try {
        val regularTx = minerWallet.createTransaction((minerWallet.balance * 0.20).toLong(), myWallet.address)

        if (!regularTx.isValid()) throw Exception("Ayy lmao, transaction ${regularTx.txHash} is not valid")

        globalUTXO.addAndRemoveOutputsFrom(regularTx)
        println("Block2:\n$coinbaseTx\n$regularTx")
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // 4. Make a nice print-out of all that has happened, as well as the end status.
    //
    //      for each of the "block"s (rounds), print
    //          "block" number
    //          the message transaction
    //              hash, message
    //              output
    //          the regular transaction(s), if any
    //              hash
    //              inputs
    //              outputs
    //      End status: the set of unspent outputs
    //      End status: for each of the wallets, print
    //          wallet id, address, balance


    println("UTXO:")
    globalUTXO.printUTXO()
    println("\nThe miner's wallet:\n$minerWallet")
    println("\nMy wallet:\n$myWallet")


}
