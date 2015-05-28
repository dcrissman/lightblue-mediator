package mediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import test.LightblueInboundRoute;

import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;
import com.redhat.lightblue.client.response.DefaultLightblueResponse;

@RunWith(MockitoJUnitRunner.class)
public class SomeTest extends CamelTestSupport {

    @Mock
    private LightblueClient client;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new LightblueInboundRoute(client);
    }

    @Test
    public void test() throws Exception {
        String message = loadResource("/user-message.xml", true);
        template.sendBody(message);

        Mockito.when(client.data(Mockito.any(AbstractLightblueDataRequest.class))).thenReturn(new DefaultLightblueResponse());
        Mockito.verify(client, Mockito.times(1)).data(Mockito.any(AbstractLightblueDataRequest.class));
    }

    public static final String loadResource(String resourceName, boolean local) throws IOException {
        StringBuilder buff = new StringBuilder();

        try (InputStream is = local ? SomeTest.class.getResourceAsStream(resourceName) : SomeTest.class.getClassLoader()
                .getResourceAsStream(resourceName);
                InputStreamReader isr = new InputStreamReader(is, Charset.defaultCharset());
                BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line).append("\n");
            }
        }

        return buff.toString();
    }
}
