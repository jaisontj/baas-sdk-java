package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class DeleteConverter<T> implements Converter<T, DeleteException> {

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
    public DeleteConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws DeleteException {
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
            throw new DeleteException(e);
        }
    }

    @Override
    public DeleteException fromIOException(IOException e) {
        return new DeleteException(e);
    }

    @Override
    public DeleteException castException(Exception e) {
        return (DeleteException) e;
    }
}
