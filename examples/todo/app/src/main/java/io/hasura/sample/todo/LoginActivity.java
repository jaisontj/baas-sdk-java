package io.hasura.sample.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.hasura.auth.AuthException;
import io.hasura.auth.LoginResponse;
import io.hasura.core.Call;
import io.hasura.core.Callback;
import io.hasura.core.Hasura;
import io.hasura.core.LoginCall;
import io.hasura.core.PersistentCookieStore;

public class LoginActivity extends Activity {

	private EditText mUsernameField;
	private EditText mPasswordField;
	private TextView mErrorField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Log.i(getClass().getSimpleName(), String.valueOf(new PersistentCookieStore(LoginActivity.this).getCookies()));
		mUsernameField = (EditText) findViewById(R.id.login_username);
		mPasswordField = (EditText) findViewById(R.id.login_password);
		mErrorField = (TextView) findViewById(R.id.error_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void signIn(final View v) {
        v.setEnabled(false);
        String userName = mUsernameField.getText().toString();
        String password = mPasswordField.getText().toString();
        Hasura.clearCookies();
        LoginCall<LoginResponse, AuthException> loginCall = Hasura.getAuth().login(userName, password);
        loginCall.enqueueOnUIThread(new Callback<LoginResponse, AuthException>() {
            @Override
            public void onSuccess(final LoginResponse response) {
				Log.i(getClass().getSimpleName(),String.valueOf(new PersistentCookieStore(LoginActivity.this).getCookies()));
//                Hasura.setUserId(response.getHasuraId());
//                Hasura.setUserToken(response.getSessionId());
                Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(final AuthException e) {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        mErrorField.setText(errMsg);
                        v.setEnabled(true);
            }
        });
    }

	public void showRegistration(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		startActivity(intent);
		finish();
	}
}
