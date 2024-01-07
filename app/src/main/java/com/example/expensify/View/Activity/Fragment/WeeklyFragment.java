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
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class WeeklyFragment extends Fragment implements HistoryFragment.DateChangeListener {

    RecyclerView rvWeekly;
    private TransactionViewModel transactionViewModel;
    HistoryTransactionAdapter historyTransactionAdapter;
    Calendar calendar = Calendar.getInstance();

    public WeeklyFragment() {
        Log.d("daily","weekly calling ");
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        rvWeekly = view.findViewById(R.id.rvWeekly);

        transactionViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                                .create(TransactionViewModel.class);

        historyTransactionAdapter = new HistoryTransactionAdapter(getContext());
        rvWeekly.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWeekly.setAdapter(historyTransactionAdapter);

        HistoryFragment historyFragment = (HistoryFragment) getParentFragment();
        historyFragment.setDateChangeListener(new HistoryFragment.DateChangeListener() {
            @Override
            public void onDateChanged(Date newDate) {
                updateWeekly(newDate);
            }
        });

        return view;
    }

    public void updateWeekly(Date currDate) {

        Calendar selectedDate = Calendar.getInstance();
        selectedDate.setTime(currDate);

        Date sd = selectedDate.getTime();
        int day = selectedDate.get(Calendar.DAY_OF_WEEK) - 1;
        selectedDate.add(selectedDate.DATE, -day);
        Date firstDateOfWeek = selectedDate.getTime();

        selectedDate.add(selectedDate.DATE, 6);
        Date lastDateOfWeek = selectedDate.getTime();

        transactionViewModel.getTransactionsByWeek(firstDateOfWeek,lastDateOfWeek).observe(getActivity(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                Log.d("weeklyTrans", "Size: " + transactions.size() + " " +currDate);
                historyTransactionAdapter.setTransactionList(transactions);
            }
        });
    }

    @Override
    public void onDateChanged(Date newDate) {
        updateWeekly(newDate);
    }
}