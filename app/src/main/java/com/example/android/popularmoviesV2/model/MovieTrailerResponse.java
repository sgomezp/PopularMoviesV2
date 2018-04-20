package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieTrailerResponse {

    // Movie Trailer Id
    @SerializedName("id")
    private int movieId;

    // Movie Trailer list
    @SerializedName("results")
    private List<MovieTrailer> movieTrailerList = null;

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

    public List<MovieTrailer> getResults() {
        return movieTrailerList;
    }
}



