#!/usr/bin/env bash

DIRECTORY="src/main/resources"
PASSWORD_FILE="$DIRECTORY/password.txt"
KEYSTORE_NAME="ssl_keystore.jceks"

#read -p "Enter your certificate password : " PASSWORD
#echo "The password is: $PASSWORD"
PASSWORD=changeit

if [ ! -d "$DIRECTORY" ]; then
    mkdir ${DIRECTORY}
fi

if [ -f "$DIRECTORY/$KEYSTORE_NAME" ]; then
    rm ${DIRECTORY}/${KEYSTORE_NAME}
fi

if [ -f "$DIRECTORY/cacerts.jceks" ]; then
    rm ${DIRECTORY}/cacerts.jceks
fi

if [ -f "$DIRECTORY/server.cer" ]; then
    rm ${DIRECTORY}/server.cer
fi

if [ -f "$PASSWORD_FILE" ]; then
    rm ${PASSWORD_FILE}
fi

echo ${PASSWORD} >> ${PASSWORD_FILE}

# generate the certificates
keytool -genkey -alias server-alias -keyalg RSA -keypass ${PASSWORD} -storepass ${PASSWORD} -keystore ${DIRECTORY}/${KEYSTORE_NAME} -storetype jceks

# Export the generated certificate to server.cer file.
keytool -export -alias server-alias -storepass ${PASSWORD} -file ${DIRECTORY}/server.cer -keystore ${DIRECTORY}/${KEYSTORE_NAME} -storetype jceks

# Add the certificate to the trust store file
keytool -import -v -trustcacerts -alias server-alias -file ${DIRECTORY}/server.cer -keystore ${DIRECTORY}/cacerts.jceks -keypass ${PASSWORD} -storepass ${PASSWORD} -storetype jceks

# Verify if the certificate was successfully added into the keystore.
keytool -list -v -keystore ${DIRECTORY}/${KEYSTORE_NAME} -storetype jceks -storepass ${PASSWORD}

# Validate if the certificate was successfully added into the trust store.
keytool -list -keystore ${DIRECTORY}/cacerts.jceks -storetype jceks -storepass ${PASSWORD}