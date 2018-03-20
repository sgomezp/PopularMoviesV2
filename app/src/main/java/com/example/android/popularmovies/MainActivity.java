package com.example.android.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.model.MovieResponse;
import com.example.android.popularmovies.model.Movies;
import com.example.android.popularmovies.utils.ApiClient;
import com.example.android.popularmovies.utils.ApiInterface;
import com.example.android.popularmovies.utils.Constants;
import com.example.android.popularmovies.utils.MovieAdapter;
import com.example.android.popularmovies.utils.NetworkUtils;

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

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    //LOG_TAG for debugging purposes
    private static final String TAG = MainActivity.class.getSimpleName();
    public static List<Movies> movies;
    RecyclerView recyclerView;
    MovieAdapter.ViewHolder mAdapter;
    MovieAdapter mMovieAdapter;
    RecyclerView.LayoutManager layoutManager;
    Call<MovieResponse> sort = null;
    String preference;
    TextView showErrorMessage;
    private ProgressBar loadingIndicator;


    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.toolbar);

        if (myToolbar != null) {
            setSupportActionBar(myToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }


        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        showErrorMessage = findViewById(R.id.show_error_message);
        showErrorMessage.setVisibility(View.INVISIBLE);


        recyclerView = findViewById(R.id.rv_movies);
        Resources res = getResources();
        int numbersOfColumns = res.getInteger(R.integer.list_columns);

        // use a gridLayout manager
        layoutManager = new GridLayoutManager(this, numbersOfColumns);
        recyclerView.setLayoutManager(layoutManager);

        preference = choosePreferences();

        //loadingIndicator.setVisibility(View.VISIBLE);
        updateDisplayPreferences(preference);

    }

    private String choosePreferences() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preference = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        Log.d(TAG, "orderBy es: " + preference);

        if (preference.equals("top")) {
            preference = Constants.URL_TOP_RATED;
            getSupportActionBar().setSubtitle("Top Rated");
        } else if (preference.equals("popular")) {
            preference = Constants.URL_POPULAR_MOVIES;
            getSupportActionBar().setSubtitle("Populars");
        }
        return preference;
    }

    // ****************************************************************************************************
    private void updateDisplayPreferences(final String preference) {


        if (Constants.API_KEY.isEmpty()) {
            showMessage(getString(R.string.api_missing_error));
            return;
        }
        try {
            recyclerView.setVisibility(View.VISIBLE);
            showErrorMessage.setVisibility(View.INVISIBLE);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> call = apiService.getMovies(preference, Constants.API_KEY);
            Log.d(TAG, "url: " + call.request().url().toString());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        loadingIndicator.setVisibility(View.INVISIBLE);
                        movies = response.body().getResults();

                        if (mMovieAdapter == null) {
                            recyclerView.setAdapter(new MovieAdapter(movies, R.layout.linearlayout_movies, getApplicationContext()));
                            recyclerView.setHasFixedSize(false);
                        } else {
                            mMovieAdapter.updateRecyclerData(movies);
                            mMovieAdapter.notifyDataSetChanged();
                        }
                    } else {
                        showMessage(getString(R.string.no_internet_error));
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    // Log error here since request failed
                    showMessage(getString(R.string.no_internet_error));

                }
            });
        } catch (Exception e) {
            showMessage(getString(R.string.no_internet_error));
        }
    }

    private void showMessage(String msg) {

        loadingIndicator.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility((View.INVISIBLE));
        showErrorMessage.setText(msg);
        showErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preference = choosePreferences();
        updateDisplayPreferences(preference);
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
        switch (id) {
            case R.id.menu_refresh:
                if (NetworkUtils.isConnected(this)) {
                    Log.d(TAG, "Estoy en refresh");
                    updateDisplayPreferences(preference); //this doesn't works
                    //I don't know what to use here!!

                }

                break;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingActivity.class);
                startActivity(settingsIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String value) {
        Log.d(TAG, "value: " + value);
        if (value.equals(getString(R.string.settings_order_by_key))) {
            Log.d(TAG, "Entré al onSharedPreference " + getString(R.string.settings_order_by_key));
        }

    }
}


