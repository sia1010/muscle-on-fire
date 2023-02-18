package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Medicine extends GameObject{

    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 26;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 64); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY()+10;
        image = new Texture(Gdx.files.internal("Medicine_box.png"));
    }
    boolean playerTouched(Player pat){
        boolean touched=false;
        if (pat.object.overlaps(object)){
            pat.healDamage(1);
            touched=true;
        }
        return touched;
    }
}
