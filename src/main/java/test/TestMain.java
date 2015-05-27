package test;

import org.apache.camel.impl.DefaultCamelContext;

/**
 * A main class to run the example from your editor.
 */
public final class TestMain {

    private TestMain() {}

    public static void main(String[] args) throws Exception {
        // create the context
        DefaultCamelContext context = new DefaultCamelContext();

        // append the routes to the context
        context.addRoutes(new LightblueInboundRoute());

        // at the end start the camel context
        context.start();
    }
}
