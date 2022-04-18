package com.example.android.bankingapp;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.android.bankingapp.data.BankContract;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String[] WEATHER_DETAIL_PROJECTION = {
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
    BankContract.BankEntry.COLUMN_IFSC_NUMBER,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_EMAIL,
    BankContract.BankEntry.COLUMN_BANK_PEOPLE_MOBILE_NUMBER,
    BankContract.BankEntry.COLUMN_TOTAL_BALANCE,
    BankContract.BankEntry._Id
    };
    //Index for columns of weather data that we are going to access.
    public static final int INDEX_NAME = 1;
    public static final int INDEX_ACCOUNT_NUMBER = 2;
    public static final int INDEX_IFSC_NUMBER = 3;
    public static final int INDEX_PEOPLE_EMAIL = 4;
    public static final int INDEX_MOBILE_NUMBER = 5;
    public static final int INDEX_TOTAL_BALANCE = 6;
    public static final int INDEX_Id = 0;
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
        if (mUri == null)
            throw new NullPointerException("URI for DetailActivity cannot be null");

        LoaderManager.getInstance(this).initLoader(ID_DETAIL_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

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

        /***************** Name *****************/
        String nameId=data.getString(INDEX_NAME);
        nameTextView.setText(nameId);
        nameTextView.setContentDescription(nameId);
        /*****************Account No *****************/
        int accountNo=data.getInt(INDEX_ACCOUNT_NUMBER);
        AccountTextView.setText(accountNo+"");
        AccountTextView.setContentDescription(accountNo+"");
        /*****************IFSC Code *****************/
        int IfscCode=data.getInt(INDEX_IFSC_NUMBER);
        ifscTextView.setText(IfscCode+"");
        ifscTextView.setContentDescription(IfscCode+"");
        /*****************Mobile *****************/
        int mobileno=data.getInt(INDEX_MOBILE_NUMBER);
        mobileNoTextView.setText(mobileno+"");
        mobileNoTextView.setContentDescription(mobileno+"");
        /*****************Email Id *****************/
        String emailId=data.getString(INDEX_PEOPLE_EMAIL);
        emailTextView.setText(emailId+"");
        emailTextView.setContentDescription(emailId+"");
        /*****************BALANCE *****************/
        int balance=data.getInt(INDEX_TOTAL_BALANCE);
        totalBalanceTextView.setText(balance+"");
        totalBalanceTextView.setContentDescription(balance+"");


        /* Store the forecast summary String in our forecast summary field to share later */
//        mForecastSummary = String.format("%s - %s - %s/%s",
//                dateText, description, highString, lowString);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
