package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
public class GameOver implements Screen {
    private Button retryButton;
    private Button menuButton;
    private Score score;
    private int coins;
    private final MuscleOnFire game;

    @Override
    public void show() {

    }

    public GameOver(final MuscleOnFire game,Score score){
        this.game = game;
        this.game.coin.addCoin(score); //add coin based on the score player got to the total coins
        this.game.coin.saveCoin(); //save the coins in the record
        this.score = score;
        this.coins = score.getScore()/100; //coins will be equal to score divided by 100
        //generate new retry button
        retryButton=new Button(90,400,300,64,"Buttons/GameOver/tap_to_retry_pressed.png","Buttons/GameOver/tap_to_retry.png");
        //generate new menu button
        menuButton=new Button(90,250,300,64,"Buttons/GameOver/back_to_menu_pressed.png","Buttons/GameOver/back_to_menu.png");
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

        // draw the font SCORE and display it on the screen
        game.font.draw(game.batch, "SCORE: "+ score.displayScore(), 150, 750);
        //draw the font Total coins and display it on the screen
        game.font.draw(game.batch, "Total Coins: "+ this.game.coin.displayCoin(), 120, 700);
        //draw the font Coins earned in the last round and display it on the screen
        game.font.draw(game.batch, "Coins earned: "+ this.coins, 120, 650);

        //draw both of the button
        menuButton.draw(game.batch);
        retryButton.draw(game.batch);


        game.batch.end();

        //check if player click the retry button(direct to game screen), or click menu button(direct to menu screen)
        if (retryButton.onReleased(this.game.camera)){
            game.setScreen(new GameScreen(this.game));
        }
        else if (menuButton.onReleased(this.game.camera)) {
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
