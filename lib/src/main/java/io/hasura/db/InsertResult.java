package io.hasura.db;

class InsertResult<R> {
    private int affectedRows;
    private R record;

    public int getCount() {
        return affectedRows;
    }

    public R getRecord() {
        return record;
    }
}
