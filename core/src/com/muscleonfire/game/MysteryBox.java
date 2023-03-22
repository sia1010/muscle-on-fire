package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MysteryBox extends GameObject{

    Rectangle power_box;

    Texture image_power;

    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Mystery Box.png"));

        power_box = new Rectangle();
        power_box.height = 17;
        power_box.width = 33;
        image_power = new Texture(Gdx.files.internal("Mystery Box.png"));

    }
}

