package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class CustomersActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

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

            if(view == view.findViewById(R.id.floatingActionButton)) {
                Intent intent = new Intent(CustomersActivity.this, InfoActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(CustomersActivity.this, InfoActivity.class);
                startActivity(intent);
            }
    }
}
