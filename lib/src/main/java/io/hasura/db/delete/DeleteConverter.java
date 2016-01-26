package io.hasura.db;

import io.hasura.core.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class DeleteConverter<T> implements Converter<T, DeleteException> {

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
                DBErrorResponse err = Util.parseJson(response, DBErrorResponse.class);
                throw new DeleteException(1, err.getError());
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
