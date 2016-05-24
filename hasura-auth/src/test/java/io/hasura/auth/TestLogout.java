package io.hasura.auth;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class TestLogout {

    @Test
    public void run() throws IOException, AuthException {

        AuthService authService = new AuthService("http://auth.bompod.hasura-app.io");

        // before testing logout, we have to make sure the user exists..
        RegisterRequest rq = new RegisterRequest();
        rq.setUsername("jafar");
        rq.setPassword("abracadabra");
        RegisterResponse rr = authService.register(rq).execute();

        // user registered, login now..
        LoginResponse r = authService.login("jafar", "abracadabra").execute();
        System.out.println(r.getHasuraId());
        System.out.println(r.getHasuraRoles());

        // logged in, now try to logout
        LogoutResponse lr = authService.logout().execute();
        System.out.println(lr.getMessage());
        assertEquals(lr.getMessage(), "Logged out");
    }
}
