package no.kalli.publish;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import no.kalli.roomcontrol.TemperatureSensor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTPubTemperature extends MQTTPub implements Runnable {

    private String topic;
    private int qos;

    private TemperatureSensor sensor;

    public MQTTPubTemperature(CloudMQTTConfiguration configuration, TemperatureSensor sensor) {
        super(configuration);
        topic = configuration.getTemperature().getTopic();
        qos = configuration.getTemperature().getQos();
        this.sensor = sensor;
    }

    public void publish() throws MqttException, InterruptedException {

        for (int i = 0; i < 10; i++) {

            String temp = String.valueOf(sensor.read());

            System.out.println("Publishing message: " + temp);

            MqttMessage message = new MqttMessage(temp.getBytes());
            message.setQos(qos);

            getPublisherClient().publish(topic, message);

            Thread.sleep(10000);
        }

    }
}
