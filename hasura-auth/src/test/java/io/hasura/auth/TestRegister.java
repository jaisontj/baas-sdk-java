package io.hasura.auth;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class TestRegister {

    @Test
    public void run() throws IOException, AuthException {

        AuthService authService = new AuthService("http://auth.localhost:2345");

        RegisterRequest rq = new RegisterRequest();
        rq.setUsername("jasmine");
        rq.setPassword("abracadabra");
        rq.setEmail("jasmine@genie.io");
        rq.setMobile("9867152343");
        RegisterResponse rr = authService.register(rq).execute();
        System.out.println(rr.getHasuraId());
        assertEquals(rr.getHasuraRole(), "user");
    }
}
