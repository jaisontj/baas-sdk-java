package io.hasura.db;

import static io.hasura.db.FileTable.FILE_TABLE;
import static io.hasura.db.FakUser.FAK_USER;

import io.hasura.db.RequestMaker;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelectObjRel {

    @Test
    public void run() throws IOException {

        RequestMaker rm = new RequestMaker("http://localhost:8080");
        List<FakUserRecord> userRecords =
            rm
            .select(FAK_USER)
            .columns(
               FAK_USER.EMAIL,
               FAK_USER.ID,
               FAK_USER.TM_ID,
               FAK_USER.PROFILE_PIC.columns(FILE_TABLE.ID, FILE_TABLE.SERVER_PATH)
               )
            .fetch();
        for (FakUserRecord fr : userRecords)
            System.out.println(fr);
    }
}
