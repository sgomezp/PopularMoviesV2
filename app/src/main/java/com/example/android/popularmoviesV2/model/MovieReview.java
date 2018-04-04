package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sgomezp on 27/03/2018.
 */

public class MovieReview {

    // Movie Review Id
    @SerializedName("id")
    private String reviewId;

    // Movie Review Author
    @SerializedName("author")
    private String reviewAuthor;

    // Movie Review Content
    @SerializedName("content")
    private String reviewContent;

    // Movie Review Url
    @SerializedName("url")
    private String reviewUrl;



    /*private MovieReview(Parcel in){
        this.reviewId = in.readString();
        this.reviewAuthor = in.readString();
        this.reviewContent = in.readString();
        this.reviewUrl = in.readString();
    }
*/

    /**
     * Getters and Setters methods
     */

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
    }

   /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeString(reviewId);
        dest.writeString(reviewAuthor);
        dest.writeString(reviewContent);
        dest.writeString(reviewUrl);

    }

    public static final Parcelable.Creator<MovieReview>
            CREATOR = new Parcelable.Creator<MovieReview>(){

        @Override
        public MovieReview createFromParcel(Parcel source){
            return new MovieReview(source);
        }

        @Override
        public MovieReview[] newArray(int size){
            return new MovieReview[size];
        }

    };*/
}
