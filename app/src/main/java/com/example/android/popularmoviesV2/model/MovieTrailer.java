package com.example.android.popularmoviesV2.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sgomezp on 27/03/2018.
 */

public class MovieTrailer implements Parcelable {

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel source) {
            return new MovieTrailer(source);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
    // Movie Trailer Id
    @SerializedName("id")
    private String trailerId;
    // Movie Trailer name
    @SerializedName("name")
    private String trailerName;
    // Movie Trailer site
    @SerializedName("site")
    private String siteTrailer;
    // Movie Trailer type
    @SerializedName("type")
    private String trailerType;

    // Empty constructor
    public MovieTrailer() {

    }

    protected MovieTrailer(Parcel in) {
        this.trailerId = in.readString();
        this.trailerName = in.readString();
        this.siteTrailer = in.readString();
        this.trailerType = in.readString();
    }

    /**
     * Getters and Setters methods
     */

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public String getSiteTrailer() {
        return siteTrailer;
    }

    public void setTrailerType(String trailerType) {
        this.trailerType = trailerType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trailerId);
        dest.writeString(this.trailerName);
        dest.writeString(this.siteTrailer);
        dest.writeString(this.trailerType);
    }
}






