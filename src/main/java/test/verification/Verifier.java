package test.verification;

import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Headers;

/**
 * A verifier does not change the body in any way, it only asserts that some aspect of
 * the body passes some verification steps.
 *
 * @author dcrissman
 *
 * @param <B> - body
 */
public abstract class Verifier<B> {

    /**
     * Verifies the passed in body conforms to this standard.
     * @param body - body to be verified
     * @param headers
     * @return the unchanged body
     */
    @Handler
    public B verify(B body, @Headers Map<String, Object> headers) {
        boolean pass = false;
        try {
            pass = doVerify(body, headers);
        } catch (RuntimeException e) {
            throw new VerificationException("Verification failed for: " + body.toString(), e);
        }

        if (!pass) {
            throw new VerificationException("Verification failed for: " + body.toString());
        }

        return body;
    }

    /**
     * Verifies the body matches some criteria.
     * @param body
     * @param headers
     * @return <code>true</code> if the body matches, otherwise <code>false</code>.
     */
    protected abstract boolean doVerify(final B body, final @Headers Map<String, Object> headers);

}
