package com.muscleonfire.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Controls {
    static Button leftButton;
    static Button rightButton;
    static Button jumpButton;
    static Button screenButton;

    enum controlMode{
        button,
        touch,
        follow
    }
    static controlMode mode;

    public Controls() {
    }

    public Controls(String setmode){ // initialise the position and size of the buttons
        if (setmode == "touch"){
            mode = controlMode.touch;
        }
        else if(setmode == "follow"){
            mode = controlMode.follow;
        }
        else if(setmode == "button"){
            mode = controlMode.button;
        }else{
            mode = controlMode.follow;
        }
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
    }

    void drawButtons(SpriteBatch batch) {
        if (mode == controlMode.button){
            leftButton.draw(batch);
            rightButton.draw(batch);
        }
        jumpButton.draw(batch);
    }

    public static void setControlMode(String control){
        if (control == "touch"){
            mode = controlMode.touch;
        }if(control == "follow"){
            mode = controlMode.follow;
        }if(control == "button"){
            mode = controlMode.button;
        }
    }

    public static String getControlMode(){
        String check = "";
        if (mode == controlMode.touch){
            check = "touch";
        }if (mode == controlMode.follow){
            check = "follow";
        }if (mode == controlMode.button){
            check = "button";
        }
        return check;
    }

}
