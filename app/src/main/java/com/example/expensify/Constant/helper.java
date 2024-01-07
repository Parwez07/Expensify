package com.example.expensify.Constant;

import android.util.Log;

import com.example.expensify.Model.Category;
import com.example.expensify.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class helper {


    public static double Income;
    public static  double Expenditure;

    public static Date getStartOfDay(Date selectedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Date getEndOfDay(Date selectedDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    public static Map<String, List<Category>> category = new HashMap<>();

    public static void add(){
        List<Category>Incomelist = new ArrayList<>();
        Incomelist.add(new Category("Salary", R.drawable.salary));
        Incomelist.add(new Category("Bonus",R.drawable.bonus));
        Incomelist.add(new Category("Other",R.drawable.income));
        List<Category>ExpenseList = new ArrayList<>();
        ExpenseList.add(new Category("BreakFast",R.drawable.breakfast));
        ExpenseList.add(new Category("Lunch",R.drawable.lunch));
        ExpenseList.add(new Category("Tea",R.drawable.tea));
        ExpenseList.add(new Category("Dinner",R.drawable.dinner));
        ExpenseList.add(new Category("Other",R.drawable.expenses));
        ExpenseList.add(new Category("Briyani",R.drawable.biryani));
        category.put("Expense",ExpenseList);
        category.put("Income",Incomelist);
    }

    public static Category getCategoryIcon(String catogaryName){
        Log.d("name",catogaryName+" inside getCategoryIcon "+category.size());
        for(List<Category> list :category.values()){
            Log.d("name",list.size()+" ");
            for(Category c : list){
                Log.d("name","inside get");
                if(c.getCategoryName().equals(catogaryName)) {
                    Log.d("name",c.getCategoryName()+" catog");
                    return c;
                }
            }
        }

        return null;
    }
/*
        SimpleDateFormat yearMonthFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String yearMonth = yearMonthFormat.format(calendar.getTime());
        long mil = new Date().getTime();
        Date date = new Date(mil);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
        SimpleDateFormat format1 = new SimpleDateFormat("hh mm a", Locale.getDefault());
        String d = format.format(date.getTime());
        String time = format1.format(date.getTime());
        Log.d("time22", "date " + d + " time " + time);

 */
}
