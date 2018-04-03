package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.android.popularmoviesV2.DetailsFragment;
import com.example.android.popularmoviesV2.ReviewsFragment;

/**
 * Created by sgomezp on 30/03/2018.
 */

public class FragmentsAdapter extends FragmentPagerAdapter {

    public static final String TAG = FragmentsAdapter.class.getSimpleName();

    /**
     * Context of the app
     */
    private Context mContext;


    public FragmentsAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }


    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */


    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                Log.d(TAG, "getItem: Estoy en DetailsFragment");
                fragment = new DetailsFragment();
                break;
            case 1:
                Log.d(TAG, "getItem: Estoy en ReviewsFragment");
                fragment = new ReviewsFragment();
                break;
            default:
                Log.d(TAG, "getItem: Estoy en ReviewsFragment");
                Log.d(TAG, "getItem: Estoy en Default DetailsFragment");
                fragment = new DetailsFragment();
                break;
        }

        return fragment;
        /*Log.d(TAG, "getItem: position =  " + position);
        if (position == 0){
            return  new DetailsFragment();
        }else {
            return new ReviewsFragment();
        }*/


    }

    /**
     * Return the total number of pages.
     */

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == 0) {
            return "Details";

        } else {
            return "Reviews";
        }


    }
}
