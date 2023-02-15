package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Settings {
    Texture settings;

    public Settings(){
        settings = new Texture(Gdx.files.internal("settings_button.png"));

    }
}
