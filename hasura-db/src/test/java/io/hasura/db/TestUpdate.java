package io.hasura.db;

import org.junit.Test;

import java.io.IOException;

import io.hasura.auth.AuthService;
import io.hasura.db.UpdateResult;

import static io.hasura.db.File.FILE;

public class TestUpdate {

    @Test
    public void run() throws IOException, DBException {

        AuthService authService = new AuthService("http://104.155.219.208");
        DBService db = new DBService(authService.getUrl(), "/api/db", authService.getClient());
        UpdateResult<FileRecord> res =
            db
            .update(FILE)
            .setAndReturn(FILE.FILENAME, "ahoy_ulman")
            .where(FILE.FILENAME.eq("ahoy_ulman"))
            .returning(FILE.ID)
            .build()
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecords());
    }
}
