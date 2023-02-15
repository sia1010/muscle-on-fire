package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacles extends GameObject{
    float dmg_timer = 0;
    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 28;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 28); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+8;
        image = new Texture(Gdx.files.internal("Fire.png"));
    }
    void playerTouched(Player pat, float delta){
        if (pat.object.overlaps(object)){
            dmg_timer += delta;
            while(dmg_timer>1){
                dmg_timer-=1;
                pat.takeDamage(1);
            }

        }
    }
}
