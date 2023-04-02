package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.Objects;


public class Settings implements Screen {
    private final MuscleOnFire game;
    private final SpriteBatch batch;
    private Button backMenuButton;;
    private Musics musics = new Musics();
    private final Stage stage = new Stage(new ScreenViewport());
    private final Skin skin = new Skin(Gdx.files.internal("Slider/uiskin.json"));
    private final Slider musicSlider = new Slider(0f, 2f, 0.1f, false, skin);
    private final Slider soundSlider = new Slider(0f,2f,0.1f,false, skin);
    private final CheckBox touchControls = new CheckBox("Touch Controls", skin);
    private final CheckBox buttonControls = new CheckBox("Button Controls",skin);
    private final CheckBox followControls = new CheckBox("Follow Controls", skin);
    private ButtonGroup<CheckBox> checkBoxGroup = new ButtonGroup<>();
    private static float BGMVolume;
    private static float soundVolume;
    private static Controls.ControlMode controlMode;
    public static FileHandle settingsFile = Gdx.files.external("settings.txt");



    public Settings(final MuscleOnFire game){

        this.game = game;
        batch = this.game.batch;


        musics.settingsPlay();
        Gdx.input.setInputProcessor(stage);

        backMenuButton = new Button(20,740,128,32,"Buttons/MainMenu/back_button_pressed.png","Buttons/MainMenu/back_button.png");

        setControls();
        if(Objects.equals(Controls.getControlMode(), Controls.ControlMode.follow)){
            followControls.setChecked(true);
        }
        if(Objects.equals(Controls.getControlMode(), Controls.ControlMode.button)){
            buttonControls.setChecked(true);
        }
        if(Objects.equals(Controls.getControlMode(), Controls.ControlMode.touch)){
            touchControls.setChecked(true);
        }

        setSliderLocation();
        setSlider();
        setVolume();
    }

    public void setControls(){
        followControls.setPosition(200,550);
        followControls.setColor(0,0,0,1);
        buttonControls.setPosition(200,500);
        buttonControls.setColor(0,0,0,1);
        touchControls.setPosition(200,450);
        touchControls.setColor(0,0,0,1);
        checkBoxGroup.add(buttonControls);
        checkBoxGroup.add(touchControls);
        checkBoxGroup.add(followControls);
        checkBoxGroup.setMaxCheckCount(1);
    }

    public static void setControlMode(Controls.ControlMode controlMode) {
        Settings.controlMode = controlMode;
    }

    public static void setSettingsVolume(float BGM, float SFX){
        BGMVolume = BGM;
        soundVolume = SFX;
    }

    public void setVolume(){
        musics.setBGMVolume(BGMVolume);
        Sounds.setVolume(soundVolume);
    }

    public void setSlider(){
        musicSlider.setVisualPercent(BGMVolume);
        soundSlider.setVisualPercent(soundVolume);
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
        ScreenUtils.clear(1, 1, 1, 1);
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
        game.font.draw(game.batch, "SFX    : ", 100, 560);
        game.batch.end();

        //creates a stage with actors
        stage.draw();
        stage.addActor(touchControls);
        stage.addActor(buttonControls);
        stage.addActor(followControls);
        stage.addActor(musicSlider);
        stage.addActor(soundSlider);


        setVolume();
        //updates bgm/sfx volume



        if (backMenuButton.onReleased(this.game.camera)) {
            Sounds.pressed();
            musics.settingsStop();
            if (followControls.isChecked()) {
                Controls.setControlMode(Controls.ControlMode.follow);
                controlMode = Controls.ControlMode.follow;
                Settings.settingsFile.writeString(controlMode.toString() + "@" + BGMVolume + "@" + soundVolume, false);
            }
            if (buttonControls.isChecked()) {
                Controls.setControlMode(Controls.ControlMode.button);
                controlMode = Controls.ControlMode.button;
                Settings.settingsFile.writeString(controlMode.toString() + "@" + BGMVolume + "@" + soundVolume, false);
            }
            if (touchControls.isChecked()) {
                Controls.setControlMode(Controls.ControlMode.touch);
                controlMode = Controls.ControlMode.touch;
                Settings.settingsFile.writeString(controlMode.toString() + "@" + BGMVolume + "@" + soundVolume, false);
            }

            game.setScreen(new Menu(this.game));
        }

        musicSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                float volume = musicSlider.getValue();
                BGMVolume = volume;
                Settings.settingsFile.writeString(controlMode.toString() + "@" + BGMVolume + "@" + soundVolume, false);
            }
        });
        soundSlider.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                float volume = soundSlider.getValue();
                soundVolume = volume;
                Settings.settingsFile.writeString(controlMode.toString() + "@" + BGMVolume + "@" + soundVolume, false);
            }
        });
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
