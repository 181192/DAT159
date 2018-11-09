package no.kalli.subscribe;

import cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public abstract class MQTTSub implements MqttCallback, Runnable {

    private String broker;
    private String username;
    private String password;

    private MqttClient client;

    MQTTSub(CloudMQTTConfiguration configuration) throws MqttException {
        broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        username = configuration.getUser();
        password = configuration.getPassword();
    }

    void connect() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());

        try {
            client = new MqttClient(broker, MqttClient.generateClientId(), new MemoryPersistence());
            client.setCallback(this);
            client.connect(connOpts);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        System.out.println("Connected");
    }

    void disconnect() {
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);

    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public abstract void messageArrived(String topic, MqttMessage message) throws Exception;

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

    @Override
    public void run() {
        try {
            System.out.println("Connecting to broker: " + getBroker());
            connect();

            String topic = "Temp";
            int qos = 1;

            getClient().subscribe(topic, qos);
            System.out.println("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    String getBroker() {
        return broker;
    }

    MqttClient getClient() {
        return client;
    }
}
