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
import android.widget.Toast;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

public class BillingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    FloatingActionButton btnEdit;
    FloatingActionButton btnSave;
    int customerID;
    private Intent mIntent;

    private CustomerDBHelper helper;
    EditText nameOnCardEditText;
    EditText billingAddressEditText;
    EditText cityEditText;
    EditText stateEditText;
    EditText zipEditText;
    EditText cardNumberEditText;
    EditText expDateEditText;
    EditText cvvEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        customerID = getIntent().getIntExtra(InfoActivity.EXTRA_ID, 0);
        nameOnCardEditText = (EditText) findViewById(R.id.nameEditText);
        billingAddressEditText = (EditText) findViewById(R.id.addressEditText);
        cityEditText = (EditText) findViewById(R.id.cityEditText);
        stateEditText = (EditText) findViewById(R.id.stateEditText);
        zipEditText = (EditText) findViewById(R.id.zipEditText);
        cardNumberEditText = (EditText) findViewById(R.id.cardNumberEditText);
        expDateEditText = (EditText) findViewById(R.id.expDateEditText);
        cvvEditText = (EditText) findViewById(R.id.cvvEditText);

        btnEdit = (FloatingActionButton) findViewById(R.id.editActionButton);
        btnEdit.setOnClickListener(this);
        btnSave = (FloatingActionButton) findViewById(R.id.saveActionButton);
        btnSave.setOnClickListener(this);

        helper = new CustomerDBHelper(this);

        btnSave.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);

        Cursor result = helper.getCustomer(customerID);
        result.moveToFirst();
        String nameOnCard = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_NAME_ON_CARD_COLUMN));
        String billingAddress = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_BILLING_ADDRESS_COLUMN));
        String city = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_CITY_COLUMN));
        String state = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_STATE_COLUMN));
        String zip = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_ZIP_COLUMN));
        String cardNumber = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_CARD_NUMBER_COLUMN));
        String expDate = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_EXP_DATE_COLUMN));
        String cvv = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_CVV_COLUMN));

        if (!result.isClosed()) {
            result.close();
        }

        nameOnCardEditText.setText(nameOnCard);
        nameOnCardEditText.setFocusable(false);
        nameOnCardEditText.setClickable(false);

        billingAddressEditText.setText(billingAddress);
        billingAddressEditText.setFocusable(false);
        billingAddressEditText.setClickable(false);

        cityEditText.setText(city);
        cityEditText.setFocusable(false);
        cityEditText.setClickable(false);

        stateEditText.setText(state);
        stateEditText.setFocusable(false);
        stateEditText.setClickable(false);

        zipEditText.setText(zip);
        zipEditText.setFocusable(false);
        zipEditText.setClickable(false);

        cardNumberEditText.setText(cardNumber);
        cardNumberEditText.setFocusable(false);
        cardNumberEditText.setClickable(false);

        expDateEditText.setText(expDate);
        expDateEditText.setFocusable(false);
        expDateEditText.setClickable(false);

        cvvEditText.setText(cvv);
        cvvEditText.setFocusable(false);
        cvvEditText.setClickable(false);

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
                mIntent = new Intent(BillingActivity.this, CustomersActivity.class);
                startActivity(mIntent);
                return true;
            case R.id.session_menu:
                mIntent = new Intent(BillingActivity.this, SessionsActivity.class);
                startActivity(mIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.infoTextView:
                mIntent = new Intent(BillingActivity.this, InfoActivity.class);
                mIntent.putExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, customerID);
                startActivity(mIntent);
                return;
            case R.id.sessionsTextView:
                mIntent = new Intent(BillingActivity.this, CustomerSessionsActivity.class);
                mIntent.putExtra(InfoActivity.EXTRA_ID, customerID);
                startActivity(mIntent);
                return;
            case R.id.billingTextView:
                return;
            case R.id.editActionButton:
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);

                nameOnCardEditText.setEnabled(true);
                nameOnCardEditText.setFocusableInTouchMode(true);
                nameOnCardEditText.setClickable(true);

                billingAddressEditText.setEnabled(true);
                billingAddressEditText.setFocusableInTouchMode(true);
                billingAddressEditText.setClickable(true);

                cityEditText.setEnabled(true);
                cityEditText.setFocusableInTouchMode(true);
                cityEditText.setClickable(true);

                stateEditText.setEnabled(true);
                stateEditText.setFocusableInTouchMode(true);
                stateEditText.setClickable(true);

                zipEditText.setEnabled(true);
                zipEditText.setFocusableInTouchMode(true);
                zipEditText.setClickable(true);

                cardNumberEditText.setEnabled(true);
                cardNumberEditText.setFocusableInTouchMode(true);
                cardNumberEditText.setClickable(true);

                expDateEditText.setEnabled(true);
                expDateEditText.setFocusableInTouchMode(true);
                expDateEditText.setClickable(true);

                cvvEditText.setEnabled(true);
                cvvEditText.setFocusableInTouchMode(true);
                cvvEditText.setClickable(true);
                return;
            case R.id.saveActionButton:
                persistCustomer();
                return;
            default:
                return;

        }

    }

    public void persistCustomer() {
        if (customerID > 0) {
            if (helper.updateCustomerBilling(customerID, nameOnCardEditText.getText().toString(),
                    billingAddressEditText.getText().toString(), cityEditText.getText().toString(),
                    stateEditText.getText().toString(),zipEditText.getText().toString(),
                    cardNumberEditText.getText().toString(), expDateEditText.getText().toString(),
                    cvvEditText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Update Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if (helper.insertNewCustomer("", "", "", "", "", nameOnCardEditText.getText().toString(),
                    billingAddressEditText.getText().toString(), cityEditText.getText().toString(),
                    stateEditText.getText().toString(),zipEditText.getText().toString(),
                    cardNumberEditText.getText().toString(), expDateEditText.getText().toString(),
                    cvvEditText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Customer Inserted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Could not Insert customer", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
            startActivity(intent);
        }
    }
}
