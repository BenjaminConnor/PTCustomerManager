package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

public class SessionsActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    public static final String EXTRA_SESSION_ID = "session_id";
    private Intent mIntent;
    int customerID;
    private CustomerDBHelper helper;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        helper = new CustomerDBHelper(this);

        final Cursor cursor = helper.getAllSessions();
        String[] cols = new String[] {
                CustomerDBHelper.SESSION_DATE_COLUMN,
                CustomerDBHelper.SESSION_TIME_COLUMN,
                CustomerDBHelper.SESSION_CUSTOMER_NAME_COLUMN
        };

        int[] widgets = new int[] {
                R.id.sessionDateTextView,
                R.id.sessionTimeTextView,
                R.id.customerNameTextView
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.session_list_item,
                cursor, cols, widgets, 0);
        listView = (ListView) findViewById(R.id.sessionsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view, int position, long id) {
                Cursor cursor = (Cursor) SessionsActivity.this.listView.getItemAtPosition(position);
                int sessionID = cursor.getInt(cursor.getColumnIndex(CustomerDBHelper.SESSION_ID_COLUMN));
                int customerID = cursor.getInt(cursor.getColumnIndex(CustomerDBHelper.CUSTOMER_ID_COLUMN));
                Intent intent = new Intent(getApplicationContext(), CompleteSessionActivity.class);
                intent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                intent.putExtra(EXTRA_SESSION_ID, sessionID);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.log_off_menu:
                FragmentManager manager = getSupportFragmentManager();
                LogOffFragment dialog = new LogOffFragment();
                dialog.show(manager, DIALOG_LOG_OFF);
                return true;
            case R.id.customer_menu:
                mIntent = new Intent(SessionsActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(SessionsActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        mIntent = new Intent(SessionsActivity.this, NewSessionActivity.class);
        mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
        mIntent.putExtra(EXTRA_SESSION_ID, 0);
        startActivity(mIntent);
    }
}
