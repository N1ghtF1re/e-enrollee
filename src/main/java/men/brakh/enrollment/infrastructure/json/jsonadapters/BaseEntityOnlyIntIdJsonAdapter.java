package men.brakh.enrollment.infrastructure.json.jsonadapters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import men.brakh.enrollment.model.BaseEntity;

import java.lang.reflect.Type;

public class BaseEntityOnlyIntIdJsonAdapter<T extends BaseEntity<Integer>> implements JsonSerializer<T> {
    
    @Override
    public JsonElement serialize(final T src, final Type typeOfSrc, final JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", src.getId());

        return obj;
    }
}
