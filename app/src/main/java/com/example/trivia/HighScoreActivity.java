package com.example.trivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements HighScoreDownloader.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Instantiates a new HighScoreDownloader and calls the method 'getScoreBoard'
        HighScoreDownloader highScoreDownloader = new HighScoreDownloader(this);
        highScoreDownloader.getScoreBoard(this);

    }

    @Override
    public void gotScoreBoard(ArrayList<String> scoreBoard) {

        // Informs the user that the highscore data is successfully retrieved using a toast
        Toast.makeText(this , "Scoreboard data retrieved!", Toast.LENGTH_LONG).show();

        // Setting the ListView with the highscores
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scoreBoard);
        ListView hsListView = findViewById(R.id.highscoreListView);
        hsListView.setAdapter(categoriesAdapter);
    }

    @Override
    public void gotScoreBoardError(String message) {

        // Informs the user if an error occurred while retrieving the highscore data
        Toast.makeText(this , "Error retrieving highscore data!", Toast.LENGTH_LONG).show();
    }
}
