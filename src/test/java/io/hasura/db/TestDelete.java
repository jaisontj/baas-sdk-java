package io.hasura.db;
import io.hasura.auth.*;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;
import io.hasura.db.delete.DeleteResult;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestDelete {

    @Test
    public void run() throws IOException, DBException {

        // DBService db = new DBService("http://localhost:8080");
        AuthService authService = new AuthService("http://104.155.219.208");
        DBService db = new DBService(authService.getUrl(), "/api/db", authService.getClient());
        DeleteResult<FileRecord> res =
            db
            .delete(FILE)
            .where(FILE.FILENAME.eq("for_delete"))
            .returning(FILE.ID)
            .build()
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecords());
    }
}
