package no.kalli;

import java.util.HashMap;
import java.util.Map;

public class UTXO {

    //Why is this a Map and not a Set?
    //  The values in this map are the UTXOs (unspent Outputs)
    //  When removing UTXOs, we need to identify which to remove.
    //  Since the Inputs are references to UTXOs, we can use those
    //  as keys.
    private Map<Input, Output> map = new HashMap<>();

    public void printUTXO() {
        //TODO
    }

    public void addOutputFrom(CoinbaseTx ctx) {
        //TODO
    }

    public void addAndRemoveOutputsFrom(Transaction tx) {
        //TODO
    }

    //TODO Getters?
}
