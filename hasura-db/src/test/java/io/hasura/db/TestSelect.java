package io.hasura.db;

import static io.hasura.db.File.FILE;

import io.hasura.db.DBService;
import io.hasura.db.HDBInterceptor;
import io.hasura.auth.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelect {

    @Test
    public void run() throws IOException, DBException {

        DBService db = DBTestService.db;
        List<FileRecord> fileRecords =
            db
            .select(FILE)
            .columns(FILE.ID, FILE.FILENAME,
                     FILE.SERVER_PATH, FILE.CREATED,
                     FILE.CREATED_TS, FILE.CREATED_T
                     )
            .orderBy(FILE.ID.asc())
            .limit(10)
            .offset(0)
            .build()
            .execute();
        for (FileRecord fr : fileRecords)
            System.out.println(fr);
    }
}
