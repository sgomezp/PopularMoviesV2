package com.example.android.popularmovies.utils;

import com.example.android.popularmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sgomezp on 05/03/2018.
 */

public interface ApiInterface {
    @GET(Constants.URL_TOP_RATED)
    Call<MovieResponse> getTopRatedMovies(@Query(Constants.API_PARAM) String apiKey);

    @GET(Constants.URL_POPULAR_MOVIES)
    Call<MovieResponse> getMoviePopular(@Query(Constants.API_PARAM) String apiKey);
}
