# AlertsApi

All URIs are relative to *https://dweet.io*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createAlertGET**](AlertsApi.md#createAlertGET) | **GET** /alert/{who}/when/{thing}/{condition} | Create an alert for a thing. A thing must be locked before an alert can be set.
[**getAlert**](AlertsApi.md#getAlert) | **GET** /get/alert/for/{thing} | Get the alert attached to a thing.
[**removeAlert**](AlertsApi.md#removeAlert) | **GET** /remove/alert/for/{thing} | Remove an alert for a thing.


<a name="createAlertGET"></a>
# **createAlertGET**
> createAlertGET(who, thing, condition, key)

Create an alert for a thing. A thing must be locked before an alert can be set.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertsApi;


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
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **who** | **String**| A comma separated list of Email addresses to send the alert to. |
 **thing** | **String**| A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions. |
 **condition** | **String**| A condition that returns a string or a true value if a condition is met. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getAlert"></a>
# **getAlert**
> getAlert(thing, key)

Get the alert attached to a thing.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertsApi;


AlertsApi apiInstance = new AlertsApi();
String thing = "thing_example"; // String | A unique name of a thing.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.getAlert(thing, key);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertsApi#getAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="removeAlert"></a>
# **removeAlert**
> removeAlert(thing, key)

Remove an alert for a thing.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.AlertsApi;


AlertsApi apiInstance = new AlertsApi();
String thing = "thing_example"; // String | A unique name of a thing.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.removeAlert(thing, key);
} catch (ApiException e) {
    System.err.println("Exception when calling AlertsApi#removeAlert");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

