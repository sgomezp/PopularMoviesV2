package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sgomezp on 26/02/2018.
 */

public class Movies {

    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_average")
    private Double voteAverage;
    @SerializedName("release_date")
    private String releaseDate;


    /*private  int mImageResource;
    private  String mImageUrl;
    private  int mTitle;
    private  int mSynopsis;*/

    public Movies(String posterPath, String originalTitle, String overview, Double voteAverage, String releaseDate){
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getOriginalTitle(){
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle){
        this.originalTitle = originalTitle;
    }

    public String getOverview(){
        return overview;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public Double getVoteAverage(){
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage){
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate(){
        return  releaseDate;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }




}
