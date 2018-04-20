package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.util.Log;

import com.example.android.popularmoviesV2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    public static final String DATE_IN = "yyyy-MM-dd";
    public static final String DATE_OUT = "dd MMM yyyy";

    private void Utils() {

    }

    // Method to convert date in format "yyyy-mm-dd" to "dd MMM yyyy"

    public static String FormatDate(Context context, String dateToFormat) {
        String resultDate = "";

        SimpleDateFormat oldFormat = new SimpleDateFormat(DATE_IN, Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat(DATE_OUT, Locale.ENGLISH);

        try {
            Date date = oldFormat.parse(dateToFormat);
            resultDate = newFormat.format(date);
        } catch (ParseException pe) {
            Log.e(TAG, "FormatDate: " + pe.toString());
            resultDate = context.getString(R.string.no_release_date);

        }

        return resultDate;
    }


}
