package com.example.android.poupularmoviesstage1;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.android.poupularmoviesstage1.Data.FavoriteContract;
import com.example.android.poupularmoviesstage1.model.Reviews;
import com.example.android.poupularmoviesstage1.model.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDetails extends AppCompatActivity {
    public ArrayList<Reviews> reviewsList = new ArrayList<>();
    public ArrayList<Trailer> trailersList = new ArrayList<>();
    RecyclerView trailerRecyclerView;
    RecyclerView reviewRecyclerView;
    TrailerAdapter trailerAdapter;
    ReviewsAdapter reviewsAdapter;

    LinearLayoutManager reviewLayoutManager;
    int rPosition;

    public int mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState != null) {

            rPosition = savedInstanceState.getInt("position");

        }else {

            rPosition = 0;

        }
        getIncomingIntent();
        final ToggleButton toggle = findViewById(R.id.favorite_toggle_button);
        newGetCheck(mId);
        toggle.setOnCheckedChangeListener(listener);


        trailerRecyclerView = findViewById(R.id.recycle_details_videos);
        trailerRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        new TrailerAsyncTask().execute("https://api.themoviedb.org/3/movie/" + mId + "/videos" + "?api_key=" + ApiKey.API_KEY);
        reviewRecyclerView = findViewById(R.id.recycle_details_reviews);
        reviewRecyclerView.setHasFixedSize(true);
        reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        new ReviewsAsyncTask().execute("https://api.themoviedb.org/3/movie/" + mId + "/reviews" + "?api_key=" + ApiKey.API_KEY);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",reviewLayoutManager.findFirstVisibleItemPosition());

    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                addNewId(mId);

            } else {
                removeId(mId);

            }
        }
    };


    public void newGetCheck(int id){
        String stringId = Integer.toString(id);
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        final Cursor cursor = getContentResolver().query(uri,null,null,null,null,null);
        final ToggleButton toggle = findViewById(R.id.favorite_toggle_button);
        if(!(cursor.moveToFirst()) || cursor.getCount()==0){
            toggle.setChecked(false);
        }else {
            toggle.setChecked(true);
        }
        cursor.close();
    }



    private void addNewId(int id) {
        ContentValues cv = new ContentValues();
        cv.put(FavoriteContract.FavoriteEntry.MOVIE_ID, id);
        Uri uri = getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, cv);

    }

    private void removeId(int id) {
        String stringId = Integer.toString(id);
        Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        getContentResolver().delete(uri ,null, null);
    }

    public void getIncomingIntent() {
        if (getIntent().hasExtra("poster") &&
                getIntent().hasExtra("releaseDate") &&
                getIntent().hasExtra("voteAverage") &&
                getIntent().hasExtra("originalTitle") &&
                getIntent().hasExtra("overview") &&
                getIntent().hasExtra("ID")) {
            String poster = getIntent().getStringExtra("poster");
            String releaseDate = getIntent().getStringExtra("releaseDate");
            String voteAverage = getIntent().getStringExtra("voteAverage");
            String originalTitle = getIntent().getStringExtra("originalTitle");
            String overview = getIntent().getStringExtra("overview");
            mId = getIntent().getIntExtra("ID", 0);

            setData(poster, releaseDate, voteAverage, originalTitle, overview);
        }

    }

    private void setData(String mPoster, String mReleaseDate, String mVoteAverage, String mOriginalTitle, String mOverview) {
        ImageView poster = findViewById(R.id.poster);
        Picasso.with(this).load(mPoster).into(poster);

        TextView releaseDate = findViewById(R.id.release_date);
        releaseDate.setText(mReleaseDate);
        TextView voteAverage = findViewById(R.id.vote_average);
        voteAverage.setText(String.format("%s/10", mVoteAverage));
        TextView originalTitle = findViewById(R.id.original_title);
        originalTitle.setText(mOriginalTitle);
        TextView overview = findViewById(R.id.overview);
        overview.setText(mOverview);

    }

    ///////////////////////////////////////////////////////////////////////////////
    // this part for getting data of movie trailers
    public void loadTrailerInfo(String jsonString) {
        try {
            if (jsonString != null) {
                JSONObject trailersObject = new JSONObject(jsonString);
                JSONArray trailersArray = trailersObject.getJSONArray("results");

                for (int i = 0; i <= trailersArray.length(); i++) {
                    JSONObject trailer = trailersArray.getJSONObject(i);
                    Trailer trailerItem = new Trailer();
                    trailerItem.setid(trailer.getString("id"));
                    if (trailer.getString("iso_639_1") == "null") {
                        trailerItem.setIso_639_1("No iso_639_1 was Found");
                    } else {
                        trailerItem.setIso_639_1(trailer.getString("iso_639_1"));
                    }
                    if (trailer.getString("iso_3166_1") == "null") {
                        trailerItem.setIso_3166_1("Unknown iso_3166_1");
                    } else {
                        trailerItem.setIso_3166_1(trailer.getString("iso_3166_1"));
                    }
                    trailerItem.setKey(trailer.getString("key"));
                    trailerItem.setName(trailer.getString("name"));
                    trailerItem.setSite(trailer.getString("site"));
                    trailerItem.setSize(trailer.getString("size"));
                    trailerItem.setType(trailer.getString("type"));

                    trailersList.add(trailerItem);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        trailerAdapter = new TrailerAdapter(this, trailersList);
        trailerRecyclerView.setAdapter(trailerAdapter);


    }



    public class TrailerAsyncTask extends AsyncTask<String, Void, String> {
        String response;
        MyHttp http = new MyHttp();

        @Override
        protected String doInBackground(String... params) {

            Uri builtUri = Uri.parse(params[0]).buildUpon()
                    .build();

            try {
                response = http.getJSON(builtUri);
                return response;
            } catch (Exception e) {
                return response;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (response != null) {
                loadTrailerInfo(response);
            }
        }


    }
    ///////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////
    // this part for getting data of movie Reviews
    public void loadReviewsInfo(String jsonString) {
        try {
            if (jsonString != null) {
                JSONObject reviewsObject = new JSONObject(jsonString);
                JSONArray reviewsArray = reviewsObject.getJSONArray("results");

                for (int i = 0; i <= reviewsArray.length(); i++) {
                    JSONObject review = reviewsArray.getJSONObject(i);
                    Reviews reviewItem = new Reviews();

                    reviewItem.setAuthor(review.getString("author"));
                    reviewItem.setContent(review.getString("content"));

                    reviewsList.add(reviewItem);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        reviewsAdapter = new ReviewsAdapter(this, reviewsList);
        reviewRecyclerView.setAdapter(reviewsAdapter);


    }

    public class ReviewsAsyncTask extends AsyncTask<String, Void, String> {
        String response;
        MyHttp http = new MyHttp();

        @Override
        protected String doInBackground(String... params) {

            Uri builtUri = Uri.parse(params[0]).buildUpon()
                    .build();

            try {
                response = http.getJSON(builtUri);
                return response;
            } catch (Exception e) {
                return response;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (response != null) {
                loadReviewsInfo(response);
            }
        }


    }
    ///////////////////////////////////////////////////////////////////////////////
}
