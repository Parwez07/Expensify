package com.example.expensify.View.Activity.Fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensify.Adapter.DailyTransAdapter;
import com.example.expensify.Constant.helper;
import com.example.expensify.DB.TransactionViewModel;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment implements AddTransactionFrag.AddTransactionListiner {
    Calendar calendar;
    TextView currDate, tvIncomeAmount, tvExpenditureAmount, tvBalanceAmount, todayExpenses;
    ImageView ivNext, ivPrev;
    RecyclerView rvDaily;
    private TransactionViewModel transactionViewModel;
    DailyTransAdapter adapter;
    LinearLayout layout ;
    PieChart pieChart;
    List<PieEntry> pieEntryList;

    public static double balance;
    double[] income = {0.0};
    double[] expense = {0.0};

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvDaily = view.findViewById(R.id.rvDaily);
        currDate = view.findViewById(R.id.currDate);
        ivNext = view.findViewById(R.id.ivNext);
        ivPrev = view.findViewById(R.id.ivPrev);
        tvIncomeAmount = view.findViewById(R.id.tvIncomeAmount);
        tvExpenditureAmount = view.findViewById(R.id.tvExpenditureAmount);
        tvBalanceAmount = view.findViewById(R.id.tvBalanceAmount);
        todayExpenses = view.findViewById(R.id.todayExpense);
        pieChart = view.findViewById(R.id.chart);
        layout = view.findViewById(R.id.pieChart);
        calendar = Calendar.getInstance();

        pieEntryList = new ArrayList<>();
        helper.add();

        adapter = new DailyTransAdapter(getContext());
        rvDaily.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDaily.setAdapter(adapter);

        currDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.YEAR, datePicker.getYear());
                        updateDate();
                    }
                });
                datePickerDialog.show();
            }
        });

        transactionViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(TransactionViewModel.class);


        Log.d("week",Calendar.WEEK_OF_MONTH+" "+Calendar.WEEK_OF_YEAR+" "+Calendar.DAY_OF_WEEK_IN_MONTH+" "+Calendar.DAY_OF_WEEK_IN_MONTH);
        updateDate();

        balance = helper.Income - helper.Expenditure;
        tvIncomeAmount.setText(helper.Income + "");
        tvExpenditureAmount.setText(helper.Expenditure + "");
        tvBalanceAmount.setText(balance + "");
        Log.d("name", balance + " " + helper.Expenditure + " " + helper.Income);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.DATE, 1);
                updateDate();
            }
        });
        ivPrev.setOnClickListener(c -> {
            calendar.add(Calendar.DATE, -1);
            updateDate();
        });


        return view;
    }

    public void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat amPm = new SimpleDateFormat("hh mm a");
        currDate.setText(dateFormat.format(calendar.getTime()));
        Log.d("datedc", calendar.getTime() + " frag");
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String yearMonth = yearMonthFormat.format(calendar.getTime());

        transactionViewModel.getTransactionsByDate(calendar.getTime()).observe(getActivity(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                // update the RecyclerView
                Log.d("trans", "Size: " + transactions.size() + " " + calendar.getTime());
                adapter.setTransactionList(transactions);

            }
        });


        transactionViewModel.getIncomeSum(yearMonth).observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d("sum", aDouble + " adouble");
                if (aDouble != null) {
                    income[0] = aDouble;
                    tvIncomeAmount.setText(aDouble.toString());
                    calculateBalance();
                }
                else{
                    income[0]=0.0;
                    tvIncomeAmount.setText(0.0+"");
                    calculateBalance();
                }
            }
        });


        transactionViewModel.getExpenditureSum(yearMonth).observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d("expense", aDouble + " ");
                if (aDouble != null) {
                    expense[0] = aDouble;
                    tvExpenditureAmount.setText(aDouble.toString());
                    calculateBalance();
                }else{
                    expense[0]=0.0;
                    tvExpenditureAmount.setText(0.0+"");
                    calculateBalance();
                }
            }
        });
        transactionViewModel.getSumByDate(calendar.getTime()).observe(getActivity(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                Log.d("texp", aDouble + "");
                if (aDouble != null) {
                    todayExpenses.setText(aDouble.toString());
                } else {
                    todayExpenses.setText("0.0");
                }
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

    private void calculateBalance() {

        double balance = income[0] - expense[0];
        double temp = Double.parseDouble(tvIncomeAmount.getText().toString())-Double.parseDouble(tvExpenditureAmount.getText().toString());
        Log.d("balance", balance + " " + income[0] + " " + expense[0]+" temp "+temp);
        tvBalanceAmount.setText(String.valueOf(temp));
        chartSetUp();
    }

    private void chartSetUp() {

        pieEntryList.clear();
        pieEntryList.add(new PieEntry(Float.parseFloat(tvExpenditureAmount.getText().toString()), "Expense"));
        pieEntryList.add(new PieEntry(Float.parseFloat(tvBalanceAmount.getText().toString()), "Remaining"));
        Log.d("chart", pieEntryList.size() + " size ");

        if(pieEntryList.get(0).getValue()==0.0 && pieEntryList.get(1).getValue()==0.0){
            layout.setVisibility(View.GONE);
            return;
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "PieChart");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(15f);
        pieDataSet.setValueTextSize(12f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieData.isHighlightEnabled();
        pieChart.invalidate();

        layout.setVisibility(View.VISIBLE);

    }


    @Override
    public void onSaveButtonClicked(Transaction newTransaction) {
        Log.d("trans", "in home frag");
        transactionViewModel.insert(newTransaction);
    }
}