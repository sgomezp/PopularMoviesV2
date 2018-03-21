package com.example.android.popularmoviesV1.utils;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by sgomezp on 05/03/2018.
 */

public class ApiClient {

    public static final String TAG = ApiClient.class.getSimpleName();

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "retrofit es: " + retrofit);
        return  retrofit;

    }
}
