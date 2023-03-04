package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Shop implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    Sprite backgroundSprite;
    Button backButton;
    public Shop(final MuscleOnFire game){
        this.game = game;
        batch = this.game.batch;
        backButton = new Button(20,752,128,32,"back_button_pressed.png","back_button.png");
    }
    @Override
    public void show() {

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
        game.font.draw(game.batch, "Shop", 210, 780);
        game.font.draw(game.batch, "Coins: "+ this.game.coin.displayCoin(), 160, 730);
        backButton.draw(batch);
        game.batch.end();
        if(backButton.getJustPressed(this.game.camera)){
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
//to be updated after i dont menge-errorkan project ni
}
