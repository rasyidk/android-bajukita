package com.example.pkkbajukita.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Dimas Maulana on 5/26/17.
 * Email : araymaulana66@gmail.com
 */

import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueBarang;

public class AdapterBarang extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<ValueBarang> moviesList;

    public AdapterBarang(List<ValueBarang> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public int getItemViewType(int position) {
        if (moviesList.get(position).getKet_barang().toLowerCase().contains("fak")) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.row_barang, parent, false);
            return new ViewHolderOne(view);
        }

        view = layoutInflater.inflate(R.layout.row_barang, parent, false);
        return new ViewHolderTwo(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        

        if (moviesList.get(position).getKet_barang().toLowerCase().contains("fak")) {
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.tv_judul.setText(moviesList.get(position).getNama_barang());
            viewHolderOne.tv_ket_barang.setText(moviesList.get(position).getKet_barang());
            viewHolderOne.tv_lokasi.setText(moviesList.get(position).getLokasi_barang());
            viewHolderOne.tv_wa.setText(moviesList.get(position).getNo_wa());

            String img = moviesList.get(position).getImg_barang();
            byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
            viewHolderOne.imageView.setImageBitmap(bitmap);
        }else {
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) holder;
            viewHolderTwo.tv_judul.setText(moviesList.get(position).getNama_barang());
            viewHolderTwo.tv_ket_barang.setText(moviesList.get(position).getKet_barang());
            viewHolderTwo.tv_lokasi.setText(moviesList.get(position).getLokasi_barang());
            viewHolderTwo.tv_wa.setText(moviesList.get(position).getNo_wa());

            String img = moviesList.get(position).getImg_barang();
            byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
            viewHolderTwo.imageView.setImageBitmap(bitmap);


            //Pindah
            viewHolderTwo.btn_hubungi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), moviesList.get(position).getNama_barang(), Toast.LENGTH_SHORT).show();

                    String judul =  moviesList.get(position).getNama_barang();
                    String number =  moviesList.get(position).getNo_wa();

                    String url = "https://api.whatsapp.com/send?phone=" + number + "&text=Permisi, Apakah " + judul + " masih tersedia? Terimakasih";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);

                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView tv_judul,tv_ket_barang,tv_lokasi, tv_wa;
        ImageView imageView;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_ket_barang = itemView.findViewById(R.id.tv_keterangan);
            tv_lokasi = itemView.findViewById(R.id.tv_lokasi);
            tv_wa = itemView.findViewById(R.id.tv_wa);
            imageView = itemView.findViewById(R.id.imagevw);

        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {

        TextView tv_judul,tv_ket_barang,tv_lokasi, tv_wa;
        ImageView imageView;
        Button btn_hubungi;
        public ViewHolderTwo(@NonNull View itemView) {
            super(itemView);
            tv_judul = itemView.findViewById(R.id.tv_judul);
            tv_ket_barang = itemView.findViewById(R.id.tv_keterangan);
            tv_lokasi = itemView.findViewById(R.id.tv_lokasi);
            tv_wa = itemView.findViewById(R.id.tv_wa);
            imageView = itemView.findViewById(R.id.imagevw);
            btn_hubungi = itemView.findViewById(R.id.btn_hubungi);
        }
    }


}
