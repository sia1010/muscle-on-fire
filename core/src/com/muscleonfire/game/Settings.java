package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import jdk.internal.icu.text.UnicodeSet;

public class Settings implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    Button backMenuButton;;
    Musics musics = new Musics();
    private Skin sliderSkin = new Skin(Gdx.files.internal("Slider/uiskin.json"));

    private Stage stage = new Stage(new ScreenViewport());
    final Slider musicSlider = new Slider(0f, 2f, 0.1f, false, sliderSkin);
    final Slider soundSlider = new Slider(0f,2f,0.1f,false, sliderSkin);


    public Settings(final MuscleOnFire game){
        this.game = game;
        batch = this.game.batch;

        Gdx.input.setInputProcessor(stage);

        backMenuButton = new Button(20,740,128,32,"back_button_pressed.png","back_button.png");

        setSliderLocation();

    }

    public void setSliderLocation(){
        musicSlider.setX(250);
        musicSlider.setY(750);
        soundSlider.setX(250);
        soundSlider.setY(650);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // clear the screen
        ScreenUtils.clear(1,1,1,1);
        // update camera
        game.camera.update();
        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        //start drawing
        game.batch.begin();
        game.font.draw(game.batch, "Settings", 200, 750);
        backMenuButton.draw(batch);
        game.font.draw(game.batch, "Music  : ", 100, 650);
        game.font.draw(game.batch,"SFX    : ", 100, 560);
        game.batch.end();

        //creates a stage with actors
        stage.draw();
        stage.addActor(musicSlider);
        stage.addActor(soundSlider);

        if(backMenuButton.getJustPressed(this.game.camera)){
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
    /*   musicSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                float volume = musicSlider.getValue();
                musics.setBGMVolume(volume);
            }
        });*/