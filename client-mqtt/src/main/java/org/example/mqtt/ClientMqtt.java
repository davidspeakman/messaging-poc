package org.example.mqtt;

import io.quarkus.runtime.StartupEvent;
import org.apache.http.ssl.SSLContexts;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Tracer;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.File;
import java.security.SecureRandom;
import java.util.Properties;

@ApplicationScoped
public class ClientMqtt {

    void onStart(@Observes StartupEvent ev) throws Exception {
        System.out.println("Start client MQTT");
        String topic        = "pubsub.topic1";
        String content      = "Message from MqttPublishSample";
        int qos             = 0;
        String broker       = "ssl://$AMQ_MQTT_ROUT_HOST:443";
        String clientId     = "client-mqtt";
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("admin");
            connOpts.setPassword("admin".toCharArray());
            Properties properties = new Properties();
            properties.setProperty("com.ibm.ssl.trustStore", "../../client.ts");
            properties.setProperty("com.ibm.ssl.trustStorePassword", "XXXXXX");
            properties.setProperty("com.ibm.ssl.keyStore", "../../client.ks");
            properties.setProperty("com.ibm.ssl.keyStorePassword", "XXXXXX");
            connOpts.setSSLProperties(properties);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            sampleClient.disconnect();
            System.out.println("Disconnected");
            System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }



}