package no.kalli.subscribe;

import cloudmqttp.CloudMQTTConfiguration;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import roomcontrol.Heating;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTSubHeating extends MQTTSub implements Runnable {

    private Heating heating;

    public MQTTSubHeating(CloudMQTTConfiguration configuration, Heating heating) throws MqttException {
        super(configuration);
        this.heating = heating;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        var state = new String(message.getPayload());
        if (state.equals("ON"))
            heating.write(true);

        if (state.equals("OFF"))
            heating.write(false);
    }

    @Override
    public void run() {
        try {
            System.out.println("Connecting to broker: " + getBroker());
            connect();

            String topic = "Heat";
            int qos = 1;

            getClient().subscribe(topic, qos);
            System.out.println("Subscribed to topic: " + topic);
        } catch (MqttException e) {
            System.out.println("Disconnecting...");
            disconnect();
            e.printStackTrace();
        }
    }
}
