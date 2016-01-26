package io.hasura.db;

import static io.hasura.db.File.FILE;
import io.hasura.auth.*;

import io.hasura.db.DBService;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestInsert {

    @Test
    public void run() throws IOException, DBException {

        // DBService db = new DBService("http://localhost:8080");
        AuthService authService = new AuthService("http://104.155.219.208");
        DBService db = new DBService(authService.getUrl(), authService.getClient());
        InsertResult <FileRecord> res =
            db
            .insert(FILE)
            .set(FILE.FILENAME, "ahoy_ulman")
            .setAndReturn(FILE.SERVER_PATH, "home/root/")
            .build()
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecord());
    }
}
