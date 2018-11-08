package no.kalli.publish;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import no.kalli.roomcontrol.Heating;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTPubHeating extends MQTTPub implements Runnable {
    private String topic;
    private int qos;
    private Heating heating;

    public MQTTPubHeating(CloudMQTTConfiguration configuration, Heating heating) {
        super(configuration);
        topic = configuration.getHeat().getTopic();
        qos = configuration.getHeat().getQos();
        this.heating = heating;
    }

    public void publish() throws MqttException, InterruptedException {

        /*
        If temperatur of room is high, turn of the heater
         */
        for (int i = 0; i < 10; i++) {

            String heat = String.valueOf("ON");

            System.out.println("Publishing message: " + heat);

            MqttMessage message = new MqttMessage(heat.getBytes());
            message.setQos(qos);

            getPublisherClient().publish(topic, message);

            Thread.sleep(10000);
        }

    }
}
