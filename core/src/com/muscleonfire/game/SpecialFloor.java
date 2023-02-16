package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class SpecialFloor extends GameObject{
    float dmg_timer;

    void trampoline_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 13;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -10; // below screen
        image = new Texture(Gdx.files.internal("trampoline.png"));
    }

    void spike_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 8;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -10; // below screen
        image = new Texture(Gdx.files.internal("spikefloor.png"));
    }


    void touchedSpike(Player pat,float delta){
        if (pat.object.overlaps(object)){
            dmg_timer += delta;
            while(dmg_timer>1){
                dmg_timer-=1;
                pat.takeDamage(1);
            }
        }


    }

}
