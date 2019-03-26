package com.example.trivia;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class HighScoreUploader {

    RequestQueue queue;
    Context context;

    // Constructor of the class
    public HighScoreUploader(Context context) {
        this.context = context;
    }

    // Uploads the users points to the server
    public void sender(final int points, final String name, final Callback activity){

        String url = "https://ide50-a11022388.legacy.cs50.io:8080/list";

        // Implementation of the OnResponse and OnErrorResponse listeners
        StringRequest newStringRequest = new StringRequest(Request.Method.POST, url,

                // Responds with the response as a String on success
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        activity.gotOnResponse(response);

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // On error responds with the error
                        activity.gotResponseError(error);
                    }
                }) {

            // Puts the Strings Points and Names into the database
            protected Map<String, String> getParams() {
                Map<String, String> InputData = new HashMap<>();
                InputData.put("Points", String.valueOf(points));
                InputData.put("Name ", name);
                return InputData;
            }
        };

        queue = Volley.newRequestQueue(context);
        queue.add(newStringRequest);
    }

    // Implements the Callback functionality
    public interface Callback {
        void gotOnResponse(String response);
        void gotResponseError(VolleyError error);
    }
}


