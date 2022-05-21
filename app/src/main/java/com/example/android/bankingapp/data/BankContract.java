package com.example.android.bankingapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BankContract {
    BankContract()
    {
    }
    public static final String CONTENT_AUTHORITY="com.example.android.bankingapp";
    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+CONTENT_AUTHORITY);
    public static  final String PATH_SET="bankingapp";
    public static  final String PATH_SET_TRANS="transfermoney";
    public static final class BankEntry implements BaseColumns {

        /**Content uri for referring to table*/
        public static final Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_SET);

        /**Table name and column name */
        public static final String TABLE_NAME="bankingapp";
        public static final String _Id=BaseColumns._ID;
        public static final String COLUMN_BANK_PEOPLE_NAME="name";
        public static final String COLUMN_BANK_PEOPLE_EMAIL="email";
        public static final String COLUMN_BANK_PEOPLE_MOBILE_NUMBER="mobile";
        public static final String COLUMN_ACCOUNT_NUMBER="account_no";
        public static final String COLUMN_IFSC_NUMBER="ifsc_no";
        public static final String COLUMN_TOTAL_BALANCE="net_balance";

        /**Content uri for referring to table*/
        public static final Uri CONTENT_URI2=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_SET_TRANS);
        //For ReCORD tABLE
        public static final String TRANSFER_TABLE_NAME="transfermoney";
        public static final String _2Id=BaseColumns._ID;
        public static final String COLUMN_FROM="fromaccount";
        public static final String COLUMN_TO="toaccount";
        public static final String COLUMN_TRANSFER_MONEY="money";




    }
}
