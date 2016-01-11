package io.hasura.db;

import static io.hasura.db.File.FILE;

import io.hasura.db.RequestMaker;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestDelete {

    @Test
    public void run() throws IOException {

        RequestMaker rm = new RequestMaker("http://localhost:8080");
        DeleteResult<FileRecord> res =
            rm
            .delete(FILE)
            .where(FILE.FILENAME.eq("for_delete"))
            .returning(FILE.ID)
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecords());
    }
}
