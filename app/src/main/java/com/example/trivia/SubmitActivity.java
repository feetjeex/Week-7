package com.example.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        TextView submitScore = findViewById(R.id.submitScore);
        Button submitSubmit = findViewById(R.id.submitSubmit);
        EditText submitName = findViewById(R.id.submitName);

        submitScore.setText(String.valueOf(score));
    }
}
