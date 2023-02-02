package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class Floor extends GameObject {


    void spawn(){ // spawn a floor
        object = new Rectangle();
        object.height = 8;
        object.width = 128;
        object.x = MathUtils.random(0, 480 - 128);
        object.y = -20;
        image = new Texture(Gdx.files.internal("floor_original.png"));
    }


}
