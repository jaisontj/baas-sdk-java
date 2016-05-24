package io.hasura.db;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    private final static SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public final static JsonSerializer<Timestamp> tsJsonSerializer = new JsonSerializer<Timestamp>() {
        @Override
        public JsonElement serialize(Timestamp src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(tsFormat.format(src));
        }
    };

    public final static JsonDeserializer<Timestamp> tsJsonDeserializer = new JsonDeserializer<Timestamp>() {
        @Override
        public Timestamp deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            try {
                return json == null ? null : new Timestamp(tsFormat.parse(json.getAsString()).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };

    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSSZ");

    public final static JsonSerializer<Time> timeJsonSerializer = new JsonSerializer<Time>() {
        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null ? null : new JsonPrimitive(timeFormat.format(src));
        }
    };

    public final static JsonDeserializer<Time> timeJsonDeserializer = new JsonDeserializer<Time>() {
        @Override
        public Time deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
            try {
                return json == null ? null : new Time(timeFormat.parse(json.getAsString()).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };
}
