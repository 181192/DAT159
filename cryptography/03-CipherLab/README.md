# 03-CipherLab - Oblig02


CLI support made possibe with [PicoCLI](https://github.com/remkop/picocli). Compiled with Java 10.

Implemented help support either with `-h` or with `--help`:

```
$ java -jar target/BasicClient-1.0.0-jar-with-dependencies.jar --help
Usage: CryptoUltimate [-h] [-m=MESSAGE]
Send message with given argument
  -h, --help              display a help message
  -m, --message=MESSAGE   Message to encrypt
```


## Part 01 - Basic
This is just the standard CipherLab.zip.

> The server
```
$ java -jar target/BasicServer-1.0.0-jar-with-dependencies.jar
Waiting for requests from client...
Connected to client at the address: /127.0.0.1
Message from DesClient: ThisIsACoolTest
Waiting for requests from client...
```

> The client
```
$ java -jar target/BasicClient-1.0.0-jar-with-dependencies.jar -m ThisIsACoolTest
Connected to DesServer on localhost/127.0.0.1
Response from server: ThisIsACoolTest
```


## Part 02 - DES encryption
This is a DES implementation using ECB mode with Bounty Castle Provider and Secret Key using Java KeyStore.

> The server
```
$ java -jar target/DesServer-1.0.0-jar-with-dependencies.jar
Waiting for requests from client...
Connected to client at the address: /127.0.0.1
Message from DesClient: ThisIsACoolTest
Waiting for requests from client...
```

> The client
```
$ java -jar target/DesClient-1.0.0-jar-with-dependencies.jar -m ThisIsACoolTest
Connected to DesServer on localhost/127.0.0.1
Response from server: ThisIsACoolTest
```

## Part 03 - SSL and CA
This is a SSL implementation with Certificate Authority using Java TrustStore and KeyStore.

> The server
```
$ java -jar target/SslServer-1.0.0-jar-with-dependencies.jar
Waiting for requests from client...
Connected to client at the address: /127.0.0.1
Message from DesClient: ThisIsACoolTest
Waiting for requests from client...
```

> The client
```
$ java -jar target/SslClient-1.0.0-jar-with-dependencies.jar -m ThisIsACoolTest
Connected to DesServer on localhost/127.0.0.1
Response from server: ThisIsACoolTest
```

## Resources

- [Java2s - Encrypting a string with DES](http://www.java2s.com/Code/Java/Security/EncryptingaStringwithDES.htm)
- [Stackoverflow - Base64 Encoding in Java](https://stackoverflow.com/questions/13109588/base64-encoding-in-java)
- [pyJKS - Difference between JKS and JCEKS keystores](https://pyjks.readthedocs.io/en/latest/jks.html)
- [neil Madden - Java keystores](https://neilmadden.blog/2017/11/17/java-keystores-the-gory-details/)
- [Should I use ECB or CBC encryption mode for my block cipher?](https://crypto.stackexchange.com/questions/225/should-i-use-ecb-or-cbc-encryption-mode-for-my-block-cipher)
- [JenKov - Java KeyStore](http://tutorials.jenkov.com/java-cryptography/keystore.html)
- [Java Code Examples for java.security.KeyStore.SecretKeyEntry](https://www.programcreek.com/java-api-examples/?class=java.security.KeyStore&method=SecretKeyEntry)
- [Generate Secure Keys with Keytool](https://docs.oracle.com/javase/tutorial/security/toolsign/step3.html)
- [keytool - Key and Certificate Management Tool](https://docs.oracle.com/javase/6/docs/technotes/tools/solaris/keytool.html#genseckeyCmd)
- [DES with ECB example](https://examples.javacodegeeks.com/core-java/security/des-with-ecb-example/)
- [Java Code Examples for javax.net.ssl.SSLServerSocketFactory](https://www.programcreek.com/java-api-examples/?api=javax.net.ssl.SSLServerSocketFactory)
- [Java Security Tutorial – Step by Step guide to create SSL connection and certificates](https://www.javacodegeeks.com/2013/06/java-security-tutorial-step-by-step-guide-to-create-ssl-connection-and-certificates.html)
- [How do I load a file from resource folder?](https://stackoverflow.com/questions/15749192/how-do-i-load-a-file-from-resource-folder)
- [Maven - Bounty Castle Provider](https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk15on/1.60)
