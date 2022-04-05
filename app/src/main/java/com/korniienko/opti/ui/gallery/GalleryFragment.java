package com.korniienko.opti.ui.gallery;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.korniienko.opti.R;
import com.korniienko.opti.WalletReaderDBHelper;
import com.korniienko.opti.databinding.FragmentGalleryBinding;
import com.ozcanalasalvar.library.utils.DateUtils;
import com.ozcanalasalvar.library.view.datePicker.DatePicker;
import com.ozcanalasalvar.library.view.popup.DatePickerPopup;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    WalletReaderDBHelper walletReaderDBHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        String[] data = {"Расход", "Приход"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                data);
        adapter.setDropDownViewResource(R.layout.custom_drop_down);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) view).setTextColor(Color.BLACK);
                ((TextView) view).setTextSize(12f);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.spinner.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        binding.textView2.setText(calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH) + "." + calendar.get(Calendar.YEAR));
        binding.textView2.setOnClickListener(view ->  {
                String[] split = binding.textView2.getText().toString().split("\\.");
                DatePickerPopup datePickerPopup = new DatePickerPopup.Builder()
                        .from(getActivity())
                        .offset(3)
                        .pickerMode(DatePicker.MONTH_ON_FIRST)
                        .textSize(19)
                        .currentDate(DateUtils.getTimeMiles(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
                        .listener(new DatePickerPopup.OnDateSelectListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onDateSelected(com.ozcanalasalvar.library.view.datePicker.DatePicker dp, long date, int day, int month, int year) {
                                binding.textView2.setText(day + "." + (month+1) + "." + year);
                            }
                        })
                        .build();
                datePickerPopup.show();
            });

        walletReaderDBHelper = new WalletReaderDBHelper(getActivity());
        binding.button.setOnClickListener(view ->
        {
            ContentValues cv = new ContentValues();
            SQLiteDatabase db = walletReaderDBHelper.getWritableDatabase();

            if(binding.editTextTextPersonName.getText().toString().length() > 0 &&
            binding.spinner.getSelectedItemPosition() >= 0 &&
            binding.editTextNumber.getText().toString().length() > 0) {
                cv.put("name", binding.editTextTextPersonName.getText().toString());
                cv.put("date", binding.textView2.getText().toString());
                int cost = Integer.parseInt(binding.editTextNumber.getText().toString());
                if(binding.spinner.getSelectedItemPosition() == 0)
                    cost -= (cost * 2);

                cv.put("cost", cost);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("wallets", null, cv);
                walletReaderDBHelper.close();
                Snackbar.make(view, "Success", Snackbar.LENGTH_SHORT).show();
            }
            else
            {
                Snackbar.make(view, "Error", Snackbar.LENGTH_SHORT).show();
            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}