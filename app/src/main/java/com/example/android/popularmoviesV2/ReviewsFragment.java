package com.example.android.popularmoviesV2;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesV2.model.MovieReview;
import com.example.android.popularmoviesV2.model.MovieReviewResponse;
import com.example.android.popularmoviesV2.model.Movies;
import com.example.android.popularmoviesV2.utils.ApiClient;
import com.example.android.popularmoviesV2.utils.ApiInterface;
import com.example.android.popularmoviesV2.utils.Constants;
import com.example.android.popularmoviesV2.utils.ReviewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.android.popularmoviesV2.utils.Constants.API_KEY;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {

    public static final String TAG = ReviewsFragment.class.getSimpleName();
    public static List<MovieReview> mMovieReviewList;
    public TextView showErrorMessage;
    DetailActivity parentActivity;
    private View mViewFragment;
    private Movies mMovies;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReviewAdapter mReviewAdapter;
    private ProgressBar mLoadingIndicator;
    private TextView mEmptyView;

    //private final Movies mMovies = DetailsFragment.currentMovie;
    private MovieReviewResponse mMovieReviewResponse;



   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (DetailActivity) getActivity();
    }
*/

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewFragment = inflater.inflate(R.layout.reviews_fragment, container, false);
        mLoadingIndicator = mViewFragment.findViewById(R.id.loading_indicator);
        mEmptyView = mViewFragment.findViewById(R.id.empty_view);
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


            //mMovieReviewList = mMovies.getId();
        }




        // Set RecyclerView Layout
        setupRecyclerViewLayout();

        //Bind RecyclerView with the adapter

        LoadListReview();

        mReviewAdapter = new ReviewAdapter(mMovieReviewList, R.layout.list_item_review, this.getActivity());
        mRecyclerView.setAdapter(mReviewAdapter);


        //Load List Reviews

        return mViewFragment;

    }

    private void LoadListReview() {


        if (API_KEY.isEmpty()) {
            Log.d(TAG, "LoadListReview: Please obtain your API Key");
            //showMessage(getString(R.string.api_missing_error));
            return;
        }
        try {
            Log.d(TAG, "LoadListReview: He entrado al try");

            mLoadingIndicator.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
            //showErrorMessage.setVisibility(View.INVISIBLE);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieReviewResponse> call = apiService.getMovieReviews(mMovies.getId(), Constants.API_KEY);
            Log.d(TAG, "LoadListReview url: " + call.request().url().toString());
            call.enqueue(new Callback<MovieReviewResponse>() {


                @Override
                public void onResponse(@NonNull Call<MovieReviewResponse> call, @NonNull Response<MovieReviewResponse> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        mLoadingIndicator.setVisibility(View.INVISIBLE);
                        Log.d(TAG, "onResponse: was successful");
                        mMovieReviewList = response.body().getResults();
                        // Display message for no trailers
                        if (mMovieReviewList.isEmpty() && (mViewFragment.isShown())) { //Ojo


                            Toast.makeText(getContext(), "No existen Reviews", Toast.LENGTH_LONG).show();
                        }

                        if (mReviewAdapter == null) {
                            Log.d(TAG, "onResponse: mReviewAdapter es Null");
                            mRecyclerView.setAdapter(new ReviewAdapter(mMovieReviewList, R.layout.list_item_review, getContext()));
                            mRecyclerView.setHasFixedSize(false);
                        } else {
                            Log.d(TAG, "onResponse: mReviewAdapter no es null");
                            mReviewAdapter.updateRecyclerData(mMovieReviewList);
                            mReviewAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d(TAG, "onResponse: No network Available ");
                        mLoadingIndicator.setVisibility(View.INVISIBLE);
                        //showMessage(getString(R.string.no_internet_error));
                    }
                }

                @Override
                public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: I'm here");
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                    // Log error here since request failed
                    //showMessage(getString(R.string.no_internet_error));

                }
            });
        } catch (Exception e) {
            Log.d(TAG, "LoadListReview: estoy en el Catch");
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            //showErrorMessage.setVisibility(View.VISIBLE);
            //showMessage(getString(R.string.no_internet_error));
        }

    }

    /*private void showMessage(String msg) {

        //loadingIndicator.setVisibility(View.INVISIBLE);
        //recyclerView.setVisibility((View.INVISIBLE));
        showErrorMessage.setText(msg);
        showErrorMessage.setVisibility(View.VISIBLE);
    }*/

    private void setupRecyclerViewLayout() {

        Log.d(TAG, "setupRecyclerViewLayout: parentActivity is: " + parentActivity);

        mLayoutManager = new LinearLayoutManager(parentActivity);
        Log.d(TAG, "setupRecyclerViewLayout: mLayoutManager is: " + mLayoutManager);
        mRecyclerView = mViewFragment.findViewById(R.id.list_review);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        //mRecyclerView.setAdapter(mReviewAdapter);
    }


    private void closeOnError() {
        getActivity().finish();
        Toast.makeText(getActivity(), R.string.detail_error_message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onStop() {
        super.onStop();
    }


}
