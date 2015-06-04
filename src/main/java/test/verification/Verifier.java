package test.verification;

import org.apache.camel.Handler;

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
     */
    @Handler
    public void verify(B body) {
        boolean pass = false;
        try {
            pass = doVerify(body);
        } catch (RuntimeException e) {
            throw new VerificationException("Verification failed for: " + body.toString(), e);
        }

        if (!pass) {
            throw new VerificationException("Verification failed for: " + body.toString());
        }
    }

    /**
     * Verifies the body matches some criteria.
     * @param body
     * @return <code>true</code> if the body matches, otherwise <code>false</code>.
     */
    protected abstract boolean doVerify(final B body);

}
