package com.example.expensify.View.Activity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensify.R;

import java.util.Date;


public class MonthlyFragment extends Fragment implements HistoryFragment.DateChangeListener {


    public MonthlyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);

        return view;
    }

    @Override
    public void onDateChanged(Date newDate) {

    }
}