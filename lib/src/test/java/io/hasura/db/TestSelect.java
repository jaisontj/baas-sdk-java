package io.hasura.db;

import static io.hasura.db.FileTable.FILE_TABLE;

import io.hasura.db.RequestMaker;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelect {

    @Test
    public void run() throws IOException {

        RequestMaker rm = new RequestMaker("http://localhost:8080");
        List<FileRecord> fileRecords =
            rm
            .select(FILE_TABLE)
            .columns(FILE_TABLE.ID, FILE_TABLE.FILENAME)
            .where(FILE_TABLE.ID.eq(3))
            .limit(10)
            .offset(0)
            .fetch();
        for (FileRecord fr : fileRecords)
            System.out.println(fr);
    }
}
