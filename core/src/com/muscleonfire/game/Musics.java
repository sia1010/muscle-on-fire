package com.muscleonfire.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.*;
public class Musics {
    Music game = Gdx.audio.newMusic(Gdx.files.internal("BGM.mp3"));
    Music menu = Gdx.audio.newMusic(Gdx.files.internal("Music/MenuBGM.mp3"));
    Music shop = Gdx.audio.newMusic(Gdx.files.internal("BGM.mp3"));
    public void gamePlay(){
        game.setVolume(1.0f);
        game.play();
    }

    public void menuPlay(){
        menu.setVolume(1.0f);
        menu.play();
    }

    public void shopPlay(){
        shop.setVolume(1.0f);
        shop.play();
    }

    public void musicStop(){
        game.stop();
        menu.stop();
        shop.stop();
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

    public void setBGMVolume(float volume){
        game.setVolume(volume);
        menu.setVolume(volume);
        shop.setVolume(volume);
    }


}