# Node-RED docker-compose setup

Includes:

- Node-RED
- PostgresSQL
- Adminer
- InfluxDB
- Grafana

List of plugins:

- node-red-node-smooth
- node-red-contrib-iot-virtual-device
- node-red-dashboard
- node-red-contrib-moment
- node-red-contrib-string
- node-red-contrib-nbrowser
- node-red-contrib-telegrambot
- node-red-node-pushbullet
- node-red-contrib-sqldbs
- node-red-node-sqlite

## How to run

```shell
docker-compose run -d
```

Node-RED accessable at `http://localhost:1880`, Adminer at `http://localhost:8080` and Grafana at `http://localhost:3000`.

## Enviroments variables

There are three files for enviroments variables:

- `.env`
- `env.grafana`
- `env.influxdb`

## Show health check

```shell
$ docker-compose ps

          Name                         Command                       State                   Ports
-----------------------------------------------------------------------------------------------------------
noderedvirtual_adminer_1    entrypoint.sh docker-php-e ...   Up (health: starting)   0.0.0.0:8080->8080/tcp
noderedvirtual_db_1         docker-entrypoint.sh postgres    Up (healthy)            5432/tcp
noderedvirtual_grafana_1    /run.sh                          Up (healthy)            0.0.0.0:3000->3000/tcp
noderedvirtual_influxdb_1   /entrypoint.sh influxd           Up (healthy)            8086/tcp
noderedvirtual_nodered_1    npm start -- --userDir /data     Up (health: starting)   0.0.0.0:1880->1880/tcp
```
