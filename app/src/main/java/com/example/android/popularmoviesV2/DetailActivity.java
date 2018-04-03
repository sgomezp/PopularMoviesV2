package com.example.android.popularmoviesV2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmoviesV2.model.Movies;
import com.example.android.popularmoviesV2.utils.FragmentsAdapter;


public class DetailActivity extends AppCompatActivity{

    public static final String TAG = DetailActivity.class.getSimpleName();

    public static Movies currentMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new DetailsFragment())
                .commit();*/

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        FragmentsAdapter adapter = new FragmentsAdapter(this, getSupportFragmentManager());


        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()

        tabLayout.setupWithViewPager(viewPager);


        Toolbar myToolbar = findViewById(R.id.toolbar);

        if (myToolbar != null) {
            Log.d(TAG, "Detail Activity myToolBar no es Null");
            setSupportActionBar(myToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
                Log.d(TAG, "Detail Activity action bar  no es Null");
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(R.string.title_bar_details_activity);
            Log.d(TAG, "title bar details: " + R.string.title_bar_details_activity);

        } else {
            Log.d(TAG, "Detail Activity myToolBar es Null");
        }

    }


    //Handle menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.menu_favorites:
                Log.d(TAG, "Estoy en Favoritos de DetailsActivity");

                return true;
            /*case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingActivity.class);
                startActivity(settingsIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);*/
        }

        return super.onOptionsItemSelected(item);
    }


}
