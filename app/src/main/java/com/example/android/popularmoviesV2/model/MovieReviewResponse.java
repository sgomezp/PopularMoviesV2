package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieReviewResponse {

    @SerializedName("id")
    private int movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieReview> results;


    // Getters and Setters Methods


    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public List<MovieReview> getResults() {
        return results;
    }

    public void setResults(List<MovieReview> results) {
        this.results = results;
    }
}
