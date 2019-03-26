package com.example.trivia;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    String amountsUsed;
    String difficultiesUsed;
    String typesUsed;

    // Implements the Callback for TriviaRequest
    public interface Callback {
        void gotTriviaRequest(ArrayList<QuestionItem> questionItems);
        void gotTriviaRequestError(String message);
    }

    // Constructor of the class
    public TriviaRequest(Context context, String amounts, String difficulties, String types) {
        this.context = context;
        amountsUsed = amounts;
        difficultiesUsed = difficulties;
        typesUsed = types;
    }

    // Implementation of the actual JSONRequest to the database
    public void getTriviaRequest(Callback activity) {
        this.activity = activity;

        // Start of the url request
        String urlString = "https://opentdb.com/api.php?";
        String urlAmount = "";
        String urlDifficulty = "";
        String urlType = "";

        // Setting the amount of questions
        switch (amountsUsed) {
            case "Five":
                urlAmount = "amount=5";
                break;

            case "Ten":
                urlAmount = "amount=10";
                break;

            case "Twenty":
                urlAmount = "amount=20";
                break;
        }

        // Setting the difficulty of the questions
        switch (difficultiesUsed) {
            case "Any":
                urlDifficulty = "";
                break;

            case "Easy":
                urlDifficulty = "&difficulty=easy";
                break;

            case "Medium":
                urlDifficulty = "&difficulty=medium";
                break;

            case "Hard":
                urlDifficulty = "&difficulty=hard";
                break;
        }

        // Choosing which type of questions will be downloaded
        switch (typesUsed) {
            case "Any":
                urlType = "";
                break;

            case "Multiple choice":
                urlType = "&type=multiple";
                break;

            case "True or False":
                urlType = "&type=boolean";
                break;
        }

        // Putting together to relevant URL
        String url = urlString + urlAmount + urlDifficulty + urlType;

        // Adding the request to the queue
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    // Implements the onErrorResponse
    @Override
    public void onErrorResponse(VolleyError error) {
        String errorMessage = error.getMessage();
        activity.gotTriviaRequestError("A network error occurred: " + errorMessage);
    }

    // Implements the OnResponse
    @Override
    public void onResponse(JSONObject response) {

        // Declares a new ArrayList of QuestionItems to hold the questions
        ArrayList<QuestionItem> questionItems = new ArrayList<>();
        questions = new JSONArray();

        // Loop to parse all the JSONObjects and extract all the fields of a QuestionItem object
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

                // Loop to parse all the incorrect answers contained in a JSONArray
                for (int j = 0; j < incorrect_answersJSONArray.length(); j++) {
                    incorrect_answers.add(j, String.valueOf(incorrect_answersJSONArray.get(j)));
                }

                // Fills in all the fields of the i'th QuestionItem object
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
            // Runs in case of an JSONException
            catch (JSONException e) {

                // Shows the user what went wrong
                Toast.makeText(context, "An error occurred: " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        // Passes the ArrayList questionItems to GamePlayActivity
        activity.gotTriviaRequest(questionItems);
    }
}
