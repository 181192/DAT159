package no.kalli;

public class Input {

    //Simplified compared to Bitcoin
    //The signature is moved to Transaction, see comment there.
    private String prevTxHash;
    private int prevOutputIndex;

    public Input(String prevTxHash, int prevOutputIndex) {
        //TODO
    }

    @Override
    public String toString() {
        //TODO For screen output
        return "";
    }

    //TODO Getters?
}
