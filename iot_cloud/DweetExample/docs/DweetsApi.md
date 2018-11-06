# DweetsApi

All URIs are relative to *https://dweet.io*

Method | HTTP request | Description
------------- | ------------- | -------------
[**dweetForThingPost**](DweetsApi.md#dweetForThingPost) | **POST** /dweet/for/{thing} | Create a dweet for a thing.
[**dweetQuietlyForThingPost**](DweetsApi.md#dweetQuietlyForThingPost) | **POST** /dweet/quietly/for/{thing} | Create a dweet for a thing.  This method differs from /dweet/for/{thing} only in that successful dweets result in an HTTP 204 response rather than the typical verbose response.
[**getDweetsForThingGet**](DweetsApi.md#getDweetsForThingGet) | **GET** /get/dweets/for/{thing} | Read the last 5 cached dweets for a thing.
[**getLatestDweet**](DweetsApi.md#getLatestDweet) | **GET** /get/latest/dweet/for/{thing} | Read the latest dweet for a thing.
[**listenForDweets**](DweetsApi.md#listenForDweets) | **GET** /listen/for/dweets/from/{thing} | Listen for dweets from a thing.


<a name="dweetForThingPost"></a>
# **dweetForThingPost**
> dweetForThingPost(thing, content, key)

Create a dweet for a thing.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DweetsApi;


DweetsApi apiInstance = new DweetsApi();
String thing = "thing_example"; // String | A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions.
String content = "content_example"; // String | The actual content of the string. Can be any valid JSON string.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.dweetForThingPost(thing, content, key);
} catch (ApiException e) {
    System.err.println("Exception when calling DweetsApi#dweetForThingPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions. |
 **content** | **String**| The actual content of the string. Can be any valid JSON string. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="dweetQuietlyForThingPost"></a>
# **dweetQuietlyForThingPost**
> dweetQuietlyForThingPost(thing, content, key)

Create a dweet for a thing.  This method differs from /dweet/for/{thing} only in that successful dweets result in an HTTP 204 response rather than the typical verbose response.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DweetsApi;


DweetsApi apiInstance = new DweetsApi();
String thing = "thing_example"; // String | A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions.
String content = "content_example"; // String | The actual content of the string. Can be any valid JSON string.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.dweetQuietlyForThingPost(thing, content, key);
} catch (ApiException e) {
    System.err.println("Exception when calling DweetsApi#dweetQuietlyForThingPost");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. It is recommended that you use a GUID as to avoid name collisions. |
 **content** | **String**| The actual content of the string. Can be any valid JSON string. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getDweetsForThingGet"></a>
# **getDweetsForThingGet**
> getDweetsForThingGet(thing, key)

Read the last 5 cached dweets for a thing.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DweetsApi;


DweetsApi apiInstance = new DweetsApi();
String thing = "thing_example"; // String | A unique name of a thing.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.getDweetsForThingGet(thing, key);
} catch (ApiException e) {
    System.err.println("Exception when calling DweetsApi#getDweetsForThingGet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="getLatestDweet"></a>
# **getLatestDweet**
> getLatestDweet(thing, key)

Read the latest dweet for a thing.

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DweetsApi;


DweetsApi apiInstance = new DweetsApi();
String thing = "thing_example"; // String | A unique name of a thing.
String key = "key_example"; // String | A valid key for a locked thing. If the thing is not locked, this can be ignored.
try {
    apiInstance.getLatestDweet(thing, key);
} catch (ApiException e) {
    System.err.println("Exception when calling DweetsApi#getLatestDweet");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**| A unique name of a thing. |
 **key** | **String**| A valid key for a locked thing. If the thing is not locked, this can be ignored. | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a name="listenForDweets"></a>
# **listenForDweets**
> listenForDweets(thing)

Listen for dweets from a thing.

Sorry, this function uses HTTP chunked responses and cannot be tested here. Try something like: &lt;pre&gt;curl --raw https://dweet.io/listen/for/dweets/from/{thing}&lt;/pre&gt;

### Example
```java
// Import classes:
//import io.swagger.client.ApiException;
//import io.swagger.client.api.DweetsApi;


DweetsApi apiInstance = new DweetsApi();
String thing = "thing_example"; // String | 
try {
    apiInstance.listenForDweets(thing);
} catch (ApiException e) {
    System.err.println("Exception when calling DweetsApi#listenForDweets");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **thing** | **String**|  |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

