package com.muscleonfire.game;

import com.badlogic.gdx.Screen;

public class Menu implements Screen {
    final MuscleOnFire game;

    public Menu(final MuscleOnFire game) {
        this.game = game;
        game.setScreen(new GameScreen(this.game));
    }

    @Override
    public void show(){

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
