package com.example.android.bankingapp;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bankingapp.data.BankContract;

import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI;
import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI2;

public class TransferActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView recyclerView;
    private TransferAdapter transferAdapter;
    public Uri intentUri;
    private Cursor mCursor;
    public Bundle bundle;
    public static int type=0;
    public static int to_account_index;
    int trans_money=0;
    private ProgressBar mLoadingIndicator;
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String[] MAIN_TRANSFER_PROJECTION = {
            BankContract.BankEntry._2Id,
            BankContract.BankEntry.COLUMN_FROM,
            BankContract.BankEntry.COLUMN_TO,
            BankContract.BankEntry.COLUMN_TRANSFER_MONEY
    };
    private int mPosition = RecyclerView.NO_POSITION;
    public static final int INDEX_COLUMN_FROM = 1;
    public static final int INDEX_COLUMN_TO = 2;
    public static final int INDEX_COLUMN_TRANSFER_MONEY = 3;

    public static final int INDEX_COLUMN_SNO = 0;
    private static final int ID_BANK_LOADER = 44;
    private static final int ID_SELECT_ACCOUNT = 84;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        setTitle("Transfer Record");
        Log.e("TransferActivity","beginning2"+"TRANSFERMONEY:::"+AllUserActivity.transferMoneyFromTransferTable);
        if(AllUserActivity.transferMoneyFromTransferTable!=0) {
    Log.e("TransferActivity","beginning");
    inserttransferRecord();

}
        recyclerView=findViewById(R.id.recylerview2);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        transferAdapter=new TransferAdapter(this);
        recyclerView.setAdapter(transferAdapter);
        LoaderManager.getInstance(this).initLoader(ID_BANK_LOADER,null,this);
    }
    /**
     * Get user input from editor and save new pet into database.
     */
    private void inserttransferRecord() {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
       int toAccountIs = AllUserActivity.toAccountForTransferTable;
        int fromAccountIs = AllUserActivity.fromAccountForTransferTable;
        int transferMoneyIs = AllUserActivity.transferMoneyFromTransferTable;
        Log.e("TransferActivity","TO account"+toAccountIs);
        Log.e("TransferActivity","From account"+fromAccountIs);
        Log.e("TransferActivity","Transfer Money"+transferMoneyIs);

        ContentValues values3 = new ContentValues();
        values3.put(BankContract.BankEntry.COLUMN_TO, toAccountIs);
        values3.put(BankContract.BankEntry.COLUMN_FROM, fromAccountIs);
        values3.put(BankContract.BankEntry.COLUMN_TRANSFER_MONEY,transferMoneyIs);

       //for inserting content values
            Uri urii = getContentResolver().insert(CONTENT_URI2, values3);
            if (urii == null) {
                // If the new content URI is null, then there was an error with insertion.

                Toast.makeText(this, R.string.User_iNSERTION_ERROR, Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, R.string.User_iNSERTION_SUCCESSFUL, Toast.LENGTH_SHORT).show();
            }
        AllUserActivity.toAccountForTransferTable=0;
       AllUserActivity.fromAccountForTransferTable=0;
        AllUserActivity.transferMoneyFromTransferTable=0;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this,BankContract.BankEntry.CONTENT_URI2,MAIN_TRANSFER_PROJECTION,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        transferAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION)
            mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0)
            showWeatherDataView();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
       transferAdapter.swapCursor(null);
    }

    private void showWeatherDataView() {

        recyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * This method will make the loading indicator visible and hide the weather View
     */
    private void showLoading() {
        /* hide the weather data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* show the loading indicator */
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
