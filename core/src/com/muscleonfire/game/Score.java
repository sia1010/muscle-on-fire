package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Score {
    int score = 0, highScore;
    Array<FloatingScore> scoreToAdd = new Array<>();
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
        scoreToAdd.add(new FloatingScore(up_amt));
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

    public void drawScore(SpriteBatch batch, BitmapFont font, float delta) {
        // draw score
        font.draw(batch, "SCORE: "+ displayScore(), 150, 700);

        // draw high score
        font.draw(batch, "HIGHEST SCORE: "+ displayHighScore(), 70, 750);

        for(FloatingScore scoreDisplay : scoreToAdd){
            if(scoreDisplay.drawScore(batch, font, delta)){
                scoreToAdd.removeValue(scoreDisplay, true);
            }
        }
    }
}
