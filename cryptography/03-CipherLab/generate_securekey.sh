#!/usr/bin/env bash

DIRECTORY="src/main/resources"
PASSWORD_FILE="$DIRECTORY/password.txt"

#read -p "Enter your certificate password : " PASSWORD
#echo "The password is: $PASSWORD"
PASSWORD=changeit

if [ ! -d "$DIRECTORY" ]; then
    mkdir ${DIRECTORY}
fi

if [ -f "$DIRECTORY"/keystore.jceks ]; then
    rm ${DIRECTORY}/keystore.jceks
fi

echo ${PASSWORD} >> ${PASSWORD_FILE}


keytool -genseckey -alias securekey -keyalg des -keysize 56 -keypass ${PASSWORD} -storepass ${PASSWORD} -keystore ${DIRECTORY}/keystore.jceks -storetype jceks

# Verify if the certificate was successfully added into the keystore.
keytool -list -v -keystore ${DIRECTORY}/keystore.jceks -storetype jceks -storepass ${PASSWORD}