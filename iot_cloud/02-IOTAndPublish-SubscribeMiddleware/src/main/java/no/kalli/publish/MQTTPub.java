package no.kalli.publish;

import cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Kristoffer-Andre Kalliainen
 */
class MQTTPub {

    private String broker;
    private String username;
    private String password;

    private MqttClient publisherClient;

    MQTTPub(CloudMQTTConfiguration configuration) {
        broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        username = configuration.getUser();
        password = configuration.getPassword();
    }

    void connect() {
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            publisherClient = new MqttClient(broker, MqttClient.generateClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);
            publisherClient.connect(connOpts);
            System.out.println("Connected");

        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }
    }

    void disconnect() throws MqttException {
        publisherClient.disconnect();
    }

    MqttClient getPublisherClient() {
        return publisherClient;
    }
}
