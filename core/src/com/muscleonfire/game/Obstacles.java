package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacles extends GameObject{
    Animation<TextureRegion> rescueAni;
    Rectangle help_box;
    Texture image_help;
    float save_timer = 0;
    boolean saved = false;
    float dmg_timer = 0;
    Animation<TextureRegion> fireAnim;
    void spawnMedicine(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 26;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Medicine_box.png"));
    }

    void spawnMysteryBox(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+30;
        image = new Texture(Gdx.files.internal("Mystery Box.png"));
    }
    void spawnRescue(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("rescue_unsaved.png"));
        rescueAni = new Ani().loadAnimation("rescue_unsaved(sheet).png", 2,1, 0.5f);

        help_box = new Rectangle();
        help_box.height = 17;
        help_box.width = 33;
        image_help = new Texture(Gdx.files.internal("Help_box.png"));
        updateHelpBoxPos();
    }

    void spawnFire(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 28;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 28); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Fire.png"));
        fireAnim = new Ani().loadAnimation("Fire(sheet).png", 3,1, 0.5f);
    }

    void updateHelpBoxPos(){
        help_box.x = object.x + 20;
        help_box.y = object.y + 70 ;
    }
    void playerTouchedRescue(Player pat, float delta, Score score){
        if (pat.object.overlaps(object)){
            save_timer += delta;
        }
        if (save_timer > 1 && !saved){
            saved = true;
            image = new Texture(Gdx.files.internal("rescue_saved.png"));
            rescueAni = new Ani().loadAnimation("rescue_saved(sheet).png", 2,1, 0.5f);
            image_help = new Texture(Gdx.files.internal("TQ.png"));
            score.upScore(1000);
            pat.healDamage(1);
        }
    }

    void playerTouchedFire(Player pat, float delta){
        if (pat.object.overlaps(object)){
            while(dmg_timer <= 0){
                dmg_timer += 1;
                pat.takeDamage(1);
            }
            dmg_timer -= delta;
        }
    }

    boolean playerTouchedMedicine(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.healDamage(1);
            touched=true;
        }
        return touched;
    }

    boolean playerTouchedMysteryBox(Player pat, Score score){
        int random = MathUtils.random(1, 10);
        boolean touched=false;
        if (pat.object.overlaps(object)){
            touched=true;
            if (random <= 2) {
                pat.setShieldUp();
            } else if (random <= 7) {
                pat.setSpeedUp();
            } else {
                score.upScore(1000);
            }

        }
        return touched;
    }

    Texture getTextureHelpBox(){
        return image_help;
    }
    float getHelpX(){
        return help_box.x;
    }

    float getHelpY(){
        return help_box.y;
    }

    void transposeRescue(float delta, float time_passed){
        super.transpose(delta, time_passed);
        if (saved){
            object.y += 200 * delta;
        }
        updateHelpBoxPos();
    }
}
