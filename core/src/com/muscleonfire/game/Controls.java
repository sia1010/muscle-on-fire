package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Controls {
    Button leftButton;
    Button rightButton;
    Button jumpButton;
    Rectangle touchPoint;
    enum controlMode{
        button,
        touch,
        follow
    }
    controlMode mode;

    public Controls(controlMode setmode){ // initialise the position and size of the buttons
        mode = setmode;
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
    }

    void getInputs(MuscleOnFire game, Player pat){
        resetButtons();
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
        leftButton.getPressed(game.camera);
        // if touch right button of patrick, set rightButtonPressed to true, else it is false
        rightButton.getPressed(game.camera);
        // if touch jump button of patrick, set jumpButtonPressed to true, else it is false
        jumpButton.getPressed(game.camera);
    }

    void resetButtons(){
        leftButton.isPressed = false;
        rightButton.isPressed = false;
        jumpButton.isPressed = false;
    }

    void drawButtons(SpriteBatch batch) {
        leftButton.draw(batch);
        rightButton.draw(batch);
        jumpButton.draw(batch);
    }
}
