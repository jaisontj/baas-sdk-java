package io.hasura.db;

import static io.hasura.db.File.FILE;
import io.hasura.auth.*;

import io.hasura.db.DBService;
import io.hasura.db.InsertResult;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TestInsert {

    @Test
    public void run() throws IOException, DBException {

        DBService db = DBTestService.db;
        long currentTime = System.currentTimeMillis();
        InsertResult<FileRecord> res =
            db
            .insert(FILE)
            .set(FILE.FILENAME, "ahoy_ulman")
            .set(FILE.SERVER_PATH, "home/root/")
            .set(FILE.CREATED, new Date(currentTime))
            .set(FILE.CREATED_TS, new Timestamp(currentTime))
            .set(FILE.CREATED_T, new Time(currentTime))
            .returning(FILE.ID, FILE.SERVER_PATH)
            .build()
            .execute();
        System.out.println(res.getCount());
        System.out.println(res.getRecords());
    }
}
