package com.example.android.popularmoviesV2.model;

import android.util.Log;

import com.example.android.popularmoviesV2.utils.Constants;
import com.google.gson.annotations.SerializedName;


public class MovieTrailer {

    private static final String TAG = MovieTrailer.class.getSimpleName();

    // Movie Trailer Id
    @SerializedName("id")
    private String trailerId;

    @SerializedName("iso_639_1")
    private String trailerIso6391;

    @SerializedName("iso_3166_1")
    private String trailerIso31661;

    @SerializedName("key")
    private String trailerKey;

    @SerializedName("name")
    private String trailerName;

    @SerializedName("site")
    private String trailerSite;

    @SerializedName("size")
    private Integer trailerSize;

    @SerializedName("type")
    private String trailerType;


    // Getters and Setters

    public String getIdTrailer() {
        return trailerId;
    }

    public void setIdTrailer(String id) {
        this.trailerId = id;
    }

    public String getIso6391Trailer() {
        return trailerIso6391;
    }

    public void setIso6391Trailer(String iso6391) {
        this.trailerIso6391 = iso6391;
    }

    public String getIso31661Trailer() {
        return trailerIso31661;
    }

    public void setIso31661Trailer(String iso31661) {
        this.trailerIso6391 = iso31661;
    }

    public String getKeyTrailer() {
        return trailerKey;
    }

    public void setKeyTrailer(String key) {
        this.trailerKey = key;
    }

    public String getNameTrailer() {
        return trailerName;
    }

    public void setNameTrailer(String name) {
        this.trailerName = name;
    }

    public String getSiteTrailer() {
        return trailerSite;
    }

    public void setSiteTrailer(String site) {
        this.trailerSite = site;
    }

    public Integer getSizeTrailer() {
        return trailerSize;
    }

    public void setSizeTrailer(Integer size) {
        this.trailerSize = size;
    }

    public String getTypeTrailer() {
        return trailerType;
    }

    public void setTypeTrailer(String type) {
        this.trailerType = type;
    }

    public String getVideoImageThumb(MovieTrailer movieTrailer) {
        Log.d(TAG, "Url image: " + String.format(Constants.BASE_URL_THUMB_TRAILER, movieTrailer.getKeyTrailer()));
        return String.format(Constants.BASE_URL_THUMB_TRAILER, movieTrailer.getKeyTrailer());
    }


}






