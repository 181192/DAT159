package no.kalli.subscribe;

import cloudmqttp.CloudMQTTConfiguration;
import no.kalli.publish.MQTTPubHeating;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import roomcontrol.TemperatureSensor;

/**
 * @author Kristoffer-Andre Kalliainen
 */
public class MQTTSubTemperature extends MQTTSub implements Runnable {

    private MQTTPubHeating pubHeating;

    public MQTTSubTemperature(CloudMQTTConfiguration configuration, MQTTPubHeating pubHeating) throws MqttException {
        super(configuration);
        this.pubHeating = pubHeating;
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            double temp = Double.parseDouble(new String(message.getPayload()));
            pubHeating.publish(temp < 20 ? "ON" : "OFF");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
