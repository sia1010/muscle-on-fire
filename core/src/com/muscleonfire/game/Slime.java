package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Slime extends FallingObjects {
    boolean move_right=true;
    boolean changeDirection=false;
    float time=0;
    Animation<TextureRegion> slimemove;
    void onfloor_spawn(GameObject slime){

        object=new Rectangle();
        object.height=32;
        object.width=64;
        object.x = slime.getX();
        object.y=slime.getY();


        image=new Texture(Gdx.files.internal("slime_left.png"));



    }
    void checkMovingDirection(){

        if (changeDirection==false) { // floor.object = the rectangle
            move_right = true;
            image=new Texture(Gdx.files.internal("slime_right.png"));

        }else {
            move_right = false;
            image=new Texture(Gdx.files.internal("slime_left.png"));
        }


    }


    void move(float delta) {
        if(onFloor) {
            if (move_right) {

                object.x += MathUtils.random(100, 150) * delta;
                if(time>100*delta){
                changeDirection=true;
                time=0;
                }
            } else if (!move_right) {

                object.x -= MathUtils.random(100, 150) * delta;
                if(time>100*delta){
                changeDirection=false;
                time=0;
                }

            }
        }
        time+=delta;
    }


    void transpose_up(float delta, float time_passed){
        super.transpose(delta, time_passed);
        object.y += 200 * delta;

        object.y += ((300 + time_passed) / 3) * delta;
    }


}
