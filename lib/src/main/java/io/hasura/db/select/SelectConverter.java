package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class SelectConverter<T> implements Converter<T, SelectException> {

    private final Type resType;
    public SelectConverter(Type resType) {
        this.resType = resType;
    }

    @Override
    public T fromResponse(okhttp3.Response response) throws SelectException {
        int code = response.code();

        try {
            if (code == 200) {
                return Util.parseJson(response, resType);
            }
            else {
                DBErrorResponse err = Util.parseJson(response, DBErrorResponse.class);
                throw new SelectException(1, err.getError());
            }
        }
        catch (HasuraJsonException e) {
            throw new SelectException(e);
        }
    }

    @Override
    public SelectException fromIOException(IOException e) {
        return new SelectException(e);
    }

    @Override
    public SelectException castException(Exception e) {
        return (SelectException) e;
    }
}
