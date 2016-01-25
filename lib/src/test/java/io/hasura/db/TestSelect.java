package io.hasura.db;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;
import io.hasura.core.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelect {

    @Test
    public void run() throws IOException, HasuraException {

        DBService db = new DBService("http://localhost:8080");
        List<FileRecord> fileRecords =
            db
            .select(FILE)
            .columns(FILE.ID, FILE.FILENAME, FILE.SERVER_PATH)
            .where(FILE.ID.eq(3))
            .limit(10)
            .offset(0)
            .build().execute();
        for (FileRecord fr : fileRecords)
            System.out.println(fr);
    }
}
