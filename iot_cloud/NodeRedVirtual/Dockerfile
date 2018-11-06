FROM nodered/node-red-docker:v8

ENV DEBIAN_FRONTEND=noninteractive

RUN npm install \
    node-red-node-smooth \
    node-red-contrib-iot-virtual-device \
    node-red-dashboard \
    node-red-contrib-moment \
    node-red-contrib-string \
    node-red-contrib-nbrowser \
    node-red-contrib-telegrambot \
    node-red-node-pushbullet \
    node-red-contrib-sqldbs \
    node-red-node-sqlite

EXPOSE 1880
