package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class FallingObjects extends GameObject{

    boolean onFloor;
    float timetoAddDifficulty=0;
    void falling_building_spawn(){
        object=new Rectangle();
        object.x=5;
        object.y=350;

        image = new Texture(Gdx.files.internal("falling building.png"));

    }


    void falling_glass_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60);
        object.y = 700;
        image = new Texture(Gdx.files.internal("falling object(glass).png"));
    }

    void falling_stone_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60); // randomly at the floor, can be at left or right
        object.y = 700;
        image = new Texture(Gdx.files.internal("falling object (stone).png"));
    }

    void falling_life_spawn(){

        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = MathUtils.random(30, 480 - 60);
        object.y = 700;
        image = new Texture(Gdx.files.internal("falling_life.png"));
    }

    void falling_slime_spawn(Array<Floor> floors){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = floors.peek().getX()+MathUtils.random(0, 128 - 64);
        object.y=700;
        image=new Texture(Gdx.files.internal("slime.png"));
    }




    @Override
    void transpose(float delta, float time_passed){
        super.transpose(delta, time_passed);
        object.y -= 200 * delta;
        object.y -= ((300 + time_passed) / 3) * delta;
        //object.y-=250*delta;
    }

    boolean playerTouched(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.takeDamage(1);
            touched=true;
        }
        return touched;
    }

    boolean playerTouchedLife(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.healDamage(1);
            touched=true;
        }
        return touched;
    }

    boolean isTouchingFloor(Array<Floor> floors){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (object.overlaps(floor.object)) { // floor.object = the rectangle
                onFloor = true;
            }
        }
        return onFloor;
    }


}
