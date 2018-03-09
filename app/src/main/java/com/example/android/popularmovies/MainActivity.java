package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.popularmovies.model.MovieResponse;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.ApiClient;
import com.example.android.popularmovies.utils.ApiInterface;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.utils.MovieAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sgomezp on 23/02/2018.
 * <p>
 * Based on Android Working with Retrofit HTTP Library
 * https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public int typeOfSort = 0;


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    Call<MovieResponse> sort = null;



    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    //private MovieAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*final RecyclerView recyclerView;
        final RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;
        Call<MovieResponse> sort = null;*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        Resources res = getResources();
        int numbersOfColumns = res.getInteger(R.integer.list_columns);

        recyclerView.setHasFixedSize(true);

        // use a gridLayout manager
        layoutManager = new GridLayoutManager(this, numbersOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        sort = apiService.getMoviePopular(Constants.API_KEY);

        PreferenceManager.setDefaultValues(this, R.xml.settings_main, false);


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        final String  orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        Log.d(TAG, "orderBy es: "+ orderBy);

            if(orderBy.equals("top")){
                sort = apiService.getTopRatedMovies(Constants.API_KEY);
            }else if(orderBy.equals("popular")){
                sort = apiService.getMoviePopular(Constants.API_KEY);
            }


            Log.d(TAG, "TypeOfSort: " + typeOfSort);


        if (Constants.API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please obtain your API KEY from themovie.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        recyclerView = findViewById(R.id.rv_movies);

        /*Resources res = getResources();
        int numbersOfColumns = res.getInteger(R.integer.list_columns);*/

        recyclerView.setHasFixedSize(true);

        // use a gridLayout manager
        layoutManager = new GridLayoutManager(this, numbersOfColumns);
        recyclerView.setLayoutManager(layoutManager);


        //ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = sort;


        final RecyclerView finalRecyclerView = recyclerView;
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                List<Movies> movies = response.body().getResults();
                finalRecyclerView.setAdapter(new MovieAdapter(movies, R.layout.linearlayout_movies, getApplicationContext()));
            }


            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());

            }
        });

    }


    //Handle menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);


    }

}


