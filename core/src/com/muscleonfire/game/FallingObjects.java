package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class FallingObjects extends GameObject{
    private boolean onFloor;

    //spawn falling building on the screen
    public void falling_building_spawn(){
        object=new Rectangle(); //object inherits from gameobject class
        object.x=5;
        object.y=350;
        //set the location of the building
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_building.png"));

    }

    //spawn falling glass on the screen
    public void falling_glass_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60); //the glass will be spawned randomly within 30-420 x location
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_glass.png"));
    }
    //spawn falling stone on the screen
    public void falling_stone_spawn(){

        object = new Rectangle();
        object.height = 32;
        object.width = 32;
        object.x = MathUtils.random(30, 480 - 60); //the stone will be spawned randomly within 30-420 x location
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_stone.png"));
    }
    //spawn falling life on the screen
    public void falling_life_spawn(){

        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = MathUtils.random(30, 480 - 60);//the life will be spawned randomly within 30-420 x location
        object.y = 700;
        image = new Texture(Gdx.files.internal("Textures/FallingObjects/falling_life.png"));
    }
    //spawn falling slime on the screen
    public void falling_slime_spawn(Array<Floor> floors){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = floors.peek().getX()+MathUtils.random(0, 128 - 64); //the slime will be spawned randomly on the floors
        object.y=700;
        image=new Texture(Gdx.files.internal("Textures/FallingObjects/falling_slime.png"));
    }




    @Override
    public void transpose(float delta, float time_passed){
        super.transpose(delta, time_passed); //superclass to make the objects fall instead of going up
        object.y -= 200 * delta; //make the object going up
        object.y -= ((300 + time_passed) / 3) * delta; //make the object going up even faster when time passes

    }

    //check if player touches the falling objects(except foe life)
    public boolean playerTouched(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.getHealthPoint().takeDamage(1); //player will lose 1 life
            touched=true;
        }
        return touched;
    }

    //check if player touches falling life
    public boolean playerTouchedLife(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.getHealthPoint().healDamage(1); //player will get 1 life
            touched=true;
        }
        return touched;
    }

    //check whether the slime is touching the floor
    public boolean isTouchingFloor(Array<Floor> floors){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (object.overlaps(floor.object)) {
                onFloor = true;
            }
        }
        return onFloor;
    }


}
