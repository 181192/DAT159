package no.kalli;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;

public class Wallet {

    private String id;
    private KeyPair keyPair;

    //A refererence to the "global" complete utxo-set
    private Map<Input, Output> utxoMap;

    public Wallet(String id, UTXO utxo) {
        //TODO
    }

    public String getAddress() {
        //TODO
        return null;
    }

    public PublicKey getPublicKey() {
        //TODO
        return null;
    }

    public Transaction createTransaction(long value, String address) throws Exception {

        //TODO - This is a big one

        // 1. Collect all UTXO for this wallet and calculate balance
        // 2. Check if there are sufficient funds --- Exception?
        // 3. Choose a number of UTXO to be spent --- Strategy?
        // 4. Calculate change
        // 5. Create an "empty" transaction
        // 6. Add chosen inputs
        // 7. Add 1 or 2 outputs, depending on change
        // 8. Sign the transaction
        // 9. Calculate the hash for the transaction
        // 10. return
        return null;

        // PS! We have not updated the UTXO yet. That is normally done
        // when appending the block to the blockchain, and not here!
        // Do that manually from the Application-no.kalli.main.
    }

    @Override
    public String toString() {
        //TODO
        return null;
    }

    public long getBalance() {
        //TODO
        return 0;
    }

    //TODO Getters?

    private long calculateBalance(Collection<Output> outputs) {
        //TODO
        return 0;
    }

    private Map<Input, Output> collectMyUtxo() {
        //TODO
        return null;
    }

}
