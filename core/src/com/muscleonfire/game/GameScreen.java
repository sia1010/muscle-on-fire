package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {


    // VARIABLE DECLARATIONS
    final MuscleOnFire game; //setscreen,batch,camera,font are included in game class
    Player patrick;
    Building background;
    Score score = new Score();
    Array<Floor> floors = new Array<Floor>(); // Floor = data type Floor(class)
    float time_passed;
    enum State{
        READY,
        RUNNING,
        OVER
    }
    State gameState;


    // FUNCTIONS
    void addFloor(){
        // add a new floor
        Floor floor = new Floor();
        floor.spawn();

        // add the floor into the floors array
        floors.add(floor);
    }

    Animation<TextureRegion> loadAnimation(String imgLocation, int imgColumns, int imgRows, float durationPerFrame){
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

    void drawAllObjects(){
        // draw score
        game.font.draw(game.batch, "SCORE: "+ score.displayScore(), 150, 700);

        //draw high score
        game.font.draw(game.batch, "HIGHEST SCORE: "+ score.displayHighScore(), 90, 750);

        // draw background
        game.batch.draw(background.getTexture(),background.getX(),background.getY());

        // draw patrick
        game.batch.draw(patrick.getTexture(), patrick.getX(), patrick.getY());

        // draw all the floors
        for (Floor floor : floors) { // for each floor(data type Floor) in floors(array) draw the floor
            game.batch.draw(floor.getTexture(), floor.getX(), floor.getY());
        }
    }


    // SCREEN METHODS
    public GameScreen(final MuscleOnFire game) {
        this.game = game;

        // spawn patrick
        patrick = new Player();
        patrick.spawn();

        // initialising the background
        background= new Building();
        background.spawn();

        // add first floor
        addFloor();

        // set game state as READY
        gameState = State.READY;

        // time
        time_passed = 0;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // render() is called once per frame.
        // delta is the time that has passed/elapsed since the last frame.

        // STOP TIME (if the game is not running)
        if (gameState != State.RUNNING){
            delta = 0;
        }

        // RECORD ANY GAME CHANGES
        // record time
        time_passed += delta;

        //add score
        score.addScore(delta);

        // DISPLAY THE SCREEN
        // clear the screen(but not rectangle)
        ScreenUtils.clear(1,1,1,1);
        // update camera
        game.camera.update();
        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        // draw all objects in the area
        game.batch.begin();

        // if game is in READY state give everything less opacity and display text
        if (gameState == State.READY){
            game.batch.setColor(1,1,1,0.5f);
            drawAllObjects();
            game.batch.setColor(1,1,1,1);
            game.font.draw(game.batch, "TAP TO START", 135, 400);
        }

        // if game is RUNNING, just draw everything normally.
        if (gameState == State.RUNNING){
            drawAllObjects();
        }

        // if game is in READY state give everything red overlay and display text
        if (gameState == State.OVER){
            game.batch.setColor(0.8f,0,0,0.8f);
            drawAllObjects();
            game.font.draw(game.batch, "GAME OVER", 160, 420);
            game.font.draw(game.batch, "TAP TO CONTINUE", 110, 380);
            game.batch.setColor(1,1,1,1);
            score.saveScore();
        }

        // stop drawing (for now)
        game.batch.end();


        // PLAYER INPUTS
        if (gameState == State.READY){
            // press to start
            if(Gdx.input.justTouched()){
                gameState = State.RUNNING;
            }
        }
        if (gameState == State.RUNNING) {
            // player movement (next frame)
            patrick.move(delta, this.game, floors);
        }
        if (gameState == State.OVER){
            // press to continue to game over screen
            if(Gdx.input.justTouched()){
                this.game.setScreen(new GameOver(this.game));
            }
        }

        // UPDATE GAME OBJECTS
        // check if patrick fall beyond the screen,if yes then game over
        if (patrick.updateGameOver()){
            gameState = State.OVER;
        }

        // move everything up
        patrick.transpose(delta);
        for (Floor floor : floors) {
            floor.transpose(delta);
        }

        // make patrick fall
        patrick.fall(delta, floors);


        // ADD / DELETE GAME OBJECTS
        // add new floors
        if(floors.peek().getY() > 100){addFloor();}
        // delete floors which are out of screen
        for (Floor floor: floors){
            if(floor.getY() > 1000){
                floors.removeValue(floor, true);
            }
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
