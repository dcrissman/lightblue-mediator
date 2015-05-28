package test.transformer;

import java.io.StringReader;
import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Headers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlToObjectTransformer<T> {

    private final Class<T> type;

    public XmlToObjectTransformer(Class<T> type) {
        this.type = type;
    }

    @Handler
    public T transform(String body, @Headers Map<String, Object> headers) throws Exception {
        return new XmlMapper().readValue(new StringReader(body), type);
    }
}
