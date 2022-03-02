package org.example.activemq;

import org.apache.activemq.command.ActiveMQTopic;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActiveMqRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        from(activemq("topic:goc_cpc.std.apps.dia.events.newstop.item.wc.*.route.*")).routeId("ActiveMQ")
                .log("${body}")
                .process(e -> {
                    ActiveMQTopic topic = e.getIn().getHeader("JMSDestination", ActiveMQTopic.class);
                    e.getIn().setHeader("wc", topic.getTopicName().split("\\.")[8]);
                    e.getIn().setHeader("route", topic.getTopicName().split("\\.")[10]);
                })
                .setBody(simple("${body} ' + response'"))
                .toD(activemq("topic:reply.wc.${header.pdt}.route.${header.route}"));
    }

}
