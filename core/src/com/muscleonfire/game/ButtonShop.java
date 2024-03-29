package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class ButtonShop extends Button {

    private Texture item_img;
    private String item_name;
    private BitmapFont font2;

    public ButtonShop(float x, float y, String item_path, String item_name) {
        super(x, y, 100, 200, "Buttons/Shop/shop_item_bg_pressed.png", "Buttons/Shop/shop_item_bg.png");
        item_img = new Texture(item_path);
        this.item_name = item_name;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        parameter.color = new Color(Color.BLACK);
        font2 = generator.generateFont(parameter);
    }

    public void draw(SpriteBatch batch, BitmapFont font, int quantity){
        batch.draw(image, object.x, object.y);
        batch.draw(item_img, object.x + 15, object.y + 110);

        font.draw(batch,item_name, object.x + 8,  object.y + 75);
        font2.draw(batch, "Owned: " + quantity, object.x + 18, object.y + 40);
    }

    public void permanentTrue(){
        forcePressed(true);
    }
}
