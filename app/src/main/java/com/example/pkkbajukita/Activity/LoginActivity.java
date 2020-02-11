package com.example.pkkbajukita.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pkkbajukita.API.RequestInterface;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.Value.ValueLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    EditText et_username, et_password;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        et_username = findViewById(R.id.log_edtEmail);
        et_password = findViewById(R.id.log_edtPass);
        btn_login = findViewById(R.id.log_btnLogin);
        txtRegister = findViewById(R.id.log_txtRegister);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                finish();
                startActivity(intent);
            }
        });

    }

    private void login() {

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://smvg.000webhostapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface api = retrofit.create(RequestInterface.class);
        Call<ValueLogin> call = api.login(username, password);
        call.enqueue(new Callback<ValueLogin>() {
            @Override
            public void onResponse(Call<ValueLogin> call, Response<ValueLogin> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                String userid = response.body().getUserid();
                String username = response.body().getUsername();
                String nama_lengkap = response.body().getNama_lengkap();
                String no_wa = response.body().getNo_wa();
                String img_profile = response.body().getImg_profile();

                if (value.equals("0")) {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                } else {
                  //  Toast.makeText(LoginActivity.this, img_profile, Toast.LENGTH_SHORT).show();

                    Intent i =  new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

                    SharedPreferences.Editor status = getSharedPreferences("status", MODE_PRIVATE).edit();
                    status.putString("status", "1");
                    status.apply();


                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor.putString("userid",userid);
                    editor.putString("username", username);
                    editor.putString("nama_lengkap", nama_lengkap);
                    editor.putString("no_wa", no_wa);
                    editor.putString("img_profile", img_profile);
                    editor.apply();

                }
            }

            @Override
            public void onFailure(Call<ValueLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
