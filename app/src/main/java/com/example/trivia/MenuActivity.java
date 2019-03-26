package com.example.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MenuActivity extends AppCompatActivity {

    String amounts;
    String difficulties;
    String types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Declaring and Initializing the String Arrays for the spinners
        String[] amount = new String[]{"Five", "Ten", "Twenty"};
        String[] difficulty = new String[]{"Any", "Easy", "Medium", "Hard"};
        String[] type = new String[]{"Any","Multiple choice", "True or False"};

        // Finding the View ID's of the spinners and the Button
        Spinner spinnerAmount = findViewById(R.id.menuSpinnerAmount);
        Spinner spinnerDifficulty = findViewById(R.id.menuSpinnerDifficulty);
        Spinner spinnerType = findViewById(R.id.menuSpinnerType);
        Button startButton = findViewById(R.id.menuStart);

        // Setting the adapter for the Spinner spinnerAmount
        ArrayAdapter<String> amountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, amount);
        amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmount.setAdapter(amountAdapter);

        // Setting the adapter for the Spinner spinnerDifficulty
        ArrayAdapter<String> difficultyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, difficulty);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(difficultyAdapter);

        // Setting the adapter for the Spinner spinnerType
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        // Sets the onItemSelectedListener for the Spinner spinnerAmount
        SpinnerAmountClick spinnerAmountClick = new SpinnerAmountClick();
        spinnerAmount.setOnItemSelectedListener(spinnerAmountClick);

        // Sets the onItemSelectedListener for the Spinner spinnerDifficulty
        SpinnerDifficultyClick spinnerDifficultyClick = new SpinnerDifficultyClick();
        spinnerDifficulty.setOnItemSelectedListener(spinnerDifficultyClick);

        // Sets the onItemSelectedListener for the Spinner spinnerType
        SpinnerTypeClick spinnerTypeClick = new SpinnerTypeClick();
        spinnerType.setOnItemSelectedListener(spinnerTypeClick);
    }

    // Implements the start button
    // Transfers the user to the next activity using Intent
    public void buttonStart (View view) {
        Intent intent = new Intent(MenuActivity.this, GamePlayActivity.class);
        intent.putExtra("questionAmount", amounts);
        intent.putExtra("questionDifficulty", difficulties);
        intent.putExtra("questionType", types);
        startActivity(intent);
    }

    // Implements the onItemSelectedListener for the Spinner spinnerAmount
    private class SpinnerAmountClick implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            amounts = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // Implements the onItemSelectedListener for the Spinner spinnerDifficulty
    private class SpinnerDifficultyClick implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            difficulties = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // Implements the onItemSelectedListener for the Spinner spinnerType
    private class SpinnerTypeClick implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            types = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
