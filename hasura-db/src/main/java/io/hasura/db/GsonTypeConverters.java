package io.hasura.db;

import java.sql.Date;
import java.lang.reflect.Type;

import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonDeserializationContext;

public class GsonTypeConverters {

    public final static JsonSerializer<Date> dateJsonSerializer = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.toString());
            }
        };

    public final static JsonDeserializer<Date> dateJsonDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : Date.valueOf(json.getAsString());
            }
        };
}
