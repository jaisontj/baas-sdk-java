package io.hasura.db.util;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestGeneration {

    @Test
    public void run() throws IOException, UnexpectedSchema {

        Configuration cfg = new Configuration();
        cfg.setDir("ws");
        cfg.setPackageName("io.hasura.gen");
        cfg.setDBUrl(System.getenv("url"));
        cfg.setAdminAPIKey(System.getenv("adminAPIKey"));
        cfg.setDBPrefix(System.getenv("dbprefix"));
        GenerationUtil.generate(cfg);
    }
}
