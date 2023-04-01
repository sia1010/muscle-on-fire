package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
public class Musics {
    Music game = Gdx.audio.newMusic(Gdx.files.internal("Music/BGM.mp3"));
    Music menu = Gdx.audio.newMusic(Gdx.files.internal("Music/MenuBGM.mp3"));
    Music shop = Gdx.audio.newMusic(Gdx.files.internal("Music/shop.mp3"));
    Music settings = Gdx.audio.newMusic(Gdx.files.internal("Music/settings.mp3"));
    float volume = 0.5f;

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
        game.setVolume(volume);
        menu.setVolume(volume);
        shop.setVolume(volume);
        settings.setVolume(volume);
    }


}