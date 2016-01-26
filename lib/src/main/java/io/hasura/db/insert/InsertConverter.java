package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class InsertConverter<T> implements Converter<T, InsertException> {

    static class AuthError {
        private int code;
        private String message;

        public int getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.message;
        }
    }

    private final Type resType;
    public InsertConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws InsertException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(response, resType);
            }
            else {
                AuthError err = Util.parseJson(response, AuthError.class);
                throw new HasuraJsonException(code, err.getMessage());
            }
        }
        catch (HasuraJsonException e) {
            throw new InsertException(e);
        }
    }

    @Override
    public InsertException fromIOException(IOException e) {
        return new InsertException(e);
    }

    @Override
    public InsertException castException(Exception e) {
        return (InsertException) e;
    }
}
