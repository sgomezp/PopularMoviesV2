package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sgomezp on 27/03/2018.
 */

public class MovieReviewResponse {

    @SerializedName("id")
    private int movieId;

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieReview> results;

    /*private MovieReviewResponse(int movieId, int page, List<MovieReview> movieReviewList){
        this.movieId = movieId;
        this.page = page;
        this.results = movieReviewList;
    }*/

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
