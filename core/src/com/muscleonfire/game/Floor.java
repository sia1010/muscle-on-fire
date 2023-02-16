package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


public class Floor extends GameObject { // inheritance


    void spawn(){ // spawn a floor
        object = new Rectangle(); // from GameObject
        object.height = 8;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -200; // below screen
        image = new Texture(Gdx.files.internal("floor_original.png"));
    }



}
