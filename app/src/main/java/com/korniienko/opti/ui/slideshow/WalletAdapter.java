package com.korniienko.opti.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.korniienko.opti.R;
import com.korniienko.opti.WalletItem;
import com.korniienko.opti.databinding.WalletItemBinding;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletAdapterWH>{

    ArrayList<WalletItem> walletItems;
    Activity activity;

    public WalletAdapter(ArrayList<WalletItem> walletItems, Activity activity) {
        this.walletItems = walletItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public WalletAdapterWH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        WalletItemBinding binding = WalletItemBinding.inflate(inflater, parent, false);
        return new WalletAdapter.WalletAdapterWH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapterWH holder, int position) {
        holder.setIsRecyclable(false);
        holder.bind(walletItems.get(position));
    }

    @Override
    public int getItemCount() {
       return walletItems.size();
    }


    public class WalletAdapterWH extends RecyclerView.ViewHolder {

        WalletItemBinding binding;

        public WalletAdapterWH(WalletItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        public void bind(WalletItem walletItem)
        {
            if(walletItem.getSum() < 0)
            {
                binding.img.setImageDrawable(activity.getDrawable(R.drawable.ic_bx_credit_card));
                binding.textCost.setTextColor(Color.parseColor("#E02B2B"));
                binding.textCost.setText(walletItem.getSum() + " ₴");
            }
            else
                binding.textCost.setText("+" + walletItem.getSum() + " ₴");
            binding.textName.setText(walletItem.getName());
            binding.textDate.setText(walletItem.getDate());

        }

    }
}
