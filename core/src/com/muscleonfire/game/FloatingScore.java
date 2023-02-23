package com.muscleonfire.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloatingScore {
    int score;
    float time_passed;

    public FloatingScore(int score){
        this.score = score;
    }

    boolean drawScore(SpriteBatch batch, BitmapFont font, float delta){
        time_passed += delta;
        font.setColor(0,0,0, 1 - time_passed);
        font.draw(batch, "+" +String.valueOf(score), 270, 660 + (time_passed * 20));
        font.setColor(0,0,0,1);
        return time_passed > 1;
    }

}
