package com.example.android.popularmoviesV1.utils;

import com.example.android.popularmoviesV1.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sgomezp on 05/03/2018.
 */

public interface ApiInterface {

    @GET("{preference}")
    Call<MovieResponse> getMovies(@Path(value = "preference", encoded = true) String preference, @Query("api_key") String apiKey);

}
