package io.hasura.db;

import static io.hasura.db.Review.REVIEW;
import static io.hasura.db.FakUser.FAK_USER;

import io.hasura.db.DBService;
import io.hasura.core.*;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestSelectArrRel {

    @Test
    public void run() throws IOException, SelectException {

        // DBService db = new DBService("http://localhost:8080");
        AuthService authService = new AuthService("http://104.155.219.208");
        DBService db = new DBService(authService.getUrl(), authService.getClient());
        List<FakUserRecord> userRecords =
            db
            .select(FAK_USER)
            .columns(
               FAK_USER.EMAIL,
               FAK_USER.ID,
               FAK_USER.TM_ID,
               FAK_USER.REVIEWS
                       .columns(REVIEW.ID, REVIEW.RATING)
                       .limit(5)
               )
            .where(FAK_USER.REVIEWS.hasAnyWith(REVIEW.RATING.gte(5)))
            .build().execute();
        for (FakUserRecord fr : userRecords)
            System.out.println(fr);
    }
}
