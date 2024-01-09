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
import android.widget.TextView;

import com.example.expensify.Adapter.HistoryTransactionAdapter;
import com.example.expensify.DB.TransactionViewModel;
import com.example.expensify.Model.SharedViewModel;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MonthlyFragment extends Fragment  {

    RecyclerView rvMonthly;
    private TransactionViewModel transactionViewModel;
    HistoryTransactionAdapter historyTransactionAdapter;
    Calendar calendar = Calendar.getInstance();
    SharedViewModel sharedViewModel;


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

        rvMonthly = view.findViewById(R.id.rvMonthly);

        sharedViewModel = new ViewModelProvider(getParentFragment()).get(SharedViewModel.class);

        transactionViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(TransactionViewModel.class);


        historyTransactionAdapter = new HistoryTransactionAdapter(getContext());
        rvMonthly.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMonthly.setAdapter(historyTransactionAdapter);
        sharedViewModel.getSelectedDate().observe(getViewLifecycleOwner(), new Observer<Date>() {
            @Override
            public void onChanged(Date date) {
                Log.d("viewDate", date + "");
                updateMonthly(date);
            }
        });

        return view;
    }
    void updateMonthly(Date date){
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String yearMonth = yearMonthFormat.format(date);
        transactionViewModel.getTransactionsByMonth(yearMonth).observe(getActivity(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                historyTransactionAdapter.setTransactionList(transactions);
            }
        });

    }
}