package no.kalli


/**
 * This class [Miner] represent a miner and takes a blockchain as a argument.
 */
class Miner(private val chain: Blockchain) {

    /**
     * This method [createAndMineNewBlock] create and mine with the goal of appending to the chain,
     * it returns the mined block. The method takes the [data] as an argument.
     */
    fun createAndMineNewBlock(data: String): Block {
        val newBlock = Block(chain.hashLastBlock(), data)
        newBlock.mine(chain.miningTarget)
        chain.validateAndAppendNewBlock(newBlock)
        return newBlock
    }
}
