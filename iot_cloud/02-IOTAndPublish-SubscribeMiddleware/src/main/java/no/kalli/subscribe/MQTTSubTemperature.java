package no.kalli.subscribe;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import no.kalli.roomcontrol.Display;
import no.kalli.roomcontrol.Heating;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTSubTemperature extends MQTTSub implements Runnable{

    private String topic;
    private int qos;
    private Heating heating;

    public MQTTSubTemperature(CloudMQTTConfiguration configuration, Heating heating) throws MqttException {
        super(configuration);
        topic = configuration.getTemperature().getTopic();
        qos = configuration.getTemperature().getQos();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Sub message: " + new String(message.getPayload()));
    }

    @Override
    public void run() {
        try {
            System.out.println("Connecting to broker: " + getBroker());
            connect();
            getClient().subscribe(topic, qos);
            System.out.println("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
