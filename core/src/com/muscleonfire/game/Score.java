package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.TimeUtils;

public class Score {
    int score = 0, highScore;
    float timeSinceLastAddScore = 0;
    FileHandle highScoreFile;

    void openHighScoreFile(){
        // open highScore.txt file and set highScore to the record
        // if no record exists, make one and set value to zero
        highScoreFile = Gdx.files.external("highScore.txt");
        if(highScoreFile.exists()){
            highScore = Integer.parseInt(highScoreFile.readString());
        } else {
            highScore = 0;
            highScoreFile.writeString(Integer.toString(highScore), false);
        }
    }
    void addScore(float delta){
        timeSinceLastAddScore += delta;
        while (timeSinceLastAddScore > 0.1f){ //0.1 second, it is a float, java cannot write floating value directly, need to add f behind
            score += 10;
            timeSinceLastAddScore -= 0.1f;
        }
    }

    void upScore(int up_amt){
        score += up_amt;
    }

    String displayScore(){
        return String.valueOf(score);
    }

    String displayHighScore(){
        return String.valueOf(highScore);
    }

    void saveScore(){
        if (score > highScore){
            highScore = score;
            highScoreFile.writeString(Integer.toString(highScore), false);
        }
    }

}
