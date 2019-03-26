package com.example.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.ArrayList;

public class SubmitActivity extends AppCompatActivity implements HighScoreUploader.Callback{

    int score;
    String name;
    EditText submitName;

    // Shows the user a warning if an error is encountered during the uploading of the score
    @Override
    public void gotResponseError(VolleyError error) {
        Toast.makeText(this, "Internet problem.", Toast.LENGTH_LONG).show();
    }

    // Shows the user a toast if the score is successfully uploaded to the database
    @Override
    public void gotOnResponse(String response) {
        Toast.makeText(this, "Results have been posted!", Toast.LENGTH_LONG).show();

        // Directs user to the next activity using Intent
        Intent intent = new Intent(SubmitActivity.this, HighScoreActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        // Gets the user score from the previous activity using Intent
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);

        // Sets the Views of the UI elements
        TextView submitScore = findViewById(R.id.submitScore);
        Button submitSubmit = findViewById(R.id.submitSubmit);
        submitName = findViewById(R.id.submitName);
        submitScore.setText(String.valueOf(score));
    }

    // OnClick method of the submit button
    public void submit(View view) {

        // Instantiates a new HighScoreUploader and calls the 'sender' method using the score and name as parameters
        HighScoreUploader highScoreUploader = new HighScoreUploader(this);
        highScoreUploader.sender(score, submitName.getText().toString(),this);
        Toast.makeText(this, "Results loading...", Toast.LENGTH_LONG).show();
    }
}
