package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;

public class Sounds {
    static Sound jump = Gdx.audio.newSound(Gdx.files.internal("Sounds/jump.mp3"));
    static Sound hurt = Gdx.audio.newSound(Gdx.files.internal("Sounds/hitHurt.mp3"));
    static Sound life = Gdx.audio.newSound(Gdx.files.internal("Sounds/pickupCoin.mp3"));
    static Sound pressed = Gdx.audio.newSound(Gdx.files.internal("Sounds/button_press.mp3"));
    static Sound kaching = Gdx.audio.newSound(Gdx.files.internal("Sounds/kaching.mp3"));
    static Sound over = Gdx.audio.newSound(Gdx.files.internal("Sounds/gameover.mp3"));

    public static void jump(){
        jump.play();
    }
    public static void over(){
        over.play();
    }
    public static void hurt(){
        hurt.play();
    }
    public static void life(){
        life.play();
    }
    public static void pressed(){
        pressed.play();
    }
    public static void kaching(){
        kaching.play();
    }

}