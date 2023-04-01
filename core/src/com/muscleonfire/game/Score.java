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
    private int score = 0, highScore;
    private Array<FloatingScore> scoreToAdd = new Array<>();
    private float timeSinceLastAddScore = 0;
    private FileHandle highScoreFile;
    private BitmapFont scorefont;


    // getter for score
    public int getScore() {
        return score;
    }

    // Constructor
    public Score(MuscleOnFire game){ // pass MuscleOnFire
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = game.parameter; // generate font parameter
        parameter.color = new Color(0.72f,0.53f,0.04f,1); // set the color to gold
        scorefont = game.generator.generateFont(parameter); // add the font properties into the scorefont
    }

    // openHighScoreFile method
    public void openHighScoreFile(){
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

    // addScore method
    public void addScore(float delta){ // pass delta
        timeSinceLastAddScore += delta;
        // add delta into the timeSinceLastAddScore
        // while the time is more than 0.1 second, the score will be added 10 and minus 0.1 second for the time
        while (timeSinceLastAddScore > 0.1f){ //0.1 second, it is a float, java cannot write floating value directly, need to add f behind
            score += 10;
            timeSinceLastAddScore -= 0.1f;
        }
    }

    // upScore method
    public void upScore(int up_amt){ // pass up_amt
        // add the new FloatingScore to scoreToAdd array
        scoreToAdd.add(new FloatingScore(up_amt));
        // add up_amt to score
        score += up_amt;
    }

    // displayScore method (return String value)
    public String displayScore(){
        return String.valueOf(score);
    }

    // displayHighScore method (return String value)
    public String displayHighScore(){
        return String.valueOf(highScore);
    }

    // saveScore method
    public void saveScore(){
        // if score is higher than highScore then will replace highScore
        if (score > highScore){
            highScore = score;
            // write the highScore into the highScoreFile
            highScoreFile.writeString(Integer.toString(highScore), false);
        }
    }

    // drawScore method
    public void drawScore(MuscleOnFire game, float delta) { // pass MuscleOnFire and delta
        // draw score
        game.font.draw(game.batch, "SCORE: "+ displayScore(), 150, 700);

        // draw high score
        game.font.draw(game.batch, "HIGHEST SCORE: "+ displayHighScore(), 70, 750);

        // draw the floating score when the player get more score added when touched something like MysteryBox or Rescue
        for(FloatingScore scoreDisplay : scoreToAdd){
            if(scoreDisplay.drawScore(scorefont, game, delta)){
                // remove the scoreDisplay from the array after drawing it
                scoreToAdd.removeValue(scoreDisplay, true);
            }
        }
    }
}
