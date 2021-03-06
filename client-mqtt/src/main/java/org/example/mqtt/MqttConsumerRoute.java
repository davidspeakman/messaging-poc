package org.example.mqtt;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Properties;

@ApplicationScoped
public class MqttConsumerRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        Properties properties = new Properties();
        properties.setProperty("com.ibm.ssl.trustStore", "C:\\dev\\aroCluster\\poc\\messaging-poc\\client.ts");
        properties.setProperty("com.ibm.ssl.trustStorePassword", "aropoc");
        properties.setProperty("com.ibm.ssl.keyStore", "C:\\dev\\aroCluster\\poc\\messaging-poc\\client.ks");
        properties.setProperty("com.ibm.ssl.keyStorePassword", "aropoc");

        from(paho("reply.wc.{{pdt}}.route.{{pdt}}").cleanSession(true).sslClientProps(properties)).routeId("Consumer")
                .log("${body}");
    }

}
