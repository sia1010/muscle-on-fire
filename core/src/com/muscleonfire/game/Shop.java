package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;

import java.util.Random;

public class Shop implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    private final BitmapFont font;
    private Texture background;
    private Sprite backgroundSprite;
    private Button backButton;
    private Button buybutton;
    private Texture shopbg;
    private Texture textbg;
    private ButtonShop shield;
    private ButtonShop speed;
    private ButtonShop selected_item;
    private Item item;
    Musics musics = new Musics();

    public Shop(final MuscleOnFire game){
        this.game = game;
        musics.shopPlay();
        Random rand = new Random();
        int randmenu = rand.nextInt(5);
        randmenu+=1;
        batch = game.batch;
        font = game.font;
        background = new Texture("menu_background"+randmenu+".png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(480, 800);

        backButton = new Button(20,752,128,32,"Buttons/MainMenu/back_button_pressed.png","Buttons/MainMenu/back_button.png");
        buybutton = new Button(200,280,128,32,"Buttons/Shop/shop_buy_pressed.png","Buttons/Shop/shop_buy_button.png");

        shopbg = new Texture("Buttons/Shop/shop_bg_items.png");
        textbg = new Texture("Buttons/Shop/textbg.png");
        shield = new ButtonShop(100, 350,"Buttons/Shop/shop_shield.png", "Shield");
        speed = new ButtonShop(270, 350, "Buttons/Shop/shop_speedup.png", "Speed");
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


        if(backButton.onReleased(game.camera)){
            game.setScreen(new Menu(game));
            musics.shopStop();
            Sounds.pressed();
        }

        if(shield.onReleased(game.camera)){
            selected_item = shield;
        }

        if(speed.onReleased(game.camera)){
            selected_item = speed;
        }

        if(buybutton.onReleased(game.camera)){
            if (selected_item.isPressed()) {
                if (selected_item == shield && game.coin.getCoin()>=50){
                    Sounds.kaching();
                    game.coin.spendCoin(50);
                    item.setShield_amt(item.getShield_amt() + 1);
                }else if(selected_item == speed && game.coin.getCoin()>=50){
                    Sounds.kaching();
                    game.coin.spendCoin(50);
                    item.setSpeed_amt(item.getSpeed_amt() + 1);
                }else{
                    Sounds.over();
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
