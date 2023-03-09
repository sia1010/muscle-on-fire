package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import sun.font.TrueTypeFont;

import java.util.ArrayList;

public class Score {
    int score = 0, highScore;
    Array<FloatingScore> scoreToAdd = new Array<>();
    float timeSinceLastAddScore = 0;
    FileHandle highScoreFile;
    BitmapFont scorefont;

    public Score(MuscleOnFire game){
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = game.parameter;
        parameter.color = new Color(0.72f,0.53f,0.04f,1);
        scorefont = game.generator.generateFont(parameter);
    }

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

    public void drawScore(MuscleOnFire game, float delta) {
        // draw score
        game.font.draw(game.batch, "SCORE: "+ displayScore(), 150, 700);

        // draw high score
        game.font.draw(game.batch, "HIGHEST SCORE: "+ displayHighScore(), 70, 750);

        for(FloatingScore scoreDisplay : scoreToAdd){
            if(scoreDisplay.drawScore(scorefont, game, delta)){
                scoreToAdd.removeValue(scoreDisplay, true);
            }
        }
    }
}
