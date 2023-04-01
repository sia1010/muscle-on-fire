package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Slime extends GameObject {

    private boolean move_right;
    private boolean changeDirection=false;
    private float time=0;
    private boolean onFloor;
    private Floor currentFloor;




    public float getTime() {
        return time;
    }


    public boolean isOnFloor() {
        return onFloor;
    }

    // spawn onfloor slime
    public void onfloor_spawn(GameObject slime){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = slime.getX(); //get the location of the slime which just touch the floor
        object.y = slime.getY();
        //spawn slime to go left or right
        if (MathUtils.random(1,2) == 1){
            move_right = true;
            image=new Texture(Gdx.files.internal("Textures/slime/slime_right.png"));
        } else {
            move_right = false;
            image=new Texture(Gdx.files.internal("Textures/slime/slime_left.png"));
        }

    }
    //spawn die slime
    public void dieSlime_spawn(GameObject onfloorslime){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = onfloorslime.getX(); //get the location od the slime which is moving on the floor
        object.y = onfloorslime.getY();
        image=new Texture(Gdx.files.internal("Textures/slime/slime_die.png"));
        time = 0;

    }


    //check the moving direction of the slime
    public void checkMovingDirection(){
        // change direction if the boolean is true
        if (changeDirection) {
            if (move_right) {
                move_right = false;
                image = new Texture(Gdx.files.internal("Textures/slime/slime_left.png"));
            } else {
                move_right = true;
                image = new Texture(Gdx.files.internal("Textures/slime/slime_right.png"));
            }
            changeDirection = false;
        }

        // force left/right if position too to the side
        if (object.x > 360) {
            move_right = false;
            image = new Texture(Gdx.files.internal("Textures/slime/slime_left.png"));
        }
        if (object.x < 40) {
            move_right = true;
            image = new Texture(Gdx.files.internal("Textures/slime/slime_right.png"));
        }
    }

//make the slime to move right or left
    public void move(float delta) {
        //check of the slime is on the floor
        if(onFloor) {
            if (move_right) {
                object.x += 150 * delta;
            } else {
                object.x -= 150 * delta;
            }
            // the time exceeds 3 then the slime will change it direction
            if(time > 3){
                changeDirection = true;
                time = MathUtils.random(1,2);
            }
        }
        time+=delta;
    }

    //make the slime to fall when it is not on the floor
    public void fall(Rectangle slime, float delta, Array<Floor> floors, float time_passed){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (slime.overlaps(floor.object)) { // floor.object = the rectangle
                onFloor = true;
                currentFloor = floor;
            }
        }
        // if not on floor, fall down
        if(!onFloor){
            object.y -= 200 * delta;
            object.y -= ((300 + time_passed) / 3) * delta;
        } else {
            object.y = currentFloor.getY() + 8;
        }
    }

    //if player touches the slime, player will lose 1 life
    public boolean playerTouched(Player pat) {
        if (pat.object.overlaps(object)) {
            pat.getHealthPoint().takeDamage(1);

            return true;
        }
        return  false;
    }

}
