package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;

import java.util.ArrayList;
import java.util.Random;

public class Shop extends GameObject implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    Texture background;
    Sprite backgroundSprite;
    Button backButton;
    Button buybutton;
    Texture shopbg;
    Texture textbg;
    ButtonShop shield;
    ButtonShop speed;
    ButtonShop selected_item;
    Item item;

    public Shop(final MuscleOnFire game){
        this.game = game;

        Random rand = new Random();
        int randmenu = rand.nextInt(5);
        randmenu+=1;
        batch = game.batch;
        font = game.font;
        background = new Texture("menu_background"+randmenu+".png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(480, 800);

        backButton = new Button(20,752,128,32,"back_button_pressed.png","back_button.png");
        buybutton = new Button(200,280,128,32,"shop_buy_pressed.png","shop_buy_button.png");

        shopbg = new Texture("shop_bg_items.png");
        textbg = new Texture("textbg.png");
        shield = new ButtonShop(100, 350,"shop_shield.png", "Shield");
        speed = new ButtonShop(270, 350, "shop_speedup.png", "Speed");
        selected_item = new ButtonShop(0,0,"nothing.png", "nothing.png");

        item = new Item();
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
        game.batch.draw(shopbg, 65, 200);
        shield.draw(batch, font, item.getShield_amt()); // shield item
        speed.draw(batch, font, item.getSpeed_amt()); // speed item
        backButton.draw(batch);
        buybutton.draw(batch);


        game.batch.end();
        if(backButton.getJustPressed(game.camera)){
            game.setScreen(new Menu(game));
        }

        if(shield.getJustPressed(game.camera)){
            selected_item = shield;
        }

        if(speed.getJustPressed(game.camera)){
            selected_item = speed;
        }

        if(buybutton.getJustPressed(game.camera)){
            if (selected_item.isPressed()) {
                if (selected_item == shield){
                    game.coin.spendCoin(50);
                    item.setShield_amt(item.getShield_amt() + 1);
                }else if(selected_item == speed){
                    game.coin.spendCoin(50);
                    item.setSpeed_amt(item.getSpeed_amt() + 1);
                }
            }
        }

        selected_item.permanentTrue();
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
