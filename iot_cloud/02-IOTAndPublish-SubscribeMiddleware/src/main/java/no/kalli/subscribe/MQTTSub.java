package no.kalli.subscribe;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public abstract class MQTTSub implements MqttCallback {

    private String broker;
    private String username;
    private String password;

    private MqttClient client;

    public MQTTSub(CloudMQTTConfiguration configuration) throws MqttException {
        broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        username = configuration.getUser();
        password = configuration.getPassword();
    }

    public void connect() {
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

    private void disconnect() {
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

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

}
