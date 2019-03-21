package com.example.trivia;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    JSONArray questions;
    JSONArray incorrect_answersJSONArray;
    String category;
    String type;
    String difficulty;
    String question;
    String correct_answer;

    public interface Callback {
        void gotTriviaRequest(ArrayList<QuestionItem> questionItems);
        void gotTriviaRequestError(String message);
    }

    public TriviaRequest(Context context) {
        this.context = context;
    }

    public void getTriviaRequest(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=2", null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotTriviaRequestError("A network error occurred: " + errorMessage);
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<QuestionItem> questionItems = new ArrayList<>();
        questions = new JSONArray();

        try {
            questions = response.getJSONArray("results");
            for (int i = 0; i < questions.length(); i++) {

                JSONObject itemObject = questions.getJSONObject(i);

                category = itemObject.getString("category");
                type = itemObject.getString("type");
                difficulty = itemObject.getString("difficulty");
                question = itemObject.getString("question");
                correct_answer = itemObject.getString("correct_answer");

                incorrect_answersJSONArray = itemObject.getJSONArray("incorrect_answers");
                ArrayList<String> incorrect_answers = new ArrayList<>();
                for (int j = 0; j < incorrect_answersJSONArray.length(); j++) {
                    incorrect_answers.add(j, String.valueOf(incorrect_answersJSONArray.get(j)));
                }

                QuestionItem questionItem = new QuestionItem();
                questionItem.setCategory(category);
                questionItem.setType(type);
                questionItem.setDifficulty(difficulty);
                questionItem.setQuestion(question);
                questionItem.setCorrect_answer(correct_answer);
                questionItem.setIncorrect_answers(incorrect_answers);
                questionItems.add(questionItem);
            }
        }
        catch (JSONException e) {

            Log.d(TAG, "JSONException: " + e.toString());
        }

        activity.gotTriviaRequest(questionItems);
    }
}
