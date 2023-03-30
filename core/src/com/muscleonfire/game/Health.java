package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.w3c.dom.Text;

public class Health {
    int maxHealth = 3;
    int currHealth = 3;

    Texture filledHeart;
    Texture emptyHeart;
    Texture flashHeart;

    public Health(){
        filledHeart = new Texture(Gdx.files.internal("Textures/health/heart_filled.png"));
        emptyHeart = new Texture(Gdx.files.internal("Textures/health/heart_empty.png"));
        flashHeart = new Texture(Gdx.files.internal("Textures/health/heart_flash.png"));
    }
}
