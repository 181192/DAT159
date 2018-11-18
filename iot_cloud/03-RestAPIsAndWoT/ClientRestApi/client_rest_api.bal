import ballerina/http;
import ballerina/log;
import ballerinax/docker;
import ballerina/time;
import ballerina/swagger;
import ballerina/system;

type Temperature record {
    float temperature;
    !...
};

type Heater record {
    string heat;
    !...
};

@docker:Expose
@swagger:ClientEndpoint
endpoint http:Listener listener {
    port: 9090
};

@swagger:ClientConfig {
    generate: true
}

@http:ServiceConfig {
    basePath: "/"
}
service<http:Service> client bind listener {

    json<Temperature> temp = {
        temperature: 0.0
    };

    json<Heater> heater = {
        heat: "OFF"
    };

    @http:ResourceConfig {
        methods: ["GET"],
        path: "/get/latest/dweet/for/{thingName}"
    }
    get(endpoint caller, http:Request req, string thingName) {
        http:Response res = new;

        time:Time time = time:currentTime();

        json payload = {
            "this": "succeeded",
            "by": "dweeting",
            "the": "dweet",
            ^"with": {
                "thing": thingName,
                "created": time.toString(),
                "transaction": system:uuid()
            }
        };

        if (thingName.equalsIgnoreCase("kalli-temperature")) {
            payload.^"with".content = temp;
            res.setJsonPayload(untaint payload);
        } else if (thingName.equalsIgnoreCase("kalli-heater")) {
            payload.^"with".content = heater;
            res.setJsonPayload(untaint payload);
        } else {
            res.statusCode = 404;
            res.setPayload("Sorry, thing not found");
        }

        caller->respond(res) but {
            error e => log:printError("Error sending response", err = e)
        };
    }

    @http:ResourceConfig {
        methods: ["PUT"],
        path: "/dweet/for/{thingName}"
    }
    update(endpoint caller, http:Request req, string thingName) {
        http:Response res = new;
        json updateResource = check req.getJsonPayload();

        if (thingName.equalsIgnoreCase("kalli-temperature")) {
            temp.temperature = updateResource.temperature;
            res.setJsonPayload(untaint temp);
        } else if (thingName.equalsIgnoreCase("kalli-heater")) {
            heater.heat = updateResource.heat;
            res.setJsonPayload(untaint heater);
        } else {
            res.statusCode = 404;
            res.setPayload("Sorry, thing not found");
        }

        caller->respond(res) but {
            error e => log:printError("Error sending response", err = e)
        };
    }
}
