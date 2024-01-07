package com.example.expensify.View.Activity.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.expensify.Adapter.viewPagerAdapter;
import com.example.expensify.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HistoryFragment extends Fragment {

    MaterialToolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    TextView currDate;
    ImageView btnPrev, btnNext;
    Calendar calendar;
    DateChangeListener dateChangeListener;


    public HistoryFragment() {
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        toolbar = view.findViewById(R.id.materialToolbar);
        toolbar.setTitle("Transaction");
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);
        btnNext = view.findViewById(R.id.ivNext);
        btnPrev = view.findViewById(R.id.btnPrev);
        currDate = view.findViewById(R.id.currDate);
        calendar = Calendar.getInstance();

        viewPagerAdapter adapter = new viewPagerAdapter(getChildFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    updateDate();
                }
                else if (position == 1) {
                    updateWeek();
                }
            }

        });

        TabLayoutMediator layoutMediator = new TabLayoutMediator(tabLayout, viewPager2, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Daily");
                        break;
                    case 1:
                        Log.d("weekly", calendar.getTime() + "");
                        tab.setText("Weekly");
                        break;
                    case 2:
                        tab.setText("Monthly");
                        break;
                }
                Log.d("tab",tab.getContentDescription()+"");
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle btnPrev click
                Toast.makeText(getContext(), "prev clicked", Toast.LENGTH_SHORT).show();
                if (viewPager2.getCurrentItem() == 0) {
                    calendar.add(Calendar.DATE, -1);
                    updateDate();
                } else if (viewPager2.getCurrentItem() == 1) {
                    calendar.add(Calendar.DATE, -6);
                    Log.d("weekly", "pre click " + calendar.getTime());
                    updateWeek();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle btnNext click
                if (viewPager2.getCurrentItem() == 0) {
                    calendar.add(Calendar.DATE, 1);
                    updateDate();
                } else if (viewPager2.getCurrentItem() == 1) {
                    calendar.add(Calendar.DATE, 6);
                    updateWeek();
                }
            }
        });
        layoutMediator.attach();
        return view;
    }

    public void updateDate() {
        Log.d("prevHistory", " update " + calendar.getTime() + " ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
        SimpleDateFormat amPm = new SimpleDateFormat("hh mm a");
        currDate.setText(dateFormat.format(calendar.getTime()));
        if(dateChangeListener !=null){
            dateChangeListener.onDateChanged(calendar.getTime());
        }else{
            Toast.makeText(getContext(),"datechange is null",Toast.LENGTH_SHORT).show();
        }
    }

    public void updateWeek() {
        Calendar selectedDate = (Calendar) calendar.clone();
        Date sd = selectedDate.getTime();
        int day = selectedDate.get(Calendar.DAY_OF_WEEK) - 1;
        selectedDate.add(selectedDate.DATE, -day);
        Date firstDateOfWeek = selectedDate.getTime();

        selectedDate.add(selectedDate.DATE, 6);
        Date lastDateOfWeek = selectedDate.getTime();

        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMM");
        String formattedFirstDate = outputFormat.format(firstDateOfWeek);
        String formattedLastDate = outputFormat.format(lastDateOfWeek);
        String week = formattedFirstDate + " to " + formattedLastDate;
        currDate.setText(week);
        if (dateChangeListener != null) {
            Log.d("dateChange","next tak sahi "+calendar.getTime());
            dateChangeListener.onDateChanged(calendar.getTime());
        } else {
            Toast.makeText(getContext(), "dateChange is null", Toast.LENGTH_SHORT).show();
        }
        Log.d("weekly", sd.getTime() + " " + formattedFirstDate + " " + formattedLastDate);
    }

    public interface DateChangeListener {
        void onDateChanged(Date newDate);
    }

    public void setDateChangeListener(DateChangeListener listener) {
        Log.d("dateChange","setChange call ");
        this.dateChangeListener = listener;

    }


}