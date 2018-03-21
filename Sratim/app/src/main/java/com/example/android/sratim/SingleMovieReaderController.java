package com.example.android.sratim;

/**
 * Created by Android on 20/03/2018.
 */
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

// Class for reading server data:
public class SingleMovieReaderController extends MovieController {

private ArrayList Movies;
    private ArrayList<Movie> movies;
    // ctor:
    public SingleMovieReaderController(Activity activity) {

        super(activity);


    }

    // Read all countries from the server:
    public void readAllMovies(String str) {
        httpRequest httpRequest = new httpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/search/movie?api_key=4c9685f64a32ab29908d264dbffbbda6&language=en-US&query="+str+"&page=1&include_adult=false");
    }

    // Got country from the server - update in activity:
    public void onSuccess(String downloadedText) {

        try {


            // Translate all to a JSON array:
            JSONObject jsonArray = new JSONObject(downloadedText);
            JSONArray resultArray = jsonArray.getJSONArray("results");
            // Create a new array list to hold all candies:
            Movies = new ArrayList<>();
            movies = new ArrayList<Movie>();
            // Run on all JSON objects:
            for (int i = 0; i < jsonArray.length(); i++) {

                // Convert each candy from a JSON object into a Candy object:
                JSONObject jsonObject = resultArray.getJSONObject(i);
                String name = jsonObject.getString("title");
                String overview = jsonObject.getString("overview");
                String image = jsonObject.getString("poster_path");
                // Add the candy object into the candies array:
                Movie newMovie = new Movie(name, overview, image);
                Movies.add(name);movies.add(newMovie);
            }
            Log.d("message", downloadedText);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, Movies);

            // Display all:
            listViewMovies.setAdapter(adapter);
           listViewMovies.setOnItemClickListener(
                   new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                           String selectedMovie = String.valueOf(adapterView.getItemAtPosition(i));
                           Intent intent = new Intent(activity, ManualNew.class);
                           intent.putExtra("Title", movies.get(i).getSubject());
                           intent.putExtra("Body", movies.get(i).getBody());
                           intent.putExtra("Url", movies.get(i).getUrl());
                       }
                   }
           );
        }
        catch (JSONException ex) {
            Toast.makeText(activity, "Error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        progressDialog.dismiss();
    }

}