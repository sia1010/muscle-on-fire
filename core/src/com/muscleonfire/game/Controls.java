package com.muscleonfire.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Controls {
    static Button leftButton;
    static Button rightButton;
    static Button jumpButton;
    static Button screenButton;
    static Button shieldButton;
    static Button speedButton;
    static Item item;
    enum ControlMode {
        button,
        touch,
        follow
    }
    static ControlMode mode;

    public Button getLeftButton() {
        return leftButton;
    }

    public Button getRightButton() {
        return rightButton;
    }

    public Button getJumpButton() {
        return jumpButton;
    }

    public Button getScreenButton() {
        return screenButton;
    }

    public Button getShieldButton() {
        return shieldButton;
    }

    public Button getSpeedButton() {
        return speedButton;
    }

    public Controls(){ // initialise the position and size of the buttons
        item = new Item();
        screenButton = new Button(0,0,480,800, "nothing.png", "nothing.png");
        if (mode == ControlMode.button) {
            leftButton = new Button(50, 30, 64, 64, "Buttons/Controls/leftButton_unpressed.png", "Buttons/Controls/leftButton_unpressed.png");
            rightButton = new Button(150, 30, 64, 64,"Buttons/Controls/rightButton_unpressed.png", "Buttons/Controls/rightButton_unpressed.png");
            jumpButton = new Button(360, 30, 64, 64, "Buttons/Controls/jumpButton_unpressed.png", "Buttons/Controls/jumpButton_unpressed.png");
        }else if (mode == ControlMode.touch){
            leftButton = new Button(0, 0, 240, 800,"nothing.png", "nothing.png");
            rightButton = new Button(240, 0, 240, 800, "nothing.png", "nothing.png");
            jumpButton = new Button(50, 30, 380, 64, "Buttons/Controls/jump_unpressed.png", "Buttons/Controls/jump_unpressed.png");
        }else if (mode == ControlMode.follow){
            leftButton = new Button(0, 0, 480, 800, "nothing.png", "nothing.png");
            rightButton = new Button(240, 0, 480, 800, "nothing.png", "nothing.png");
            jumpButton = new Button(50, 30, 380, 64, "Buttons/Controls/jump_unpressed.png", "Buttons/Controls/jump_unpressed.png");
        }

        shieldButton = new Button(30,600, 64,64,"Buttons/Controls/shieldButton_pressed.png","Buttons/Controls/shieldButton.png");
        speedButton = new Button(30,520, 64,64,"Buttons/Controls/speedButton_pressed.png", "Buttons/Controls/speedButton.png");
    }

    public void getInputs(OrthographicCamera camera, Player pat){
        if (mode == ControlMode.follow) {
            if (pat.isFront()){
                leftButton.object.x = pat.getX() - 480 + 30;
                rightButton.object.x = pat.getX() + 34;
            }else{
                leftButton.object.x = pat.getX() - 480 + 30 - 16;
                rightButton.object.x = pat.getX() + 34 - 16;
            }
        }
        // if touch left button of patrick, set leftButtonPressed to true, else it is false
        leftButton.onHeldDown(camera);
        // if touch right button of patrick, set rightButtonPressed to true, else it is false
        rightButton.onHeldDown(camera);
        // if touch jump button of patrick, set jumpButtonPressed to true, else it is false
        jumpButton.onHeldDown(camera);
        // if touch jump button of patrick, set shieldButtonPressed to true, else it is false
        shieldButton.setTexture();
        // if touch jump button of patrick, set shieldButtonPressed to true, else it is false
        speedButton.setTexture();

    }

    public void drawButtons(SpriteBatch batch, BitmapFont font) {
        if (mode == ControlMode.button){
            leftButton.draw(batch);
            rightButton.draw(batch);
        }
        jumpButton.draw(batch);
        shieldButton.draw(batch);
        speedButton.draw(batch);

        font.draw(batch, String.valueOf(item.getShield_amt()), 105, 640);
        font.draw(batch, String.valueOf(item.getSpeed_amt()), 105, 560);
    }

    public static void setControlMode(ControlMode controlMode){
            mode = controlMode;
    }

    public static ControlMode getControlMode(){
        return mode;
    }

}
