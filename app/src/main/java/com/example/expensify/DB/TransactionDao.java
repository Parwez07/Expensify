package com.example.expensify.DB;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensify.Model.Transaction;

import java.util.Date;
import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    public void insert(Transaction transaction);

    @Delete
    void delte(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Query("Select * from Transactions order by CreatedAt desc")
      LiveData<List<Transaction>>getAllTransactions();

    @Query("SELECT * FROM Transactions WHERE date BETWEEN :startOfDay AND :endOfDay order by CreatedAt desc")
    LiveData<List<Transaction>> getTransactionsByDate(Date startOfDay, Date endOfDay);
    @Query("SELECT sum(Amount) FROM Transactions WHERE date BETWEEN :startOfDay AND :endOfDay and type = 'Expense'")
    LiveData<Double> getSumByDate(Date startOfDay, Date endOfDay);

    // Get the sum of income for the selected month
    @Query("SELECT SUM(Amount) FROM Transactions WHERE strftime('%Y-%m', datetime(date/1000, 'unixepoch')) = :formattedDate and type ='Income'")
    LiveData<Double> getIncomeSumForMonth(String formattedDate);

    // Get the sum of expenditure for the selected month
    @Query("SELECT SUM(Amount) FROM Transactions WHERE strftime('%Y-%m', datetime(date/1000, 'unixepoch')) = :formattedDate and type ='Expense'")
    LiveData<Double> getExpenditureSumForMonth(String formattedDate);

    @Query("SELECT * FROM Transactions WHERE date BETWEEN :firstDate AND :lastDate ORDER BY TYPE ")
    LiveData<List<Transaction>> getTransactionOfWeek(Date firstDate,Date lastDate);
}
