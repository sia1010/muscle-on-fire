package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import jdk.internal.icu.text.UnicodeSet;

public class Settings implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    Button backMenuButton;;
    private Skin skin;
    private Stage stage;
    private Slider musicSlider;
    Table table = new Table();

    public Settings(final MuscleOnFire game){
        this.game = game;
        batch = this.game.batch;

        //creates a skin from json file for slider
        skin = new Skin(Gdx.files.internal("defaultui/uiskin.json"));
        stage = new Stage(new ScreenViewport());
        Slider musicSlider = new Slider(0f, 100f, 1f, true, skin);
        musicSlider.draw(batch,100f);
        backMenuButton = new Button(20,752,128,32,"back_button_pressed.png","back_button.png");
//        musicSlider.setBounds(100, 300, 200, 20);

//        musicSlider.setValue(game.getMusicVolume());

//        stage.addActor(musicSlider);


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

        game.batch.begin();
        game.font.draw(game.batch, "Settings", 200, 780);

        backMenuButton.draw(batch);
        game.batch.end();
        if(backMenuButton.getJustPressed(this.game.camera)){
            game.setScreen(new Menu(this.game));
        }

//        musicSlider = new Slider(0f, 100f, 1f, true, skin);

//        musicSlider.setDebug(true);

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
