package test.verification;

import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Headers;

import com.redhat.lightblue.client.response.LightblueErrorResponseException;
import com.redhat.lightblue.client.response.LightblueResponse;

public class LightblueErrorVerifier {

    @Handler
    public LightblueResponse verify(LightblueResponse body, @Headers Map<String, Object> headers) {
        if (body.hasError()) {
            throw new LightblueErrorResponseException("Error returned in response: " + body.getText());
        }

        return body;
    }

}
