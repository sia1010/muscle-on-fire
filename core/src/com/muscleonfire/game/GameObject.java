package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

public class GameObject{
    protected Rectangle object;
    protected Texture image;

    void spawn(){

    }
    void transpose(float delta){
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
