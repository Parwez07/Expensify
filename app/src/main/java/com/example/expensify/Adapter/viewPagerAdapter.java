package com.example.expensify.Adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.expensify.View.Activity.Fragment.DailyFragment;
import com.example.expensify.View.Activity.Fragment.MonthlyFragment;
import com.example.expensify.View.Activity.Fragment.WeeklyFragment;

public class viewPagerAdapter extends FragmentStateAdapter {
    private ViewPager2 viewPager ;
    public viewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        Fragment fragment;
        switch (position){
            case 0:
                Log.d("create","creating daily ");
                fragment = DailyFragment.newInstance();
                break;
            case 1:
                fragment = new WeeklyFragment();
                break;
            case 2:
                fragment = new MonthlyFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
