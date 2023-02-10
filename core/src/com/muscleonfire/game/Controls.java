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
        setMode(setmode);
        if (mode == controlMode.button) {
            leftButton = new Button(50, 30, 64, 64, new Texture(Gdx.files.internal("badlogic.jpg")), new Texture(Gdx.files.internal("badlogic.jpg")));
            rightButton = new Button(150, 30, 64, 64, new Texture(Gdx.files.internal("badlogic.jpg")), new Texture(Gdx.files.internal("badlogic.jpg")));
            jumpButton = new Button(360, 30, 64, 64, new Texture(Gdx.files.internal("badlogic.jpg")), new Texture(Gdx.files.internal("badlogic.jpg")));
        }else if (mode == controlMode.touch){
            leftButton = new Button(0, 0, 240, 800, new Texture(Gdx.files.internal("nothing.png")), new Texture(Gdx.files.internal("nothing.png")));
            rightButton = new Button(240, 0, 240, 800, new Texture(Gdx.files.internal("nothing.png")), new Texture(Gdx.files.internal("nothing.png")));
            jumpButton = new Button(50, 30, 380, 64, new Texture(Gdx.files.internal("badlogic.jpg")), new Texture(Gdx.files.internal("badlogic.jpg")));
        }else if (mode == controlMode.follow){
            leftButton = new Button(0, 0, 0, 0, new Texture(Gdx.files.internal("nothing.png")), new Texture(Gdx.files.internal("nothing.png")));
            rightButton = new Button(0, 0, 0, 0, new Texture(Gdx.files.internal("nothing.png")), new Texture(Gdx.files.internal("nothing.png")));
            jumpButton = new Button(50, 30, 380, 64, new Texture(Gdx.files.internal("badlogic.jpg")), new Texture(Gdx.files.internal("badlogic.jpg")));
        }
    }

    void getInputs(MuscleOnFire game, Player pat){
        resetButtons();
        // check if got touch screen (use for loop for multiple touches)
        for(int i =0; i < 10; i++){ //for many fingers touch together
            if (Gdx.input.isTouched(i)){
                // if got touch, get the position of the touch
                Vector3 touchPos = new Vector3();
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                game.camera.unproject(touchPos);
                touchPoint = new Rectangle(touchPos.x, touchPos.y, 5,5);

                if (mode != controlMode.follow){// if touch left of patrick, set leftButtonPressed to true, else it is false
                    if ((leftButton.object.overlaps(touchPoint))) {
                        leftButton.isPressed = true;
                    }
                    // if touch right of patrick, set rightButtonPressed to true, else it is false
                    if ((rightButton.object.overlaps(touchPoint))) {
                        rightButton.isPressed = true;
                    }
                    // if touch right of patrick, set rightButtonPressed to true, else it is false
                    if ((jumpButton.object.overlaps(touchPoint))) {
                        jumpButton.isPressed = true;
                    }
                } else if (mode == controlMode.follow) {
                    if ((touchPos.x < pat.getX())) {
                        leftButton.isPressed = true;
                    }
                    // if touch right of patrick, set rightButtonPressed to true, else it is false
                    if ((touchPos.x > pat.getX())) {
                        rightButton.isPressed = true;
                    }
                    // if touch right of patrick, set rightButtonPressed to true, else it is false
                    if ((jumpButton.object.overlaps(touchPoint))) {
                        jumpButton.isPressed = true;
                    }
                }
            }
        }
        leftButton.setTexture();
        rightButton.setTexture();
        leftButton.setTexture();
    }

    void resetButtons(){
        leftButton.isPressed = false;
        rightButton.isPressed = false;
        jumpButton.isPressed = false;
    }

    void drawButtons(SpriteBatch batch) {
        if (mode != controlMode.follow){
            batch.draw(leftButton.image, leftButton.getX(), leftButton.getY());
            batch.draw(rightButton.image, rightButton.getX(), rightButton.getY());
        }
        batch.draw(jumpButton.image, jumpButton.getX(), jumpButton.getY());
    }

    void setMode(controlMode setmode){
        mode = setmode;
    }
}
