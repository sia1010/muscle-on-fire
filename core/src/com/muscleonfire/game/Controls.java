package com.muscleonfire.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Controls {
    Button leftButton;
    Button rightButton;
    Button jumpButton;
    Button screenButton;
    Button shieldButton;
    Button speedButton;
    Item item;


    FileHandle itemFile;
    enum controlMode{
        button,
        touch,
        follow
    }
    controlMode mode;

    public Controls(controlMode setmode){ // initialise the position and size of the buttons
        item = new Item();
        mode = setmode;
        screenButton = new Button(0,0,480,800, "nothing.png", "nothing.png");
        if (mode == controlMode.button) {
            leftButton = new Button(50, 30, 64, 64, "leftButton_unpressed.png", "leftButton_unpressed.png");
            rightButton = new Button(150, 30, 64, 64,"rightButton_unpressed.png", "rightButton_unpressed.png");
            jumpButton = new Button(360, 30, 64, 64, "jumpButton_unpressed.png", "jumpButton_unpressed.png");
        }else if (mode == controlMode.touch){
            leftButton = new Button(0, 0, 240, 800,"nothing.png", "nothing.png");
            rightButton = new Button(240, 0, 240, 800, "nothing.png", "nothing.png");
            jumpButton = new Button(50, 30, 380, 64, "jump_unpressed.png", "jump_unpressed.png");
        }else if (mode == controlMode.follow){
            leftButton = new Button(0, 0, 480, 800, "nothing.png", "nothing.png");
            rightButton = new Button(240, 0, 480, 800, "nothing.png", "nothing.png");
            jumpButton = new Button(50, 30, 380, 64, "jump_unpressed.png", "jump_unpressed.png");
        }

        shieldButton = new Button(30,600, 64,64,"shieldButton_pressed.png","button1.png");
        speedButton = new Button(30,520, 64,64,"speedButton_pressed.png", "button2.png");
    }

    void getInputs(OrthographicCamera camera, Player pat){
        if (mode == controlMode.follow) {
            if (pat.isFront){
                leftButton.object.x = pat.getX() - 480 + 30;
                rightButton.object.x = pat.getX() + 34;
            }else{
                leftButton.object.x = pat.getX() - 480 + 30 - 16;
                rightButton.object.x = pat.getX() + 34 - 16;
            }
        }
        // if touch left button of patrick, set leftButtonPressed to true, else it is false
        leftButton.getHeldDown(camera);
        // if touch right button of patrick, set rightButtonPressed to true, else it is false
        rightButton.getHeldDown(camera);
        // if touch jump button of patrick, set jumpButtonPressed to true, else it is false
        jumpButton.getHeldDown(camera);
        // if touch jump button of patrick, set shieldButtonPressed to true, else it is false
        shieldButton.setTexture();
        // if touch jump button of patrick, set shieldButtonPressed to true, else it is false
        speedButton.setTexture();

    }

    void drawButtons(SpriteBatch batch, BitmapFont font) {
        if (mode == controlMode.button){
            leftButton.draw(batch);
            rightButton.draw(batch);
        }
        jumpButton.draw(batch);
        shieldButton.draw(batch);
        speedButton.draw(batch);

        font.draw(batch, String.valueOf(item.getShield_amt()), 105, 640);
        font.draw(batch, String.valueOf(item.getSpeed_amt()), 105, 560);
    }


}
