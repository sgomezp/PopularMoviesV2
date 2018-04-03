package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sgomezp on 27/03/2018.
 */

public class MovieTrailerResponse {

    // Movie Trailer list
    @SerializedName("results")
    private List<MovieTrailer> movieTrailerList;

    // Movie Trailer Id
    @SerializedName("id")
    private int movieId;


    private MovieTrailerResponse(List<MovieTrailer> movieTrailerList, int movieId) {
        this.movieId = movieId;
        this.movieTrailerList = movieTrailerList;
    }

    // Getters and Setters methods


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public List<MovieTrailer> getMovieTrailerList() {
        return movieTrailerList;
    }

    public void setMovieTrailerList(List<MovieTrailer> movieTrailerList) {
        this.movieTrailerList = movieTrailerList;
    }
}



