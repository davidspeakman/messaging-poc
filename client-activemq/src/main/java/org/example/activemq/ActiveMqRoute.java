package org.example.activemq;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActiveMqRoute extends EndpointRouteBuilder {

    @Override
    public void configure() throws Exception {

        from(activemq("topic:goc_cpc.stg.apps.dia.events.newstop.item.wc.wc1234.route.r1234")).routeId("ActiveMQ")
                .log("${body}")
                .setBody(simple("${body} + ' + response'"))
                .to(activemq("topic:goc_cpc.stg.apps.dia.notifications.purequest.pdt.wc.wc1234.route.r1234.puassign"));
    }

}