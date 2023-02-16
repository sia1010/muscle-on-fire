package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class Rescue extends GameObject{
    Animation<TextureRegion> rescueAni;
    Rectangle help_box;
    Texture image_help;
    float save_timer = 0;
    boolean saved = false;
    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+8;
        image = new Texture(Gdx.files.internal("rescue_unsaved.png"));
        rescueAni = new Ani().loadAnimation("rescue_unsaved(sheet).png", 2,1, 0.5f);

        help_box = new Rectangle();
        help_box.height = 17;
        help_box.width = 33;
        image_help = new Texture(Gdx.files.internal("Help_box.png"));
        updateHelpBoxPos();
    }

    void updateHelpBoxPos(){
        help_box.x = object.x + 20;
        help_box.y = object.y + 70 ;
    }
    void playerTouched(Player pat, float delta, Score score){
        if (pat.object.overlaps(object)){
            save_timer += delta;
        }
        if (save_timer > 2 && !saved){
            saved = true;
            image = new Texture(Gdx.files.internal("rescue_saved.png"));
            rescueAni = new Ani().loadAnimation("rescue_saved(sheet).png", 2,1, 0.5f);
            image_help = new Texture(Gdx.files.internal("TQ.png"));
            score.upScore(1000);
            pat.healDamage(1);
        }
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
    @Override
    void transpose(float delta){
        super.transpose(delta);
        if (saved){
            object.y += 200 * delta;
        }
        updateHelpBoxPos();
    }
}
