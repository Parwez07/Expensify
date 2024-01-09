package com.example.expensify.DB;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.expensify.Model.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    TransactionRepositry repositry ;
    LiveData<List<Transaction>>list;
    private LiveData<List<Transaction>> transactionsByDate;
    private LiveData<Double> incomeSum;
    private LiveData<Double> expenditureSum;


    public TransactionViewModel(@NonNull Application application) {
        super(application);

        repositry = new TransactionRepositry(application);
        // Observe changes in the selected date and fetch transactions accordingly
        list = repositry.getTransactionsByDate(new Date());
    }

    public void insert(Transaction transaction){
        Log.d("trans","inside viewmodel");
        repositry.insert(transaction);
    }

    public void delete(Transaction transaction){
        repositry.delete(transaction);
    }
    public void update(Transaction transaction){
        repositry.update(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactionList(){
        //Log.d("listSize",list.getValue().size()+" Live size");
        return list;
    }
    public LiveData<List<Transaction>> getTransactionsByDate(Date selectedDate) {
        transactionsByDate = repositry.getTransactionsByDate(selectedDate);
        return transactionsByDate;
    }

    public LiveData<Double> getExpenditureSum(String selectedDate) {
        expenditureSum = repositry.getExpenditureSumForMonth(selectedDate);
        return expenditureSum;
    }
    public LiveData<Double> getIncomeSum(String selectedDate){
        Log.d("datedc",selectedDate+" vmodel");
        incomeSum = repositry.getIncomeSumForMonth(selectedDate);
        Log.d("sum",incomeSum.getValue()+"");
        return incomeSum;
    }

    public LiveData<Double> getSumByDate(Date selectedDate){
        return repositry.getSumByDate(selectedDate);
    }

    public LiveData<List<Transaction>> getTransactionsByWeek(Date firstDay, Date lastDay){

        return repositry.getTransactionOfWeek(firstDay,lastDay);
    }
    public LiveData<List<Transaction>> getTransactionsByMonth(String date){

        return  repositry.getTransactionByMonth(date);
    }
}
