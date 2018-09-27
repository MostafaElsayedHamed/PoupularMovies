package com.example.android.poupularmoviesstage1;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.poupularmoviesstage1.Data.FavoriteContract;
import com.example.android.poupularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Movie> moviesList = new ArrayList<>();
    RecyclerView recyclerView;
    MovieAdapter adapter;
    public ArrayList<Integer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_main);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        new MyAsyncTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=" + ApiKey.API_KEY);




    }


    public ArrayList<Integer> all(Cursor mCursor) {
        ArrayList<Integer> list = new ArrayList<>();

        if (mCursor.moveToFirst()) {
            do {
                list.add(mCursor.getInt(mCursor.getColumnIndex(FavoriteContract.FavoriteEntry.MOVIE_ID)));
            } while (mCursor.moveToNext());
        }

        return list;
    }


    private Cursor getAllId() {
        return getContentResolver().query(
                FavoriteContract.FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null);
    }


    public void loadInfo(String jsonString) {
        try {
            if (jsonString != null) {
                JSONObject moviesObject = new JSONObject(jsonString);
                JSONArray moviesArray = moviesObject.getJSONArray("results");

                for (int i = 0; i <= moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    Movie movieItem = new Movie();
                    movieItem.setTitle(movie.getString("title"));
                    movieItem.set_Id(movie.getInt("id"));
                    movieItem.setBackdrop_path(movie.getString("backdrop_path"));
                    movieItem.setOriginal_title(movie.getString("original_title"));
                    movieItem.setOriginal_language(movie.getString("original_language"));
                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setRelease_date("Unknown Release Date");
                    } else {
                        movieItem.setRelease_date(movie.getString("release_date"));
                    }
                    movieItem.setPopularity(movie.getString("popularity"));
                    movieItem.setVote_average(movie.getString("vote_average"));
                    if (movie.getString("poster_path") == "null") {
                        movieItem.setPoster_path("http://www.classicposters.com/images/nopicture.gif");
                    } else {
                        movieItem.setPoster_path("http://image.tmdb.org/t/p/" + "w500" + movie.getString("poster_path"));
                    }
                    moviesList.add(movieItem);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MovieAdapter(this, moviesList);
        recyclerView.setAdapter(adapter);


    }

    public class MyAsyncTask extends AsyncTask<String, Void, String> {
        String response;
        MyHttp http = new MyHttp();

        @Override
        protected String doInBackground(String... params) {

            Uri builtUri = Uri.parse(params [0]).buildUpon()
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
                loadInfo(response);
            }
        }


    }

    public void loadOneInfo(String jsonString) {
        try {
            if (jsonString != null) {
                JSONObject movie = new JSONObject(jsonString);
                    Movie movieItem = new Movie();
                    movieItem.setTitle(movie.getString("title"));
                    movieItem.set_Id(movie.getInt("id"));
                    movieItem.setBackdrop_path(movie.getString("backdrop_path"));
                    movieItem.setOriginal_title(movie.getString("original_title"));
                    movieItem.setOriginal_language(movie.getString("original_language"));
                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setRelease_date("Unknown Release Date");
                    } else {
                        movieItem.setRelease_date(movie.getString("release_date"));
                    }
                    movieItem.setPopularity(movie.getString("popularity"));
                    movieItem.setVote_average(movie.getString("vote_average"));
                    if (movie.getString("poster_path") == "null") {
                        movieItem.setPoster_path("http://www.classicposters.com/images/nopicture.gif");
                    } else {
                        movieItem.setPoster_path("http://image.tmdb.org/t/p/" + "w500" + movie.getString("poster_path"));
                    }
                    moviesList.add(movieItem);
                }

//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new MovieAdapter(this, moviesList);
        recyclerView.setAdapter(adapter);


    }
    public class FavoriteAsyncTask extends AsyncTask<String, Void, String> {
        String response;
        MyHttp http = new MyHttp();

        @Override
        protected String doInBackground(String... params) {

            Uri builtUri = Uri.parse(params [0]).buildUpon()
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
                loadOneInfo(response);
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.top_rated:
                setTitle( R.string.top_rated );
                moviesList = new ArrayList<>();
                recyclerView.setAdapter(null);
                new MyAsyncTask().execute("https://api.themoviedb.org/3/movie/top_rated?api_key=" + ApiKey.API_KEY);
                return true;

            case R.id.most_popular:
                setTitle( R.string.most_popular );
                moviesList = new ArrayList<>();
                recyclerView.setAdapter(null);
                new MyAsyncTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=" + ApiKey.API_KEY);
                return true;

            case R.id.favorite_menu:
                setTitle( R.string.favorite );
                moviesList = new ArrayList<>();
                recyclerView.setAdapter(null);
                Cursor cursor = getAllId();
                list = all(cursor);
                cursor.close();
                for (int i : list) {
                    new FavoriteAsyncTask().execute("https://api.themoviedb.org/3/movie/" + i + "?api_key=" + ApiKey.API_KEY);
                }


            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


