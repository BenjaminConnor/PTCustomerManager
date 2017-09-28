package com.appdesigner.android.ptcustomermanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.appdesigner.android.ptcustomermanager.database.CustomerDBHelper;

import java.io.File;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String DIALOG_LOG_OFF = "DialogLogOff";
    public static final String EXTRA_ID = "EXTRA_ID";
    static final int REQUEST_IMAGE_CAPTURE= 1;
    final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    private ImageButton newPicButton;
    private ImageView customerPicView;
    private Context mContext;
    private File mPhotoFile;

    FloatingActionButton btnEdit;
    FloatingActionButton btnSave;
    private Intent mIntent;
    int customerID;

    private CustomerDBHelper helper;
    EditText nameEditText;
    EditText phoneEditText;
    EditText addressEditText;
    EditText emailEditText;
    EditText infoNotesEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        customerID = getIntent().getIntExtra(CustomersActivity.EXTRA_CREATE_OR_EDIT_KEY, 0);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        addressEditText = (EditText) findViewById(R.id.addressEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        infoNotesEditText = (EditText) findViewById(R.id.notesEditText);

        newPicButton = (ImageButton) findViewById(R.id.newPicButton);
        mPhotoFile = getPhotoFile(customerID);
        boolean canTakePic = mPhotoFile != null && captureImage.resolveActivity(getPackageManager()) != null;
        newPicButton.setEnabled(canTakePic);

        customerPicView = (ImageView) findViewById(R.id.customerPic);
        updatePicView();

        btnSave = (FloatingActionButton) findViewById(R.id.saveInfoButton);
        btnSave.setOnClickListener(this);
        btnEdit = (FloatingActionButton) findViewById(R.id.editInfoButton);
        btnEdit.setOnClickListener(this);

        helper = new CustomerDBHelper(this);

        if (customerID > 0) {
            btnSave.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);

            Cursor result = helper.getCustomer(customerID);
            result.moveToFirst();
            String name = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_NAME_COLUMN));
            String phone = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_PHONE_COLUMN));
            String address = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_ADDRESS_COLUMN));
            String email = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_EMAIL_COLUMN));
            String infoNotes = result.getString(result.getColumnIndex(CustomerDBHelper.CUSTOMER_INFO_NOTES_COLUMN));

            if (!result.isClosed()) {
                result.close();
            }

            nameEditText.setText(name);
            nameEditText.setFocusable(false);
            nameEditText.setClickable(false);

            phoneEditText.setText(phone);
            phoneEditText.setFocusable(false);
            phoneEditText.setClickable(false);

            addressEditText.setText(address);
            addressEditText.setFocusable(false);
            addressEditText.setClickable(false);

            emailEditText.setText(email);
            emailEditText.setFocusable(false);
            emailEditText.setClickable(false);

            infoNotesEditText.setText(infoNotes);
            infoNotesEditText.setFocusable(false);
            infoNotesEditText.setClickable(false);

        }

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
                mIntent.putExtra(EXTRA_ID, customerID);
                startActivity(mIntent);
                return;
            case R.id.billingTextView:
                mIntent = new Intent(InfoActivity.this, BillingActivity.class);
                mIntent.putExtra(EXTRA_ID, customerID);
                startActivity(mIntent);
                return;
            case R.id.newPicButton:

                if (mPhotoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(InfoActivity.this,
                            "com.appdesigner.android.ptcustomermanager.fileprovider", mPhotoFile);
                    captureImage.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    List<ResolveInfo> cameraActivities = InfoActivity.this.getPackageManager()
                            .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                    for (ResolveInfo activity : cameraActivities) {
                        InfoActivity.this.grantUriPermission(activity.activityInfo.packageName,
                                photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    startActivityForResult(captureImage, REQUEST_IMAGE_CAPTURE);
                }
                return;
            case R.id.saveInfoButton:
                persistCustomer();
                return;
            case R.id.editInfoButton:
                btnSave.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.GONE);

                nameEditText.setEnabled(true);
                nameEditText.setFocusableInTouchMode(true);
                nameEditText.setClickable(true);

                phoneEditText.setEnabled(true);
                phoneEditText.setFocusableInTouchMode(true);
                phoneEditText.setClickable(true);

                addressEditText.setEnabled(true);
                addressEditText.setFocusableInTouchMode(true);
                addressEditText.setClickable(true);

                emailEditText.setEnabled(true);
                emailEditText.setFocusableInTouchMode(true);
                emailEditText.setClickable(true);

                infoNotesEditText.setEnabled(true);
                infoNotesEditText.setFocusableInTouchMode(true);
                infoNotesEditText.setClickable(true);
                return;
            default:
                return;
        }

    }

    public void persistCustomer() {
        if (customerID > 0) {
            if (helper.updateCustomerInfo(customerID, nameEditText.getText().toString(),
                    phoneEditText.getText().toString(), addressEditText.getText().toString(),
                    emailEditText.getText().toString(),infoNotesEditText.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Save Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Save Failed", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if (helper.insertNewCustomer(nameEditText.getText().toString(),
                    phoneEditText.getText().toString(), addressEditText.getText().toString(),
                    emailEditText.getText().toString(),infoNotesEditText.getText().toString(), "", "",
                    "", "", "", "", "", "")) {
                Toast.makeText(getApplicationContext(), "Customer Added", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Could not add customer", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(InfoActivity.this,
                    "com.appdesigner.android.ptcustomermanager.fileprovider", mPhotoFile);
            InfoActivity.this.revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePicView();
        }
    }

    public File getPhotoFile(int id) {
        mContext = getApplicationContext();
        File filesDir = mContext.getFilesDir();
        String filename = "IMG_" + id + ".jpg";
        return new File(filesDir, filename);

    }

    private void updatePicView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            return;
        } else {
            Bitmap bitmap = PicUtil.getScaledBitmap(mPhotoFile.getPath(), InfoActivity.this);
            customerPicView.setImageBitmap(bitmap);
        }
    }

}
