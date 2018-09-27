package com.example.android.poupularmoviesstage1;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.poupularmoviesstage1.model.Trailer;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by mostafa on 4/8/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> implements View.OnClickListener {
    ArrayList<Trailer> trailers = new ArrayList<>();
    Context context;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        this.context = context;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, parent, false);


        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerHolder holder,final int position) {
        holder.trailerTextView.setText(trailers.get(holder.getAdapterPosition()).getName());
        final String key = trailers.get(holder.getAdapterPosition()).getKey();
        String thumbnailUrl = "http://img.youtube.com/vi/" + key + "/0.jpg";
        Picasso.with(context).load(thumbnailUrl).into(holder.imageView);

        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + key));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    @Override
    public void onClick(View v) {

    }

    class TrailerHolder extends RecyclerView.ViewHolder {
        public TextView trailerTextView;
        public LinearLayout mLinearLayout;
        public ImageView imageView;


        public TrailerHolder(View itemView) {
            super(itemView);
            trailerTextView = itemView.findViewById(R.id.trailer_text_view);
            mLinearLayout = itemView.findViewById(R.id.item_trailer);
            imageView = itemView.findViewById(R.id.trailer_video);
        }

    }
}
