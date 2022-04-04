package com.korniienko.opti.ui.slideshow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.korniienko.opti.WalletItem;
import com.korniienko.opti.WalletReaderDBHelper;
import com.korniienko.opti.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        WalletReaderDBHelper walletReaderDBHelper = new WalletReaderDBHelper(getActivity());
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

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}