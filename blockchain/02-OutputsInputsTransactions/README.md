# Oblig3: Blockchain Techology - Outputs, Inputs and Transactions

##### *By Kristoffer-Andre Kalliainen (181192)*
[GitHub repository - 181192](https://github.com/181192/DAT159/tree/master/blockchain/02-OutputsInputsTransactions)

## About the project
- Written with Kotlin 1.2.71
- Compiled to JVM 1.8
- Using [Maven](https://maven.apache.org/) as build framework and [dependency manager](https://mvnrepository.com/)
- [Maven-Assemby-Plugin](http://maven.apache.org/plugins/maven-assembly-plugin/) to deploy runnable jar

To build the project from scratch run `mvn clean install` in the project directory, or provide a path to the `pom.xml`.
Kotlin is setup to be installed as a Maven assembly, and the `javax.xml.bind` library is also provided as a dependency.

## Run the project
To run the generated jar from Maven, execute the following command
```shell
$ java -jar target/02-OutputsInputsTransactions-1.0-SNAPSHOT-jar-with-dependencies.jar 

Block1:
CoinbaseTx(xPWefpId9Yder69ZRR9m3/03jmPrdBkKtP6q70K9M2Q=)
        message='Hello Genesis', output=Output(value=100, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=))
Block2:
CoinbaseTx(Jm9Nb43vcFFfj3ISNObIJU1B6lIsHYSs6ToRUQ5fXfY=)
        message='Hello from the other side', output=Output(value=100, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=))
Trasaction( eahyISqjQC9f0EF9boM4q0zGQTWaYDFGWh9lrFThWrQ= )
        Inputs:
                Input(prevTxHash=xPWefpId9Yder69ZRR9m3/03jmPrdBkKtP6q70K9M2Q=, prevOutputIndex=0)
        Outputs:
                Output(value=20, address=2snmUoJNScvCOlmneUoNST95Ft3fDTebkNbrAklLWQY=)
                Output(value=80, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=)
Block2:
CoinbaseTx(Ym9DHZ7aSEut8dtA1uN2xjA+YD0AG6mqaX0L0LPtPQE=)
        message='Hello darkness, my old friend', output=Output(value=100, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=))
Trasaction( yfUIhy/BLgZ97z80lKgJHMQg+xYcmllBqNHquhNq3hk= )
        Inputs:
                Input(prevTxHash=Jm9Nb43vcFFfj3ISNObIJU1B6lIsHYSs6ToRUQ5fXfY=, prevOutputIndex=0)
        Outputs:
                Output(value=20, address=2snmUoJNScvCOlmneUoNST95Ft3fDTebkNbrAklLWQY=)
                Output(value=80, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=)
UTXO:
Input(prevTxHash=yfUIhy/BLgZ97z80lKgJHMQg+xYcmllBqNHquhNq3hk=, prevOutputIndex=0)
        -->     Output(value=20, address=2snmUoJNScvCOlmneUoNST95Ft3fDTebkNbrAklLWQY=)
Input(prevTxHash=yfUIhy/BLgZ97z80lKgJHMQg+xYcmllBqNHquhNq3hk=, prevOutputIndex=1)
        -->     Output(value=80, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=)
Input(prevTxHash=Ym9DHZ7aSEut8dtA1uN2xjA+YD0AG6mqaX0L0LPtPQE=, prevOutputIndex=0)
        -->     Output(value=100, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=)
Input(prevTxHash=eahyISqjQC9f0EF9boM4q0zGQTWaYDFGWh9lrFThWrQ=, prevOutputIndex=1)
        -->     Output(value=80, address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=)
Input(prevTxHash=eahyISqjQC9f0EF9boM4q0zGQTWaYDFGWh9lrFThWrQ=, prevOutputIndex=0)
        -->     Output(value=20, address=2snmUoJNScvCOlmneUoNST95Ft3fDTebkNbrAklLWQY=)

The miner's wallet:
Wallet(id='Miner Wallet', address=b5fTQMKDoa9zkvI+AOfckaLJkoLhgDx82o1RqDQMDsg=, balance=260)

My wallet:
Wallet(id='Kalli's Wallet', address=2snmUoJNScvCOlmneUoNST95Ft3fDTebkNbrAklLWQY=, balance=40)

```