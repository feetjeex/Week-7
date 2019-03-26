package com.example.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GamePlayActivity extends AppCompatActivity implements TriviaRequest.Callback {

    ArrayList<QuestionItem> questionItems = new ArrayList<>();
    ArrayList<QuestionItem> questions = new ArrayList<>();
    TextView gameplayDifficulty;
    TextView gameplayType;
    TextView gameplayCategory;
    TextView gameplayQuestion;
    TextView gameplayScore;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button confirmation;
    ArrayList<String> shufflerOutput;
    int counter = 0;
    int score;
    int buttonChosen;
    String answerChosen;
    String correctAnswer;
    ArrayList<String> shufflerInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        // OnCreate, sets the score to zero and instantiates a new TriviaRequest
        score = 0;
        Intent intent = getIntent();
        String amounts = intent.getStringExtra("questionAmount");
        String difficulties = intent.getStringExtra("questionDifficulty");
        String types = intent.getStringExtra("questionType");
        TriviaRequest x = new TriviaRequest(this, amounts, difficulties, types);
        x.getTriviaRequest(this);

        // Find the views of all the UI/XML elements
        gameplayDifficulty = findViewById(R.id.gameplayDifficulty);
        gameplayType = findViewById(R.id.gameplayType);
        gameplayCategory = findViewById(R.id.gameplayCategory);
        gameplayQuestion = findViewById(R.id.gameplayQuestion);
        gameplayScore = findViewById(R.id.gameplayScore);
        button1 = findViewById(R.id.gameplayAnswer1);
        button2 = findViewById(R.id.gameplayAnswer2);
        button3 = findViewById(R.id.gameplayAnswer3);
        button4 = findViewById(R.id.gameplayAnswer4);
        button1.setBackgroundColor(Color.parseColor("#ffffff"));
        button2.setBackgroundColor(Color.parseColor("#ffffff"));
        button3.setBackgroundColor(Color.parseColor("#ffffff"));
        button4.setBackgroundColor(Color.parseColor("#ffffff"));
        confirmation = findViewById(R.id.gameplayConfirm);
    }

    // Calls the Trivia Request downloader
    @Override
    public void gotTriviaRequest(ArrayList<QuestionItem> questionItems) {

        questions = questionItems;

        // Calls the method 'uiSetter' using the first question as a parameter
        uiSetter(0);
        gameplayScore.setText(String.valueOf(0));
    }

    // Method to check the validity of the user input 'answerChosen'
    public int answerChecker(String correctAnswer, String answerChosen) {

        // Increases the score counter on a valid user answer
        if (correctAnswer.equals(answerChosen)) {
            score++;
        }

        return score;
    }

    // Method which displays the current question on the UI using counter to keep track of the current question
    public void uiSetter(int counter) {

        String difficulty;
        String type;
        String category;
        String question;

        // Gets the current QuestionItem object using counter
        // Gets all the relevant fields from the current QuestionItem and shuffles the position of the answers
        QuestionItem items = (QuestionItem) questions.get(counter);
        difficulty = items.getDifficulty();
        type = items.getType();
        category = items.getCategory();
        question = items.getQuestion();
        correctAnswer = items.getCorrect_answer();
        shufflerInput = items.getIncorrect_answers();
        shufflerInput.add(correctAnswer);
        Collections.shuffle(shufflerInput);

        // If the question is a True/False question
        if (shufflerInput.size() == 2) {
            gameplayDifficulty.setText(difficulty);
            gameplayType.setText(type);
            gameplayCategory.setText(category);
            gameplayQuestion.setText(question);
            button1.setText(shufflerInput.get(0));
            button2.setText(shufflerInput.get(1));
            button3.setText("");
            button4.setText("");
        }

        // If the question is a Multiple Choice question
        else {
            gameplayDifficulty.setText(difficulty);
            gameplayType.setText(type);
            gameplayCategory.setText(category);
            gameplayQuestion.setText(question);
            button1.setText(shufflerInput.get(0));
            button2.setText(shufflerInput.get(1));
            button3.setText(shufflerInput.get(2));
            button4.setText(shufflerInput.get(3));
        }
    }

    // Method which is called after user presses 'confirm' button
    public void confirmation(View view) {

        // Counter increases by one on each press
        counter++;

        // Checks if the current question is the final question
        if (counter >= questions.size()) {

            // If the current question is the final question, directs the user to the next activity using Intent
            Intent intent = new Intent(this, SubmitActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        // If the current question is not the final question
        else {

            // Resets the background color of each button to white
            button1.setBackgroundColor(Color.parseColor("#ffffff"));
            button2.setBackgroundColor(Color.parseColor("#ffffff"));
            button3.setBackgroundColor(Color.parseColor("#ffffff"));
            button4.setBackgroundColor(Color.parseColor("#ffffff"));

            // Calling the method 'answerChecker' using the user input 'answerChosen'
            answerChosen = shufflerInput.get(buttonChosen);
            answerChecker(correctAnswer, answerChosen);

            // Sets the score counter and calls 'uiSetter' to display the next question on the UI
            gameplayScore.setText(String.valueOf(score));
            uiSetter(counter);
        }
    }

    // Sets the color of the background of the button that is pressed to grey
    // Sets all other buttons background to white
    // sets 'buttonChosen' to the proper value
    public void button1(View view) {
        button1.setBackgroundColor(Color.parseColor("#808080"));
        button2.setBackgroundColor(Color.parseColor("#ffffff"));
        button3.setBackgroundColor(Color.parseColor("#ffffff"));
        button4.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonChosen = 0;
    }

    public void button2(View view) {
        button1.setBackgroundColor(Color.parseColor("#ffffff"));
        button2.setBackgroundColor(Color.parseColor("#808080"));
        button3.setBackgroundColor(Color.parseColor("#ffffff"));
        button4.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonChosen = 1;
    }

    public void button3(View view) {
        button1.setBackgroundColor(Color.parseColor("#ffffff"));
        button2.setBackgroundColor(Color.parseColor("#ffffff"));
        button3.setBackgroundColor(Color.parseColor("#808080"));
        button4.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonChosen = 2;
    }

    public void button4(View view) {
        button1.setBackgroundColor(Color.parseColor("#ffffff"));
        button2.setBackgroundColor(Color.parseColor("#ffffff"));
        button3.setBackgroundColor(Color.parseColor("#ffffff"));
        button4.setBackgroundColor(Color.parseColor("#808080"));
        buttonChosen = 3;
    }

    // Informs the user that an error occurred while downloading questions
    @Override
    public void gotTriviaRequestError(String message) {
        Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show();
    }
}
