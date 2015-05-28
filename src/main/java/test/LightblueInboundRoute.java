package test;

import org.apache.camel.builder.RouteBuilder;

import test.model.User;
import test.transformer.LightblueInsertRequestTransformer;
import test.transformer.XmlToObjectTransformer;
import test.verification.LightblueErrorVerifier;

import com.redhat.lightblue.client.LightblueClient;

public class LightblueInboundRoute extends RouteBuilder {

    private final LightblueClient lightblueClient;

    public LightblueInboundRoute(LightblueClient lightblueClient) {
        this.lightblueClient = lightblueClient;
    }

    @Override
    public void configure() throws Exception {
        from("direct:start")
                .bean(new XmlToObjectTransformer<User[]>(User[].class))
                .bean(new LightblueInsertRequestTransformer("user"))
                .to(new LightblueDataEndpoint(lightblueClient, getContext()))
                .bean(new LightblueErrorVerifier());
    }
}
