package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.popularmoviesV2.DetailsFragment;
import com.example.android.popularmoviesV2.ReviewsFragment;
import com.example.android.popularmoviesV2.TrailersFragment;


public class FragmentsAdapter extends FragmentPagerAdapter {

    public static final String TAG = FragmentsAdapter.class.getSimpleName();
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Details", "Reviews", "Trailers"};

    /**
     * Context of the app
     */
    private Context mContext;


    public FragmentsAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }


    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */



    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        Log.d(TAG, "getItem: Position: " + position);
        switch (position) {
            case 0:
                Log.d(TAG, "getItem: Estoy en DetailsFragment");
                fragment = new DetailsFragment();
                break;
            case 1:
                Log.d(TAG, "getItem: Estoy en ReviewsFragment");
                fragment = new ReviewsFragment();
                break;
            case 2:
                fragment = new TrailersFragment();
                break;
            default:
                fragment = new DetailsFragment();
                break;
        }

        return fragment;

    }

    /**
     * Return the total number of pages.
     */

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];

    }

}
