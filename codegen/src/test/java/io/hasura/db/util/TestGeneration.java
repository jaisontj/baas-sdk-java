package io.hasura.db.util;

import org.junit.Test;
import java.util.List;
import java.io.IOException;

public class TestGeneration {

    @Test
    public void run() throws IOException {

        Configuration cfg = new Configuration("http://localhost:8080",
                                              "io.fak.db",
                                              "ws"
                                              );
        GenerationUtil.generate(cfg);
    }
}
