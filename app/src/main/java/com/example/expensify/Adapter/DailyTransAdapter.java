package com.example.expensify.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensify.Constant.helper;
import com.example.expensify.Model.Category;
import com.example.expensify.Model.Transaction;
import com.example.expensify.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyTransAdapter extends RecyclerView.Adapter<DailyTransAdapter.DailyTransViewHolder>{

    List<Transaction>transactionList = new ArrayList<>();
    Context context;
    public DailyTransAdapter(Context context) {
        this.context = context;
    }




    @NonNull
    @Override
    public DailyTransViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transcation,parent,false);
        return new DailyTransViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTransViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);
        // Img need to added...
        Category category = helper.getCategoryIcon(transaction.getCategoryName());
        Log.d("name", transaction.getCategoryName()+" "+category.getCategoryName());

        holder.imgCategory.setImageResource(category.getCategoryImage());
        holder.tvAmount.setText(transaction.getAmount()+"");
        holder.tvCategory.setText(transaction.getCategoryName());
        SimpleDateFormat format = new SimpleDateFormat("dd MMM YYYY");
        Date da = transaction.getDate();
        String d = format.format(da);
        holder.tvDate.setText(d);

        if(transaction.getType().equals("Income")){

            holder.tvAmount.setTextColor(context.getColor(R.color.green));
            holder.expense.setTextColor(context.getColor(R.color.green));
        }else if (transaction.getType().equals("Expense")){

            holder.tvAmount.setTextColor(context.getColor(R.color.red));
            holder.expense.setTextColor(context.getColor(R.color.red));
            //holder.expense.setCompoundDrawablesWithIntrinsicBounds(R.drawable.minus, 0, 0, 0);
        }
        Date date = new Date(transaction.getCreatedAt());
        SimpleDateFormat format1 = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String time = format1.format(date);
        holder.tvTime.setText(time);
    }

    @Override
    public int getItemCount() {
        Log.d("listSize",transactionList.size()+" size");
        return transactionList.size();
    }

    public void setTransactionList(List<Transaction>list){
        this.transactionList = list;
        notifyDataSetChanged();
    }
    class  DailyTransViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCategory;
        TextView tvAmount,tvCategory,tvDate,tvTime,expense;
        public DailyTransViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.ivCategoryImg);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCategory = itemView.findViewById(R.id.transCategory);
            tvDate = itemView.findViewById(R.id.date);
            tvTime = itemView.findViewById(R.id.time);
            expense = itemView.findViewById(R.id.expense);

        }
    }
}
