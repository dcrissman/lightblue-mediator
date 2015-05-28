package mediator;

import static com.redhat.lightblue.util.test.AbstractJsonNodeTest.loadJsonNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import mediator.LightblueJUnitRunner.LightblueTestMethods;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;

import test.LightblueInboundRoute;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.redhat.lightblue.client.LightblueClient;
import com.redhat.lightblue.mongo.test.MongoServerExternalResource.InMemoryMongoServer;

@RunWith(LightblueJUnitRunner.class)
@InMemoryMongoServer
public class SomeTest extends CamelTestSupport implements LightblueTestMethods {

    private LightblueClient client;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Override
    public void setLightblueClient(LightblueClient client) {
        this.client = client;
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new LightblueInboundRoute(client);
    }

    @Override
    public JsonNode[] getMetadataJsonNodes() throws Exception {
        return new JsonNode[]{
                (ObjectNode) loadJsonNode("./user.json")
        };
    }

    @Test
    public void test() throws Exception {
        String message = loadResource("/user-message.xml", true);
        template.sendBody(message);

        //TODO add asserts
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
