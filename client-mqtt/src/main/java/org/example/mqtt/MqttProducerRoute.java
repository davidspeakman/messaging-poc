package org.example.mqtt;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;
import java.util.Properties;

@ApplicationScoped
public class MqttProducerRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        Properties properties = new Properties();
        properties.setProperty("com.ibm.ssl.trustStore", "../../client.ts");
        properties.setProperty("com.ibm.ssl.trustStorePassword", "123456");
        properties.setProperty("com.ibm.ssl.keyStore", "../../client.ks");
        properties.setProperty("com.ibm.ssl.keyStorePassword", "123456");

        from(timer("demo").period(2000).repeatCount(3000)).routeId("Producer")
                .setBody(simple("Hello World from {{pdt}}"))
                .log("${body}")
                .to(paho("goc_cpc.std.apps.dia.events.newstop.item.wc.{{pdt}}.route.{{pdt}}").cleanSession(true).qos(2).sslClientProps(properties));
    }

}
