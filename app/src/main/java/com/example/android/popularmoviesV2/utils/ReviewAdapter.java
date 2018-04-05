package com.example.android.popularmoviesV2.utils;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmoviesV2.R;
import com.example.android.popularmoviesV2.model.MovieReview;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sgomezp on 28/03/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();

    private List<MovieReview> mMovieReviewList;
    private int mLayout;
    private Context mContext;

    public ReviewAdapter(List<MovieReview> movieReviewList, int layout, Context context) {
        mMovieReviewList = movieReviewList;
        mLayout = layout;
        mContext = context;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Create a new View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    //Create new views (invoked by the layout manager)

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {

        Log.d(TAG, "onBindViewHolder: Position:" + position);

        final MovieReview movieReview = mMovieReviewList.get(position);
        Log.d(TAG, "onBindViewHolder: position: " + mMovieReviewList.get(position));
        holder.mTextViewReviewAuthor.setText(movieReview.getReviewAuthor());

        holder.mTextViewReview.setText(movieReview.getReviewContent());

    }

    public void updateRecyclerData(List<MovieReview> newReview) {
        this.mMovieReviewList = newReview;
        notifyDataSetChanged();
    }

    // Replace the contents of a view (invoke by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if (mMovieReviewList == null) {
            Log.d(TAG, "getItemCount: mMovieReviewList es null");
            return 0;
        } else {
            Log.d(TAG, "getItemCount: mMovieReviewList " + mMovieReviewList.size());
            return (mMovieReviewList.size());
        }
    }

    // Custom ViewHolder class
    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_review)
        CardView cardView;

        //@BindView(R.id.expand_view) //Box expandable
        //ExpandableTextView mExpandableView;
        @BindView(R.id.tv_author_review)  //Author of the review
                TextView mTextViewReviewAuthor;
        @BindView(R.id.content_review) //Review
                TextView mTextViewReview;

        private ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
