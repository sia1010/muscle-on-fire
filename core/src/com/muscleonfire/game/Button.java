package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Button extends GameObject {
    boolean isPressed;
    Texture image_Pressed;

    public Button(float x, float y, float width, float height, Texture texture){
        object = new Rectangle(x, y, width, height);
        image_Pressed = texture;
    }
}
