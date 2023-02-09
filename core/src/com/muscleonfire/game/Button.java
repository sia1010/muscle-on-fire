package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button extends GameObject {
    boolean isPressed;
    Texture image_Pressed;
    Texture image_notPressed;

    public Button(float x, float y, float width, float height, Texture Pressed, Texture notPressed){
        object = new Rectangle(x, y, width, height);
        image_Pressed = Pressed;
        image_notPressed = notPressed;
    }

    void setTexture(){
        if (isPressed){
            image = image_Pressed;
        }else{
            image = image_notPressed;
        }
    }
}

