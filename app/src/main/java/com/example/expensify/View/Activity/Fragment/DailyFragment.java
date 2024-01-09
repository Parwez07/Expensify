package com.example.expensify.View.Activity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensify.Adapter.HistoryTransactionAdapter;
import com.example.expensify.DB.TransactionViewModel;
import com.example.expensify.Model.SharedViewModel;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DailyFragment extends Fragment {


    RecyclerView rvDaily;
    private TransactionViewModel transactionViewModel;
    HistoryTransactionAdapter historyTransactionAdapter;
    Calendar calendar = Calendar.getInstance();
    SharedViewModel sharedViewModel;

    public DailyFragment() {
        Log.d("daily", "daily calling 1");
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


        sharedViewModel = new ViewModelProvider(getParentFragment()).get(SharedViewModel.class);

        transactionViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(TransactionViewModel.class);


        historyTransactionAdapter = new HistoryTransactionAdapter(getContext());
        rvDaily.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaily.setAdapter(historyTransactionAdapter);
        sharedViewModel.getSelectedDate().observe(getViewLifecycleOwner(), new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                Log.d("viewDate", date + "");
                updateDate(date);
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


}