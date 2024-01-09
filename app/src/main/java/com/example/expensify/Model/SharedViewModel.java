package com.example.expensify.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<Date> selectedDate = new MutableLiveData<>();

    public LiveData<Date> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date date) {
        selectedDate.setValue(date);
    }
}
