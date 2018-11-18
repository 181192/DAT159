import ballerina/io;
import ballerina/http;

//=====================================
//============Client Config============
//=====================================
public type clientClientConfig record {
    string serviceUrl;
};

//=======================================
//============Client Endpoint============
//=======================================
public type clientClientEp object {
    public http:Client client;
    public clientClientConfig config;

    public function init(clientClientConfig clientConfig);
    public function getCallerActions() returns (clientClient);
};

function clientClientEp::init(clientClientConfig clientConfig) {
    endpoint http:Client httpEp {
        url: clientConfig.serviceUrl
    };

    self.client = httpEp;
    self.config = clientConfig;
}

function clientClientEp::getCallerActions() returns (clientClient) {
    return new clientClient(self);
}

//==============================
//============Client============
//==============================
public type clientClient object {
    public clientClientEp clientEp;

    new(clientEp) {}

    public function get(string thingName) returns http:Response|error;
    public function update(string thingName) returns http:Response|error;
};

function clientClient::get(string thingName) returns http:Response|error {
    endpoint http:Client ep = self.clientEp.client;
    http:Request request = new;

    //Create the required request as needed
    string path = string `/get/latest/dweet/for/{{thingName}}`;
    return check ep->get(path, message = request);
}

function clientClient::update(string thingName) returns http:Response|error {
    endpoint http:Client ep = self.clientEp.client;
    http:Request request = new;

    //Create the required request as needed
    string path = string `/dweet/for/{{thingName}}`;
    return check ep->put(path, message = request);
}

//======================================================
//============Invocation of Generated Client============
//======================================================
public function main(string... args) {
    endpoint clientClientEp ep0 {
        serviceUrl: "http://localhost:9090/"
    };


    // Sample invocation of the generated client connector
    var res = ep0->get("thingName");
    match (res) {
        error err => io:println(err.message);
        http:Response resp => {
            var payload = resp.getTextPayload();
            match payload {
                error err => io:println(err.message);
                string message => io:println(message);
            }
        }
    }
}

