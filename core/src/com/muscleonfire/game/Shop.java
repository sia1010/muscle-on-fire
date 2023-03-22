package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class Shop extends GameObject implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    Texture background;
    Sprite backgroundSprite;
    Button backButton;
    Texture shopbg;
    Texture textbg;
    Texture shield;
    Texture itembg;
    Texture speed;
    public Shop(final MuscleOnFire game){
        this.game = game;

        Random rand = new Random();
        int randmenu = rand.nextInt(5);
        randmenu+=1;
        batch = game.batch;
        background = new Texture("menu_background"+randmenu+".png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(480, 800);

        backButton = new Button(20,752,128,32,"back_button_pressed.png","back_button.png");
        shopbg = new Texture("shop_bg_items.png");
        textbg = new Texture("textbg.png");
        shield = new Texture("shop_shield.png");
        speed = new Texture("shop_speedup.png");
        itembg = new Texture("shop_item_bg");
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
        backgroundSprite.draw(batch);
        game.batch.draw(textbg,145,690); //upper textbox
        game.font.draw(game.batch, "Shop", 210, 780);
        game.font.draw(game.batch, "Coins: "+ this.game.coin.displayCoin(), 160, 730);
        backButton.draw(batch);
        game.batch.draw(shopbg, 65, 200);
        game.batch.draw(itembg,80,350); //first itembox
        game.batch.draw(itembg,222,350); //second itembox
        game.batch.draw(shield, 95,480);
        game.font.draw(game.batch,"Shield", 88,450);
        game.batch.draw(speed, 235,480);
        game.font.draw(game.batch,"Speed", 228,450);


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
