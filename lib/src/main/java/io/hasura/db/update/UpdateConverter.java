package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class UpdateConverter<T> implements Converter<T, UpdateException> {

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
    public UpdateConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws UpdateException {
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
            throw new UpdateException(e);
        }
    }

    @Override
    public UpdateException fromIOException(IOException e) {
        return new UpdateException(e);
    }

    @Override
    public UpdateException castException(Exception e) {
        return (UpdateException) e;
    }
}
