package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Shop implements Screen {
    final MuscleOnFire game;
    private SpriteBatch batch;
    Sprite backgroundSprite;
    Button backMenuButton;
    public Shop(final MuscleOnFire game){
        this.game = game;
        batch = game.batch;
        backMenuButton = new Button(20,752,101,31,"Back_to_menu_shop_pressed.png","Back_to_menu_shop.png");
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
        game.font.draw(game.batch, "SHOP", 210, 780);
        game.font.draw(game.batch, "Coins: "+ this.game.coin.displayCoin(), 160, 730);
        backMenuButton.draw(batch);
        game.batch.end();
        if(backMenuButton.getPressed(this.game.camera)){
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
