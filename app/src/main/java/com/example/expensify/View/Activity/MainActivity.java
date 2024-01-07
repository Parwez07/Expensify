package com.example.expensify.View.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expensify.DB.TransactionViewModel;
import com.example.expensify.Model.Transaction;
import com.example.expensify.View.Activity.Fragment.AddTransactionFrag;
import com.example.expensify.View.Activity.Fragment.HistoryFragment;
import com.example.expensify.View.Activity.Fragment.HomeFragment;
import com.example.expensify.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements AddTransactionFrag.AddTransactionListiner {

    FloatingActionButton fabAdd;
    BottomNavigationView bottomNavigationView;
    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);

        replaceFragment(new HomeFragment());

        fabAdd.setOnClickListener(view -> {
           new AddTransactionFrag().show(getSupportFragmentManager(),null);
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.history)
                    replaceFragment(new HistoryFragment());
                else
                    replaceFragment(new HomeFragment());
                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    @Override
    public void onSaveButtonClicked(Transaction newTransaction) {
        TransactionViewModel transactionViewModel = new TransactionViewModel(getApplication());
        transactionViewModel.insert(newTransaction);
    }
}