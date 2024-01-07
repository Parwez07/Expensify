package com.example.expensify.Model;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        Log.d("datedc",timestamp+" conv");
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        Log.d("datedc",date+" conv to");
        return date == null ? null : date.getTime();
    }
}

