package com.muscleonfire.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Bat extends GameObject {
    private Animation<TextureRegion> batmanfly;
    private Rectangle head, body;
    private boolean givenScore = false;
    private float time = 0;
    private int dmg =0;

    public Animation<TextureRegion> getBatmanfly() {
        return batmanfly;
    }

    public Rectangle getHead() {
        return head;
    }

    public boolean isGivenScore() {
        return givenScore;
    }

    public void setGivenScore(boolean givenScore) {
        this.givenScore = givenScore;
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public void spawn(){ // spawn a floor
        object = new Rectangle(); // from GameObject
        object.height = 40;
        object.width = 64;
        object.x = MathUtils.random(32, 480 - 64 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = 0; // below screen
        //image = new Texture(Gdx.files.internal("Batman.png"));
        batmanfly = new AnimationLoader().loadAnimation("Textures/batman/batman_right(sheet).png", 2,1, 0.5f);

        head = new Rectangle();
        head.height = 8;
        head.width = 64;

        body = new Rectangle();
        body.height = 36;
        body.width = 64;
    }
    public void updateHeadAndBodyPosition(){
        head.x = object.x;
        head.y = object.y + 40;

        body.x = object.x;
        body.y = object.y;
    }
    private boolean move_right = false;
    private boolean move_down = false;
    boolean killed = false;

    public void checkDirection(){
        if (object.x <= 32) { // floor.object = the rectangle
            move_right = true;
        }else if (object.x >= 480 - 64 - 32){
            move_right = false;
        }
        if (object.y >= 770 && time < 10) {
            move_down = true;
        }else if(object.y <= 0){
            move_down = false;
        }
    }

    public void move(float delta){
        if (move_right && move_down){
            batmanfly = new AnimationLoader().loadAnimation("Textures/batman/batman_right(sheet).png", 2,1, 0.5f);
            object.x += MathUtils.random(100,300) * delta;
            object.y -= MathUtils.random(100,300) * delta;
        }else if(!move_right && move_down){
            batmanfly = new AnimationLoader().loadAnimation("Textures/batman/batman_left(sheet).png", 2,1, 0.5f);
            object.x -= MathUtils.random(100,200) * delta;
            object.y -= MathUtils.random(100,200) * delta;
        }else if(!move_right && !move_down){
            batmanfly = new AnimationLoader().loadAnimation("Textures/batman/batman_left(sheet).png", 2,1, 0.5f);
            object.x -= MathUtils.random(100,200) * delta;
            object.y += MathUtils.random(100,200) * delta;
        }else if(move_right && !move_down){
            batmanfly = new AnimationLoader().loadAnimation("Textures/batman/batman_right(sheet).png", 2,1, 0.5f);
            object.x += MathUtils.random(100,200) * delta;
            object.y += MathUtils.random(100,200) * delta;
        }
        updateHeadAndBodyPosition();
        time += delta;
    }

    public boolean playerTouched(Player pat, float delta) {
        if (pat.object.overlaps(body)) {
            pat.takeDamage(1);
            return true;
        }
        return false;
    }

    @Override
    public void transpose(float delta, float time_passed){
        if (killed){
            object.y -= 200 * delta;
            updateHeadAndBodyPosition();
        }
    }
}

