package io.hasura.sample.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.hasura.auth.AuthException;
import io.hasura.auth.RegisterRequest;
import io.hasura.auth.RegisterResponse;
import io.hasura.core.Callback;
import io.hasura.db.DBException;
import io.hasura.db.InsertQuery;
import io.hasura.sample.todo.db.tables.records.UserRecord;
import static io.hasura.sample.todo.db.Tables.*;

public class RegisterActivity extends Activity {

	private EditText mUsernameField;
	private EditText mPasswordField;
	private TextView mErrorField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		mUsernameField = (EditText) findViewById(R.id.register_username);
		mPasswordField = (EditText) findViewById(R.id.register_password);
		mErrorField = (TextView) findViewById(R.id.error_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	public void register(final View v){
		if (mUsernameField.getText().length() == 0 || mPasswordField.getText().length() == 0) {
            mErrorField.setText("username/password can't be empty");
            return;
        }
		v.setEnabled(false);

		final String userName = mUsernameField.getText().toString();
		String password = mPasswordField.getText().toString();

		mErrorField.setText("");

        RegisterRequest rr = new RegisterRequest();
        rr.setUsername(userName);
        rr.setPassword(password);

        Hasura.auth.register(rr).enqueue(new Callback<RegisterResponse, AuthException>() {
            @Override
            public void onSuccess(final RegisterResponse registerResponse) {
                InsertQuery<UserRecord> query
                        = Hasura.db.insert(USER)
                        .set(USER.ID, registerResponse.getHasuraId())
                        .set(USER.USERNAME, userName);
                try {
                    query.build().execute();
                    Hasura.setUserId(registerResponse.getHasuraId());
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(RegisterActivity.this, TodoActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                } catch (final DBException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                            mErrorField.setText(errMsg);
                            v.setEnabled(true);
                        }
                    });

                }
            }

            @Override
            public void onFailure(final AuthException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String errMsg = e.getCode().toString() + " : " + e.getLocalizedMessage();
                        mErrorField.setText(errMsg);
                        v.setEnabled(true);
                    }
                });
            }
        });
	}

	public void showLogin(View v) {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
}
