package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class GameObject{
    protected Rectangle object; // protected is in between public and private, can be used in this project
    protected Texture image;

    void spawn(){

    }
    void transpose(float delta){ // the camera position is constant, just the object moving upward
        object.y += 100 * delta;
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
