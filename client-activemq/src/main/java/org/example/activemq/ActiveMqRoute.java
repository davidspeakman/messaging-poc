package org.example.activemq;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActiveMqRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        from(activemq("topic:pubsub.topic1")).routeId("ActiveMQ")
                .log("${body}")
                .setBody(simple("${body} + ' + response'"))
                .to(activemq("topic:pubsub.topic2"));
    }

}