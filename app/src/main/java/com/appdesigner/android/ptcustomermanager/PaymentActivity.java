package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class PaymentActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    public static final String EXTRA_TOTAL = "total";
    public static final String EXTRA_METHOD = "method";
    public static final String EXTRA_SIGN = "sign";
    private Intent mIntent;

    EditText totalDueEditText;
    EditText methodEditText;
    EditText signEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        totalDueEditText = (EditText) findViewById(R.id.totalDueEditText);
        methodEditText = (EditText) findViewById(R.id.methodEditText);
        signEditText = (EditText) findViewById(R.id.signEditText);
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
                mIntent = new Intent(PaymentActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(PaymentActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.payButton:
                mIntent = new Intent(PaymentActivity.this, ReceiptActivity.class);
                mIntent.putExtra(EXTRA_TOTAL, totalDueEditText.getText().toString());
                mIntent.putExtra(EXTRA_METHOD, methodEditText.getText().toString());
                mIntent.putExtra(EXTRA_SIGN, signEditText.getText().toString());
                startActivity(mIntent);
                return;
            default:
                mIntent = new Intent(PaymentActivity.this, CompleteSessionActivity.class);
                startActivity(mIntent);
                return;
        }

    }
}
