package com.example.pkkbajukita.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.Activity.DonasiActivity;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueRespon;
import com.example.pkkbajukita.Value.ValueUnggahan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterTab1_unggahan extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<ValueUnggahan> unggahanList;
    public static final String URL = "http://retrofitbuos.000webhostapp.com/";

    public AdapterTab1_unggahan(List<ValueUnggahan> unggahanList) {
        this.unggahanList = unggahanList;
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


            view = layoutInflater.inflate(R.layout.row_unggahan, parent, false);
            return new ViewHolderOne(view);




    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            AdapterTab1_unggahan.ViewHolderOne viewHolderOne = (AdapterTab1_unggahan.ViewHolderOne) holder;
            viewHolderOne.tv_judul.setText(unggahanList.get(position).getNama_barang());
            viewHolderOne.tv_keterangan.setText(unggahanList.get(position).getKet_barang());


            String img = unggahanList.get(position).getImg_barang();
            byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);
            viewHolderOne.imageView.setImageBitmap(bitmap);

            viewHolderOne.btnselesai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RequestInterface api = retrofit.create(RequestInterface.class);

                    String userid = "1";
                    String idbarang = unggahanList.get(position).getId_barang();
                    Toast.makeText(v.getContext(), idbarang, Toast.LENGTH_SHORT).show();

                    Call<ValueRespon> call = api.updateproses(userid, idbarang);
                    call.enqueue(new Callback<ValueRespon>() {
                        @Override
                        public void onResponse(Call<ValueRespon> call, Response<ValueRespon> response) {
                            String value = response.body().getValue();
                            String message = response.body().getMessage();

                            if (value.equals("1")) {
                                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ValueRespon> call, Throwable t) {
                            Toast.makeText(v.getContext(), "Jaringan Error!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });




    }

    @Override
    public int getItemCount() {
        return unggahanList.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        TextView tv_judul,tv_keterangan;
        ImageView imageView;
        Button btnselesai;

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);

            tv_judul = itemView.findViewById(R.id.tv_judul_tab1);
            tv_keterangan = itemView.findViewById(R.id.tv_ket_tab1);
            imageView = itemView.findViewById(R.id.img_tab1);
            btnselesai = itemView.findViewById(R.id.btn_selesai_tab1);

        }
    }




}
