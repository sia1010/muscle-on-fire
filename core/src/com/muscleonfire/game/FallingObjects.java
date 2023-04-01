package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class FallingObjects extends GameObject{
    private boolean onFloor;

    public void falling_building_spawn(){
        object=new Rectangle();
        object.x=5;
        object.y=350;

        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_building.png"));

    }


    public void falling_glass_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60);
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_glass.png"));
    }

    public void falling_stone_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60); // randomly at the floor, can be at left or right
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_stone.png"));
    }

    public void falling_life_spawn(){

        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = MathUtils.random(30, 480 - 60);
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_life.png"));
    }

    public void falling_slime_spawn(Array<Floor> floors){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = floors.peek().getX()+MathUtils.random(0, 128 - 64);
        object.y=700;
        image=new Texture(Gdx.files.internal("Textures/FallingObjects/falling_slime.png"));
    }




    @Override
    public void transpose(float delta, float time_passed){
        super.transpose(delta, time_passed);
        object.y -= 200 * delta;
        object.y -= ((300 + time_passed) / 3) * delta;
        //object.y-=250*delta;
    }

    public boolean playerTouched(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.getHealthPoint().takeDamage(1);
            touched=true;
        }
        return touched;
    }

    public boolean playerTouchedLife(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.healDamage(1);
            touched=true;
        }
        return touched;
    }

    public boolean isTouchingFloor(Array<Floor> floors){
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
