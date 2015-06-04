package test.transformer;

import java.util.Map;

import org.apache.camel.Headers;

import com.redhat.lightblue.client.projection.FieldProjection;
import com.redhat.lightblue.client.request.data.DataInsertRequest;

public class LightblueInsertRequestTransformer implements Transformer<Object[], DataInsertRequest> {

    private final String entityName;
    private final String entityVersion;

    public LightblueInsertRequestTransformer(String entityName) {
        this(entityName, null);
    }

    public LightblueInsertRequestTransformer(String entityName, String entityVersion) {
        if (entityName == null) {
            throw new IllegalArgumentException("entityName cannot be null");
        }

        this.entityName = entityName;
        this.entityVersion = entityVersion;
    }

    @Override
    public DataInsertRequest transform(Object[] body, @Headers Map<String, Object> headers) {
        DataInsertRequest insertRequest = new DataInsertRequest(entityName, entityVersion);
        insertRequest.create(body);
        insertRequest.returns(FieldProjection.includeFieldRecursively("*")); //TODO doing now for simplicity, revisit.

        return insertRequest;
    }

}
