package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

public class NewSessionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private Intent mIntent;
    private int mCheckCode;
    int customerID;
    int sessionID;
    private CustomerDBHelper helper;
    EditText customerNameEditText;
    EditText dateEditText;
    EditText timeEditText;
    EditText notesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);

        sessionID = getIntent().getIntExtra(SessionsActivity.EXTRA_SESSION_ID, 0);

        customerID = getIntent().getIntExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, 0);
        customerNameEditText = (EditText) findViewById(R.id.customerNameEditText);
        dateEditText = (EditText) findViewById(R.id.pickDateEditText);
        timeEditText = (EditText) findViewById(R.id.timeEditText);
        notesEditText = (EditText) findViewById(R.id.notesEditText);

        helper = new CustomerDBHelper(this);


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
                mIntent = new Intent(NewSessionActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(NewSessionActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.cancelButton:
                mIntent = new Intent(NewSessionActivity.this, SessionsActivity.class);
                mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                mIntent.putExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, sessionID);
                startActivity(mIntent);
                return;
            case R.id.createButton:
                if (helper.insertNewSession(customerID, customerNameEditText.getText().toString(),
                        dateEditText.getText().toString(), timeEditText.getText().toString(), "",
                        notesEditText.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Session Added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Could not add session", Toast.LENGTH_SHORT).show();
                }
                if (mCheckCode == 1) {
                    mIntent = new Intent(NewSessionActivity.this, PaymentActivity.class);
                    mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                    mIntent.putExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, sessionID);
                    startActivity(mIntent);
                }
                else {
                    mIntent = new Intent(NewSessionActivity.this, SessionsActivity.class);
                    mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                    mIntent.putExtra(CustomerSessionsActivity.EXTRA_SESSION_ID, sessionID);
                    startActivity(mIntent);
                }
                return;
        }

    }
    public void onPayNowClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            mCheckCode = 1;
        }
        else {
            mCheckCode = 0;
        }
    }
}
