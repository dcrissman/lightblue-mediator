package mediator;

import static com.redhat.lightblue.util.test.AbstractJsonNodeTest.loadJsonNode;
import mediator.LightblueExternalResource.LightblueTestMethods;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import test.LightblueOutboundRoute;

import com.fasterxml.jackson.databind.JsonNode;

public class TestLightblueOutboundRoute extends CamelTestSupport {

    @BeforeClass
    public static void prepareMetadataDatasources() {
        System.setProperty("mongo.datasource", "mongodata");
    }

    @ClassRule
    public static LightblueExternalResource lightblue = new LightblueExternalResource(new LightblueTestMethods() {

        @Override
        public JsonNode[] getMetadataJsonNodes() throws Exception {
            return new JsonNode[]{
                    loadJsonNode("./metadata/publish.json"),
                    loadJsonNode("./outbound/customer.json")
            };
        }

    });

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new LightblueOutboundRoute(lightblue.getLightblueClient());
    }

    public TestLightblueOutboundRoute() throws Exception {
        lightblue.loadData("customer", "1.0.0", "./outbound/data/insert/customers.json");
    }

    @Test
    public void testMessageFromLightblue() throws Exception {

    }

}
