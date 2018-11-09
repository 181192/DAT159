package no.kalli.publish;

import cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTPubHeating extends MQTTPub {
    private String topic;
    private int qos;

    public MQTTPubHeating(CloudMQTTConfiguration configuration) {
        super(configuration);
        topic = "Heat";
        qos = 1;
    }

    public void publish(String heatState) throws MqttException, InterruptedException {
        System.out.println("Publishing message: " + heatState);

        MqttMessage message = new MqttMessage(heatState.getBytes());

        message.setQos(qos);

        getPublisherClient().publish(topic, message);
    }
}
