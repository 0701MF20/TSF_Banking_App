package com.example.android.bankingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bankingapp.data.BankContract;

class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankAdapterViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    /*
     *Defined an interface to handle clicks on items within this Adapter.Constructor
     * of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface.
     */
    final private BankAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface BankAdapterOnClickHandler {
        void onClick(int sno);
    }

  //  private boolean mUseTodayLayout;

    private Cursor mCursor;

    /**
     * Creates a BankAdapter.
     */
    public BankAdapter(@NonNull Context context, BankAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @Override
    public BankAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

      int layoutId=R.layout.individual_user;
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
     //  view.setFocusable(true);
        return new BankAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     */
    @Override
    public void onBindViewHolder(BankAdapterViewHolder bankAdapterViewHolder, int position) {
        mCursor.moveToPosition(position);

       // int viewType = getItemViewType(position);
/******************Name*********************/
String name=mCursor.getString(AllUserActivity.INDEX_PEOPLE_NAME);
bankAdapterViewHolder.nameView.setText(name);
bankAdapterViewHolder.nameView.setContentDescription(name);


/******************Account Nos*********************/
        int accountNos=mCursor.getInt(AllUserActivity.INDEX_ACCOUNT_NOS);
        bankAdapterViewHolder.accountView.setText(accountNos+"");
        bankAdapterViewHolder.accountView.setContentDescription(accountNos+"");

        /******************IFSC Nos*********************/
        int ifscNos=mCursor.getInt(AllUserActivity.INDEX_IFSC_CODE);
        bankAdapterViewHolder.ifscView.setText(ifscNos+"");
        bankAdapterViewHolder.ifscView.setContentDescription(ifscNos+"");

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     */
    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    /**
     * Swaps the cursor used by the ForecastAdapter for its weather data. This method is called by
     * MainActivity after a load has finished, as well as when the Loader responsible for loading
     * the weather data is reset. When this method is called, we assume we have a completely new
     * set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     */

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    /**
     * A ViewHolder is a required part of the pattern for RecyclerViews. It mostly behaves as
     * a cache of the child views for a forecast item. It's also a convenient place to set an
     * OnClickListener, since it has access to the adapter and the views.
     */
    class BankAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView ifscView;
        final TextView nameView;
        final TextView accountView;

        BankAdapterViewHolder(View view) {
            super(view);
            ifscView=(TextView)view.findViewById(R.id.IFSCId);
            nameView=(TextView)view.findViewById(R.id.nameId);
            accountView=(TextView)view.findViewById(R.id.AccountId);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int sNumber  = mCursor.getInt(AllUserActivity.INDEX_SNO);
            mClickHandler.onClick(sNumber);
        }
    }
}