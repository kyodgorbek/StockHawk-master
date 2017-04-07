package com.udacity.stockhawk.widget;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.data.PrefUtils;
import com.udacity.stockhawk.ui.ChartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.udacity.stockhawk.R.id.symbol;
import static com.udacity.stockhawk.ui.ChartActivity.EXTRA_SYMBOL;

/**
 * Created by yodgorbekkomilov on 4/2/17.
 */

public class StockWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockWidgetViewFactory(getApplicationContext());
    }


    private class StockWidgetViewFactory implements RemoteViewsFactory {
        private final Context mApplicationContext;
        private List<ContentValues> mCvList = new ArrayList<>();

        public StockWidgetViewFactory(Context applicationContext) {
            mApplicationContext = applicationContext;
        }


        @Override
        public void onCreate() {
            ContentResolver contentResolver = mApplicationContext.getContentResolver();
             mCvList.clear();
// are you here

                Cursor cursor = contentResolver.query
                        (Contract.Quote.URI, null,
                                null, null, null
                        );
              while(cursor.moveToFirst()){
              String symbol =  cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));
                float price = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE));

                float absChange = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
                float percentChange = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));


                ContentValues cv = new ContentValues();

                cv.put(Contract.Quote.COLUMN_SYMBOL, symbol);
                cv.put(Contract.Quote.COLUMN_PRICE, price);
                cv.put(Contract.Quote.COLUMN_ABSOLUTE_CHANGE, absChange);
                cv.put(Contract.Quote.COLUMN_PERCENTAGE_CHANGE, percentChange);

                mCvList.add(cv);




            }

            cursor.close();

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mCvList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

           ContentValues cv  =  mCvList.get(position);
           RemoteViews remoteViews = new RemoteViews(mApplicationContext.getPackageName(),
                   R.layout.list_item_quote);

            //start intent
            Intent graphIntent = new Intent(mApplicationContext, ChartActivity.class);
            graphIntent.putExtra(
                    EXTRA_SYMBOL,symbol
            );

            remoteViews.setTextViewText(symbol, cv.getAsString(Contract.Quote.COLUMN_SYMBOL));

            // here you have to tell at the Device to fire the GraphActivity
            remoteViews.setOnClickFillInIntent(R.id.item_view, graphIntent);


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
