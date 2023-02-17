package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Button extends GameObject {
    boolean isPressed;
    Texture image_Pressed;
    Texture image_notPressed;

    public Button(float x, float y, float width, float height, String Pressed, String notPressed){
        object = new Rectangle(x, y, width, height);
        image_Pressed = new Texture(Gdx.files.internal(Pressed));
        image_notPressed = new Texture(Gdx.files.internal(notPressed));
        setTexture();
    }

    void setTexture(){ // set the texture to when button is pressed or not pressed
        if (isPressed){
            image = image_Pressed;
        }else{
            image = image_notPressed;
        }
    }

    void draw(SpriteBatch batch){
        batch.draw(image, object.x, object.y);
    }

    boolean getPressed(OrthographicCamera camera){ // put the coordinate of the touchPoint here
        for (int i = 0; i < 10; i++){
            if (Gdx.input.justTouched()) {
                // place the touched coordinate into a vector3
                Vector3 touchPoint = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                // unproject the coordinate, so it correlate with the screen shown by the camera
                camera.unproject(touchPoint);
                // create a Rectangle() with the coordinates of the touchPoint
                // and check if the button overlaps with the Rectangle()
                // if it overlaps, it means the button is being pressed
                // and isPressed = true
                isPressed = object.overlaps(new Rectangle(touchPoint.x, touchPoint.y, 0,0));
                // if the button is pressed set the Texture to being pressed and return true
                if (isPressed) {
                    setTexture();
                    return isPressed;
                }
            }
        }
        // if no touch, return false
        return false;
    }
}

