package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;


public class Wallpaper extends GameObject { // inherits game object
    public Wallpaper(){
        spawn();
    }

    public void spawn(){ // spawn a wallpaper
        Random random = new Random();
        int randomNum = random.nextInt(2);

        object = new Rectangle(); // from GameObject
        object.height = 120;
        object.width = 480;

        // for loop (delta) with number randomizer with specific number assigned to each type of png
        if(randomNum == 0){
            image = new Texture(Gdx.files.internal("Office BG2.png"));
        }else if(randomNum == 1){
            image = new Texture(Gdx.files.internal("Burning Office BG2.png"));
        }

        object.x = 0;
        object.y =-210;
    }



}
