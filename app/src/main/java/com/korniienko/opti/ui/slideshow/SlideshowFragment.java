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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.korniienko.opti.R;
import com.korniienko.opti.WalletItem;
import com.korniienko.opti.WalletReaderDBHelper;
import com.korniienko.opti.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.Observable;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    WalletAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        slideshowViewModel.setActivity(getActivity());

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        binding.imageButton.setOnClickListener(v ->
        {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_nav_slideshow_to_nav_gallery);
        });

        slideshowViewModel.getWalletItemMutableLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<WalletItem>>() {
            @Override
            public void onChanged(ArrayList<WalletItem> walletItems) {
                adapter = new WalletAdapter(walletItems, getActivity());
                LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
                linearLayout.setOrientation(RecyclerView.VERTICAL);
                binding.recycler.setLayoutManager(linearLayout);
                binding.recycler.setAdapter(adapter);
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