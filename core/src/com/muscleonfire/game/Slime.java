package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Slime extends GameObject {
    boolean move_right;
    boolean changeDirection=false;
    float time=0;
    boolean onFloor;
    Floor currentFloor;
    Animation<TextureRegion> slimemove;
    void onfloor_spawn(GameObject slime){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = slime.getX();
        object.y = slime.getY();

        if (MathUtils.random(1,2) == 1){
            move_right = true;
            image=new Texture(Gdx.files.internal("slime_right.png"));
        } else {
            move_right = false;
            image=new Texture(Gdx.files.internal("slime_left.png"));
        }

    }
    void dieSlime_spawn(GameObject onfloorslime){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = onfloorslime.getX();
        object.y = onfloorslime.getY();
        image=new Texture(Gdx.files.internal("slime_die.png"));
        time = 0;

    }

    void checkMovingDirection(){
        // change direction if the boolean is true
        if (changeDirection) {
            if (move_right) {
                move_right = false;
                image = new Texture(Gdx.files.internal("slime_left.png"));
            } else {
                move_right = true;
                image = new Texture(Gdx.files.internal("slime_right.png"));
            }
            changeDirection = false;
        }

        // force left/right if position too to the side
        if (object.x > 360) {
            move_right = false;
            image = new Texture(Gdx.files.internal("slime_left.png"));
        }
        if (object.x < 40) {
            move_right = true;
            image = new Texture(Gdx.files.internal("slime_right.png"));
        }
    }


    void move(float delta) {
        if(onFloor) {
            if (move_right) {
                object.x += 150 * delta;
            } else {
                object.x -= 150 * delta;
            }
            if(time > 3){
                changeDirection = true;
                time = MathUtils.random(1,2);
            }
        }
        time+=delta;
    }

    void fall(Rectangle slime, float delta, Array<Floor> floors, float time_passed){
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

    boolean playerTouched(Player pat) {
        if (pat.object.overlaps(object)) {
            pat.takeDamage(1);

            return true;
        }
        return  false;
    }

}
