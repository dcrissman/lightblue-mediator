package test.transformer;

import java.util.Map;

import org.apache.camel.Handler;
import org.apache.camel.Headers;
import org.apache.commons.lang3.StringUtils;

import com.redhat.lightblue.client.request.data.DataInsertRequest;

public class LightblueInsertRequestTransformer {

    private final String entityName;
    private final String entityVersion;

    public LightblueInsertRequestTransformer(String entityName) {
        this(entityName, null);
    }

    public LightblueInsertRequestTransformer(String entityName, String entityVersion) {
        if (StringUtils.isEmpty(entityName)) {
            throw new IllegalArgumentException("entityName cannot be null or empty");
        }

        this.entityName = entityName;
        this.entityVersion = entityVersion;
    }

    @Handler
    public DataInsertRequest transform(Object[] body, @Headers Map<String, Object> headers) {
        DataInsertRequest insertRequest = new DataInsertRequest(entityName, entityVersion);
        insertRequest.create(body);

        return insertRequest;
    }

}
