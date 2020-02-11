package com.example.pkkbajukita.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueRespon;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileActivity extends AppCompatActivity {
    ImageView imgedtprofile, imgcentang;
    Uri mImageUri;
    EditText et_nama, et_nowa;
    public static final String URL = "http://retrofitbuos.000webhostapp.com/";
    MainActivity mainActivity;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imgedtprofile =  findViewById(R.id.img_profile_edit);
        imgcentang = findViewById(R.id.img_centang_edit);
        et_nama = findViewById(R.id.et_nama_profile_edit);
        et_nowa = findViewById(R.id.et_nowa_profile_edit);

        mainActivity = new MainActivity();

        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama_lengkap","");
        String no_wa = sharedPreferences.getString("no_wa","");
        String img = sharedPreferences.getString("img_profile","");

        userid = sharedPreferences.getString("userid","");
        et_nama.setText(nama);
        et_nowa.setText(no_wa);
        byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

        imgedtprofile.setImageBitmap(bitmap);

        imgcentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void updateProfile() {



        String nama = et_nama.getText().toString();
        String nowa = et_nowa.getText().toString();


        final String imgstr;


        Bitmap image = ((BitmapDrawable) imgedtprofile.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imgstr = Base64.encodeToString(byteArray, Base64.DEFAULT);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface api = retrofit.create(RequestInterface.class);
        Call<ValueRespon> call = api.updateprofile(imgstr,nama,nowa,userid);
        call.enqueue(new Callback<ValueRespon>() {
            @Override
            public void onResponse(Call<ValueRespon> call, Response<ValueRespon> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")) {
                    Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor.putString("img_profile", imgstr);
                    editor.apply();


                    mainActivity.changeImg();
                    Intent i = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueRespon> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void updateprofile(View v){

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
                imgedtprofile.setImageURI(mImageUri);

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception e = result.getError();
                Toast.makeText(getApplicationContext(),"Pos"+e,Toast.LENGTH_SHORT).show();

            }
        }
    }
}
