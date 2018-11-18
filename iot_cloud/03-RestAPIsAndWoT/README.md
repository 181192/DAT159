# 02-IOTAndPublish-SubscribeMiddleware

##### _By Kristoffer-Andre Kalliainen (181192)_

[GitHub repository - 181192](https://github.com/181192/DAT159/tree/master/iot_cloud/03-RestAPIsAndWoT)

## About the project

- Written with Kotlin 1.3.10, and Java 10
- Compiled to JVM 1.8
- Using [Maven](https://maven.apache.org/) as build framework and [dependency manager](https://mvnrepository.com/)
- [Maven-Assemby-Plugin](http://maven.apache.org/plugins/maven-assembly-plugin/) to deploy runnable jar

To build the project from scratch run `mvn clean install` in the project directory, or provide a path to the `pom.xml`.
Kotlin is setup to be installed as a Maven assembly.

## Overview

The architecture of this system is that we have a Room Device that has a temperature
sensor and a heating element. The temperature sensor sends a `HTTP PUT` request with the temperature 
to the REST API Endpoint. The Room Device also listens for messages to turn on and off the heating 
element by doing a `HTTP GET` request to the REST API Endpoint, that is being controlled by the Controller.
This controller listens for the temperatures updates by also doing a `HTTP GET` request, and adjust the state 
of the heating element on the Room Device. And to have an overview of whats happening we have a display, that is 
displaying out the temperatures from the temperature sensor.
![overview](https://raw.githubusercontent.com/181192/DAT159/master/iot_cloud/03-RestAPIsAndWoT/overview.png)

## Build and configure the project

To build the project run

```shell
mvn clean install
```

## Room Device

```shell
$ java -jar target/RoomDevice.jar
```

## Heat Controller

```shell
$ java -jar target/HeatController.jar
```

## Display

```shell
$ java -jar target/Display.jar
```

## Server

```shell
$ java -jar target/Server.jar
```