package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;


public class Sounds {
    public static float volume = 1.0f;
    private static FileHandle volumeFile = Gdx.files.external("Sounds/volume.txt");;
    private static Sound jump = Gdx.audio.newSound(Gdx.files.internal("Sounds/jump.mp3"));
    private static Sound hurt = Gdx.audio.newSound(Gdx.files.internal("Sounds/hitHurt.mp3"));
    private static Sound life = Gdx.audio.newSound(Gdx.files.internal("Sounds/pickupCoin.mp3"));
    private static Sound pressed = Gdx.audio.newSound(Gdx.files.internal("Sounds/button_press.mp3"));
    private static Sound kaching = Gdx.audio.newSound(Gdx.files.internal("Sounds/kaching.mp3"));
    private static Sound over = Gdx.audio.newSound(Gdx.files.internal("Sounds/gameover.mp3"));

    public static void jump(){
        jump.play(volume);
    }
    public static void over(){
        over.play(volume);
    }
    public static void hurt(){
        hurt.play(volume);
    }
    public static void life(){
        life.play(volume);
    }
    public static void pressed(){
        pressed.play(volume);
    }
    public static void kaching(){
        kaching.play(volume);
    }
    public static void setVolume(float v){
        volumeFile.writeString(Float.toString(v), false);
        volume = Float.parseFloat(volumeFile.readString());
    }

}