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

    Rectangle feet, head, sword;
    float jumpTime = 0;
    float flashTime = 0;
    int jumpPower = 1400;
    boolean isJumping = false;
    boolean isFlashing;
    boolean onFloor;
    boolean isFront;
    Health healthPoint = new Health();
    Animation<TextureRegion> front, left, right, playerAnim;

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
        feet.width = 32; // smaller than the object, if the body touch the floor but the feet not, then will drop

        head = new Rectangle();
        head.height = 8;
        head.width = 32; // if head hit floor, then will stop jump

        sword = new Rectangle();

        updateFeetAndHeadPosition();

        // initialise the picture of patrick
        front = new Ani().loadAnimation("player_front.png", 2,1, 0.5f);
        left = new Ani().loadAnimation("player_left.png", 4,1, 0.2f);
        right = new Ani().loadAnimation("player_right.png", 4,1, 0.2f);
        playerAnim = front;
    }

    void updateFeetAndHeadPosition(){
        // make the head Rectangle above the body
        if(isFront){
            head.x = object.x + 16;// from left + 14 pixel (left 14, center 36 - feet, right 14 = 64 pixel)
            head.width = 36;
        }else{
            head.x = object.x;
            head.width = 36;
        }
        head.y = object.y + 56;

        // make the feet Rectangle() located beneath the body
        if(isFront){
            feet.x = object.x + 16; // from left + 18 pixel (left 18, center 28 - feet, right 18 = 64 pixel)
            feet.width = 32;
        }else{
            feet.x = object.x;
            feet.width = 32;
        }
        feet.y = object.y - 2;
    }

    void fall(float delta, float time_passed, Array<Floor> floors, Array<SpecialFloor> spikefloors, Array<SpecialFloor> tramfloors,Array<SpecialFloor> woodfloors){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (feet.overlaps(floor.object)) { // floor.object = the rectangle
                onFloor = true;
            }
        }

        for (SpecialFloor spikefloor: spikefloors){
            if (feet.overlaps(spikefloor.object)) { // floor.object = the rectangle
                onFloor = true;
            }
        }

        for (SpecialFloor woodfloor: woodfloors){
            if (feet.overlaps(woodfloor.object)) { // floor.object = the rectangle
                onFloor = true;
            }
        }

        for (SpecialFloor tramfloor: tramfloors){
            if (feet.overlaps(tramfloor.object)) { // floor.object = the rectangle
                onFloor = true;
                isJumping = true;
                jumpTime = 0;
                jumpPower = 600;
            }
        }

        // if not on floor, fall down
        if (!onFloor){
            //object.y-=300*delta;
            object.y -= 300 * delta;
            object.y -= ((500 + time_passed) / 5) * delta;
            updateFeetAndHeadPosition();
        }
    }

    void move(float delta, Controls controls){
        // take controls from Controls and apply them
        // additional keyboard controls for debugging
        boolean isMoving = false;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controls.leftButton.isPressed) {
            goLeft(150 * delta);
            playerAnim = left;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controls.rightButton.isPressed) {
            goRight(150 * delta);
            playerAnim = right;
            isMoving = true;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controls.jumpButton.isPressed) && onFloor && !isJumping) {
            isJumping = true; // set isJumping to true
            jumpTime = 0; // set jumpTime to 0
            isMoving = true;
        }

        if (!isMoving){
            playerAnim = front;
        }
    }

    void goLeft(float px){ // move patrick left
        if(object.x > 16){
            object.x -= px;
            updateFeetAndHeadPosition();
        }

    }

    void goRight(float px){ // move patrick right
        if(object.x<(480 - 32 - 16)) {
            object.x += px;
            updateFeetAndHeadPosition();
        }
    }

    public void jump(float delta, float passed_time, Array<Floor> floors,Array<SpecialFloor> spikefloors, Array<SpecialFloor> tramfloors,Array<SpecialFloor> woodfloors){
        // check if standing on floor){ // check for isJumping, if isJumping, then jump
        if (isJumping && !headIsTouching(floors,spikefloors,tramfloors,woodfloors)) { // check for jumping and not hitting head
            float ori_y = object.y;
            object.y += (jumpPower * ((500 + passed_time) / 500) * Math.pow(0.01, jumpTime)) * delta; // higher jump at start and lower jump when ending (a < 1 exponential graph)
            /*if (bodyIsTouching(floors,spikefloors,tramfloors) && !headIsTouching(floors,spikefloors,tramfloors)){
                while (bodyIsTouching(floors,spikefloors,tramfloors) && object.y >= ori_y) {
                    object.y--;
                }
            }*/
            updateFeetAndHeadPosition();
            jumpTime += delta;
            if (jumpTime > 0.3f) { // after 0.3 seconds, stop jumping
                isJumping = false;
            }
        }else{
            jumpPower = 1400;
            isJumping = false;
        }
    }

    boolean headIsTouching(Array<Floor> floors,Array<SpecialFloor> spikefloors, Array<SpecialFloor> tramfloors,Array<SpecialFloor> woodfloors ){
        // check if standing on floor){ // check if head is touching
        for (Floor floor: floors){
            if (head.overlaps(floor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor spikefloor: spikefloors){
            if (head.overlaps(spikefloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor tramfloor: tramfloors){
            if (head.overlaps(tramfloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor woodfloor: woodfloors){
            if (head.overlaps(woodfloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        return false;
    }

    boolean bodyIsTouching(Array<Floor> floors,Array<SpecialFloor> spikefloors, Array<SpecialFloor> tramfloors,Array<SpecialFloor> woodfloors ){
        // check if standing on floor){ // check if head is touching
        for (Floor floor: floors){
            if (object.overlaps(floor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor spikefloor: spikefloors){
            if (object.overlaps(spikefloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor tramfloor: tramfloors){
            if (object.overlaps(tramfloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor tramfloor: tramfloors){
            if (object.overlaps(tramfloor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        for (SpecialFloor woodfloor: woodfloors){
            if (object.overlaps(woodfloor.object)) { // floor.object = the rectangle
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
        if (flashTime > 2){
            flashTime = 0;
            isFlashing = false;
        }

        if (!isFlashing) {
            for (int i = 0; i < healthPoint.maxHealth; i++) {
                if (i < healthPoint.currHealth) {
                    batch.draw(healthPoint.filledHeart, 300 + 40 * i, 100);
                } else {
                    batch.draw(healthPoint.emptyHeart, 300 + 40 * i, 100);
                }
            }
        } else {
            for (int i = 0; i < healthPoint.maxHealth; i++) {
                if (i < healthPoint.currHealth) {
                    batch.draw(healthPoint.filledHeart, 300 + 40 * i, 100);
                } else {
                    if ((int) (flashTime * 10) % 2 == 0) {
                        batch.draw(healthPoint.flashHeart, 300 + 40 * i, 100);
                    } else if ((int) (flashTime * 10) % 2 == 1) {
                        batch.draw(healthPoint.emptyHeart, 300 + 40 * i, 100);
                    }
                }

                flashTime += delta;
            }
        }
    }

    void drawPatrick(SpriteBatch batch, float time_passed){
        if (playerAnim == left){
            if(isFront){
                object.width = 32;
                object.x += 16;
                updateFeetAndHeadPosition();
                isFront = false;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x - 16, object.y);
        }
        if (playerAnim == right){
            if(isFront){
                object.width = 32;
                object.x += 16;
                updateFeetAndHeadPosition();
                isFront = false;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x - 16, object.y);
        }
        if (playerAnim == front){
            if(!isFront){
                object.width = 64;
                object.x -= 16;
                updateFeetAndHeadPosition();
                isFront = true;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x, object.y);
        }

    }

    boolean updateGameOver(){
        return (object.y < -64 || object.y > 800 - 64-50 || healthPoint.currHealth < 1);
        // return false; // invincibility mode
    }

    @Override // overlap the old thing which u inherit
    void transpose(float delta, float time_passed) {
        super.transpose(delta, time_passed); // super - call original(GameObject's transpose) then add this transpose, so that it will run both
        updateFeetAndHeadPosition();
    }
}
