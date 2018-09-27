package com.example.android.poupularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.android.poupularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mostafa on 3/31/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements View.OnClickListener{

    ArrayList<Movie> movies = new ArrayList<>();
    Context context;


    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);


        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        Picasso.with(context).load(movies.get(holder.getAdapterPosition()).getPoster_path()).into(holder.image);

        final String originalTitle = movies.get(holder.getAdapterPosition()).getOriginal_title();
        final String overview = movies.get(holder.getAdapterPosition()).getOverview();
        final String poster = movies.get(holder.getAdapterPosition()).getPoster_path();
        final String voteAverage = movies.get(holder.getAdapterPosition()).getVote_average();
        final String releaseDate = movies.get(holder.getAdapterPosition()).getRelease_date();
        final int id = movies.get(holder.getAdapterPosition()).get_Id();



        holder.mlinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context,MovieDetails.class);
                intent.putExtra("originalTitle",originalTitle);
                intent.putExtra("overview",overview);
                intent.putExtra("poster",poster);
                intent.putExtra("voteAverage",voteAverage);
                intent.putExtra("releaseDate",releaseDate);
                intent.putExtra("ID",id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @Override
    public void onClick(View v) {

    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mlinearLayout;
        public ImageView image;


        public MovieViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_item_view);
            mlinearLayout =  itemView.findViewById(R.id.linear_item_view);

        }
    }


}
