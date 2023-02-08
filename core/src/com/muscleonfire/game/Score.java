package com.muscleonfire.game;

public class Score {
    int score = 0;

    void addScore(float delta){
        score += (delta*100);
    }

    String displayScore(){
        return String.valueOf(score);
    }

}
