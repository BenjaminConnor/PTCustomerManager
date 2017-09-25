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

public class CustomersActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    public static final String EXTRA_CREATE_OR_EDIT_KEY = "EXTRA_CREATE_OR_EDIT_KEY";

    private ListView listView;
    CustomerDBHelper helper;

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        helper = new CustomerDBHelper(this);

        final Cursor cursor = helper.getAllCustomers();
        String[] cols = new String[] {
                CustomerDBHelper.CUSTOMER_ID_COLUMN,
                CustomerDBHelper.CUSTOMER_NAME_COLUMN
        };

        int[] widgets = new int[] {
                R.id.customerID,
                R.id.customerName
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.customer_list_item,
                cursor, cols, widgets, 0);
        listView = (ListView) findViewById(R.id.customerListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view, int position, long id) {
                Cursor cursor = (Cursor) CustomersActivity.this.listView.getItemAtPosition(position);
                int customerID = cursor.getInt(cursor.getColumnIndex(CustomerDBHelper.CUSTOMER_ID_COLUMN));
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra(EXTRA_CREATE_OR_EDIT_KEY, customerID);
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
                mIntent = new Intent(CustomersActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(CustomersActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        Intent intent = new Intent(CustomersActivity.this, InfoActivity.class);
        intent.putExtra(EXTRA_CREATE_OR_EDIT_KEY, 0);
        startActivity(intent);
    }
}
