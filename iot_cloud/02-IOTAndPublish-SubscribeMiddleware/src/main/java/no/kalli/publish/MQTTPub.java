package no.kalli.publish;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public abstract class MQTTPub implements Runnable {

    private String broker;
    private String username;
    private String password;

    private MqttClient publisherClient;

    public MQTTPub(CloudMQTTConfiguration configuration) {
        broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        username = configuration.getUser();
        password = configuration.getPassword();
    }

    abstract void publish() throws MqttException, InterruptedException;

    private void connect() {
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

    private void disconnect() throws MqttException {
        publisherClient.disconnect();
    }

    @Override
    public void run() {
        try {
            System.out.println("Sensor publisher running");
            connect();
            publish();
            disconnect();
            System.out.println("Sensor publisher stopping");
        } catch (InterruptedException | MqttException e) {
            System.out.println("Sensor publisher: " + e.getMessage());
            e.printStackTrace();
        }
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

    public MqttClient getPublisherClient() {
        return publisherClient;
    }

    public void setPublisherClient(MqttClient publisherClient) {
        this.publisherClient = publisherClient;
    }
}
