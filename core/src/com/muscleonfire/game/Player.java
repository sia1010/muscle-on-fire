package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player extends GameObject{

    Rectangle feet, head, sword;
    float timeToAddDifficulty=0;
    float jumpTime = 0;
    float flashTime = 0;
    float speedUp = 0;
    float speedTime = 0;
    int jumpPower = 1400;
    boolean isJumping = false;
    boolean isFlashing;
    boolean onFloor;
    boolean isFront;
    boolean forcedJump = false;

    PowerUp powerUp = null;
    Health healthPoint = new Health();
    Controls controls;
    Floor currentFloor;
    Animation<TextureRegion> front, left, right, playerAnim;
    Item item = new Item();
    enum PowerUp{
        Shield,
        Speed
    }

    public Player(Controls.controlMode controlMode){
        controls = new Controls(controlMode);
    }

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
        head.height = 4;
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
            head.width = 32;
        }
        head.y = object.y + 64;

        // make the feet Rectangle() located beneath the body
        if(isFront){
            feet.x = object.x + 16; // from left + 18 pixel (left 18, center 28 - feet, right 18 = 64 pixel)
            feet.width = 32;
        }else{
            feet.x = object.x;
            feet.width = 28;
        }
        feet.y = object.y;
    }

    void initiateJump(int power, boolean forced){
        isJumping = true;
        jumpTime = 0;
        jumpPower = power;
        if (forced){
            forcedJump = true;
        }
    }

    void fall(float delta, Array<Floor> floors, Array<Enemies> ebat, float time_passed){
        // check if standing on floor
        onFloor = false;
        for (Floor floor: floors){
            if (feet.overlaps(floor.object)) { // floor.object = the rectangle
                currentFloor = floor;
                onFloor = true;
            }
        }

        for (Enemies killbat: ebat){
            if (feet.overlaps(killbat.head)) { // killbat.object = the rectangle
                initiateJump(600, true);
                killbat.killed = true;
            }
        }

        // if not on floor, fall down
        if (!onFloor) {
            object.y -= 200 * delta;
            object.y -= ((300 + time_passed) / 3) * delta;
            updateFeetAndHeadPosition();
        } else {
            object.y = currentFloor.getY() + 8;
        }
    }

    void processControls(float delta, OrthographicCamera camera){
        // take controls from Controls and apply them
        // additional keyboard controls for debugging
        boolean isMoving = false;

        if(powerUp == PowerUp.Speed){
            if (speedTime < 10) {
                speedTime += delta;
            } else {
                speedUp = 0;
                powerUp = null;
                front = new Ani().loadAnimation("player_front.png", 2, 1, 0.5f);
                left = new Ani().loadAnimation("player_left.png", 4, 1, 0.2f);
                right = new Ani().loadAnimation("player_right.png", 4, 1, 0.2f);
            }
        }

        if(controls.shieldButton.getJustPressed(camera) && item.getShield_amt() > 0){
            item.setShield_amt(item.getShield_amt() - 1);
            setShieldUp();
        }

        if(controls.speedButton.getJustPressed(camera) && item.getSpeed_amt() > 0){
            item.setSpeed_amt(item.getSpeed_amt() - 1);
            setSpeedUp();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || controls.leftButton.isPressed() && (!controls.speedButton.isPressed() && !controls.shieldButton.isPressed())) {
            goLeft((150 + speedUp) * delta);
            playerAnim = left;
            isMoving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controls.rightButton.isPressed()) {
            goRight((150 + speedUp) * delta);
            playerAnim = right;
            isMoving = true;
        }

        if ((Gdx.input.isKeyPressed(Input.Keys.SPACE) || controls.jumpButton.isPressed()) && onFloor && !forcedJump) {
            initiateJump(1400, false);
            isMoving = true;
        }

        if(!(Gdx.input.isKeyPressed(Input.Keys.SPACE) || controls.jumpButton.isPressed()) && !forcedJump){
            jumpTime += delta * 8;
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

    public void jump(float delta, float time_passed, Array<Floor> floors){
        // check if standing on floor){ // check for isJumping, if isJumping, then jump
        if (isJumping && !headIsTouching(floors)) { // check for jumping and not hitting head
            object.y += jumpPower * Math.pow(0.01, jumpTime) * delta; // higher jump at start and lower jump when ending (a < 1 exponential graph)
            jumpTime += delta;
            if (jumpTime > 0.4f) { // after 0.4 seconds, stop jumping
                isJumping = false;
                forcedJump = false;
            }
        }else{
            isJumping = false;
        }
    }

    boolean headIsTouching(Array<Floor> floors){
        // check if standing on floor{ // check if head is touching
        for (Floor floor: floors){
            if (head.overlaps(floor.object)) { // floor.object = the rectangle
                return true;
            }
        }
        return false;
    }

    void takeDamage(int damage){ // minus health equals to passed damage
        if (powerUp == PowerUp.Shield){
            powerUp = null;
            front = new Ani().loadAnimation("player_front.png", 2,1, 0.5f);
            left = new Ani().loadAnimation("player_left.png", 4,1, 0.2f);
            right = new Ani().loadAnimation("player_right.png", 4,1, 0.2f);
            return;
        }
        healthPoint.currHealth -= damage;
        isFlashing = true;
        flashTime = 0;
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
                    if ((int) (flashTime * 5) % 2 == 0) {
                        batch.draw(healthPoint.flashHeart, 300 + 40 * i, 100);
                    } else if ((int) (flashTime * 5) % 2 == 1) {
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
                isFront = false;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x - 16, object.y);
        }
        if (playerAnim == right){
            if(isFront){
                object.width = 32;
                object.x += 16;
                isFront = false;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x - 16, object.y);
        }
        if (playerAnim == front){
            if(!isFront){
                object.width = 64;
                object.x -= 16;
                isFront = true;
            }
            batch.draw(playerAnim.getKeyFrame(time_passed, true), object.x, object.y);
        }

    }

    boolean updateGameOver(){
        return (object.y < -64 || object.y > 800 - 64-50 || healthPoint.currHealth < 1);
    }

    void setShieldUp(){
        powerUp = PowerUp.Shield;
        front = new Ani().loadAnimation("playerfront_powerup.png", 2,1, 0.5f);
        left = new Ani().loadAnimation("playerleft_powerup.png", 4,1, 0.2f);
        right = new Ani().loadAnimation("playerright_powerup.png", 4,1, 0.2f);
    }

    void setSpeedUp(){
        powerUp = PowerUp.Speed;
        speedTime = 0;
        speedUp = 100;
        front = new Ani().loadAnimation("playerfront_speed.png", 2,1, 0.5f);
        left = new Ani().loadAnimation("playerleft_speed.png", 4,1, 0.2f);
        right = new Ani().loadAnimation("playerright_speed.png", 4,1, 0.2f);
    }

    @Override // overlap the old thing which u inherit
    void transpose(float delta, float time_passed) {
        super.transpose(delta, time_passed); // super - call original(GameObject's transpose) then add this transpose, so that it will run both
        updateFeetAndHeadPosition();
    }
}
