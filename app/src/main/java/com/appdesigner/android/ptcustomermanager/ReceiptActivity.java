package com.appdesigner.android.ptcustomermanager;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class ReceiptActivity extends AppCompatActivity {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    private Intent mIntent;

    String total;
    String method;
    String sign;

    TextView dateTextView2;
    TextView amountTextView2;
    TextView receivedDateTextView2;
    TextView paymentTextView2;
    EditText signatureEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        dateTextView2 = (TextView) findViewById(R.id.dateTextView2);
        amountTextView2 = (TextView) findViewById(R.id.amountTextView2);
        receivedDateTextView2 = (TextView) findViewById(R.id.receivedDateTextView2);
        paymentTextView2 = (TextView) findViewById(R.id.paymentTextView2);
        signatureEditText = (EditText) findViewById(R.id.signatureEditText);

        total = getIntent().getStringExtra(PaymentActivity.EXTRA_TOTAL);
        method = getIntent().getStringExtra(PaymentActivity.EXTRA_METHOD);
        sign = getIntent().getStringExtra(PaymentActivity.EXTRA_SIGN);

        dateTextView2.setText(new Date().toString());
        amountTextView2.setText(total);
        receivedDateTextView2.setText(new Date().toString());
        paymentTextView2.setText(method);
        signatureEditText.setText(sign);


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
                mIntent = new Intent(ReceiptActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(ReceiptActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.emailButton:
                return;
            default:
                return;
        }

    }
}
