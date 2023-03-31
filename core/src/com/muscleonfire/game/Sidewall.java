package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Sidewall extends GameObject{

    void spawn(){
        object = new Rectangle();
        object.x=0;
        object.y=0;
        image=new Texture(Gdx.files.internal("Walls.png"));
    }
}
