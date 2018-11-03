package no.kalli.publish;

import no.kalli.cloudmqttp.CloudMQTTPConfiguration;
import no.kalli.cloudmqttp.YamlConfigRunner;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

public class MQTTPubTemperature {

    private String topic;
    private int qos;
    private String broker;
    private String clientId;
    private String username;
    private String password;

    private MqttClient publisherClient;
    private Random rand;

    public MQTTPubTemperature(CloudMQTTPConfiguration configuration) {
        topic = configuration.getPublisher().getTopic();
        qos = configuration.getPublisher().getQos();
        broker = configuration.getServer().getUrl() + ":" + configuration.getServer().getPort();
        clientId = configuration.getApi();
        username = configuration.getUser();
        password = configuration.getPassword();
        rand = new Random();
    }

    private void publish() throws MqttPersistenceException, MqttException, InterruptedException {

        //loop forever
        while (true) {
            String temp = String.valueOf(rand.nextInt(60));
            System.out.println("Publishing message: " + temp);
            MqttMessage message = new MqttMessage(temp.getBytes());
            message.setQos(qos);
            publisherClient.publish(topic, message);
            Thread.sleep(4000);
        }

    }

    private void connect() {
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            publisherClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());
            System.out.println("Connecting to broker: " + broker);
            publisherClient.connect(connOpts);
            System.out.println("Connected");

        } catch (MqttException e) {
            System.out.println("reason " + e.getReasonCode());
            System.out.println("msg " + e.getMessage());
            System.out.println("loc " + e.getLocalizedMessage());
            System.out.println("cause " + e.getCause());
            System.out.println("excep " + e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException, MqttPersistenceException, MqttException {
        var configRunner = new YamlConfigRunner();
        MQTTPubTemperature mqttpub = new MQTTPubTemperature(configRunner.setup(args));
        mqttpub.connect();
        mqttpub.publish();
    }
}
