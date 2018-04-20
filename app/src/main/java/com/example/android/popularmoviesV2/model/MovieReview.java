package com.example.android.popularmoviesV2.model;

import com.google.gson.annotations.SerializedName;


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


}
