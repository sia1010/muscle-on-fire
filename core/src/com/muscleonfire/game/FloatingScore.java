package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FloatingScore {
    private int score;
    private float time_passed;

    // Constructor
    public FloatingScore(int score){ // pass score
        this.score = score; // set score
    }

    // drawScore method (return boolean value)
    public boolean drawScore(BitmapFont scorefont, MuscleOnFire game, float delta){ // pass scorefont, MuscleOnFire and delta
        time_passed += delta; // add delta to time_passed
        scorefont.draw(game.batch, "+" +String.valueOf(score), 270, 655 + (time_passed * 10)); // draw the score added

        return time_passed > 2; // return true if the time_passed more than 2 so that the score will be deleted after 2 seconds
    }

}
