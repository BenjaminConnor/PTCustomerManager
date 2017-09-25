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
import android.widget.ListView;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

public class CustomerSessionsActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private Intent mIntent;
    public static final String EXTRA_CREATE_OR_EDIT_KEY = "EXTRA_CREATE_OR_EDIT_KEY";
    public static final String EXTRA_SESSION_ID = "session_id";

    private ListView listView;
    CustomerDBHelper helper;
    int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sessions);

        customerID = getIntent().getIntExtra(InfoActivity.EXTRA_ID, 0);
        helper = new CustomerDBHelper(this);

        final Cursor cursor = helper.getCustomerSessions(customerID);
        String[] cols = new String[] {
                CustomerDBHelper.SESSION_DATE_COLUMN,
                CustomerDBHelper.SESSION_TIME_COLUMN
        };

        int[] widgets = new int[] {
                R.id.sessionDateTextView,
                R.id.sessionTimeTextView
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.customer_session_list_item,
                cursor, cols, widgets, 0);
        listView = (ListView) findViewById(R.id.customerSessionsListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view, int position, long id) {
                Cursor cursor = (Cursor) CustomerSessionsActivity.this.listView.getItemAtPosition(position);
                int sessionID = cursor.getInt(cursor.getColumnIndex(CustomerDBHelper.SESSION_ID_COLUMN));
                int customerID = cursor.getInt(cursor.getColumnIndex(CustomerDBHelper.CUSTOMER_ID_COLUMN));
                Intent intent = new Intent(getApplicationContext(), CompleteSessionActivity.class);
                intent.putExtra(EXTRA_CREATE_OR_EDIT_KEY, customerID);
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
                mIntent = new Intent(CustomerSessionsActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(CustomerSessionsActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.infoTextView:
                mIntent = new Intent(CustomerSessionsActivity.this, InfoActivity.class);
                mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                startActivity(mIntent);
                return;
            case R.id.sessionsTextView:
                return;
            case R.id.billingTextView:
                mIntent = new Intent(CustomerSessionsActivity.this, BillingActivity.class);
                mIntent.putExtra(InfoActivity.EXTRA_ID, customerID);
                startActivity(mIntent);
                return;
            case R.id.addActionButton:
                mIntent = new Intent(CustomerSessionsActivity.this, NewSessionActivity.class);
                mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                mIntent.putExtra(EXTRA_SESSION_ID, 0);
                startActivity(mIntent);
                return;
            default:
                return;
        }

    }
}
