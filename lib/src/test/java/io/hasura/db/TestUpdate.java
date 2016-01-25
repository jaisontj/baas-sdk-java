package io.hasura.db;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;
import io.hasura.core.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestUpdate {

    @Test
    public void run() throws IOException, HasuraException {

        DBService db = new DBService("http://localhost:8080");
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
