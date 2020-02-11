package com.example.pkkbajukita.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pkkbajukita.Adapter.TabAdapter.ProfileTabAdapter;
import com.example.pkkbajukita.R;
import com.example.pkkbajukita.TAB.Tab1_unggahan;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileActivity extends AppCompatActivity {

    TextView tv_nama,tv_nowa, tv_logout;
    ImageView imageProfile;
    Tab1_unggahan tab1_unggahan;
    Button btnEdtPr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        SharedPreferences sharedPreferences = getSharedPreferences("user",MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama_lengkap","");
        String no_wa = sharedPreferences.getString("no_wa","");
        String img = sharedPreferences.getString("img_profile","");
        String userid = sharedPreferences.getString("userid","");

        Toast.makeText(getApplicationContext(),userid, Toast.LENGTH_SHORT).show();

        byte[] decodestring = Base64.decode(img,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodestring,0,decodestring.length);

        tv_nama = findViewById(R.id.tv_nama_profile);
        tv_nowa =  findViewById(R.id.tv_nowa_profile);
        tv_logout = findViewById(R.id.tv_logout);
        imageProfile = findViewById(R.id.img_profile);
        btnEdtPr =  findViewById(R.id.btn_edit_profile);



        tv_nama.setText(nama);
        tv_nowa.setText(no_wa);
        imageProfile.setImageBitmap(bitmap);



        btnEdtPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this , EditProfileActivity.class);
                startActivity(i);
            }
        });



        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        ViewPager2 viewPager2= findViewById(R.id.viewPager);
        viewPager2.setAdapter(new ProfileTabAdapter(this));
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText("Proses");
                        break;
                    }
                    case 1:{
                        tab.setText("Selesai");
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();
    }

    private void logout() {
        Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(i);
        SharedPreferences.Editor status = getSharedPreferences("status", MODE_PRIVATE).edit();
        status.putString("status", "0");
        status.apply();
    }
}
