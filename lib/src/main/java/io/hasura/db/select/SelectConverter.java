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
                SelectError errCode;
                switch (code) {
                case 400:
                    errCode = SelectError.BAD_REQUEST;
                    break;
                case 401:
                    errCode = SelectError.UNAUTHORIZED;
                    break;
                case 403:
                    errCode = SelectError.INVALID_SESSION;
                    break;
                case 500:
                    errCode = SelectError.INTERNAL_ERROR;
                    break;
                default:
                    errCode = SelectError.UNEXPECTED_CODE;
                    break;
                }
                throw new SelectException(errCode, err.getError());
            }
        }
        catch (HasuraJsonException e) {
            throw new SelectException(SelectError.INTERNAL_ERROR, e);
        }
    }

    @Override
    public SelectException fromIOException(IOException e) {
        return new SelectException(SelectError.CONNECTION_ERROR, e);
    }

    @Override
    public SelectException castException(Exception e) {
        return (SelectException) e;
    }
}
