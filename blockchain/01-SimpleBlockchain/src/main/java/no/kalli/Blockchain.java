package no.kalli;

import java.util.List;

public class Blockchain {

    private int miningDifficulty; // The number of leading zeros in block hashes
    private String miningTarget;  // The reg-exp representing the mining target

    private List<Block> listOfBlocks; // The list of blocks

    public Blockchain(int miningDifficulty) {
        // TODO
        // Initializing *ALL* the instance variables to a valid state.
    }

    public String getHashLastBlock() {
        // TODO
        // Returning the hash of the last block appended to the chain.
        // If the chain is empty, "0" is returned.
        return null;
    }

    public boolean validateAndAppendNewBlock(Block b) {
        // TODO
        // Validate and append to chain if valid.
        // Return whether everything went OK and the block was appended.
        return false;
    }

    public boolean isValidChain() {
        // TODO
        // Validate the entire chain.
        return false;
    }

    // getters and setters

}
