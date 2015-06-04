package test.verification;

/**
 * Exception thrown if verification fails.
 *
 * @author dcrissman
 */
public class VerificationException extends RuntimeException {

    private static final long serialVersionUID = 5636123448298123170L;

    public VerificationException(String message) {
        super(message);
    }

    public VerificationException(String message, Throwable cause) {
        super(message, cause);
    }

}
