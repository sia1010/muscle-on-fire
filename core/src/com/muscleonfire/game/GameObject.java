package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class GameObject{
    protected Rectangle object;
    protected Texture image;

    void transpose(float delta, float time_passed){
        object.y += ((300 + time_passed) / 3) * delta;
      //object.y+=100*delta;

    }

    float getX(){
        return object.x;
    }

    float getY(){
        return object.y;
    }
    Texture getTexture(){
        return image;
    }
}
