package men.brakh.enrollment.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import men.brakh.enrollment.model.Dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchHelper<T extends Dto> {
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    private List<String> allFields(final JsonObject jsonObject) {
        final List<String> fields = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            final JsonElement currElement = jsonObject.get(entry.getKey());
            if (!currElement.isJsonNull() && currElement.isJsonObject()) {
                List<String> list = allFields(jsonObject.getAsJsonObject(entry.getKey()));
                fields.addAll(list
                        .stream()
                        .map(s -> entry.getKey() + "." + s)
                        .collect(Collectors.toList()));
            } else {
                fields.add(entry.getKey());
            }
        }
        return fields;
    }

    public List<String> availableFields(final List<T> entities) {
        if (entities.size() == 0) return new ArrayList<>();
        final T t = entities.get(0);

        JsonElement element = gson.toJsonTree(t);

        JsonObject jsonObject = element.getAsJsonObject();
        return allFields(jsonObject);
    }


    boolean isFieldEquals(final JsonObject parent, String path, String value) {
        String[] pathElement = path.split("\\.", 2);
        if(pathElement.length > 1) {
           return isFieldEquals(parent.getAsJsonObject(pathElement[0]), pathElement[1], value);
        } else {
            return parent.getAsJsonPrimitive(path).getAsString().equals(value);
        }
    }

    public List<T> search(final List<T> entities,
                          final Map<String, String> filterValues,
                          final Class<? extends T> dtoClass,
                          final String sortByField) {
        return entities.stream()
                .map(gson::toJsonTree)
                .map(JsonElement::getAsJsonObject)
                .filter(jsonObject -> filterValues.entrySet()
                        .stream()
                        .allMatch(entry -> {
                            String path = entry.getKey();
                            String value = entry.getValue();
                            return isFieldEquals(jsonObject, path, value);
                        })
                )
                .sorted(Comparator.comparing(j -> j.get(sortByField).getAsString()))
                .map(jsonElement -> gson.fromJson(jsonElement, dtoClass))
                .collect(Collectors.toList());
    }


}
