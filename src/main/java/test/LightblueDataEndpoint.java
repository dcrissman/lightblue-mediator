package test;

import org.apache.camel.Consumer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultProducer;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;
import com.redhat.lightblue.client.response.LightblueResponse;

public class LightblueDataEndpoint extends DefaultEndpoint {

    private final LightblueClient lightblueClient;

    public LightblueDataEndpoint(LightblueClient lightblueClient) {
        this.lightblueClient = lightblueClient;
    }

    @Override
    public Producer createProducer() throws Exception {
        return new DefaultProducer(this) {

            @Override
            public void process(Exchange exchange) throws Exception {
                AbstractLightblueDataRequest request = exchange.getIn(AbstractLightblueDataRequest.class);
                LightblueResponse response = lightblueClient.data(request);
                exchange.getOut().setBody(response);
            }

        };
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new DefaultConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
