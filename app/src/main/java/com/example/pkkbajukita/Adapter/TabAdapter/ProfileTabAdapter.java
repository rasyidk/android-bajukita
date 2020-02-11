package com.example.pkkbajukita.Adapter.TabAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pkkbajukita.TAB.Tab1_unggahan;
import com.example.pkkbajukita.TAB.Tab2_Selesai;

public class ProfileTabAdapter extends FragmentStateAdapter {


    public ProfileTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Tab1_unggahan();
            default:
                return new Tab2_Selesai();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
