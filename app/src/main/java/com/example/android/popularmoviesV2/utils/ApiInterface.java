package com.example.android.popularmoviesV2.utils;

import com.example.android.popularmoviesV2.model.MovieResponse;
import com.example.android.popularmoviesV2.model.MovieReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("{preference}")
    Call<MovieResponse> getMovies(@Path(value = "preference", encoded = true) String preference,
                                  @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<MovieReviewResponse> getMovieReviews(@Path(value = "id", encoded = true) int id,
                                              @Query("api_key") String apiKey);

}
