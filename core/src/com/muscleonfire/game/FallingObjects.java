package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class FallingObjects extends GameObject{

    void spawn(){
        object=new Rectangle();
        object.x=5;
        object.y=350;

        image = new Texture(Gdx.files.internal("falling building.png"));

    }
}
