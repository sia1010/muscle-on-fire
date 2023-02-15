package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
public class GameOver implements Screen {

    final MuscleOnFire game;
    @Override
    public void show() {

    }

    public GameOver(final MuscleOnFire game,Score score){
        this.game = game;
        this.game.coin.addCoin(score);
        this.game.coin.saveCoin();
    }

    @Override
    public void render(float delta) {
        // DISPLAY THE SCREEN
        // clear the screen but not rectangle
        ScreenUtils.clear(1,1,1,1);
        // update camera
        game.camera.update();
        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "TAP TO RETRY", 135, 400);
        game.batch.end();

        if (Gdx.input.justTouched()){
            game.setScreen(new Menu(this.game));
        }
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
