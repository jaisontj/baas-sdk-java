package io.hasura.db;

import static io.hasura.db.File.FILE;

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
            .insert(FILE)
            .set(FILE.FILENAME, "ahoy_ulman")
            .setAndReturn(FILE.SERVER_PATH, "home/root/")
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecord());
    }
}
