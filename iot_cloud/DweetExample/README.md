# swagger-java-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>io.swagger</groupId>
    <artifactId>swagger-java-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "io.swagger:swagger-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

* target/swagger-java-client-1.0.0.jar
* target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import io.swagger.client.*;
import io.swagger.client.auth.*;
import io.swagger.client.model.*;
import io.swagger.client.api.AlertsApi;

import java.io.File;
import java.util.*;

public class AlertsApiExample {

    public static void main(String[] args) {
        
        AlertsApi apiInstance = new AlertsApi();
        String who = "who_example"; // String | A comma separated list of Email addresses to send the alert to.
        String thing = "thing_example"; // String | A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions.
        String condition = "condition_example"; // String | A condition that returns a string or a true value if a condition is met.
        String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
        try {
            apiInstance.createAlertGET(who, thing, condition, key);
        } catch (ApiException e) {
            System.err.println("Exception when calling AlertsApi#createAlertGET");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *https://dweet.io*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AlertsApi* | [**createAlertGET**](docs/AlertsApi.md#createAlertGET) | **GET** /alert/{who}/when/{thing}/{condition} | Create an alert for a thing. A thing must be locked before an alert can be set.
*AlertsApi* | [**getAlert**](docs/AlertsApi.md#getAlert) | **GET** /get/alert/for/{thing} | Get the alert attached to a thing.
*AlertsApi* | [**removeAlert**](docs/AlertsApi.md#removeAlert) | **GET** /remove/alert/for/{thing} | Remove an alert for a thing.
*DweetsApi* | [**dweetForThingPost**](docs/DweetsApi.md#dweetForThingPost) | **POST** /dweet/for/{thing} | Create a dweet for a thing.
*DweetsApi* | [**dweetQuietlyForThingPost**](docs/DweetsApi.md#dweetQuietlyForThingPost) | **POST** /dweet/quietly/for/{thing} | Create a dweet for a thing.  This method differs from /dweet/for/{thing} only in that successful dweets result in an HTTP 204 response rather than the typical verbose response.
*DweetsApi* | [**getDweetsForThingGet**](docs/DweetsApi.md#getDweetsForThingGet) | **GET** /get/dweets/for/{thing} | Read the last 5 cached dweets for a thing.
*DweetsApi* | [**getLatestDweet**](docs/DweetsApi.md#getLatestDweet) | **GET** /get/latest/dweet/for/{thing} | Read the latest dweet for a thing.
*DweetsApi* | [**listenForDweets**](docs/DweetsApi.md#listenForDweets) | **GET** /listen/for/dweets/from/{thing} | Listen for dweets from a thing.
*LocksApi* | [**lockThing**](docs/LocksApi.md#lockThing) | **GET** /lock/{thing} | Reserve and lock a thing.
*LocksApi* | [**removeLock**](docs/LocksApi.md#removeLock) | **GET** /remove/lock/{lock} | Remove a lock from thing.
*LocksApi* | [**unlockThing**](docs/LocksApi.md#unlockThing) | **GET** /unlock/{thing} | Unlock a thing.
*StorageApi* | [**getStoredAlerts**](docs/StorageApi.md#getStoredAlerts) | **GET** /get/stored/alerts/for/{thing} | Read all the saved alerts for a thing from long term storage.  You can query a maximum of 1 day per request and a granularly of 1 hour.
*StorageApi* | [**getStoredDweetsForThingGet**](docs/StorageApi.md#getStoredDweetsForThingGet) | **GET** /get/stored/dweets/for/{thing} | Read all the saved dweets for a thing from long term storage.  You can query a maximum of 1 day per request and a granularly of 1 hour.


## Documentation for Models



## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



