package com.example.pkkbajukita.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pkkbajukita.R;
import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.Value.ValueRespon;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DonasiActivity extends AppCompatActivity {


    EditText et_nm_brg, et_ket, et_lokasi;
    ImageView img_brg;
    Button btn_dns;
    Uri mImageUri;
    public static final String URL = "http://retrofitbuos.000webhostapp.com/";
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        et_nm_brg = findViewById(R.id.et_nmbrg_dns);
        et_ket = findViewById(R.id.et_ket_dns);
        et_lokasi = findViewById(R.id.et_lokasi_dns);
        img_brg = findViewById(R.id.img_donasi_dns);
        btn_dns = findViewById(R.id.btn_donasi_dns);
        mainActivity =  new MainActivity();


        btn_dns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_dns();
            }
        });

    }

    public void chooseFile(View v){

        CropImage.activity()
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result= CropImage.getActivityResult(data);
            if (resultCode ==  RESULT_OK){
                mImageUri = result.getUri();
                img_brg.setImageURI(mImageUri);

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(getApplicationContext(),"Pos"+e,Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void btn_dns() {

        String nama_brg, ket_brg,lokasi_brg, img_brg_str;

        nama_brg = et_nm_brg.getText().toString();
        ket_brg = et_ket.getText().toString();
        lokasi_brg = et_lokasi.getText().toString();

        Bitmap image = ((BitmapDrawable) img_brg.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        img_brg_str = Base64.encodeToString(byteArray, Base64.DEFAULT);


        //Upload data
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface api = retrofit.create(RequestInterface.class);
        Call<ValueRespon> call = api.uploadBrg(nama_brg,ket_brg,lokasi_brg,img_brg_str);
        call.enqueue(new Callback<ValueRespon>() {
            @Override
            public void onResponse(Call<ValueRespon> call, Response<ValueRespon> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(DonasiActivity.this, message, Toast.LENGTH_SHORT).show();
                    mainActivity.loadJSON();
                } else {
                    Toast.makeText(DonasiActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueRespon> call, Throwable t) {
                Toast.makeText(DonasiActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
