package io.hasura.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.hasura.core.Call;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class AuthService {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new GsonBuilder().create();
    private OkHttpClient httpClient;
    private String dbUrl;

    public AuthService(String dbUrl, OkHttpClient httpClient) {
        this.dbUrl = dbUrl;
        this.httpClient = httpClient;
    }

    public AuthService(String dbUrl) {
        this.dbUrl = dbUrl;
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        this.httpClient
                = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    public OkHttpClient getClient() {
        return this.httpClient;
    }

    public String getUrl() {
        return this.dbUrl;
    }

    private <T> Call<T, AuthException> mkPostCall(String url, String jsonBody, Type bodyType) {
        RequestBody reqBody = RequestBody.create(JSON, jsonBody);
        Request request = new Request.Builder()
                .url(this.dbUrl + url)
                .post(reqBody)
                .build();
        Call<T, AuthException> newCall
                = new Call<T, AuthException>(
                httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
        return newCall;
    }

    private <T> Call<T, AuthException> mkGetCall(String url, Type bodyType) {
        Request request = new Request.Builder()
                .url(this.dbUrl + url)
                .build();
        Call<T, AuthException> newCall
                = new Call<T, AuthException>(
                httpClient.newCall(request), new AuthResponseConverter<T>(bodyType));
        return newCall;
    }

    public Call<RegisterResponse, AuthException> register(RegisterRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<RegisterResponse>() {
        }.getType();
        return mkPostCall("/auth/signup", jsonBody, respType);
    }

    public Call<LoginResponse, AuthException> login(LoginRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<LoginResponse>() {
        }.getType();
        return mkPostCall("/auth/login", jsonBody, respType);
    }

    public Call<LoginResponse, AuthException> login(
            String userName, String password, JsonObject info) {
        return this.login(new LoginRequest(userName, password, info));
    }

    public Call<LogoutResponse, AuthException> logout() {
        Type respType = new TypeToken<LogoutResponse>() {
        }.getType();
        return mkGetCall("/auth/logout", respType);
    }

    public Call<GetCredentialsResponse, AuthException> getCredentials() {
        Type respType = new TypeToken<GetCredentialsResponse>() {
        }.getType();
        return mkGetCall("/auth/get_credentials", respType);
    }

    public Call<CheckLoginResponse, AuthException> checkLogin(CheckLoginRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<CheckLoginResponse>() {
        }.getType();
        return mkPostCall("/auth/check_login", jsonBody, respType);
    }

    public Call<ResendEmailResponse, AuthException> resendVerifyEmail(ResendEmailRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResendEmailResponse>() {
        }.getType();
        return mkPostCall("/auth/resend_verify_email", jsonBody, respType);
    }

    public Call<ConfirmEmailResponse, AuthException> confirmEmail(ConfirmEmailRequest r) {
        String token = r.token;
        Type respType = new TypeToken<ConfirmEmailResponse>() {
        }.getType();
        return mkGetCall("/auth/confirm_email/" + token, respType);
    }

    public Call<ChangeEmailResponse, AuthException> changeEmail(ChangeEmailRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeEmailResponse>() {
        }.getType();
        return mkPostCall("/auth/change_email", jsonBody, respType);
    }

    public Call<ChangePasswordResponse, AuthException> changePassword(ChangePasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangePasswordResponse>() {
        }.getType();
        return mkPostCall("/auth/change_password", jsonBody, respType);
    }

    public Call<ForgotPasswordResponse, AuthException> forgotPassword(ForgotPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ForgotPasswordResponse>() {
        }.getType();
        return mkPostCall("/auth/forgot_password", jsonBody, respType);
    }

    public Call<ResetPasswordResponse, AuthException> resetPassword(ResetPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResetPasswordResponse>() {
        }.getType();
        return mkPostCall("/auth/reset_password", jsonBody, respType);
    }

    public Call<ChangeUserNameResponse, AuthException> changeUserName(ChangeUserNameRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeUserNameResponse>() {
        }.getType();
        return mkPostCall("/auth/change_username", jsonBody, respType);
    }

    public Call<CheckPasswordResponse, AuthException> checkPassword(CheckPasswordRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<CheckPasswordResponse>() {
        }.getType();
        return mkPostCall("/auth/check_password", jsonBody, respType);
    }

    public Call<ConfirmMobileResponse, AuthException> confirmMobile(ConfirmMobileRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ConfirmMobileResponse>() {
        }.getType();
        return mkPostCall("/auth/confirm_mobile", jsonBody, respType);
    }

    public Call<ChangeMobileResponse, AuthException> changeMobile(ChangeMobileRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ChangeMobileResponse>() {
        }.getType();
        return mkPostCall("/auth/change_mobile", jsonBody, respType);
    }

    public Call<ResendOTPResponse, AuthException> resendOTP(ResendOTPRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<ResendOTPResponse>() {
        }.getType();
        return mkPostCall("/auth/resend_otp_mobile", jsonBody, respType);
    }

    public Call<DeleteAccountResponse, AuthException> deleteAccount(DeleteAccountRequest r) {
        String jsonBody = gson.toJson(r);
        Type respType = new TypeToken<DeleteAccountResponse>() {
        }.getType();
        return mkPostCall("/auth/delete_account", jsonBody, respType);
    }

    public Call<SocialLoginResponse, AuthException> socialAuth(SocialLoginRequest r) {
        String url = r.prepareRequestURL();
        Type respType = new TypeToken<SocialLoginResponse>() {
        }.getType();
        return mkGetCall(url, respType);
    }

    // confirm_email ?? --> confirm response type
}
