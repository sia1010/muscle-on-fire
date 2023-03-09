package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;



public class Sounds {
    static Sound jump = (Sound) Gdx.audio.newSound(Gdx.files.internal("Sounds/jump.mp3"));

    public static void jump(){
        jump.play();
    }



}