package com.example.android.bankingapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;

import static com.example.android.bankingapp.data.BankContract.BankEntry.COLUMN_BANK_PEOPLE_NAME;

public class BankProvider extends ContentProvider {
    /**Tag for the log messgaes*/
    public static final String LOG_TAG=BankProvider.class.getSimpleName();
//FOR INTEGER CODE CONSTANTS
    /** URI matcher code for the content URI for the bank table */
    public static final int BANK_ID=100;
    /** URI matcher code for the content URI for a single cutomer in the bank table */
    public static final int BANKS=101;

    /** URI matcher code for the content URI for the transfer table */
    public static final int TRANSFER_ID=102;
    /** URI matcher code for the content URI for a single transfer in the transfer table */
    public static final int TRANSFERS=103;



    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
//for declaring Urimatcher and declared as static AND WE DECLARED IT FINAL SO THAT NO ONE CAN CAN AC
    public static final UriMatcher sUriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        //1st parrameter is content authority , 2nd parameter is path and last parameter is alloted code constant
        sUriMatcher.addURI(BankContract.CONTENT_AUTHORITY,BankContract.PATH_SET,BANKS);
        sUriMatcher.addURI(BankContract.CONTENT_AUTHORITY,BankContract.PATH_SET+"/#",BANK_ID);
//THIS IS I CHANGE IF NOT WORKING REMOVE IT
        sUriMatcher.addURI(BankContract.CONTENT_AUTHORITY,BankContract.PATH_SET_TRANS+"/#",TRANSFER_ID);
        sUriMatcher.addURI(BankContract.CONTENT_AUTHORITY,BankContract.PATH_SET_TRANS,TRANSFERS);

    }
    //REFERENCE VARIABLE OF PetDbHelper
    private BankDbHelper mDbHelpers;
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        // Create and initialize a PetDbHelper object to gain access to the pets database.
        mDbHelpers=new BankDbHelper(getContext());
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        //sqlite database is object is created and get readable method is called
        SQLiteDatabase db=mDbHelpers.getReadableDatabase();
        //cursor
        Cursor cursor;
        //match through UriMatcher
        int match=sUriMatcher.match(uri);
        Log.e("BankProvider","match is:::::::::::"+match);
        switch(match)
        {
            case BANKS:
                //for whole table pet
                Log.e("BankProvider","ALL TABLE");
                cursor=db.query(BankContract.BankEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BANK_ID:
                Log.e("BankProvider","SINGLE TABLE");
                //for single id of pet

                selection= BankContract.BankEntry._Id+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                Log.e("BankProvider","SELECTION ARGS"+ContentUris.parseId(uri));
                cursor=db.query(BankContract.BankEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);

                break;
            case TRANSFERS:
                Log.e("BankProvider","ALL transfer TABLE");
                cursor=db.query(BankContract.BankEntry.TRANSFER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case TRANSFER_ID:
                Log.e("BankProvider","SINGLE TRANSFER TABLE");
                //for single id of pet

                selection= BankContract.BankEntry._2Id+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                Log.e("BankProvider","SELECTION ARGStrans"+ContentUris.parseId(uri));
                cursor=db.query(BankContract.BankEntry.TRANSFER_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
            default:
                throw new IllegalArgumentException("Cannot Query unknown URI"+uri);
        }
        //to provide us the notification about the uri for a cursor and it acts as a listener to listen the changes that occur by default or update or delete in order to prevent unnecessary loading
        cursor.setNotificationUri(getContext().getContentResolver(),uri);//it is just like listening noise

        return cursor;
    }

    /**
     * Insert a pet into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        // Get writeable database
        SQLiteDatabase db=mDbHelpers.getWritableDatabase();
        //match the uri and extract the code associated with uri
        int match=sUriMatcher.match(uri);
        Log.e(LOG_TAG,"Match is found::::"+match);
        // Insert the new pet with the given values
        //long ids;
        Uri uriss=null;
        switch(match)
        {
            case BANKS:
                long ids=db.insert(BankContract.BankEntry.TABLE_NAME,null,contentValues);
                // Once we know the ID of the new row in the table,
                // return the new URI with the ID appended to the end of it
                Log.e(LOG_TAG,"ID IS::::"+ids);
                uriss=ContentUris.withAppendedId(BankContract.BankEntry.CONTENT_URI,ids);
                break;
            case TRANSFERS:
                long idss=db.insert(BankContract.BankEntry.TRANSFER_TABLE_NAME,null,contentValues);
                // Once we know the ID of the new row in the table,
                // return the new URI with the ID appended to the end of it
                Log.e(LOG_TAG,"ID IS of transfer::::"+idss);
                uriss=ContentUris.withAppendedId(BankContract.BankEntry.CONTENT_URI2,idss);
                break;
            default:
                throw new IllegalArgumentException("Cannot insert the row        ---------"+uri);
        }
        //notify about if any change occur(in local term it is sound)
        getContext().getContentResolver().notifyChange(uri,null);
        //notify the changes to all the listener to tell then some row is edited
        // getContext().getContentResolver().notifyChange(uri,null);
        return uriss;
    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        int updatedRow=0;
        //sanity checking
        //now check for uri matcher
        int match=sUriMatcher.match(uri);

        switch (match)
        {//for whole table
            case BANKS:
                //notify about if any change occur(in local term it is sound)
                Log.e("BankProvider","multiple row");

                updatedRow=updatePet(uri,contentValues,selection,selectionArgs);
                break;
            // For the PETS code, extract out the ID from the URI,
            // so we know which row to update. Selection will be "_id=?" and selection
            // arguments will be a String array containing the actual ID.
            //for single rows

            //TODO:BANKS is going to use for sure
            case BANK_ID:

                selection= BankContract.BankEntry._Id+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
Log.e("BankProvider","single row");
                updatedRow= updatePet(uri,contentValues,selection,selectionArgs);
                Log.e("BankProvider","Updated Row:::::::"+updatedRow);
                break;
            default:
                throw new IllegalArgumentException("Update is not supported for"+uri);
        }
        if(updatedRow!=0)
        {
            //notify about if any change occur(in local term it is sound)
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return updatedRow;
    }
    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updatePet(Uri uri,ContentValues contentValues,String selection,String[] selectionArgs)
    {

        // If there are no values to update, then don't try to update the database
        //if there is no value change in content value then does not change anything
        if(contentValues.size()==0)
        {
            return 0;
        }
        // Otherwise, get writeable database to update the data
        SQLiteDatabase db=mDbHelpers.getWritableDatabase();
        // Returns the number of database rows affected by the update statement
        return db.update(BankContract.BankEntry.TABLE_NAME,contentValues,selection,selectionArgs);
    }


    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        //row deleted
        int rowDeleted;
        //get writable database
        SQLiteDatabase db=mDbHelpers.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        switch (match)
        {
            //FOR ALL TABLE ROW
            case BANKS:
                // Delete all rows that match the selection and selection args
                rowDeleted=db.delete(BankContract.BankEntry.TABLE_NAME,selection,selectionArgs);
                break;
            //FOR SINGLE ROW OF TABLE
            case BANK_ID:
                // Delete a single row given by the ID in the URI
                selection= BankContract.BankEntry._Id+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowDeleted=db.delete(BankContract.BankEntry.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for"+uri);
        }
        if(rowDeleted!=0)
        {
            //notify about if any change occur(in local term it is just liking sound)
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

}
