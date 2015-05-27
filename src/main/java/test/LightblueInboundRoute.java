package test;

import org.apache.camel.builder.RouteBuilder;

public class LightblueInboundRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("stream:in")
                .transform(simple("transformed ${body.toUpperCase()}"))
                .to("stream:out");
    }
}
