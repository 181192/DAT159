package no.kalli.subscribe;

import no.kalli.cloudmqttp.CloudMQTTPConfiguration;
import no.kalli.cloudmqttp.YamlConfigRunner;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author tosindo
 */
public class MQTTSubTemperature implements MqttCallback {

    private String topic;

    public MQTTSubTemperature(CloudMQTTPConfiguration configuration) throws MqttException {
        String topic = configuration.getSubscriber().getTopic();
        int qos = configuration.getSubscriber().getQos();
        String broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        String clientId = configuration.getApi();
        String username = configuration.getUser();
        String password = configuration.getPassword();

        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(username);
        connOpts.setPassword(password.toCharArray());

        System.out.println("Connecting to broker: " + broker);

        MqttClient client = new MqttClient(broker, clientId, new MemoryPersistence());
        client.setCallback(this);
        client.connect(connOpts);
        System.out.println("Connected");

        client.subscribe(topic, qos);
        System.out.println("Subscribed to topic: " + topic);

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
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
        this.setTopic(new String(message.getPayload()));
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public static void main(String args[]) throws MqttException {
        var configRunner = new YamlConfigRunner();
        new MQTTSubTemperature(configRunner.setup(args));
    }

}
