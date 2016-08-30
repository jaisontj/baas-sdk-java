package io.hasura.db;
import io.hasura.auth.*;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;
import io.hasura.db.DeleteResult;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestDelete {

    @Test
    public void run() throws IOException, DBException {

        DBService db = DBTestService.db;
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
