package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import jdk.internal.icu.text.UnicodeSet;



public class Settings implements Screen {
    final MuscleOnFire game;
    private final SpriteBatch batch;
    Button backMenuButton;;
    Musics musics = new Musics();
    private Stage stage = new Stage(new ScreenViewport());
    private Skin skin = new Skin(Gdx.files.internal("Slider/uiskin.json"));
    final Slider musicSlider = new Slider(0f, 2f, 0.1f, false, skin);
    final Slider soundSlider = new Slider(0f,2f,0.1f,false, skin);
    final CheckBox touchControls = new CheckBox("Touch Controls", skin);
    final CheckBox buttonControls = new CheckBox("Button Controls",skin);
    final CheckBox followControls = new CheckBox("Follow Controls", skin);

    Controls.ControlMode mode;
    private Controls controls;


    public Settings(final MuscleOnFire game){
        this.game = game;
        batch = this.game.batch;

        Gdx.input.setInputProcessor(stage);

        backMenuButton = new Button(20,740,128,32,"Buttons/MainMenu/back_button_pressed.png","Buttons/MainMenu/back_button.png");
        setSliderLocation();
        setControls();

    }

    public void setControls(){
        followControls.setPosition(200,550);
        followControls.setColor(0,0,0,1);
        buttonControls.setPosition(200,500);
        buttonControls.setColor(0,0,0,1);
        touchControls.setPosition(200,450);
        touchControls.setColor(0,0,0,1);
    }

    public void setSliderLocation(){
        musicSlider.setX(250);
        musicSlider.setY(750);
        soundSlider.setX(250);
        soundSlider.setY(645);
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
        //touchControls.show(batch, 1);
        backMenuButton.draw(batch);
        game.font.draw(game.batch, "Music  : ", 100, 650);
        game.font.draw(game.batch,"SFX    : ", 100, 560);
        game.batch.end();

        //creates a stage with actors
        stage.draw();
        stage.addActor(touchControls);
        stage.addActor(buttonControls);
        stage.addActor(followControls);
        stage.addActor(musicSlider);
        stage.addActor(soundSlider);



        if(backMenuButton.getJustPressed(this.game.camera)){
            game.setScreen(new Menu(this.game));
        }

        if (touchControls.isChecked()){
            followControls.setChecked(false);
            buttonControls.setChecked(false);
            mode = Controls.ControlMode.touch;
            controls = new Controls(mode);

        }
        if (buttonControls.isChecked()){
            followControls.setChecked(false);
            touchControls.setChecked(false);
            mode = Controls.ControlMode.button;
            controls = new Controls(mode);

        }
        if (followControls.isChecked()){
            touchControls.setChecked(false);
            buttonControls.setChecked(false);
            mode = Controls.ControlMode.follow;
            controls = new Controls(mode);
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