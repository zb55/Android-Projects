package com.example.keep;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class Pager2Adapter_home extends FragmentStateAdapter {

    private List<Fragment> lf = new ArrayList<Fragment>();
    public Pager2Adapter_home(List<Fragment> lf, @NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.lf = lf;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return lf.get(position);
    }

    @Override
    public int getItemCount() {
        return lf.size();
    }
}
