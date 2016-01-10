package io.hasura.db;

import static io.hasura.db.FileTable.FILE_TABLE;

import io.hasura.db.RequestMaker;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestInsert {

    @Test
    public void run() throws IOException {

        RequestMaker rm = new RequestMaker("http://localhost:8080");
        InsertResult <FileRecord> res =
            rm
            .insert(FILE_TABLE)
            .set(FILE_TABLE.FILENAME, "ahoy_ulman")
            .setAndReturn(FILE_TABLE.SERVER_PATH, "home/root/")
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecord());
    }
}
