package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

public class Health {
    int maxHealth = 3;
    int currHealth = 3;

    Texture filledHeart;
    Texture emptyHeart;
    public Health(){
        filledHeart = new Texture(Gdx.files.internal("heart_filled.png"));
        emptyHeart = new Texture(Gdx.files.internal("heart_empty.png"));
    }
}
