package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
public class GameOver implements Screen {


    Button retryButton;
    Button menuButton;
    Score score;
    int coins;
    final MuscleOnFire game;
    @Override
    public void show() {

    }

    public GameOver(final MuscleOnFire game,Score score){
        this.game = game;
        this.game.coin.addCoin(score);
        this.game.coin.saveCoin();
        this.score = score;
        this.coins = score.score/100;

        retryButton=new Button(90,400,300,64,"tap_to_retry_notpressed.png","tap_to_retry.png");
        menuButton=new Button(90,250,300,64,"back_to_menu_notpressed.png","back_to_menu.png");
    }





    @Override
    public void render(float delta) {
        // DISPLAY THE SCREEN
        // clear the screen but not rectangle
        ScreenUtils.clear(1,1,1,1);
        // update camera
        game.camera.update();
        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        //game.font.draw(game.batch, "TAP TO RETRY", 135, 400);

        game.font.draw(game.batch, "SCORE: "+ score.displayScore(), 150, 750);
        game.font.draw(game.batch, "Total Coins: "+ this.game.coin.displayCoin(), 120, 700);
        game.font.draw(game.batch, "Coins earned: "+ this.coins, 120, 650);


        menuButton.draw(game.batch);
        retryButton.draw(game.batch);


        game.batch.end();

        if (retryButton.getJustPressed(this.game.camera)){
            game.setScreen(new GameScreen(this.game));
        }
        else if (menuButton.getJustPressed(this.game.camera)) {
            game.setScreen(new Menu(this.game));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
