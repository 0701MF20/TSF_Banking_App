package com.example.android.bankingapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.bankingapp.data.BankContract;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String[] WEATHER_DETAIL_PROJECTION =new String[] {  BankContract.BankEntry._Id,
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
    BankContract.BankEntry.COLUMN_IFSC_NUMBER,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,
    BankContract.BankEntry.COLUMN_TOTAL_BALANCE,
    };

private TextView nameTextView;
    private TextView AccountTextView;
    private TextView ifscTextView;
    private TextView emailTextView;
    private TextView mobileNoTextView;
    private TextView totalBalanceTextView;
    private Uri mUri;
    private static final int ID_DETAIL_LOADER = 353;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
        nameTextView=findViewById(R.id.nametextView);
        ifscTextView=findViewById(R.id.IfsctextView3);
        AccountTextView=findViewById(R.id.AccounttextView4);
        emailTextView=findViewById(R.id.emailIdtextView8);
        mobileNoTextView=findViewById(R.id.mobileIdtextView9);
        totalBalanceTextView=findViewById(R.id.balancetextView10);

        mUri = getIntent().getData();
        Log.e("DetailActivity","Uri "+mUri);
        if (mUri == null)
            throw new NullPointerException("URI for DetailActivity cannot be null");

        LoaderManager.getInstance(this).initLoader(ID_DETAIL_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
Log.e("DetailActivity","ID is:"+id+"  muri   "+mUri);
        switch (id) {

            case ID_DETAIL_LOADER:
                return new CursorLoader(this,
                        mUri,
                        WEATHER_DETAIL_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " +id);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* if we have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;
        }

        if (!cursorHasValidData) {
            /*if no data to display, simply return and do nothing */
            return;
        }
        if(data.getCount()<1||data==null)
        {
            return;
        }
        if(data.moveToFirst()) {

            /***************** Name *****************/
            int nameIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME);

            String nameId = data.getString(nameIndex);
            nameTextView.setText(nameId);
            nameTextView.setContentDescription(nameId);
            /*****************Account No *****************/
            int accIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER);
            int accountNo = data.getInt(accIndex);
            AccountTextView.setText(accountNo + "");
            AccountTextView.setContentDescription(accountNo + "");
            /*****************IFSC Code *****************/
            int IFSCIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_IFSC_NUMBER);
            int IfscCode = data.getInt(IFSCIndex);
            ifscTextView.setText(IfscCode + "");
            ifscTextView.setContentDescription(IfscCode + "");
            /*****************Mobile *****************/
            int mobileIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER);
            int mobileno = data.getInt(mobileIndex);
            mobileNoTextView.setText(mobileno + "");
            mobileNoTextView.setContentDescription(mobileno + "");
            /*****************Email Id *****************/
            int emailIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL);
            String emailId = data.getString(emailIndex);
            emailTextView.setText(emailId + "");
            emailTextView.setContentDescription(emailId + "");
            /*****************BALANCE *****************/
            int balanceIndex = data.getColumnIndex(BankContract.BankEntry.COLUMN_TOTAL_BALANCE);
            int balance = data.getInt(balanceIndex);
            totalBalanceTextView.setText(balance + "");
            totalBalanceTextView.setContentDescription(balance + "");

        }
        /* Store the forecast summary String in our forecast summary field to share later */
//        mForecastSummary = String.format("%s - %s - %s/%s",
//                dateText, description, highString, lowString);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        nameTextView.setText("");
        AccountTextView.setText("");
        ifscTextView.setText("");
        emailTextView.setText("");
        mobileNoTextView.setText("");
        totalBalanceTextView.setText("");
    }
}
