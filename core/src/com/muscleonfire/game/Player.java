package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Player extends GameObject{

    boolean onFloor;
    Rectangle feet, head;
    float jumpTime = 0;
    float flashTime = 0;
    boolean isJumping;
    boolean isFlashing;
    Health healthPoint = new Health();
    Animation<TextureRegion> playerAnim;

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
        feet.width = 28; // smaller than the object, if the body touch the floor but the feet not, then will drop

        head = new Rectangle();
        head.height = 4;
        head.width = 36; // if head hit floor, then will stop jump

        updateFeetAndHeadPosition();

        // initialise the picture of patrick
        image = new Texture(Gdx.files.internal("patrick_original.png"));
        playerAnim = new Ani().loadAnimation("player.png", 4,1, 0.5f);
    }

    void updateFeetAndHeadPosition(){
        // make the head Rectangle above the body
        head.x = object.x + 14; // from left + 14 pixel (left 14, center 36 - feet, right 14 = 64 pixel)
        head.y = object.y + 64;

        // make the feet Rectangle() located beneath the body
        feet.x = object.x + 18; // from left + 18 pixel (left 18, center 28 - feet, right 18 = 64 pixel)
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

    void move(float delta, Controls controls){
        // take controls from Controls and apply them
        // additional keyboard controls for debugging
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controls.leftButton.isPressed) {
            goLeft(150 * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controls.rightButton.isPressed) {
            goRight(150 * delta);
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controls.jumpButton.isPressed) && onFloor) {
            isJumping = true; // set isJumping to true
            jumpTime = 0; // set jumpTime to 0
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

    void jump(float delta, Array<Floor> floors){ // check for isJumping, if isJumping, then jump
        if (isJumping && !headIsTouching(floors)) { // check for jumping and not hitting head
            object.y += 1400 * Math.pow(0.01, jumpTime) * delta; // higher jump at start and lower jump when ending (a < 1 exponential graph)
            jumpTime += delta;
            if (jumpTime > 0.6f) { // after 0.6 seconds, stop jumping
                isJumping = false;
            }
        }else{
            isJumping = false;
        }
    }

    boolean headIsTouching(Array<Floor> floors){ // check if head is touching
        for (Floor floor: floors){
            if (head.overlaps(floor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        return false;
    }

    void takeDamage(int damage){ // minus health equals to passed damage
        healthPoint.currHealth -= damage;
        isFlashing = true;
    }

    void healDamage(int heal){
        if (healthPoint.currHealth < healthPoint.maxHealth){
            healthPoint.currHealth += heal;
        }
    }

    void drawHearts(SpriteBatch batch, float delta) {
        if (flashTime > 3){
            flashTime = 0;
            isFlashing = false;
        }

        if (!isFlashing) {
            for (int i = 0; i < healthPoint.maxHealth; i++) {
                if (i < healthPoint.currHealth) {
                    batch.draw(healthPoint.filledHeart, 300 + 40 * i, 50);
                } else {
                    batch.draw(healthPoint.emptyHeart, 300 + 40 * i, 50);
                }
            }
        } else {
            for (int i = 0; i < healthPoint.maxHealth; i++) {
                if (i < healthPoint.currHealth) {
                    batch.draw(healthPoint.filledHeart, 300 + 40 * i, 50);
                } else {
                    if ((int) (flashTime * 5) % 2 == 0) {
                        batch.draw(healthPoint.flashHeart, 300 + 40 * i, 50);
                    } else if ((int) (flashTime * 5) % 2 == 1) {
                        batch.draw(healthPoint.emptyHeart, 300 + 40 * i, 50);
                    }
                }

                flashTime += delta;
            }
        }
    }

    boolean updateGameOver(){
        return (object.y < -64 || object.y > 800 - 64-50 || healthPoint.currHealth < 1);
    }

    @Override // overlap the old thing which u inherit
    void transpose(float delta) {
        super.transpose(delta); // super - call original(GameObject's transpose) then add this transpose, so that it will run both
        updateFeetAndHeadPosition();
    }
}
