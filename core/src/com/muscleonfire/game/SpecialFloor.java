package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class SpecialFloor extends GameObject{
    float dmg_timer;
    Animation<TextureRegion> woodAnim;
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
        object.height = 10;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -10; // below screen
        image = new Texture(Gdx.files.internal("spikefloor.png"));
    }

    void wood_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 12;
        object.width = 122;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -10; // below screen
        woodAnim = new Ani().loadAnimation("rollingwood.png", 2,1, 0.2f);
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
