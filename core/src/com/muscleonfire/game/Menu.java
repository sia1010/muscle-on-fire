package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class Menu implements Screen { //implements=inherit, Screen-inbuilt class, can use f(x) below
    final MuscleOnFire game; //declare class as variable

    public Menu(final MuscleOnFire game) {
        this.game = game;
    } //parameter is from muscleonfire

    @Override
    public void show(){

    }

    @Override
    public void render(float delta) { //change screen to gamescreen

        this.game.setScreen(new GameScreen(this.game));
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
