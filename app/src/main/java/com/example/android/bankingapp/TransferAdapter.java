package com.example.android.bankingapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransferAdapter extends RecyclerView.Adapter<TransferAdapter.TransferAdapterViewHolder> {

    /* The context we use to utility methods, app resources and layout inflaters */
    private final Context mContext;

    /*
     *Defined an interface to handle clicks on items within this Adapter.Constructor
     * of our ForecastAdapter, we receive an instance of a class that has implemented
     * said interface.
     */
   // final private TransferAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    /*public interface BankAdapterOnClickHandler {
        void onClick(int sno);
    }*/

    //  private boolean mUseTodayLayout;

    private Cursor mCursor;

    /**
     * Creates a BankAdapter.
     */
    public TransferAdapter(@NonNull Context context) {
        mContext = context;
      //  mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     */
    @Override
    public TransferAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        int layoutId=R.layout.activity_transferitem;
        View view = LayoutInflater.from(mContext).inflate(layoutId, viewGroup, false);
        //  view.setFocusable(true);
        return new TransferAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     */
    @Override
    public void onBindViewHolder(TransferAdapterViewHolder transferAdapterViewHolder, int position) {
        mCursor.moveToPosition(position);

/******************TO ACCOUNT*********************/
       int to_acc=mCursor.getInt(TransferActivity.INDEX_COLUMN_TO);
        transferAdapterViewHolder.toAccView.setText(to_acc+"");
        transferAdapterViewHolder.toAccView.setContentDescription(to_acc+"");


/******************FROM ACCOUNT*********************/
        int from_account=mCursor.getInt(TransferActivity.INDEX_COLUMN_FROM);
        transferAdapterViewHolder.fromAccView.setText(from_account+"");
        transferAdapterViewHolder.fromAccView.setContentDescription(from_account+"");

        /******************Transfer Money*********************/
        int transfer=mCursor.getInt(TransferActivity.INDEX_COLUMN_TRANSFER_MONEY);
        //     Log.e("BankAdapter","IFSC no:::"+ifscNos+"  index IFSC"+AllUserActivity.INDEX_IFSC_CODE);
        transferAdapterViewHolder.transferAmountView.setText(transfer+"");
        transferAdapterViewHolder.transferAmountView.setContentDescription(transfer+"");

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
    class TransferAdapterViewHolder extends RecyclerView.ViewHolder  {

        final TextView toAccView;
        final TextView fromAccView;
        final TextView transferAmountView;

        TransferAdapterViewHolder(View view) {
            super(view);
            toAccView=(TextView)view.findViewById(R.id.to_account);
            fromAccView=(TextView)view.findViewById(R.id.from_account);
            transferAmountView=(TextView)view.findViewById(R.id.transfer_money);
        }

        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         */
    }
}