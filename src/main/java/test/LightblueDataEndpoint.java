package test;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultProducer;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;

public class LightblueDataEndpoint extends DefaultEndpoint {

    private final LightblueClient lightblueClient;

    public LightblueDataEndpoint(LightblueClient lightblueClient, CamelContext camelContext) {
        super();
        super.setCamelContext(camelContext);
        this.lightblueClient = lightblueClient;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new DefaultProducer(this) {

            @Override
            public void process(Exchange exchange) throws Exception {
                AbstractLightblueDataRequest request = exchange.getIn().getBody(AbstractLightblueDataRequest.class);
                if (request == null) {
                    throw new Exception("request cannot be null");
                }
                exchange.getOut().setBody(lightblueClient.data(request));
            }

        };
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new DefaultConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false; //TODO can/should this be a singleton?
    }

    @Override
    protected String createEndpointUri() {
        return "someuri"; //TODO somehow generate based on lightblueClient?
    }

}
