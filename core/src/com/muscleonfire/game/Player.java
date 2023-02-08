package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Player extends GameObject{

    boolean onFloor;
    Rectangle feet, head;
    float jumpTime = 0;
    boolean isJumping;

    void spawn(){ // spawn patrick
        // this is the main body
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = 240 - 32; // center - half of patrick's body width
        object.y = 600;

        // this is the feet (for collision detection or floor detection)
        feet = new Rectangle();
        feet.height = 4;
        feet.width = 36; // smaller than the object, if the body touch the floor but the feet not, then will drop

        head = new Rectangle();
        head.height = 4;
        head.width = 36; // smaller than the object, if the body touch the floor but the feet not, then will drop

        updateFeetAndHeadPosition();

        // initialise the picture of patrick
        image = new Texture(Gdx.files.internal("patrick_original.png"));
    }

    void updateFeetAndHeadPosition(){
        // make the head Rectangle above the body
        head.x = object.x + 14;
        head.y = object.y + 64;

        // make the feet Rectangle() located beneath the body
        feet.x = object.x + 14; // from left + 14 pixel (left 14, center 36 - feet, right 14 = 64 pixel)
        feet.y = object.y;
    }

    void fall(float delta, Array<Floor> floors){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (feet.overlaps(floor.object)) { // floor.object = the rectangle
                onFloor = true;
            }
        }

        // if not on floor, fall down
        if (!onFloor){
            object.y -= 300 * delta;
            updateFeetAndHeadPosition();
        }
    }

    void move(float delta, MuscleOnFire game, Array<Floor> floors){
        // check if got touch screen (use for loop for multiple touches)
        for(int i =0; i < 10; i++){ //for many fingers touch together
            if (Gdx.input.isTouched(i)){
                // if got touch, get the position of the touch
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.camera.unproject(touchPos);

                // if touch left of patrick, move patrick left
                if (touchPos.x < object.x) {
                    goLeft(100 * delta);
                }
                // if touch right of patrick, move patrick right
                if (touchPos.x > object.x) {
                    goRight(100 * delta);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            goLeft(150 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            goRight(150 * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && onFloor) {
            isJumping = true;
            jumpTime = 0;
        }
    }

    void goLeft(float px){ // move patrick left
        if(object.x>32){
            object.x -= px;
            updateFeetAndHeadPosition();
        }

    }

    void goRight(float px){ // move patrick right
        if(object.x<(480 - 64 - 32)) {
            object.x += px;
            updateFeetAndHeadPosition();
        }
    }

    void jump(float delta, Array<Floor> floors){
        if (isJumping && !headIsTouching(floors)) {
            object.y += 25 * Math.pow(0.01, jumpTime);
            jumpTime += delta;
            if (jumpTime > 1.8f) {
                isJumping = false;
            }
        }else{
            isJumping = false;
        }
    }

    boolean headIsTouching(Array<Floor> floors){
        for (Floor floor: floors){
            if (head.overlaps(floor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        return false;
    }

    boolean updateGameOver(){
        return (object.y < -64 || object.y > 800 - 64);
    }

    @Override // overlap the old thing which u inherit
    void transpose(float delta) {
        super.transpose(delta); // super - call original(GameObject's transpose) then add this transpose, so that it will run both
        updateFeetAndHeadPosition();
    }
}
