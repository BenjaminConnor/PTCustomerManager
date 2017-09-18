package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private static final int REQUEST_LOG_OFF = 0;

    private Button mAddButton;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
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
                mIntent = new Intent(InfoActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(InfoActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.infoTextView:
                return;
            case R.id.sessionsTextView:
                mIntent = new Intent(InfoActivity.this, CustomerSessionsActivity.class);
                startActivity(mIntent);
                return;
            case R.id.billingTextView:
                mIntent = new Intent(InfoActivity.this, BillingActivity.class);
                startActivity(mIntent);
                return;
            case R.id.newPicButton:
                return;
            default:
                return;
        }

    }
}
