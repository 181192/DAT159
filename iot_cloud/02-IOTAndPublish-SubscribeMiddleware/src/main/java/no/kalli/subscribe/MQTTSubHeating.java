package no.kalli.subscribe;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import no.kalli.roomcontrol.Heating;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTSubHeating extends MQTTSub implements Runnable{

    private Heating heating;

    private String topic;
    private int qos;

    public MQTTSubHeating(CloudMQTTConfiguration configuration, Heating heating) throws MqttException {
        super(configuration);
        this.heating = heating;
        topic = configuration.getHeat().getTopic();
        qos = configuration.getHeat().getQos();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        var state = new String(message.getPayload());
        if (state.equals("ON"))
            heating.getRoom().actuate(true);

        if (state.equals("OFF"))
            heating.getRoom().actuate(false);
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
