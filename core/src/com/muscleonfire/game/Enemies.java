package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Enemies extends GameObject {

    float time = 0;
    int dmg =0;
    void spawn(){ // spawn a floor
        object = new Rectangle(); // from GameObject
        object.height = 40;
        object.width = 64;
        object.x = MathUtils.random(32, 480 - 64 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = 0; // below screen
        image = new Texture(Gdx.files.internal("Batman.png"));
    }

    boolean move_right = false;
    boolean move_down = false;



    void checkDirection(){
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

    void move(float delta){
        if (move_right && move_down){
            object.x += MathUtils.random(100,300) * delta;
            object.y -= MathUtils.random(100,300) * delta;
        }else if(!move_right && move_down){
            object.x -= MathUtils.random(100,200) * delta;
            object.y -= MathUtils.random(100,200) * delta;
        }else if(!move_right && !move_down){
            object.x -= MathUtils.random(100,200) * delta;
            object.y += MathUtils.random(100,200) * delta;
        }else if(move_right && !move_down){
            object.x += MathUtils.random(100,200) * delta;
            object.y += MathUtils.random(100,200) * delta;
        }
        time += delta;
    }

    boolean playerTouched(Player pat, float delta) {
        if (pat.object.overlaps(object)) {
            pat.takeDamage(1);
            return  true;
        }
        return  false;
    }


}
