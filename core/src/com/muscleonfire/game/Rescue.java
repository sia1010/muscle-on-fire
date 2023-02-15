package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.MathUtils;

public class Rescue extends GameObject{
    float save_timer = 0;
    boolean saved = false;
    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 64;
        object.width = 64;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+8;
        image = new Texture(Gdx.files.internal("rescue_unsaved.png"));
    }
    void playerTouched(Player pat, float delta, Score score){
        if (pat.object.overlaps(object)){
            save_timer += delta;
        }
        if (save_timer > 2 && !saved){
            saved = true;
            image = new Texture(Gdx.files.internal("rescue_saved.png"));
            score.upScore(1000);
            pat.healDamage(1);
        }
    }

    @Override
    void transpose(float delta){
        super.transpose(delta);
        if (saved){
            object.y += 200 * delta;
        }
    }
}
