package com.korniienko.opti.ui.slideshow;

import android.app.Activity;
import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.korniienko.opti.WalletItem;
import com.korniienko.opti.WalletReaderDBHelper;

import java.util.ArrayList;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    MutableLiveData<ArrayList<WalletItem>> walletItemMutableLiveData;
    Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Сделки");
    }

    public MutableLiveData<ArrayList<WalletItem>> getWalletItemMutableLiveData() {
        WalletReaderDBHelper walletReaderDBHelper = new WalletReaderDBHelper(activity);
        SQLiteDatabase db = walletReaderDBHelper.getWritableDatabase();
        Cursor c = db.query("wallets", null, null, null, null, null, null);
        ArrayList<WalletItem> walletItems = new ArrayList<>();

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int costlColIndex = c.getColumnIndex("cost");
            int datelColIndex = c.getColumnIndex("date");

            do {
                walletItems.add(new WalletItem(c.getInt(idColIndex), c.getString(nameColIndex), c.getInt(costlColIndex), c.getString(datelColIndex)));
            } while (c.moveToNext());
        } else
            Log.d("LOG_TAG", "0 rows");
        c.close();
        walletItemMutableLiveData = new MutableLiveData<>();
        this.walletItemMutableLiveData.setValue(walletItems);

        return walletItemMutableLiveData;
    }

    public LiveData<String> getText() {
        return mText;
    }
}