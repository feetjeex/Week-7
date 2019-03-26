package com.example.trivia;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreDownloader implements Response.ErrorListener, Response.Listener<JSONArray> {

    Context context;
    JSONArray JSONScoreBoard;
    Callback activity;

    // HighScoreDownloader constructor
    public HighScoreDownloader(Context context) {
        this.context = context;
    }

    // Implementing the OnErrorResponse
    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotScoreBoardError("A network error occurred: " + errorMessage);
    }

    // Implementing the OnResponse
    @Override
    public void onResponse(JSONArray data) {

        // Declares a new ArrayList which holds the scores
        ArrayList<String> scoreBoard = new ArrayList<>();
        JSONScoreBoard = new JSONArray();

        // Loop to parse all the score data from the downloaded JSONObject
        try {
            for (int i = 0; i < data.length(); i++) {
                JSONObject selected = (JSONObject) data.get(i);

                // Data
                String names = selected.getString("Name ");
                String points = selected.getString("Points");
                String scoreBoardInput = points + " " + names;
                scoreBoard.add(scoreBoardInput);
            }
        }

        // Runs in case of an exception
        catch (JSONException e) {
            Toast.makeText(context, "JSONException: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        // Sorts the scoreBoard ArrayList and passes it to 'getScoreBoard'
        Collections.sort(scoreBoard, Collections.reverseOrder());
        activity.gotScoreBoard(scoreBoard);
    }

    // Implements the Callback method
    public interface Callback {
        void gotScoreBoard(ArrayList<String> scoreBoard);
        void gotScoreBoardError(String message);
    }

    // Implements the actual JSONRequest
    public void getScoreBoard(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://ide50-a11022388.legacy.cs50.io:8080/list", this, this);
        queue.add(jsonArrayRequest);
    }
}
