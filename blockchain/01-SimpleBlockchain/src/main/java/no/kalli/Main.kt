package no.kalli

import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
    val difficulty = 5
    val numberOfRounds = 8
    val chain = Blockchain(difficulty)
    val miner = Miner(chain)

    println("Difficulty: $difficulty")
    println("\n###########################")

    val totalTime = measureTimeMillis {
        runTheMiner(miner, numberOfRounds)
    }

    println("\n###########################")
    println("Total time: $totalTime milli sec")
    println("Average time: ${totalTime / numberOfRounds} milli sec")
    println("The chain is valid: ${chain.isValidChain()}")
}

private fun runTheMiner(miner: Miner, numberOfRounds: Int) {
    for (i in 1..numberOfRounds) {

        var b = Block("", "")

        val time = measureTimeMillis {
            b = miner.createAndMineNewBlock(Math.random().toString())!!
        }

        print("Total time: $time milli sec\t")
        print("| Block $i:  $b")
        println()
    }
}
