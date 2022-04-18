package com.example.android.bankingapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bankingapp.data.BankContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URI;

import static com.example.android.bankingapp.data.BankContract.BankEntry.CONTENT_URI;

public class AllUserActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,BankAdapter.BankAdapterOnClickHandler {
    private RecyclerView recyclerView;
    private BankAdapter bankAdapter;
    private ProgressBar mLoadingIndicator;
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String[] MAIN_FORECAST_PROJECTION = {
            BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
            BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
            BankContract.BankEntry.COLUMN_IFSC_NUMBER,

    };
    private int mPosition = RecyclerView.NO_POSITION;
    public static final int INDEX_PEOPLE_NAME = 0;
    public static final int INDEX_ACCOUNT_NOS = 1;
    public static final int INDEX_IFSC_CODE = 2;

    private static final int ID_BANK_LOADER = 44;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUserActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);
    bankAdapter=new BankAdapter(this,this);
   recyclerView.setAdapter(bankAdapter);
      //  showLoading();

        LoaderManager.getInstance(this).initLoader(ID_BANK_LOADER,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
/*
        switch (id) {

            case ID_BANK_LOADER:
                //URI for all rows of weather data in our weather table
                Uri forecastQueryUri = CONTENT_URI;
                // Sort order: Ascending by date
                String sortOrder =  BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER + " ASC";

return new CursorLoader(this,forecastQueryUri,MAIN_FORECAST_PROJECTION,null//BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,null,sortOrder);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }*/
//        String[] projections={
//                BankContract.BankEntry._Id,
//                BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME,
//                BankContract.BankEntry.COLUMN_ACCOUNT_NUMBER,
//                BankContract.BankEntry.COLUMN_IFSC_NUMBER};
        return new CursorLoader(this,BankContract.BankEntry.CONTENT_URI,MAIN_FORECAST_PROJECTION,null,null,null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        bankAdapter.swapCursor(data);
        if (mPosition == RecyclerView.NO_POSITION)
            mPosition = 0;
        recyclerView.smoothScrollToPosition(mPosition);
        if (data.getCount() != 0)
            showWeatherDataView();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        bankAdapter.swapCursor(null);
    }

    private void showWeatherDataView() {
        /* hide the loading indicator */
     //   mLoadingIndicator.setVisibility(View.INVISIBLE);
        /* weather data is visible */
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
    public void onClick(int account) {
        Intent CustomerDetailIntent = new Intent(AllUserActivity.this,DetailActivity.class);
Uri uri_for_account_clicked= CONTENT_URI.buildUpon().appendPath(Integer.toString(account)).build();
CustomerDetailIntent.setData(uri_for_account_clicked);
startActivity(CustomerDetailIntent);
    }
}
