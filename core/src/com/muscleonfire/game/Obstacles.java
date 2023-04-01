package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacles extends GameObject{ // inherit GameObject class
    private Animation<TextureRegion> rescueAni;
    private Rectangle help_box;
    private Texture image_help;
    private float save_timer = 0;
    private boolean saved = false;
    private float dmg_timer = 0;
    private Animation<TextureRegion> fireAnim;

    // getter for rescueAni
    public Animation<TextureRegion> getRescueAni() {
        return rescueAni;
    }

    // getter for fireAnim
    public Animation<TextureRegion> getFireAnim() {
        return fireAnim;
    }

    // spawn new medicine
    public void spawnMedicine(Array<Floor> floors){ // pass floors array so that the object of medicine will spawn on the floor
        object = new Rectangle();
        object.height = 32;
        object.width = 26;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Textures/medicine_box.png"));
    }

    // spawn new mysteryBox
    public void spawnMysteryBox(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+30;
        image = new Texture(Gdx.files.internal("Textures/mystery_box.png"));
    }

    // spawn new rescue and add the help box for new rescue
    public void spawnRescue(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Textures/rescue/rescue_unsaved.png"));
        rescueAni = new AnimationLoader().loadAnimation("Textures/rescue/rescue_unsaved(sheet).png", 2,1, 0.5f);

        help_box = new Rectangle();
        help_box.height = 17;
        help_box.width = 33;
        image_help = new Texture(Gdx.files.internal("Textures/rescue/help_box.png"));
        updateHelpBoxPos();
    }

    // spawn new fire
    public void spawnFire(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 28;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 28); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Textures/fire/fire.png"));
        fireAnim = new AnimationLoader().loadAnimation("Textures/fire/fire(sheet).png", 3,1, 0.5f);
    }

    // update the helpBox position
    public void updateHelpBoxPos(){
        help_box.x = object.x + 20;
        help_box.y = object.y + 70 ;
    }

    // PlayerTouchedRescue method
    public void playerTouchedRescue(Player pat, float delta, Score score){ // pass player, delta and score
        if (pat.object.overlaps(object)){
            // if player touches object, the saver_timer will add the delta
            save_timer += delta;
        }
        if (save_timer > 1 && !saved){ // if the save_timer more than 1, and is not saved
            // the saved become true, image and animation for rescue and also the help box will be changed
            saved = true;
            image = new Texture(Gdx.files.internal("Textures/rescue/rescue_saved.png"));
            rescueAni = new AnimationLoader().loadAnimation("Textures/rescue/rescue_saved(sheet).png", 2,1, 0.5f);
            image_help = new Texture(Gdx.files.internal("Textures/rescue/TQ_box.png"));
            // the score will be added 1000 and will heal the player 1 life/heart
            score.upScore(1000);
            pat.getHealthPoint().healDamage(1);
        }
    }

    // playerTouchedFire method
    public void playerTouchedFire(Player pat, float delta){ // pass player and delta
        if (pat.object.overlaps(object)){ // if the player touches fire
            while(dmg_timer <= 0){ // so that when the player touches fire, it will directly cause damage to player
                dmg_timer += 1;
                pat.getHealthPoint().takeDamage(1);
            }
            dmg_timer -= delta;
        }
    }

    // playerTouchedMedicine method return boolean value (true or false)
    public boolean playerTouchedMedicine(Player pat){ // pass player
        boolean touched=false;
        if (pat.object.overlaps(object)){ // if the player touches medicine
            pat.getHealthPoint().healDamage(1); // heal 1 heart
            touched=true; // change the boolean value to true
        }
        return touched; // return touched boolean value
    }

    // playerTouchedMysteryBox method
    public boolean playerTouchedMysteryBox(Player pat, Score score){ // pass player and score
        int random = MathUtils.random(1, 10); // initialize a random number in the range of 1 to 10
        boolean touched=false;
        if (pat.object.overlaps(object)){ // if the player touches MysteryBox
            touched=true; // change the boolean value to true
            if (random <= 2) { // if random number is less than or equal to 2
                pat.getPowerUp().setShieldUp(); // get a shield that can block one damage
            } else if (random <= 7) { // random number is less than or equal to 7
                pat.getPowerUp().setSpeedUp(); // speed up the player
            } else {
                score.upScore(1000); // add 1000 score
            }

        }
        return touched; // return touched boolean value
    }

    // return image for helpBox
    public Texture getTextureHelpBox(){
        return image_help;
    }

    // return helpBox's x value (position)
    public float getHelpX(){
        return help_box.x;
    }

    // return helpBox's y value (position)
    public float getHelpY(){
        return help_box.y;
    }

    // transpose the rescue
    public void transposeRescue(float delta, float time_passed){ // pass delta and time_passed
        super.transpose(delta, time_passed); // override the original transpose from GameObject
        if (saved){ // if the player touched rescue for more than 1, the saved will become true and run the code below
            object.y += 200 * delta; // the y of object will add 200 * delta
        }
        updateHelpBoxPos(); // update the position of HelpBox
    }
}
