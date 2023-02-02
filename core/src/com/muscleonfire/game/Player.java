package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Player extends GameObject{

    boolean onFloor;
    Rectangle feet;

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
        updateFeetPosition();

        // initialise the picture of patrick
        image = new Texture(Gdx.files.internal("patrick_original.png"));
    }

    void updateFeetPosition(){
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
            object.y -= 200 * delta;
            updateFeetPosition();
        }
    }

    void move(float delta, MuscleOnFire game, Array<Floor> floors){
        // check if got touch screen (use for loop for multiple touches)
        for(int i =0; i < 10; i++){
            if (Gdx.input.isTouched(i)){
                // if got touch, get the position of the touch
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.camera.unproject(touchPos);

                // if touch left, move patrick left
                if (touchPos.x < 240) {
                    goLeft(100 * delta);
                }
                // if touch right, move patrick right
                if (touchPos.x > 240) {
                    goRight(100 * delta);
                }
            }
        }
    }

    void goLeft(float px){ // move patrick left
        object.x -= px;
        updateFeetPosition();
    }

    void goRight(float px){ // move patrick right
        object.x += px;
        updateFeetPosition();
    }

    Texture getTexture(){return image;} // return the image when called

    @Override // overlap the old thing which u inherit
    void transpose(float delta) {
        super.transpose(delta); // super - call original(GameObject's transpose) then add this transpose, so that it will run both
        updateFeetPosition();
    }
}
