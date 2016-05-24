package io.hasura.auth;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class TestLogin {

    @Test
    public void run() throws IOException, AuthException {

        AuthService authService = new AuthService("http://auth.bompod.hasura-app.io");

        // before testing login, we have to make sure the user exists..
        RegisterRequest rq = new RegisterRequest();
        rq.setUsername("aladdin");
        rq.setPassword("abracadabra");
        rq.setEmail("aladdin@genie.io");
        rq.setMobile("1010011000");
        RegisterResponse rr = authService.register(rq).execute();

        // user registered, test login now..
        LoginResponse r = authService.login("aladdin", "abracadabra").execute();
        System.out.println(r.getHasuraId());
        System.out.println(r.getHasuraRoles());
        assertEquals(r.getHasuraRoles()[0], "user");
    }
}
