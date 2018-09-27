package com.example.android.poupularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.poupularmoviesstage1.model.Reviews;

import java.util.ArrayList;

/**
 * Created by mostafa on 4/18/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {
    ArrayList<Reviews> reviews = new ArrayList<>();
    Context context;

    public ReviewsAdapter(Context context, ArrayList<Reviews> reviews) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewsAdapter.ReviewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reviews, parent, false);


        return new ReviewsHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsHolder holder, int position) {
        holder.authorTextView.setText(reviews.get(holder.getAdapterPosition()).getAuthor());
        holder.contentTextView.setText(reviews.get(holder.getAdapterPosition()).getContent());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
    class ReviewsHolder extends RecyclerView.ViewHolder{
        public TextView authorTextView;
        public TextView contentTextView;

        public ReviewsHolder(View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.reviews_author);
            contentTextView = itemView.findViewById(R.id.reviews_content);

        }
    }
}
