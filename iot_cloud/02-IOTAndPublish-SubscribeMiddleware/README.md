# 02-IOTAndPublish-SubscribeMiddleware

##### *By Kristoffer-Andre Kalliainen (181192)*
[GitHub repository - 181192](https://github.com/181192/DAT159/tree/master/iot_cloud/02-IOTAndPublish-SubscribeMiddleware)

## About the project
- Written with Kotlin 1.3.0, and Java 10
- Compiled to JVM 1.8
- Using [Maven](https://maven.apache.org/) as build framework and [dependency manager](https://mvnrepository.com/)
- [Maven-Assemby-Plugin](http://maven.apache.org/plugins/maven-assembly-plugin/) to deploy runnable jar
- CLI support made possible with [PicoCLI](https://github.com/remkop/picocli)
- [CloudMQTT](https://www.cloudmqtt.com/) as a hosted MQTT broker.
- [Docker](https://www.docker.com/) support
- [Docker-Compose](https://docs.docker.com/compose/install/) service.

To build the project from scratch run `mvn clean install` in the project directory, or provide a path to the `pom.xml`.
Kotlin is setup to be installed as a Maven assembly.


## Overview
The architecture of this system is that we have a Room Device that has a temperature
sensor and a heating element. The temperature sensor publishes the temperature to the CloudMQTT
server. The Room Device also listens for messages to turn on and off the heating element, that
is being controlled by the Controller. This controller listens for the temperatures updates,
and adjust the state of the heating element on the Room Device.
And to have an overview of whats happening we have a display, that is displaying out the temperatures
from the temperature sensor. 
![overview](https://raw.githubusercontent.com/181192/DAT159/master/iot_cloud/02-IOTAndPublish-SubscribeMiddleware/overview.png)

## Build and configure the project
First we need to create a `config.yml` file to store our CloudMQTT credentials.
I have provided a sample file `config.sample.yml`, that have this content:
```yaml
server:
  url: tcp://m21.cloudmqtt.com
  port: 15453
  sslPort: 25453
  websocketPort: 35453
name: 02-IOTAndPublish-SubscribeMiddleware
user: sdfgsdf
password: sadfgdsgs
api: 047b9d88-6da0-4123d-a94d-598c80asdaasdf
```
Change the values according to your CloudMQTT account.

Now we can build the project
```shell
mvn clean install
```

## CLI Support
```shell
$ java -jar target/Display.jar --help

Usage: MQTTP Temperature [-h] [-f=string]
02-IOT and Publish-Subscribe Middleware
  -f, --file=string   Name of config file for CloudMQTTP
  -h, --help          Display a help message
```

## Room Device
```shell
$ java -jar RoomDevice.jar -f config.yaml

Sensor publisher running
Connecting to broker: tcp://m21.cloudmqtt.com:15453
Connecting to broker: tcp://m21.cloudmqtt.com:15453
Connected
Connected
Publishing message: 19.9484
Subscribed to topic: Heat
Publishing message: 20.507599999999996
Publishing message: 19.948299999999996
Publishing message: 20.508199999999995
Publishing message: 19.948099999999997
Publishing message: 20.507899999999996
Publishing message: 19.947799999999997
Publishing message: 20.5078
Publishing message: 19.9483
Publishing message: 20.5079
Sensor publisher stopping

```

## Heat Controller
```shell
$ java -jar 02-IOTAndPublish-SubscribeMiddleware-HeatController.jar -f config.yaml

Sensor publisher running
Connecting to broker: tcp://m21.cloudmqtt.com:15453
Connecting to broker: tcp://m21.cloudmqtt.com:15453
Connected
Connected
Publishing message: ON
Subscribed to topic: Temp
Sub message: 20.507599999999996
Publishing message: OFF
Sub message: 19.948299999999996
Publishing message: ON
Sub message: 20.508199999999995
Publishing message: OFF
Sub message: 19.948099999999997
Publishing message: ON
Sub message: 20.507899999999996
Publishing message: OFF
Sub message: 19.947799999999997
Publishing message: ON
Sub message: 20.5078
Publishing message: OFF
Sub message: 19.9483
Publishing message: ON
Sub message: 20.5079
Publishing message: OFF
Sensor publisher stopping
```

## Display
```shell
$ java -jar 02-IOTAndPublish-SubscribeMiddleware-HeatController.jar -f config.yaml

Connecting to broker: tcp://m21.cloudmqtt.com:15453
Connected
Subscribed to topic: Temp
Time:	2018-11-09 01:06:34.453  Topic:	Temp  Message:	20.507599999999996  QoS:	1
Time:	2018-11-09 01:06:44.511  Topic:	Temp  Message:	19.948299999999996  QoS:	1
Time:	2018-11-09 01:06:54.565  Topic:	Temp  Message:	20.508199999999995  QoS:	1
Time:	2018-11-09 01:07:04.617  Topic:	Temp  Message:	19.948099999999997  QoS:	1
Time:	2018-11-09 01:07:14.668  Topic:	Temp  Message:	20.507899999999996  QoS:	1
Time:	2018-11-09 01:07:24.72   Topic:	Temp  Message:	19.947799999999997  QoS:	1
Time:	2018-11-09 01:07:34.769  Topic:	Temp  Message:	20.5078             QoS:	1
Time:	2018-11-09 01:07:44.821  Topic:	Temp  Message:	19.9483             QoS:	1
Time:	2018-11-09 01:07:54.881  Topic:	Temp  Message:	20.5079             QoS:	1

```

## Run with Docker-compose
First we need to setup the environment variables. Create a .env file in the project directory.

I have provided a `env.sample` file as a reference.
```shell
CONFIG_FILE=/home/k/config.yml
```
Docker-compose will use this env variable to point to the correct config file that you created earlier.

To build the project run
```shell
mvn clean install
```
To build the docker images
```shell
docker-compose build
```
To run the docker-compose service
```shell
docker-compose up -d
```
To view the logs of all the containers
```shell
docker-compose logs -tf
```
To view the log of a single container, replace name with either `display`, `room_device` or `heat_controller`
```shell
docker-compose logs -tf name
```

## Output from docker-compose
![output](https://raw.githubusercontent.com/181192/DAT159/master/iot_cloud/02-IOTAndPublish-SubscribeMiddleware/output.png)

## Output from CloudMQTT
![cloud_mqtt](https://raw.githubusercontent.com/181192/DAT159/master/iot_cloud/02-IOTAndPublish-SubscribeMiddleware/cloudmqtt_output.png)