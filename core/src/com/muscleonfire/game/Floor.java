package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;


public class Floor extends GameObject { // inheritance
    private float dmg_timer;
    private Animation<TextureRegion> anim;
    enum FloorID{
        floor,
        trampoline,
        spike,
        rollLeft,
        rollRight
    }
    private FloorID id;

    public void touched(Player pat, float delta){
        if (pat.feet.overlaps(object)){
            switch (id){
                case trampoline:
                    pat.initiateJump(600, true);
                    break;
                case spike:
                    dmg_timer += delta;
                    while(dmg_timer>1){
                        dmg_timer-=1;
                        pat.takeDamage(1);
                    }
                    break;
                case rollLeft:
                    pat.goRight(50 * delta);
                    break;
                case rollRight:
                    pat.goLeft(50 * delta);
                    break;
            }
        }
    }

    public void spawn(FloorID floorID){ // spawn a floor
        object = new Rectangle(); // from GameObject
        object.height = 10;
        object.width = 129;
        object.x = MathUtils.random(32, 480 - 128 - 32); // full screen 480 pixel, floor width 128 pixel, minus floor so that the floor will inside the screen
        object.y = -120; // below screen
        id = floorID;
        setAttributes();
    }

    public void setAttributes(){
        switch (id){
            case floor:
                image = new Texture(Gdx.files.internal("floor_original.png"));
                break;
            case trampoline:
                image = new Texture(Gdx.files.internal("trampoline.png"));
                break;
            case spike:
                image = new Texture(Gdx.files.internal("spikefloor.png"));
                break;
            case rollLeft:
                anim = new Ani().loadAnimation("rightrollingfloor.png", 2,1, 0.2f);
                break;
            case rollRight:
                anim = new Ani().loadAnimation("leftrollingfloor.png", 2,1, 0.2f);
                break;

        }
    }

    public void draw(SpriteBatch batch, float time_passed){
        if (id != FloorID.rollLeft && id != FloorID.rollRight){
            batch.draw(image, getX(), getY());
        }else{
            batch.draw(anim.getKeyFrame(time_passed, true), getX(), getY());
        }
    }

}
