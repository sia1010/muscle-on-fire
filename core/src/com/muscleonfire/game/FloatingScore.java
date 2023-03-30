package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FloatingScore {
    private int score;
    private float time_passed;

    public FloatingScore(int score){
        this.score = score;
    }

    boolean drawScore(BitmapFont scorefont, MuscleOnFire game, float delta){
        time_passed += delta;
        scorefont.draw(game.batch, "+" +String.valueOf(score), 270, 655 + (time_passed * 10));

        return time_passed > 2;
    }

}
