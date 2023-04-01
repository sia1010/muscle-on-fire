package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Health {
    private int maxHealth = 3;
    private int currHealth = 3;

    private Player pat;
    private Texture filledHeart;
    private Texture emptyHeart;
    private Texture flashHeart;
    private float flashTime;
    private boolean isFlashing;

    public Health(Player pat){
        filledHeart = new Texture(Gdx.files.internal("Textures/health/heart_filled.png"));
        emptyHeart = new Texture(Gdx.files.internal("Textures/health/heart_empty.png"));
        flashHeart = new Texture(Gdx.files.internal("Textures/health/heart_flash.png"));
        this.pat = pat;
    }

    public int getCurrHealth() {
        return currHealth;
    }

    public void takeDamage(int damage){ // minus health equals to passed damage
        Sounds.hurt();//Sound effects
        if(pat.getPowerUp().checkShield()){
            return;
        }
        currHealth -= damage;
        isFlashing = true;
        flashTime = 0;
    }

    public void healDamage(int heal){
        Sounds.life();
        if (currHealth < maxHealth){
            currHealth += heal;
        }
    }

    public void drawHearts(SpriteBatch batch, float delta) {
        if (flashTime > 3){
            flashTime = 0;
            isFlashing = false;
        }

        if (!isFlashing) {
            for (int i = 0; i < maxHealth; i++) {
                if (i < currHealth) {
                    batch.draw(filledHeart, 300 + 40 * i, 100);
                } else {
                    batch.draw(emptyHeart, 300 + 40 * i, 100);
                }
            }
        } else {
            for (int i = 0; i < maxHealth; i++) {
                if (i < currHealth) {
                    batch.draw(filledHeart, 300 + 40 * i, 100);
                } else {
                    if ((int) (flashTime * 5) % 2 == 0) {
                        batch.draw(flashHeart, 300 + 40 * i, 100);
                    } else if ((int) (flashTime * 5) % 2 == 1) {
                        batch.draw(emptyHeart, 300 + 40 * i, 100);
                    }
                }

                flashTime += delta;
            }
        }
    }
}
