package com.muscleonfire.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloatingScore {
    int score;
    float time_passed;

    public FloatingScore(int score){
        this.score = score;
    }

    boolean drawScore(MuscleOnFire game, float delta){
        time_passed += delta;
        game.parameter.color = new Color(0.72f,0.53f,0.04f,1 - time_passed / 2);
        game.font = game.generator.generateFont(game.parameter);
        game.font.draw(game.batch, "+" +String.valueOf(score), 270, 655 + (time_passed * 10));
        game.parameter.color = new Color(0,0,0,1);
        game.font = game.generator.generateFont(game.parameter);
        return time_passed > 2;
    }

}
