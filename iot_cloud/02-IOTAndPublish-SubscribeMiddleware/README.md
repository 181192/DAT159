# 02-IOTAndPublish-SubscribeMiddleware

##### *By Kristoffer-Andre Kalliainen (181192)*
[GitHub repository - 181192](https://github.com/181192/DAT159/tree/master/iot_cloud/02-IOTAndPublish-SubscribeMiddleware)

## About the project
- Written with Kotlin 1.3.0
- Compiled to JVM 1.8
- Using [Maven](https://maven.apache.org/) as build framework and [dependency manager](https://mvnrepository.com/)
- [Maven-Assemby-Plugin](http://maven.apache.org/plugins/maven-assembly-plugin/) to deploy runnable jar
- CLI support made possible with [PicoCLI](https://github.com/remkop/picocli)

To build the project from scratch run `mvn clean install` in the project directory, or provide a path to the `pom.xml`.
Kotlin is setup to be installed as a Maven assembly.


## Room Device
```shell
$ java -jar 02-IOTAndPublish-SubscribeMiddleware-RoomDevice.jar -f config.yaml

CloudMQTTConfiguration(
	server=Server(url=tcp://m21.cloudmqtt.com, port=15453, sslPort=25453, websocketPort=35453),
name='02-IOTAndPublish-SubscribeMiddleware',
user='*******',
password='*******',
api='*******',
heat=Heat(topic=Heat, qos=1),
temp=Temperature(topic=Temp, qos=1))
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

CloudMQTTConfiguration(
	server=Server(url=tcp://m21.cloudmqtt.com, port=15453, sslPort=25453, websocketPort=35453),
name='02-IOTAndPublish-SubscribeMiddleware',
user='*******',
password='*******',
api='*******',
heat=Heat(topic=Heat, qos=1),
temp=Temperature(topic=Temp, qos=1))
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

CloudMQTTConfiguration(
	server=Server(url=tcp://m21.cloudmqtt.com, port=15453, sslPort=25453, websocketPort=35453),
name='02-IOTAndPublish-SubscribeMiddleware',
user='*******',
password='*******',
api='*******',
heat=Heat(topic=Heat, qos=1),
temp=Temperature(topic=Temp, qos=1))
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
