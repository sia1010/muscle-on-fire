package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Menu implements Screen { //implements=inherit, Screen-inbuilt class, can use f(x) below
    final MuscleOnFire game; //declare class as variable
    private SpriteBatch batch;
    Texture background;
    Sprite backgroundSprite;
    Button startButton;
    Button quitButton;
    Button shopButton;
    Button settingButton;
    Musics musics = new Musics();

    public Menu(final MuscleOnFire game) {
        this.game = game;
        Random rand = new Random();

        musics.musicStop();
        musics.menuPlay();

        int randmenu = rand.nextInt(5);
        randmenu+=1;
        batch = game.batch;

        background = new Texture("menu_background"+randmenu+".png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(480, 800);
        startButton = new Button(115, 400,256,64, "Buttons/MainMenu/start_button_pressed.png", "Buttons/MainMenu/start_button.png");
        quitButton = new Button(150,160,192,48,"Buttons/MainMenu/quit_pressed.png","Buttons/MainMenu/quit.png");
        shopButton = new Button(150,300,192,48,"Buttons/MainMenu/shop_button_menu_pressed.png","Buttons/MainMenu/shop_button_menu.png");
        settingButton = new Button(150,230,192,48,"Buttons/MainMenu/settings_button_pressed.png", "Buttons/MainMenu/settings_button.png");
    } //parameter is from muscleonfire


    @Override
    public void show() {

    }

    @Override
    public void render( float delta){ //change screen to gamescreen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();

        game.batch.setProjectionMatrix(game.camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
        game.font.draw(game.batch,"MUSCLE ON FIRE",115,600);
        startButton.draw(batch);
        quitButton.draw(batch);
        shopButton.draw(batch);
        settingButton.draw(batch);

        batch.end();

        if(startButton.onReleased(this.game.camera)){
            Sounds.pressed();
            musics.musicStop();
            game.setScreen(new GameScreen(this.game));
        }
        if(quitButton.onReleased(this.game.camera)){
            Gdx.app.exit();
            System.exit(0);
        }
        if(shopButton.onReleased(this.game.camera)){
            Sounds.pressed();
            musics.menuStop();
            game.setScreen(new Shop(this.game));
        }
        if(settingButton.onReleased(this.game.camera)){
            Sounds.pressed();
            musics.menuStop();
            game.setScreen(new Settings(this.game));
        }
    }

    @Override
    public void resize ( int width, int height){

    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    @Override
    public void dispose () {
        batch.dispose();
    }


}

