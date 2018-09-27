# Affine Cipher and Hill Cipher over Java client/server application

CLI support made possibe with [PicoCLI](https://github.com/remkop/picocli).
Compiled with Java 10.


Implemented help support either with `-h` or with `--help`:
```
$ java -jar Server-1.0.0.jar -h
Usage: CipherUltimate [-h] [-a=keyA] [-b=keyB] -c=affine|hill [-k=KEY]
                      [-m=MESSAGE]
Encrypt and decrypt message with provided cipher algorithm as argument
  -a, --keyA=keyA            Affine key A
  -b, --keyB=keyB            Affine key B
  -c, --cipher=affine|hill   Select cipher type (affine or hill)
  -h, --help                 display a help message
  -k, --keyHill=KEY          Hill key. A four letter key.
  -m, --message=MESSAGE      Message to encrypt
Copyright(c) 2018

```


## Affine example

The server:
```
$ java -jar Server-1.0.0.jar -c affine -a 5 -b 7
Waiting for requests from client...
Connected to client at the address: /127.0.0.1
Message from Client: THISISCOOL
Waiting for requests from client...

```


The client:
```
$ java -jar Client-1.0.0.jar -c affine -a 5 -b 7 -m THISISCOOL
Connected to Server on localhost/127.0.0.1
Response from server: THISISCOOL

```


## Hill example

The server:
```
$ java -jar Server-1.0.0.jar -c hill -k PATH
Waiting for requests from client...
Connected to client at the address: /127.0.0.1
Message from Client: THISISCOOL
Waiting for requests from client...
```


The client:
```
$ java -jar Client-1.0.0.jar -c hill -k PATH -m THISISCOOL
Connected to Server on localhost/127.0.0.1
Response from server: THISISCOOL

```
