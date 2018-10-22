package no.kalli;

public class CoinbaseTx {

    //Simplified compared to Bitcoin (nothing significant missing)
    private String coinbase; // "The Times 03/Jan/2009 Chancellor
    //  on brink of second bailout for banks"
    private Output output;
    private String txHash;

    public CoinbaseTx(String coinbase, int value, String address) {
        //TODO
        //Remember to calculate txHash
    }

    @Override
    public String toString() {
        //TODO For screen output
        return "";
    }

    //TODO Getters?

}
