package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {

    public Animation<TextureRegion> loadAnimation(String imgLocation, int imgColumns, int imgRows, float durationPerFrame){
        // takes in (sprite sheet file location, sprite column, sprite rows, duration per frame)
        // returns animation texture array
        Texture Sheet = new Texture(Gdx.files.internal(imgLocation));
        TextureRegion[][] tmp = TextureRegion.split(Sheet,
                Sheet.getWidth() / imgColumns,
                Sheet.getHeight() / imgRows);

        TextureRegion[] Frames = new TextureRegion[imgColumns * imgRows];
        int index = 0;
        for (int i = 0; i < imgRows; i++) {
            for (int j = 0; j < imgColumns; j++) {
                Frames[index++] = tmp[i][j];
            }
        }
        return new Animation<TextureRegion>(durationPerFrame, Frames);
    }

}
