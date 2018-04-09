package com.example.android.popularmoviesV2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.popularmoviesV2.model.MovieTrailer;
import com.example.android.popularmoviesV2.model.MovieTrailerResponse;
import com.example.android.popularmoviesV2.model.Movies;
import com.example.android.popularmoviesV2.utils.ApiClient;
import com.example.android.popularmoviesV2.utils.ApiInterface;
import com.example.android.popularmoviesV2.utils.Constants;
import com.example.android.popularmoviesV2.utils.TrailerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.popularmoviesV2.utils.Constants.API_KEY;

/**
 * Created by sgomezp on 08/04/2018.
 */

public class TrailersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = TrailersFragment.class.getSimpleName();
    public static List<MovieTrailer> mMovieTrailerList;
    private View mViewFragment;
    private Movies mMovies;
    private DetailActivity parentActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TrailerAdapter mTrailerAdapter;
    private TrailerAdapter.OnItemClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewFragment = inflater.inflate(R.layout.trailer_fragment, container, false);
        Bundle extras = getActivity().getIntent().getExtras();

        Intent intent = getActivity().getIntent();
        if (intent == null) {
            closeOnError();
        }

        if (extras != null) {

            int position = extras.getInt("position");
            Log.d(TAG, "onCreateView: position = " + position);

            //showMovieDetails(position);
            mMovies = MainActivity.movies.get(position);
            Log.d(TAG, "onCreateView: position movies:" + mMovies.getId());


        }


        // Set RecyclerView Layout
        setupRecyclerViewLayout();

        //Bind RecyclerView with the adapter

        LoadListTrailer();

        mTrailerAdapter = new TrailerAdapter(mMovieTrailerList, R.layout.list_item_trailer, this.getActivity(), listener);
        mRecyclerView.setAdapter(mTrailerAdapter);

        //Load List Reviews

        return mViewFragment;

    }

    private void LoadListTrailer() {


        if (API_KEY.isEmpty()) {
            Log.d(TAG, "LoadListReview: Please obatin your API Key");
            //showMessage(getString(R.string.api_missing_error));
            return;
        }
        try {
            Log.d(TAG, "LoadListTrailer: He entrado al try");
            //mSwipeRefreshLayout.setRefreshing(false);
            //mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            //showErrorMessage.setVisibility(View.INVISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieTrailerResponse> call = apiService.getMovieTrailers(mMovies.getId(), Constants.API_KEY);
            Log.d(TAG, "LoadListTrailer url: " + call.request().url().toString());
            call.enqueue(new Callback<MovieTrailerResponse>() {


                @Override
                public void onResponse(@NonNull Call<MovieTrailerResponse> call, @NonNull Response<MovieTrailerResponse> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        //loadingIndicator.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onResponse: was successful");
                        mMovieTrailerList = response.body().getResults();
                        //Log.d(TAG, "onResponse: response body " + mMovieReviewResponse.toString());

                        if (mTrailerAdapter == null) {
                            Log.d(TAG, "onResponse: mTrailerAdapter es Null");
                            mRecyclerView.setAdapter(new TrailerAdapter(mMovieTrailerList, R.layout.list_item_trailer, getContext(), listener));
                            mRecyclerView.setHasFixedSize(false);
                        } else {
                            Log.d(TAG, "onResponse: mReviewAdapter no es null");
                            mTrailerAdapter.updateRecyclerData(mMovieTrailerList);
                            mTrailerAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d(TAG, "onResponse: No network Available ");
                        //showMessage(getString(R.string.no_internet_error));
                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: I'm here");
                    // Log error here since request failed
                    //showMessage(getString(R.string.no_internet_error));

                }
            });
        } catch (Exception e) {
            Log.d(TAG, "LoadListReview: estoy en el Catch");
            //showErrorMessage.setVisibility(View.VISIBLE);
            //showMessage(getString(R.string.no_internet_error));
        }

    }

    private void setupRecyclerViewLayout() {

        mLayoutManager = new LinearLayoutManager(parentActivity);
        mRecyclerView = mViewFragment.findViewById(R.id.list_trailer);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mTrailerAdapter);
    }


    private void closeOnError() {
        getActivity().finish();
        Toast.makeText(getActivity(), R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onRefresh() {

    }
}
