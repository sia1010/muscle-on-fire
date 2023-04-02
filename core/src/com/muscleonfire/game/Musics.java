package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

public class Musics {

    private FileHandle volumeFile;
    private static Music game = Gdx.audio.newMusic(Gdx.files.internal("Music/BGM.mp3"));
    private static Music menu = Gdx.audio.newMusic(Gdx.files.internal("Music/MenuBGM.mp3"));
    private static Music shop = Gdx.audio.newMusic(Gdx.files.internal("Music/shop.mp3"));
    private static Music settings = Gdx.audio.newMusic(Gdx.files.internal("Music/settings.mp3"));
    private static float volume = 0.5f;
    public Musics(){
        volumeFile = Gdx.files.external("Music/volume.txt");
        game.setLooping(true);
        menu.setLooping(true);
        shop.setLooping(true);
        settings.setLooping(true);
    }

    public void gamePlay(){
        game.setVolume(volume/2);
        game.play();
    }

    public void settingsPlay(){
        settings.setVolume(volume);
        settings.play();
    }

    public void menuPlay(){
        menu.setVolume(volume);
        menu.play();
    }

    public void shopPlay(){
        shop.setVolume(volume);
        shop.play();
    }

    public void musicStop(){
        game.stop();
        menu.stop();
        shop.stop();
        settings.stop();
    }

    public void gameStop(){
        game.stop();
    }

    public void menuStop(){
        menu.stop();
    }

    public void shopStop(){
        shop.stop();
    }
    public void settingsStop(){
        settings.stop();
    }

    public void setBGMVolume(float volume){
        this.volume = volume;
        volumeFile.writeString(Float.toString(volume), false);
        volume = Float.parseFloat(volumeFile.readString());
        game.setVolume(volume);
        menu.setVolume(volume);
        shop.setVolume(volume);
        settings.setVolume(volume);
    }


}