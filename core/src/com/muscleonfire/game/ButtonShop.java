package com.muscleonfire.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonShop extends Button {

    Texture item_img;
    String item_name;

    public ButtonShop(float x, float y, String item_path, String item_name) {
        super(x, y, 100, 200, "shop_item_bg_pressed.png", "shop_item_bg.png");
        item_img = new Texture(item_path);
        this.item_name = item_name;
    }

    void draw(SpriteBatch batch, BitmapFont font){
        batch.draw(image, object.x, object.y);
        batch.draw(item_img, object.x + 15, object.y + 110);
        font.draw(batch,item_name, object.x + 8,  object.y + 70);
    }

    void permanentTrue(){
        isPressed = true;
    }
}
