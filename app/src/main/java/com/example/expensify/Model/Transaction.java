package com.example.expensify.Model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;
import java.util.Date;


@Entity(tableName = "Transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
   public int id;
    String Type;
    String CategoryName;
    String Notes;
    @TypeConverters(DateConverter.class)
    Date date;
    long CreatedAt;
    double Amount;

    public Transaction() {
    }

    public void setType(String type) {
        Type = type;
    }



    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public void setDate(Date date) {
        Log.d("datedc",date+" ");
        this.date = date;
    }

    public void setCreatedAt(long createdAt) {
        CreatedAt = createdAt;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return Type;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getNotes() {
        return Notes;
    }

    public Date getDate() {
        return date;
    }

    public long getCreatedAt() {
        return CreatedAt;
    }

    public double getAmount() {
        return Amount;
    }

    public Transaction(String type, String categoryName, String notes, Date date, long createdAt, double amount) {
        this.Type = type;
        this.CategoryName = categoryName;
        this.Notes = notes;
        this.date = date;
        Log.d("datedc",date+ " ");
        this.CreatedAt = createdAt;
        this.Amount = amount;
    }
}