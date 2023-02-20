package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


import javax.swing.text.View;

public class Menu implements Screen { //implements=inherit, Screen-inbuilt class, can use f(x) below
    final MuscleOnFire game; //declare class as variable
    private SpriteBatch batch;
    Texture background;
    Sprite backgroundSprite;


    Button startButton;
    Button quitButton;
    Button shopButton;

    public Menu(final MuscleOnFire game) {
        this.game = game;
        batch = game.batch;
        background = new Texture("nothing.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(480, 800);
        startButton = new Button(93, 400,300,80, "start_button.png", "start_button.png");
        quitButton = new Button(93,230,330,140,"quit.png","quit.png");
        shopButton = new Button(93,130,300,80,"Shop_button_menu_pressed.png","Shop_button_menu.png");

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
        game.font.draw(game.batch,"MUSCLE ON FIRE",93,600);
        startButton.draw(batch);
        quitButton.draw(batch);
        shopButton.draw(batch);
        batch.end();

        if(startButton.getJustPressed(this.game.camera)){
            game.setScreen(new GameScreen(this.game));
        }
        if(quitButton.getJustPressed(this.game.camera)){
            Gdx.app.exit();
            System.exit(0);
        }
        if(shopButton.getJustPressed(this.game.camera)){
            game.setScreen(new Shop(this.game));
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

