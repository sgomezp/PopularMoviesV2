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
import android.widget.TextView;
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


public class TrailersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = TrailersFragment.class.getSimpleName();
    public static List<MovieTrailer> mMovieTrailerList;
    TextView mEmptyView;
    String mMessage;
    private View mViewFragment;
    private Movies mMovies;
    private DetailActivity parentActivity;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TrailerAdapter mTrailerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewFragment = inflater.inflate(R.layout.trailer_fragment, container, false);
        mEmptyView = mViewFragment.findViewById(R.id.empty_view_trailers);
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


        mTrailerAdapter = new TrailerAdapter(mMovieTrailerList, R.layout.list_item_trailer, this.getActivity());
        mRecyclerView.setAdapter(mTrailerAdapter);

        //Load List Reviews
        LoadListTrailer();

        return mViewFragment;

    }

    private void LoadListTrailer() {


        if (API_KEY.isEmpty()) {
            Log.d(TAG, "LoadListReview: Please obatin your API Key");
            mMessage = getString(R.string.api_missing_error);
            handleEmptyList(true);
            return;
        }
        try {
            Log.d(TAG, "LoadListTrailer: He entrado al try");

            mRecyclerView.setVisibility(View.VISIBLE);

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

                        // Display message for no trailers
                        if (mMovieTrailerList.isEmpty()) {
                            mMessage = getString(R.string.no_trailers_found);
                            handleEmptyList(true);

                        } else {
                            mMessage = "";
                            handleEmptyList(false);

                        }


                        if (mTrailerAdapter == null) {
                            Log.d(TAG, "onResponse: mTrailerAdapter es Null");
                            mRecyclerView.setAdapter(new TrailerAdapter(mMovieTrailerList, R.layout.list_item_trailer, getContext()));
                            mRecyclerView.setHasFixedSize(false);
                        } else {
                            Log.d(TAG, "onResponse: mReviewAdapter no es null");
                            mTrailerAdapter.updateRecyclerData(mMovieTrailerList);
                            mTrailerAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Log.d(TAG, "onResponse: No network Available ");
                        mMessage = getString(R.string.network_no_available);
                        handleEmptyList(true);

                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: I'm here");
                    mMessage = getString(R.string.network_no_available);
                    handleEmptyList(true);


                }
            });
        } catch (Exception e) {
            Log.d(TAG, "LoadListReview: estoy en el Catch");
            mMessage = getString(R.string.no_internet_error);
            handleEmptyList(true);

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

    public void handleEmptyList(boolean isEmpty) {
        if (isEmpty) {
            mEmptyView.setText(mMessage);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setText("");
            mEmptyView.setVisibility(View.GONE);
        }
    }
}
