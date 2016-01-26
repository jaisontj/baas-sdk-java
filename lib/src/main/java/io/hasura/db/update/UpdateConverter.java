package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class UpdateConverter<T> implements Converter<T, UpdateException> {

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
                DBErrorResponse err = Util.parseJson(response, DBErrorResponse.class);
                throw new UpdateException(1, err.getError());
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
