package io.hasura.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

public class Util {
    private static final Gson gson = new GsonBuilder().create();

    public static <R> R parseJson(
            okhttp3.Response response, Type bodyType) throws HasuraJsonException {
        int code = response.code();
        try {
            String rawBody = response.body().string();
            System.out.println(rawBody);
            return gson.fromJson(rawBody, bodyType);
        } catch (JsonSyntaxException e) {
            String msg
                    = "FATAL : JSON structure not as expected. Schema changed maybe? : "
                    + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        } catch (JsonParseException e) {
            String msg = "FATAL : Server didn't return valid JSON : " + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        } catch (IOException e) {
            String msg = "FATAL : Decoding response body failed : " + e.getMessage();
            throw new HasuraJsonException(code, msg, e);
        }
    }
}
