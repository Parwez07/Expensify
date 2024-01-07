package com.example.expensify.View.Activity.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensify.Adapter.CategoryAdapter;
import com.example.expensify.Constant.helper;
import com.example.expensify.Model.Category;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddTransactionFrag extends BottomSheetDialogFragment {

    TextView btnIncome,btnExpense;
    EditText etDate,etAmount,etCatogery,etNotes;

    Button btnSave;
    Boolean isIncome = true;
    Date date;
    public AddTransactionFrag() {
        // Required empty public constructor
    }

    private AddTransactionListiner addTransactionListiner;
    public interface AddTransactionListiner {
        void onSaveButtonClicked(Transaction newTransaction);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        btnIncome = view.findViewById(R.id.tvIncome);
        btnExpense = view.findViewById(R.id.tvExpense);
        etAmount = view.findViewById(R.id.etAmount);
        etDate = view.findViewById(R.id.etDate);
        etCatogery = view.findViewById(R.id.etCategory);
        etNotes = view.findViewById(R.id.etNotes);
        btnSave = view.findViewById(R.id.btnSave);

        btnIncome.setOnClickListener(view1 -> {
            btnIncome.setBackground(getContext().getDrawable(R.drawable.income_selector));
            btnExpense.setBackground(getContext().getDrawable(R.drawable.default_selector));
            btnIncome.setTextColor(getContext().getColor(R.color.green));
            btnExpense.setTextColor(getContext().getColor(R.color.black));
            isIncome = true;
        });
        btnExpense.setOnClickListener(view1 -> {
            btnExpense.setBackground(getContext().getDrawable(R.drawable.expense_selector));
            btnIncome.setBackground(getContext().getDrawable(R.drawable.default_selector));
            btnExpense.setTextColor(getContext().getColor(R.color.red));
            btnIncome.setTextColor(getContext().getColor(R.color.black));
            isIncome = false;
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        calendar.set(Calendar.MONTH, datePicker.getMonth());
                        calendar.set(Calendar.YEAR, datePicker.getYear());

                        date = calendar.getTime();

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM YYYY");
                        String dateToShow = dateFormat.format(calendar.getTime());
                        etDate.setText(dateToShow);
                    }
                });
                datePickerDialog.show();
            }
        });
        etCatogery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customeLayout = LayoutInflater.from(getContext()).inflate(R.layout.list_dialoge, null);
                AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme).create();
                dialog.setTitle("Categories");
                dialog.setView(customeLayout);
                RecyclerView rv = customeLayout.findViewById(R.id.rvCategory);

                CategoryAdapter adapter;
                helper.add();
                if (isIncome) {
                    adapter = new CategoryAdapter(getContext(), helper.category.get("Income"), new CategoryAdapter.onCategoryClickListener() {
                        @Override
                        public void onCategoryClicked(Category c) {
                            etCatogery.setText(c.getCategoryName());
                            dialog.dismiss();
                        }
                    });
                } else {
                    adapter = new CategoryAdapter(getContext(), helper.category.get("Expense"), new CategoryAdapter.onCategoryClickListener() {
                        @Override
                        public void onCategoryClicked(Category c) {
                            etCatogery.setText(c.getCategoryName());
                            dialog.dismiss();
                        }
                    });
                }
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                rv.setLayoutManager(gridLayoutManager);
                rv.setAdapter(adapter);
                dialog.show();


            }
        });



            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(date!=null && Integer.parseInt(etAmount.getText().toString())!=0){
                        Transaction transaction = addTransaction();
                        addTransactionListiner.onSaveButtonClicked(transaction);
                        Log.d("trans", transaction.getType() + " " + transaction.getCreatedAt() + " " + transaction.getAmount() + " " + transaction.getDate());
                        dismiss();
                    }else {
                        Toast.makeText(getContext(),"Plz select the date or check amount",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            return view;
        }

    public Transaction addTransaction()  {
        Transaction transaction = new Transaction();
        Log.d("trans","date "+ new Date()+ " mydate "+date);

        transaction.setDate(date);

        if(isIncome)
            transaction.setType("Income");
        else
            transaction.setType("Expense");

        String cn = etCatogery.getText().toString();
        transaction.setCategoryName(cn.isEmpty()?"Other":cn);
        String notes = etNotes.getText().toString();
        transaction.setNotes(notes);
        long createdAt = new Date().getTime();
        transaction.setCreatedAt(createdAt);
        String rup = etAmount.getText().toString();
        Log.d("rup",Integer.parseInt(rup)+" ");
        if(!rup.isEmpty()) {
            double amount = Double.parseDouble(rup);
            transaction.setAmount(amount);

        }
        return transaction;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            addTransactionListiner = (AddTransactionListiner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement BottomSheetListener");
        }
    }
}