package io.hasura.db;

public enum SelectError {
    INVALID_SESSION,
    UNAUTHORIZED,
    BAD_REQUEST,
    INTERNAL_ERROR,
    UNEXPECTED_CODE,
    CONNECTION_ERROR
}
