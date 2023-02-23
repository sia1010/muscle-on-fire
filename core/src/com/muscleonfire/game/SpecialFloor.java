package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class SpecialFloor extends GameObject{
    float dmg_timer;
    Animation<TextureRegion> rightrollAnim;
    Animation<TextureRegion> leftrollAnim;
    void trampoline_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 13;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -120; // below screen
        image = new Texture(Gdx.files.internal("trampoline.png"));
    }

    void spike_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 10;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -120; // below screen
        image = new Texture(Gdx.files.internal("spikefloor.png"));
    }

    void rightroll_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 13;
        object.width = 130;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -120; // below screen
        rightrollAnim = new Ani().loadAnimation("rightrollingfloor.png", 2,1, 0.2f);
    }

    void leftroll_spawn(){
        object = new Rectangle(); // from GameObject
        object.height = 13;
        object.width = 130;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -120; // below screen
        leftrollAnim = new Ani().loadAnimation("leftrollingfloor.png", 2,1, 0.2f);
    }
    void touchedSpike(Player pat,float delta){
        if (pat.feet.overlaps(object)){
            dmg_timer += delta;
            while(dmg_timer>1){
                dmg_timer-=1;
                pat.takeDamage(1);
            }
        }
    }

    void touchedRightRolls(Player pat,float delta){
        if (pat.feet.overlaps(object)){
            pat.goRight(50 * delta);
        }
    }

    void touchedLeftRolls(Player pat,float delta){
        if (pat.feet.overlaps(object)){
            pat.goLeft(50 * delta);
        }
    }
}
