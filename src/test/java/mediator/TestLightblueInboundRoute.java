package mediator;

import static com.redhat.lightblue.util.test.AbstractJsonNodeTest.loadJsonNode;
import static com.redhat.lightblue.util.test.AbstractJsonNodeTest.loadResource;
import mediator.LightblueExternalResource.LightblueTestMethods;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.ClassRule;
import org.junit.Test;

import test.LightblueInboundRoute;
import test.model.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.lightblue.client.expression.query.ValueQuery;
import com.redhat.lightblue.client.projection.FieldProjection;
import com.redhat.lightblue.client.request.data.DataFindRequest;

public class TestLightblueInboundRoute extends CamelTestSupport {

    @ClassRule
    public static LightblueExternalResource lightblue = new LightblueExternalResource(new LightblueTestMethods() {

        @Override
        public JsonNode[] getMetadataJsonNodes() throws Exception {
            return new JsonNode[]{
                    loadJsonNode("./inbound/user.json")
            };
        }

    });

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new LightblueInboundRoute(lightblue.getLightblueClient());
    }

    @Test
    public void testMessageToLightblue() throws Exception {
        String message = loadResource("./inbound/user-message.xml");
        template.sendBody(message);

        DataFindRequest findRequest = new DataFindRequest("user", null);
        findRequest.where(ValueQuery.withValue("objectType = user"));
        findRequest.select(FieldProjection.includeField("*"));
        User[] users = lightblue.getLightblueClient().data(findRequest, User[].class);

        assertNotNull(users);
        assertEquals(2, users.length);
    }

}
