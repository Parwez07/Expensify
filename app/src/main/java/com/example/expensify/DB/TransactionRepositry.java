package com.example.expensify.DB;

import static com.example.expensify.Constant.helper.getEndOfDay;
import static com.example.expensify.Constant.helper.getStartOfDay;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.expensify.Model.Transaction;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransactionRepositry {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> transactionList;

    ExecutorService executorService = Executors.newSingleThreadExecutor();

    public TransactionRepositry(Application application) {

        AppDataBase appDataBase = AppDataBase.getInstance(application);
        transactionDao = appDataBase.getTransactionDao();
        transactionList = transactionDao.getAllTransactions();
    }

    public void insert(Transaction transaction) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("trans","inside repositry");
                transactionDao.insert(transaction);
            }
        });
    }

    public void delete(Transaction transaction) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionDao.delte(transaction);
            }
        });
    }

    public void update(Transaction transaction) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                transactionDao.update(transaction);
            }
        });
    }

    public LiveData<List<Transaction>> getAllTransactionList() {
        Log.d("listSize", transactionList + " repositry size");
        return transactionList;
    }

    public LiveData<List<Transaction>> getTransactionsByDate(Date selectedDate) {
        Date startOfDay = getStartOfDay(selectedDate);
        Date endOfDay = getEndOfDay(selectedDate);
        Log.d("date" , startOfDay +" date "+endOfDay);
        return transactionDao.getTransactionsByDate(startOfDay, endOfDay);
    }

    public LiveData<List<Transaction>> getTransactionOfWeek(Date firstDate, Date lastDate){

        Date startDate = getStartOfDay(firstDate);
        Date endDate = getEndOfDay(lastDate);
        Log.d("weekly",startDate+" "+endDate);
        return transactionDao.getTransactionOfWeek(startDate,endDate);
    }

    public LiveData<Double> getIncomeSumForMonth(String  date){
        Log.d("sum",transactionDao.getIncomeSumForMonth(date).getValue()+" in repo ");
       return transactionDao.getIncomeSumForMonth(date);
    }
    public  LiveData<Double> getExpenditureSumForMonth(String date){

        return transactionDao.getExpenditureSumForMonth(date);
    }
    public LiveData<Double> getSumByDate(Date selectedDate){
        Date startOfDay = getStartOfDay(selectedDate);
        Date endOfDay = getEndOfDay(selectedDate);
        Log.d("date" , startOfDay +" date "+endOfDay);
        return transactionDao.getSumByDate(startOfDay, endOfDay);
    }

    public LiveData<List<Transaction>> getTransactionByMonth(String date){

        return transactionDao.getTransationsByMonth(date);
    }
}