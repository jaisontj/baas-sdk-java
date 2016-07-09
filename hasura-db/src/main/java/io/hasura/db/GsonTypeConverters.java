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

    private final static SimpleDateFormat tsFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");

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
                String inp = json.getAsString();
                if (inp.length() > 10) {
                    inp = inp.substring(0, 11) + normaliseTime(inp.substring(11));
                }
                return json == null ? null : new Timestamp(tsFormat.parse(inp).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };

    private final static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSSSSSXXX");

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
                String inp = normaliseTime(json.getAsString());
                return json == null ? null : new Time(timeFormat.parse(inp).getTime());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    };

    private static String normaliseTime(String s) {
        int sLen = s.length();
        // No padding required
        if (sLen < 9 || sLen == 23)
            return s;
        String sTZNorm = s;
        // ends with Z
        if (s.charAt(sLen - 1) == 'Z') {
            sTZNorm = s.replace("Z", "+00:00");
        } else {
            // Check the last 3rd char for *+00
            char l3 = s.charAt(sLen - 3);
            if (l3 == '+' || l3 == '-') {
                sTZNorm = s + ":00";
            }
        }
        String finalNorm = sTZNorm;
        char msBegin = sTZNorm.charAt(8);
        if (msBegin != '.') {
            finalNorm = sTZNorm.substring(0, 8) + ".000000" + sTZNorm.substring(8);
        } else {
            int colPos = finalNorm.indexOf(':', 9);
            // 18 is the actual pos in normalised string
            String zeroPad = new String(new char[18 - colPos]).replace('\0', '0');
            finalNorm = sTZNorm.substring(0, colPos - 3) + zeroPad + sTZNorm.substring(colPos - 3);
        }
        return finalNorm;
    }

}
