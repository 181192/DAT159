package no.kalli

import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {

    // TODO

    /*
     * Create a blockchain and a miner, add some blocks and validate the chain.
     * You should also System.print out the blocks as soon as the are appended,
     * and print out the final validation result. See output.txt for example
     * output for a solution to this assignment.
     */

    val difficulty = 5
    val numberOfRounds = 8
    val chain = Blockchain(difficulty)
    val miner = Miner(chain)

    println("Difficulty: $difficulty")
    println("\n###########################")

    val totalTime = measureTimeMillis {
        runTheMiners(miner, numberOfRounds)
    }

    println("\n###########################")
    println("Total time: $totalTime milli sec")
    println("Average time: ${totalTime/numberOfRounds} milli sec")
}

private fun runTheMiners(miner: Miner, numberOfRounds: Int) {
    for (i in 1..numberOfRounds) {

        var b: Block = Block("", "")

        val time = measureTimeMillis {
            b = miner.createAndMineNewBlock("Test")
        }

        print("Total time: $time milli sec\t")
        print("| Block $i:  $b")
        println()
    }
}
