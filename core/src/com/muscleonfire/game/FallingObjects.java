package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class FallingObjects extends GameObject{

    void falling_building_spawn(){
        object=new Rectangle();
        object.x=5;
        object.y=350;

        image = new Texture(Gdx.files.internal("falling building.png"));

    }


    void falling_glass_spawn(){

        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = MathUtils.random(20, 480 - 20); // randomly at the floor, can be at left or right
        object.y = 700;
        image = new Texture(Gdx.files.internal("falling object(glass).png"));
    }

    void falling_stone_spawn(){

        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = MathUtils.random(20, 480 - 20); // randomly at the floor, can be at left or right
        object.y = 700;
        image = new Texture(Gdx.files.internal("falling object (stone).png"));
    }

    @Override
    void transpose(float delta){
        super.transpose(delta);
        object.y-=300*delta;
    }
}
