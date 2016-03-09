package io.hasura.sample.todo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.hasura.core.Callback;
import io.hasura.db.DBException;
import io.hasura.db.InsertQuery;
import io.hasura.db.InsertResult;
import io.hasura.db.SelectQuery;
import static io.hasura.sample.todo.db.Tables.*;

import io.hasura.db.UpdateQuery;
import io.hasura.db.UpdateResult;
import io.hasura.sample.todo.db.tables.records.TaskRecord;

public class TodoActivity extends Activity implements OnItemClickListener {

	private EditText mTaskInput;
	private ListView mListView;
	private TaskAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);

		Integer currentUser = Hasura.getCurrentUserId();

		if (currentUser == null) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		} else {
            mAdapter = new TaskAdapter(this, new ArrayList<TaskRecord>());
            mTaskInput = (EditText) findViewById(R.id.task_input);
            mListView = (ListView) findViewById(R.id.task_list);
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(this);

            updateData();
        }

	}

	public void updateData(){
        SelectQuery<TaskRecord> q
                = Hasura.db.select(TASK)
                .columns(TASK.ID, TASK.DESCRIPTION, TASK.IS_COMPLETED, TASK.TITLE);
        q.build().enqueue(new Callback<List<TaskRecord>, DBException>() {
            @Override
            public void onSuccess(final List<TaskRecord> taskRecords) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.clear();
                        for (TaskRecord tr : taskRecords) {
                            mAdapter.add(tr);
                        }
                    }
                });
            }

            @Override
            public void onFailure(DBException e) {

            }
        });
	}

	public void createTask(View v) {
		if (mTaskInput.getText().length() > 0){
            Log.d("user_id_insert", Hasura.getCurrentUserId().toString());
            InsertQuery<TaskRecord> query
                    = Hasura.db.insert(TASK)
                    .set(TASK.TITLE, mTaskInput.getText().toString())
                    .set(TASK.DESCRIPTION, "")
                    .set(TASK.IS_COMPLETED, false)
                    .set(TASK.USER_ID, Hasura.getCurrentUserId())
                    .returning(TASK.ID, TASK.TITLE, TASK.IS_COMPLETED);

            query.build().enqueue(new Callback<InsertResult<TaskRecord>, DBException>() {
                @Override
                public void onSuccess(final InsertResult<TaskRecord> taskRecordInsertResult) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            mAdapter.insert(taskRecordInsertResult.getRecords().get(0), 0);
                            mTaskInput.setText("");
                        }
                    });
                }

                @Override
                public void onFailure(DBException e) {
                    // Where to show this?
                }
            });
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_logout: 
			// TODO ParseUser.logOut();
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return true; 
		} 
		return false; 
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		TaskRecord task = mAdapter.getItem(position);
		TextView taskDescription = (TextView) view.findViewById(R.id.task_description);

		task.isCompleted = !task.isCompleted;

		if (task.isCompleted) {
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		} else {
			taskDescription.setPaintFlags(taskDescription.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
		}

        UpdateQuery<TaskRecord> q =
                Hasura.db.update(TASK)
                .set(TASK.IS_COMPLETED, task.isCompleted)
                .where(TASK.ID.eq(task.id));

        q.build().enqueue(new Callback<UpdateResult<TaskRecord>, DBException>() {
            @Override
            public void onSuccess(UpdateResult<TaskRecord> taskRecordUpdateResult) {
                // Toast?
            }

            @Override
            public void onFailure(DBException e) {
                // Update failed? Revert?
            }
        });
	}

}
