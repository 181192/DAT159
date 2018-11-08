package no.kalli.subscribe;

import no.kalli.cloudmqttp.CloudMQTTConfiguration;
import no.kalli.roomcontrol.Display;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.Timestamp;

public class MQTTSubDisplay extends MQTTSub implements Runnable {

    private Display display;

    private String topic;
    private int qos;

    public MQTTSubDisplay(CloudMQTTConfiguration configuration, Display display) throws MqttException {
        super(configuration);
        this.display = display;
        topic = configuration.getTemperature().getTopic();
        qos = configuration.getTemperature().getQos();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String time = new Timestamp(System.currentTimeMillis()).toString();
        String tmp = "Time:\t" + time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos();

        display.write(tmp);
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
