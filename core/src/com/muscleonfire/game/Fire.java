package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Fire extends GameObject{
    float dmg_timer = 0;
    Animation<TextureRegion> fireAnim;
    void spawn(Array<Floor> floors){
        object = new Rectangle();
        object.height = 32;
        object.width = 28;
        object.x = floors.peek().getX() + MathUtils.random(0, 128 - 28); // randomly at the floor, can be at left or right
        object.y = floors.peek().getY() + 10;
        image = new Texture(Gdx.files.internal("Textures/Fire.png"));
        fireAnim = new Ani().loadAnimation("Textures/fire(sheet).png", 3,1, 0.5f);
    }
    void playerTouched(Player pat, float delta){
        if (pat.object.overlaps(object)){
            while(dmg_timer <= 0){
                dmg_timer += 1;
                pat.takeDamage(1);
            }
            dmg_timer -= delta;
        }
    }
}
