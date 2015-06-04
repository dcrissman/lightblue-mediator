package test.verification;

import java.util.Map;

import org.apache.camel.Headers;

import com.redhat.lightblue.client.response.LightblueErrorResponseException;
import com.redhat.lightblue.client.response.LightblueResponse;

/**
 * Verifies that a {@link LightblueResponse} does not contain any errors.
 *
 * @author dcrissman
 */
public class LightblueErrorVerifier extends Verifier<LightblueResponse> {

    @Override
    public boolean doVerify(LightblueResponse body, @Headers Map<String, Object> headers) {
        if (body.hasError()) {
            throw new LightblueErrorResponseException("Error returned in response: " + body.getText());
        }

        return true;
    }

}
