package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Score {
    int score = 0,highScore;
    FileHandle highScoreFile;

    void openHighScoreFile(){
        // open highScore.txt file and set highScore to the record
        // if no record exists, make one and set value to zero
        highScoreFile = Gdx.files.internal("highScore.txt");
        if(highScoreFile.exists()){
            highScore = Integer.parseInt(highScoreFile.readString());
        } else {
            highScore = 0;
            highScoreFile.writeString(Integer.toString(highScore), false);
        }
    }
    void addScore(float delta){
        score += (delta*100);
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
