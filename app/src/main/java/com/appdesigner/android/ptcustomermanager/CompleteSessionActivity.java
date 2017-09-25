package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

import java.util.Date;

public class CompleteSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private Intent mIntent;
    CustomerDBHelper helper;
    int customerID;
    int sessionID;

    FloatingActionButton btnEdit;
    FloatingActionButton btnSave;

    EditText nameEditText;
    EditText dateEditText;
    EditText timeEditText;
    EditText locationEditText;
    TextView completedDateTextView;
    EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_session);

        customerID = getIntent().getIntExtra(CustomerSessionsActivity.EXTRA_CREATE_OR_EDIT_KEY, 0);
        sessionID = getIntent().getIntExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, 0);
        helper = new CustomerDBHelper(this);

        nameEditText = (EditText) findViewById(R.id.customerName);
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        completedDateTextView = (TextView) findViewById(R.id.completedDateTextView);
        notesEditText = (EditText) findViewById(R.id.notesEditText);

        btnEdit = (FloatingActionButton) findViewById(R.id.floatingEditButton);
        btnEdit.setOnClickListener(this);
        btnSave = (FloatingActionButton) findViewById(R.id.floatingSaveButton);
        btnSave.setOnClickListener(this);

        btnSave.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);

        Cursor result = helper.getSession(sessionID);
        result.moveToFirst();
        String customerName = result.getString(result.getColumnIndex(CustomerDBHelper.SESSION_CUSTOMER_NAME_COLUMN));
        String sessionDate = result.getString(result.getColumnIndex(CustomerDBHelper.SESSION_DATE_COLUMN));
        String sessionTime = result.getString(result.getColumnIndex(CustomerDBHelper.SESSION_TIME_COLUMN));
        String location = result.getString(result.getColumnIndex(CustomerDBHelper.SESSION_LOCATION_COLUMN));
        String notes = result.getString(result.getColumnIndex(CustomerDBHelper.SESSION_NOTES_COLUMN));

        if (!result.isClosed()) {
            result.close();
        }

        nameEditText.setText(customerName);
        nameEditText.setFocusable(false);
        nameEditText.setClickable(false);

        dateEditText.setText(sessionDate);
        dateEditText.setFocusable(false);
        dateEditText.setClickable(false);

        timeEditText.setText(sessionTime);
        timeEditText.setFocusable(false);
        timeEditText.setClickable(false);

        locationEditText.setText(location);
        locationEditText.setFocusable(false);
        locationEditText.setClickable(false);

        completedDateTextView.setText(new Date().toString());

        notesEditText.setText(notes);
        notesEditText.setFocusable(false);
        notesEditText.setClickable(false);

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
                mIntent = new Intent(CompleteSessionActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(CompleteSessionActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.payButton:
                mIntent = new Intent(CompleteSessionActivity.this, PaymentActivity.class);
                startActivity(mIntent);
                return;
            case R.id.cancelButton:
                mIntent = new Intent(CompleteSessionActivity.this, SessionsActivity.class);
                mIntent.putExtra(CustomerSessionsActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                mIntent.putExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, sessionID);
                startActivity(mIntent);
                return;
            case R.id.floatingEditButton:
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);

                nameEditText.setEnabled(true);
                nameEditText.setFocusableInTouchMode(true);
                nameEditText.setClickable(true);

                dateEditText.setEnabled(true);
                dateEditText.setFocusableInTouchMode(true);
                dateEditText.setClickable(true);

                timeEditText.setEnabled(true);
                timeEditText.setFocusableInTouchMode(true);
                timeEditText.setClickable(true);

                locationEditText.setEnabled(true);
                locationEditText.setFocusableInTouchMode(true);
                locationEditText.setClickable(true);

                notesEditText.setEnabled(true);
                notesEditText.setFocusableInTouchMode(true);
                notesEditText.setClickable(true);
                return;
            case R.id.floatingSaveButton:
                if (helper.updateSession(sessionID, customerID, nameEditText.getText().toString(),
                        dateEditText.getText().toString(), timeEditText.getText().toString(),
                        locationEditText.getText().toString(), notesEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                    mIntent = new Intent(getApplicationContext(), CompleteSessionActivity.class);
                    mIntent.putExtra(CustomerSessionsActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                    mIntent.putExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, sessionID);
                    startActivity(mIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
            default:
                return;
        }

    }
}
