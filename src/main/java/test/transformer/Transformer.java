package test.transformer;

import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Headers;

/**
 * A transformer is responsible for changing the value of body into another form that
 * can be understood by the next step.
 *
 * @author dcrissman
 *
 * @param <B> - in-bound body
 * @param <T> - transformed body
 */
public interface Transformer<B, T> {

    /**
     * Transforms the body and returns the result.
     * @param body - in-bound body
     * @param headers
     * @return transformed body
     * @throws Exception
     */
    @Handler
    public T transform(B body, @Headers Map<String, Object> headers) throws Exception;

}
