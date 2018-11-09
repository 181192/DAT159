package no.kalli.publish;

import cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import roomcontrol.TemperatureSensor;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTPubTemperature extends MQTTPub implements Runnable {

    private String topic;
    private int qos;

    private TemperatureSensor sensor;

    public MQTTPubTemperature(CloudMQTTConfiguration configuration, TemperatureSensor sensor) {
        super(configuration);
        topic = "Temp";
        qos = 1;
        this.sensor = sensor;
    }

    private void publish() throws MqttException, InterruptedException {
        for (int i = 0; i < 10; i++) {

            String temp = String.valueOf(sensor.read());

            System.out.println("Publishing message: " + temp);

            MqttMessage message = new MqttMessage(temp.getBytes());
            message.setQos(qos);

            getPublisherClient().publish(topic, message);

            Thread.sleep(10000);
        }
    }

    @Override
    public void run() {
        try {
            connect();
            System.out.println("Sensor publisher running");
            publish();
            disconnect();
            System.out.println("Sensor publisher stopping");
        } catch (InterruptedException | MqttException e) {
            System.out.println("Sensor publisher: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
