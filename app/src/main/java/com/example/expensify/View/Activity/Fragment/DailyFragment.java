package com.example.expensify.View.Activity.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.expensify.Adapter.HistoryTransactionAdapter;
import com.example.expensify.DB.TransactionViewModel;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DailyFragment extends Fragment implements HistoryFragment.DateChangeListener {


    RecyclerView rvDaily;
    private TransactionViewModel transactionViewModel;
    HistoryTransactionAdapter historyTransactionAdapter;
    Calendar calendar = Calendar.getInstance() ;

    public DailyFragment() {
        Log.d("daily","daily calling 1");
        // Required empty public constructor
    }

    public static Fragment newInstance(){
        Log.d("daily","daily calling 2 ");
        return new DailyFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_daily, container, false);
        rvDaily =  view.findViewById(R.id.rvDaily);



        transactionViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(TransactionViewModel.class);


        historyTransactionAdapter = new HistoryTransactionAdapter(getContext());
        rvDaily.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaily.setAdapter(historyTransactionAdapter);

        HistoryFragment historyFragment = (HistoryFragment) requireParentFragment();

        historyFragment.setDateChangeListener(new HistoryFragment.DateChangeListener() {
            @Override
            public void onDateChanged(Date newDate) {
                Log.d("prevHistory","on click "+newDate +" ");
                updateDate(newDate);
            }
        });
        return view;
    }

    public void updateDate(Date currDate) {

        Log.d("datedc", currDate+ " frag");
        transactionViewModel.getTransactionsByDate(currDate).observe(getActivity(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                // update the RecyclerView
                Log.d("transHistory", "Size: " + transactions.size() + " " +currDate);
                historyTransactionAdapter.setTransactionList(transactions);
            }
        });


//        long mil = new Date().getTime();
//        Date date = new Date(mil);
//        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
//        SimpleDateFormat format1 = new SimpleDateFormat("hh mm a", Locale.getDefault());
//        String d = format.format(date.getTime());
//        String time = format1.format(date.getTime());
//        Log.d("time22", "date " + d + " time " + time);
    }

    @Override
    public void onDateChanged(Date newDate) {
        updateDate(newDate);
    }


}