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

    private static final String TAG = "MainActivity";

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
    int score = 0;
    int buttonChosen;
    String answerChosen;
    String correctAnswer;
    ArrayList<String> shufflerInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        score = 0;
        TriviaRequest x = new TriviaRequest(this);
        x.getTriviaRequest(this);
        
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

    @Override
    public void gotTriviaRequest(ArrayList<QuestionItem> questionItems) {

        questions = questionItems;
        Log.d(TAG, "Amount of questions: " + questions.size());
        uiSetter(0);
        gameplayScore.setText(String.valueOf(0));
    }

    public int answerChecker(String correctAnswer, String answerChosen) {

        if (correctAnswer.equals(answerChosen)) {
            score++;
        }

        return score;
    }

    public void uiSetter(int counter) {

        String difficulty;
        String type;
        String category;
        String question;


        QuestionItem items = (QuestionItem) questions.get(counter);
        difficulty = items.getDifficulty();
        type = items.getType();
        category = items.getCategory();
        question = items.getQuestion();
        correctAnswer = items.getCorrect_answer();
        shufflerInput = items.getIncorrect_answers();
        shufflerInput.add(correctAnswer);
        Collections.shuffle(shufflerInput);

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

    public void confirmation(View view) {

        counter++;
        if (counter == questions.size()) {
            Intent intent = new Intent(this, SubmitActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }

        button1.setBackgroundColor(Color.parseColor("#ffffff"));
        button2.setBackgroundColor(Color.parseColor("#ffffff"));
        button3.setBackgroundColor(Color.parseColor("#ffffff"));
        button4.setBackgroundColor(Color.parseColor("#ffffff"));

        answerChosen = shufflerInput.get(buttonChosen);
        answerChecker(correctAnswer, answerChosen);
        Log.d(TAG, "answerchosen: " + answerChosen);
        Log.d(TAG, "correctanswer: " + correctAnswer);

        gameplayScore.setText(String.valueOf(score));
        uiSetter(counter);
    }

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

    @Override
    public void gotTriviaRequestError(String message) {
        Toast.makeText(this, "No internet connection.", Toast.LENGTH_LONG).show();
    }
}
