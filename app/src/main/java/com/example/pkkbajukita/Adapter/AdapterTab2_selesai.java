package com.example.pkkbajukita.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueSelesai;
import com.example.pkkbajukita.Value.ValueUnggahan;

import java.util.List;

public class AdapterTab2_selesai extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<ValueSelesai> selesaiList;

    public AdapterTab2_selesai(List<ValueSelesai> selesaiList) {
        this.selesaiList = selesaiList;
    }

    @Override
    public int getItemViewType(int position) {

        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;


        view = layoutInflater.inflate(R.layout.row_selesai, parent, false);
        return new ViewHolderOne(view);




    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {




        AdapterTab2_selesai.ViewHolderOne viewHolderOne = (AdapterTab2_selesai.ViewHolderOne) holder;
        viewHolderOne.tv_judul.setText(selesaiList.get(position).getNama_barang());


        String img = selesaiList.get(position).getImg_barang();
        byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
        viewHolderOne.imageView.setImageBitmap(bitmap);




    }

    @Override
    public int getItemCount() {
        return selesaiList.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView tv_judul;
        ImageView imageView;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);

            tv_judul = itemView.findViewById(R.id.tv_judul_tab2);
            imageView = itemView.findViewById(R.id.img_tab2);

        }
    }

}
