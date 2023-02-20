package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Settings implements Screen {
    final MuscleOnFire game;
    private SpriteBatch batch;
    Button setting;

    public Settings(final MuscleOnFire game){
        this.game = game;
        batch = game.batch;
        Button setting = new Button(0, 0,64, 16,"Settings button.png", "Settings button.png");
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
