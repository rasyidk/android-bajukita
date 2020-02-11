package com.example.pkkbajukita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueRespon;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    public static final String URL = "http://smvg.000webhostapp.com/";

    ProgressDialog pDialog;
    Button btn_register;
    EditText txt_namalengkap, txt_nowa, txt_username, txt_password, txt_confirm_password;
    Intent intent;
    TextView txt_sudahpunya, txt_masuk;
    AwesomeText ImgShowHidePassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    boolean pwd_status = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_sudahpunya = (TextView) findViewById(R.id.txt_sudahpunya);
        txt_masuk =(TextView) findViewById(R.id.txt_masuk);
        btn_register = (Button) findViewById(R.id.btn_register);
        txt_namalengkap = (EditText) findViewById(R.id.txt_namalengkap);
        txt_nowa = (EditText)  findViewById(R.id.txt_nowa);
        txt_username = (EditText) findViewById(R.id.log_edtEmail);
        txt_password = (EditText) findViewById(R.id.txt_password);
        txt_confirm_password = (EditText) findViewById(R.id.txt_confirm_password);
        ImgShowHidePassword = (AwesomeText)findViewById(R.id.ImgShowPassword);

        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    txt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    txt_confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    txt_password.setSelection(txt_password.length());
                    txt_confirm_password.setSelection(txt_confirm_password.length());
                } else {
                    txt_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    txt_confirm_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    txt_password.setSelection(txt_password.length());
                    txt_confirm_password.setSelection(txt_confirm_password.length());
                }
            }
        });

        txt_sudahpunya.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        txt_masuk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(RegisterActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }



    private void register() {
        String nama_lengkap = txt_namalengkap.getText().toString();
        String no_wa = txt_nowa.getText().toString();
        String username = txt_username.getText().toString();
        String password = txt_password.getText().toString();

        String confirm_password = txt_confirm_password.getText().toString();


        if (nama_lengkap.equals("") || no_wa.equals("") || username.equals("") || password.equals("") || confirm_password.equals("")) {
            Toast.makeText(getApplicationContext(),"Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
        }else if(password.equals(confirm_password)){

            if (username.trim().matches(emailPattern)){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestInterface api = retrofit.create(RequestInterface.class);
                Call<ValueRespon> call = api.register(nama_lengkap ,no_wa ,username, password, confirm_password);
                call.enqueue(new Callback<ValueRespon>() {
                    @Override
                    public void onResponse(Call<ValueRespon> call, Response<ValueRespon> response) {
                        String value = response.body().getValue();
                        String message = response.body().getMessage();

                        if (value.equals("0")) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }


                        else {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ValueRespon> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(getApplicationContext(),"IMEL kolom harus diisi", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"PW e", Toast.LENGTH_LONG).show();
        }



    }

}
