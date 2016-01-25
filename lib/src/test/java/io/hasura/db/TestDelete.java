package io.hasura.db;
import io.hasura.core.*;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestDelete {

    @Test
    public void run() throws IOException, HasuraException {

        DBService db = new DBService("http://localhost:8080");
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
