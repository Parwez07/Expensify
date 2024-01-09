package com.example.expensify.DB;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.expensify.Model.DateConverter;
import com.example.expensify.Model.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class}, version = 5)
@TypeConverters(DateConverter.class) // Add this line
abstract class AppDataBase extends RoomDatabase {
    abstract TransactionDao getTransactionDao();

    private static AppDataBase instance;

    public static synchronized AppDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "transaction_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
            Log.d("inDb","yaha tak ");
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            TransactionDao transactionDao = instance.getTransactionDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("inCall","inside call back");
         /*           transactionDao.insert(new Transaction("Income","Salary","no notes", new Date(),new Date().getTime(),200));
                    transactionDao.insert(new Transaction("Expense","Briyani","no notes",new Date(),new Date().getTime(),200));
                    transactionDao.insert(new Transaction("Income","Salary","no notes",new Date(),new Date().getTime(),200));
                    transactionDao.insert(new Transaction("Expense","Other","no notes",new Date(),new Date().getTime(),200));
                    transactionDao.insert(new Transaction("Income","Salary","no notes",new Date(),new Date().getTime(),200));
                    transactionDao.insert(new Transaction("Income","Salary","no notes",new Date(),new Date().getTime(),200.0));

          */
                }
            });
            Log.d("inCall","in call back");
            //new PopulateDbAsynTask(instance).execute();
        }
    };
}
