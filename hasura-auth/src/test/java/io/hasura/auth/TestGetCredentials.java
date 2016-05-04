package io.hasura.auth;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class TestGetCredentials {

    @Test
    public void run() throws IOException, AuthException {

        AuthService authService = new AuthService("http://auth.localhost:2345");

        // before testing get_credentials, we have to make sure the user exists..
        RegisterRequest rq = new RegisterRequest();
        rq.setUsername("abu");
        rq.setPassword("abracadabra");
        rq.setEmail("abu@genie.io");
        RegisterResponse rr = authService.register(rq).execute();

        // user registered, login now..
        LoginResponse r = authService.login("abu", "abracadabra").execute();

        // user logged in, now test get_credentials
        GetCredentialsResponse gr = authService.getCredentials().execute();
        System.out.println(r.getHasuraId());
        System.out.println(r.getHasuraRole());
        assertEquals(r.getHasuraRole(), "user");
    }
}
