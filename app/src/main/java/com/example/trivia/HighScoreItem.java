package com.example.trivia;

import java.io.Serializable;

public class HighScoreItem implements Serializable {

    String username;
    int score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
