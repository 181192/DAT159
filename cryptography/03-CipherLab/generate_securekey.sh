#!/usr/bin/env bash

DIRECTORY="src/main/resources"
PASSWORD_FILE="$DIRECTORY/password.txt"
KEYSTORE_NAME="des_keystore.jceks"

#read -p "Enter your certificate password : " PASSWORD
#echo "The password is: $PASSWORD"
PASSWORD=changeit

if [ ! -d "$DIRECTORY" ]; then
    mkdir ${DIRECTORY}
fi

if [ -f "$DIRECTORY"/keystore.jceks ]; then
    rm ${DIRECTORY}/keystore.jceks
fi

if [ -f "$PASSWORD_FILE" ]; then
    rm ${PASSWORD_FILE}
fi

echo ${PASSWORD} >> ${PASSWORD_FILE}

# Generate DES 56 bit secure key with alias "securekey"
keytool -genseckey -alias securekey -keyalg des -keysize 56 -keypass ${PASSWORD} -storepass ${PASSWORD} -keystore ${DIRECTORY}/${KEYSTORE_NAME} -storetype jceks

# Verify if the certificate was successfully added into the keystore.
keytool -list -v -keystore ${DIRECTORY}/${KEYSTORE_NAME} -storetype jceks -storepass ${PASSWORD}