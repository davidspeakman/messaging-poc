package org.example.mqtt;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Properties;

@ApplicationScoped
public class MqttConsumerRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        Properties properties = new Properties();
        properties.setProperty("com.ibm.ssl.trustStore", "../../client.ts");
        properties.setProperty("com.ibm.ssl.trustStorePassword", "123456");
        properties.setProperty("com.ibm.ssl.keyStore", "../../client.ks");
        properties.setProperty("com.ibm.ssl.keyStorePassword", "123456");

        from(paho("pubsub.topic2").clientId("mqtt-consumer").cleanSession(true).sslClientProps(properties)).routeId("Consumer")
                .log("${body}");
    }

}