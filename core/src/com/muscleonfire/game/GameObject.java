package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public abstract class GameObject{
    protected Rectangle object;
    protected Texture image;

    public void transpose(float delta, float time_passed){
        object.y += ((300 + time_passed) / 3) * delta;
        //object.y+=100*delta;

    }

    public float getX(){
        return object.x;
    }

    public float getY(){
        return object.y;
    }
    public Texture getTexture(){
        return image;
    }
}
