package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class NewSessionActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private Intent mIntent;
    private CheckBox mCheckBox;
    private int mCheckCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_session);
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
            case R.id.selectCustomerTextView:
                return;
            case R.id.pickDateTextView:
                return;
            case R.id.pickTimeTextView:
                return;
            case R.id.cancelButton:
                mIntent = new Intent(NewSessionActivity.this, CustomerSessionsActivity.class);
                startActivity(mIntent);
                return;
            case R.id.createButton:
                setContentView(R.layout.activity_new_session);
                mCheckBox = (CheckBox) view.findViewById(R.id.payNowCheckBox);
                mCheckBox.setChecked(true);
                mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mIntent = new Intent(NewSessionActivity.this, PaymentActivity.class);
                            startActivity(mIntent);
                        }
                        else {
                            mIntent = new Intent(NewSessionActivity.this, SessionsActivity.class);
                            startActivity(mIntent);
                        }
                    }
                });

                return;
        }

    }
}
