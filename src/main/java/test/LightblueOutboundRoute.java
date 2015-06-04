package test;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultConsumerTemplate;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.hook.publish.model.Event;

public class LightblueOutboundRoute extends RouteBuilder {

    private final LightblueClient lightblueClient;

    public LightblueOutboundRoute(LightblueClient lightblueClient) {
        this.lightblueClient = lightblueClient;
    }

    @Override
    public void configure() throws Exception {
        from("timer:publishCollection?period=1000")
                .bean(new EventListener(
                        new DefaultConsumerTemplate(getContext()),
                        new LightblueDataEndpoint(lightblueClient, getContext())), "listen")
                .process(new P());
    }

    public static class EventListener {

        private final Endpoint endpoint;
        private final DefaultConsumerTemplate consumer;

        public EventListener(DefaultConsumerTemplate consumer, Endpoint endpoint) {
            this.endpoint = endpoint;
            this.consumer = consumer;
        }

        @Handler
        public Event listen() {
            return consumer.receiveBody(endpoint, 1000, Event.class);
        }
    }

    public static class P implements Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            System.out.println("here");
        }

    }

}
