package io.hasura.db;

import static io.hasura.db.File.FILE;
import static io.hasura.db.FakUser.FAK_USER;

import io.hasura.db.DBService;
import io.hasura.auth.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelectObjRel {

    @Test
    public void run() throws IOException, DBException {

        DBService db = DBTestService.db;
        List<FakUserRecord> userRecords =
            db
            .select(FAK_USER)
            .columns(
               FAK_USER.EMAIL,
               FAK_USER.ID,
               FAK_USER.TM_ID,
               FAK_USER.PROFILE_PIC.columns(FILE.ID, FILE.SERVER_PATH)
               )
            .build().execute();
        for (FakUserRecord fr : userRecords)
            System.out.println(fr);
    }
}
