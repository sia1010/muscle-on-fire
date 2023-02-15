package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {


    // VARIABLE DECLARATIONS
    final MuscleOnFire game; //setscreen,batch,camera,font are included in game class
    Player patrick;
    Building background;
    FallingObjects fallingObjects;
    Score score = new Score();
    Array<Floor> floors = new Array<Floor>(); // Floor = data type Floor(class)
    Array<Rescue> rescues = new Array<Rescue>();
    Array<Obstacles> obstacle = new Array<Obstacles>();
    Array<FallingObjects> falling_glass =new Array<FallingObjects>();
    Array<FallingObjects> falling_stone =new Array<FallingObjects>();
    Array<SpecialFloor> sfloors = new Array<SpecialFloor>();

    Array<Enemies> ebat = new Array<Enemies>();
    Controls controls;

    boolean touch_glass=false;
    boolean touch_stone=false;
    float time_passed;
    float randomizer_obstacle;
    float randomizer_sfloor;
    float randomizer_objects;
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

    void addSFloor(){

        SpecialFloor sfloor = new SpecialFloor();
        sfloor.spawn();

        // add the floor into the floors array
        sfloors.add(sfloor);
    }
    void addEnemies(){

        Enemies enemy = new Enemies();
        enemy.spawn();

        // add the floor into the floors array
        ebat.add(enemy);
    }
    void addObstacles(){
        // add a new obstacles
        Obstacles o = new Obstacles();
        o.spawn(floors);

        // add the o into the obstacles_ppl array
        obstacle.add(o);
    }

    void addRescue(){
        // add a new obstacles
        Rescue r = new Rescue();
        r.spawn(floors);

        // add the o into the obstacles_ppl array
        rescues.add(r);
    }

    void addGlass(){
        FallingObjects glass=new FallingObjects();
        glass.falling_glass_spawn();

        falling_glass.add(glass);
    }

    void addStone(){
        FallingObjects stone=new FallingObjects();
        stone.falling_stone_spawn();

        falling_stone.add(stone);
    }


    void drawAllObjects(){
        // draw score
        game.font.draw(game.batch, "SCORE: "+ score.displayScore(), 150, 700);

        //draw high score
        game.font.draw(game.batch, "HIGHEST SCORE: "+ score.displayHighScore(), 70, 750);

        // draw background
        game.batch.draw(background.getTexture(),background.getX(),background.getY());

        // draw patrick
        game.batch.draw(patrick.getTexture(), patrick.getX(), patrick.getY());

        // draw all the floors
        for (Floor floor : floors) { // for each floor(data type Floor) in floors(array) draw the floor
            game.batch.draw(floor.getTexture(), floor.getX(), floor.getY());
        }

        for (SpecialFloor sfloor : sfloors) {
            game.batch.draw(sfloor.getTexture(), sfloor.getX(), sfloor.getY());
        }

        // draw bat_enemy
        for (Enemies enemy : ebat) {
            game.batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
        }

        // draw all the rescue
        for (Rescue res : rescues) {
            game.batch.draw(res.getTexture(), res.getX(), res.getY());
        }

        // draw all the obstacles
        for (Obstacles obs : obstacle) {
            game.batch.draw(obs.fireAnim.getKeyFrame(time_passed, true), obs.getX(), obs.getY());
        }

        for(FallingObjects gls : falling_glass ){
            game.batch.draw(gls.getTexture(), gls.getX(), gls.getY());
        }

        for(FallingObjects stn : falling_stone ){
            game.batch.draw(stn.getTexture(), stn.getX(),stn.getY());
        }
        //draw falling building
        game.batch.draw(fallingObjects.getTexture(),fallingObjects.getX(),fallingObjects.getY());

        // draw hearts
        patrick.drawHearts(this.game.batch);

        // draw all the buttons
        controls.drawButtons(this.game.batch);
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

        //initialising the falling building

        fallingObjects=new FallingObjects();
        fallingObjects.falling_building_spawn();

        // add first floor
        addFloor();

        // set randomizer obstacle
        randomizer_obstacle = MathUtils.random(15, 20);

        // set randomizer falling_objects
        randomizer_objects=MathUtils.random(8,13);

        // set game state as READY
        gameState = State.READY;

        // time
        time_passed = 0;

        // open High Score File
        score.openHighScoreFile();

        // initialise Controls
        controls = new Controls(Controls.controlMode.follow);
        controls.jumpButton.setTexture();
        controls.leftButton.setTexture();
        controls.rightButton.setTexture();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // render() is called once per frame.
        // delta is the time that has passed/elapsed since the last frame.

        // STOP TIME (if the game is not running)
        if (gameState != State.RUNNING) {
            delta = 0;
        }

        // RECORD ANY GAME CHANGES
        // record time
        time_passed += delta;

        //add score
        score.addScore(delta);

        // DISPLAY THE SCREEN
        // clear the screen(but not rectangle)
        ScreenUtils.clear(1, 1, 1, 1);
        // update camera
        game.camera.update();
        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        // draw all objects in the area
        game.batch.begin();

        // if game is in READY state give everything less opacity and display text
        if (gameState == State.READY) {
            game.batch.setColor(1, 1, 1, 0.5f);
            drawAllObjects();
            game.batch.setColor(1, 1, 1, 1);
            game.font.draw(game.batch, "TAP TO START", 135, 400);
        }

        // if game is RUNNING, just draw everything normally.
        if (gameState == State.RUNNING) {
            drawAllObjects();
        }

        // if game is in OVER state give everything red overlay and display text
        if (gameState == State.OVER) {
            game.batch.setColor(0.8f, 0, 0, 0.8f);
            drawAllObjects();
            game.font.draw(game.batch, "GAME OVER", 160, 420);
            game.font.draw(game.batch, "TAP TO CONTINUE", 110, 380);
            game.batch.setColor(1, 1, 1, 1);
            score.saveScore();
        }

        // stop drawing (for now)
        game.batch.end();


        // PLAYER INPUTS
        controls.getInputs(this.game, patrick);

        if (gameState == State.READY) {
            // press to start
            if (Gdx.input.justTouched()) {
                gameState = State.RUNNING;
            }
        }
        if (gameState == State.RUNNING) {
            // player movement (next frame)
            patrick.move(delta, controls);
            patrick.jump(delta, floors);
        }
        if (gameState == State.OVER) {
            // press to continue to game over screen
            if (Gdx.input.justTouched()) {
                this.game.setScreen(new GameOver(this.game,score));
            }
        }

        // UPDATE GAME OBJECTS
        // check if patrick fall beyond the screen,if yes then game over
        if (patrick.updateGameOver()) {
            gameState = State.OVER;
        }

        // update everything
        patrick.transpose(delta);
        for (Floor floor : floors) {
            floor.transpose(delta);
        }

        for (Rescue res : rescues) {
            res.playerTouched(patrick, delta, score);
            res.transpose(delta);
        }

        for (Obstacles obs : obstacle) {
            obs.playerTouched(patrick,delta);
            obs.transpose(delta);
        }

        for (SpecialFloor sfloor : sfloors) {
            sfloor.transpose(delta);
        }

        for (Enemies enemy : ebat) {

            enemy.checkDirection();
            enemy.move(delta);
            if (enemy.playerTouched(patrick,delta)){
                ebat.removeValue(enemy, true);
            };
        }

        for (FallingObjects gls : falling_glass) {

            gls.transpose(delta);


            if (gls.playerTouched(patrick)){
                falling_glass.removeValue(gls,true);
            }

        }

        for (FallingObjects stn : falling_stone) {
            stn.transpose(delta);

            if (stn.playerTouched(patrick)){
                falling_stone.removeValue(stn,true);
            }
        }

        // make patrick fall
        patrick.fall(delta, floors);


        // ADD / DELETE GAME OBJECTS
        // add new floors
        if (floors.peek().getY() > -80) {
            if (time_passed > randomizer_sfloor) {
                addSFloor();
                randomizer_sfloor += MathUtils.random(5, 10); // add the obstacles time
            }
            if (sfloors.peek().getY() > -80) {

                addFloor();
            }
        }

        if (time_passed > randomizer_objects) {
            if (MathUtils.random(1, 5) <= 2) {
                addGlass();
            } else {
                addStone();
            }
            randomizer_objects += MathUtils.random(10,15); // add the object time
        }

        // the obstacle will be added in the range of (15, 20) of the time passed
        // every 15-20 s will add one rescue
        if (time_passed > randomizer_obstacle) {
            if (MathUtils.random(1, 5) <= 2) {
                addRescue();
            } else {
                addObstacles();
            }
            addEnemies();
            randomizer_obstacle += MathUtils.random(8, 12); // add the obstacles time
        }

        // delete floors which are out of screen
        for (Floor floor : floors) {
            if (floor.getY() > 1000) {
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
