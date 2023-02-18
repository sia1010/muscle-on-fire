package com.muscleonfire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    float floor_time = 0;
    int random;
    // VARIABLE DECLARATIONS
    final MuscleOnFire game; //setscreen,batch,camera,font are included in game class
    Player patrick;
    Building background;
    FallingObjects fallingObjects;
    Score score = new Score();
    Array<Floor> floors = new Array<Floor>(); // Floor = data type Floor(class)
    Array<Rescue> rescues = new Array<Rescue>();
    Array<Medicine> medicines = new Array<Medicine>();
    Array<Obstacles> obstacle = new Array<Obstacles>();
    Array<FallingObjects> falling_glass =new Array<FallingObjects>();
    Array<FallingObjects> falling_stone =new Array<FallingObjects>();
    Array<FallingObjects> falling_life =new Array<FallingObjects>();
    Array<SpecialFloor> spikefloors = new Array<SpecialFloor>();
    Array<SpecialFloor> tramfloors = new Array<SpecialFloor>();
    Array<SpecialFloor> woodfloors = new Array<SpecialFloor>();
    Array<Enemies> ebat = new Array<Enemies>();
    Controls controls;

    boolean touch_glass=false;
    boolean touch_stone=false;

    boolean touch_life=false;
    float time_passed;
    float randomizer_obstacle;
    boolean rescue_backlog = false;
    boolean obstacle_backlog = false;
    boolean medicine_backlog = false;
    float randomizer_spikefloor;
    float randomizer_tramfloor;
    float randomizer_woodfloor;
    float randomizer_objects;
    enum State{
        READY,
        RUNNING,
        OVER
    }
    State gameState;


    // FUNCTIONS

    void initialFloor(){
        for (int i = 0; i < 4; i++){
            addFloor();
            floors.peek().object.y += i * 100;
        }
    }
    void addFloor(){
        // add a new floor
        Floor floor = new Floor();
        floor.spawn();

        // add the floor into the floors array
        floors.add(floor);
    }

    void addSpikeFloor(){

        SpecialFloor spikefloor = new SpecialFloor();
        spikefloor.spike_spawn();

        // add the floor into the floors array
        spikefloors.add(spikefloor);
    }
    void addTramFloor(){

        SpecialFloor tramfloor = new SpecialFloor();
        tramfloor.trampoline_spawn();

        // add the floor into the floors array
        tramfloors.add(tramfloor);
    }
    void addWoodFloor(){

        SpecialFloor woodfloor = new SpecialFloor();
        woodfloor.wood_spawn();

        // add the floor into the floors array
        woodfloors.add(woodfloor);
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

        // add the o into the obstacle array
        obstacle.add(o);
    }

    void addRescue(){
        // add a new obstacles
        Rescue r = new Rescue();
        r.spawn(floors);

        // add the o into the rescues array
        rescues.add(r);
    }

    void addMedicine(){
        // add a new obstacles
        Medicine m = new Medicine();
        m.spawn(floors);

        // add the o into the medicines array
        medicines.add(m);
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

    void addLife(){
        FallingObjects life=new FallingObjects();
        life.falling_life_spawn();

        falling_life.add(life);
    }


    void drawAllObjects(float delta){

        // draw background
        game.batch.draw(background.getTexture(),background.getX(),background.getY());

        // draw score
        game.font.draw(game.batch, "SCORE: "+ score.displayScore(), 150, 700);

        //draw high score
        game.font.draw(game.batch, "HIGHEST SCORE: "+ score.displayHighScore(), 70, 750);

        // draw all the floors
        for (Floor floor : floors) { // for each floor(data type Floor) in floors(array) draw the floor
            game.batch.draw(floor.getTexture(), floor.getX(), floor.getY());
        }

        for (SpecialFloor spikefloor : spikefloors) {
            game.batch.draw(spikefloor.getTexture(), spikefloor.getX(), spikefloor.getY());
        }

        for (SpecialFloor tramfloor : tramfloors) {
            game.batch.draw(tramfloor.getTexture(), tramfloor.getX(), tramfloor.getY());
        }
        for (SpecialFloor woodfloor : woodfloors) {
            game.batch.draw(woodfloor.woodAnim.getKeyFrame(time_passed, true), woodfloor.getX(), woodfloor.getY());
        }
        // draw bat_enemy
        for (Enemies enemy : ebat) {
            game.batch.draw(enemy.batmanfly.getKeyFrame(time_passed, true), enemy.getX(), enemy.getY());
            //game.batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
        }

        // draw all the rescue
        for (Rescue res : rescues) {
            game.batch.draw(res.rescueAni.getKeyFrame(time_passed, true), res.getX(), res.getY());
            game.batch.draw(res.getTextureHelpBox(), res.getHelpX(), res.getHelpY());
        }

        // draw all the medicine
        for (Medicine med : medicines) {
            game.batch.draw(med.getTexture(), med.getX(), med.getY());
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

        for(FallingObjects life : falling_life ){
            game.batch.draw(life.getTexture(), life.getX(),life.getY());
        }

        // draw patrick
        patrick.drawPatrick(this.game.batch, time_passed);

        //draw falling building
        game.batch.draw(fallingObjects.getTexture(),fallingObjects.getX(),fallingObjects.getY());

        // draw hearts
        patrick.drawHearts(this.game.batch, delta);

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
        initialFloor();

        // set randomizer obstacle
        randomizer_obstacle = MathUtils.random(15, 20);

        //set random for obstacle,rescue, medicine
        random = MathUtils.random(1, 10);

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
        floor_time += delta;

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
            drawAllObjects(delta);
            game.batch.setColor(1, 1, 1, 1);
            game.font.draw(game.batch, "TAP TO START", 135, 400);
        }

        // if game is RUNNING, just draw everything normally.
        if (gameState == State.RUNNING) {
            drawAllObjects(delta);
        }

        // if game is in OVER state give everything red overlay and display text
        if (gameState == State.OVER) {
            game.batch.setColor(0.8f, 0, 0, 0.8f);
            drawAllObjects(delta);
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
            patrick.jump(delta, time_passed, floors, spikefloors, tramfloors,woodfloors);
            patrick.jump(delta, floors, spikefloors, tramfloors, ebat);
        }
        if (gameState == State.OVER) {
            // press to continue to game over screen
            if (Gdx.input.justTouched()) {
                this.game.setScreen(new GameOver(this.game, score));
            }
        }

        // UPDATE GAME OBJECTS
        // check if patrick fall beyond the screen,if yes then game over
        if (patrick.updateGameOver()) {
            gameState = State.OVER;
        }

        // update everything
        patrick.transpose(delta, time_passed);
        for (Floor floor : floors) {
            floor.transpose(delta, time_passed);
        }

        for (Rescue res : rescues) {
            res.playerTouched(patrick, delta, score);
            res.transpose(delta, time_passed);
        }

        for (Medicine med : medicines) {
            med.playerTouched(patrick);
            med.transpose(delta, time_passed);
            if (med.playerTouched(patrick)) {
                medicines.removeValue(med, true);
            }
            ;
        }

        for (Obstacles obs : obstacle) {
            obs.playerTouched(patrick, delta);
            obs.transpose(delta, time_passed);
        }

        for (SpecialFloor spikefloor : spikefloors) {

            spikefloor.transpose(delta, time_passed);


            spikefloor.touchedSpike(patrick, delta);
        }
//        for (SpecialFloor sfloor : spikefloors) {
//            sfloor.transpose(delta);
//        }

        for (SpecialFloor tramfloor : tramfloors) {
            tramfloor.transpose(delta, time_passed);
        }

        for (SpecialFloor woodfloor : woodfloors) {
            woodfloor.transpose(delta, time_passed);
        }

        for (Enemies enemy : ebat) {
            enemy.batmanKilled(patrick, delta);
            if (enemy.killed){
                enemy.transpose(delta, time_passed);
            }
            else if (enemy.playerTouched(patrick, delta)){
                ebat.removeValue(enemy, true);
            }else{
                enemy.checkDirection();
                enemy.move(delta);
            }

        }

        for (FallingObjects gls : falling_glass) {

            gls.transpose(delta, time_passed);
            if (gls.playerTouched(patrick)) {
                falling_glass.removeValue(gls, true);
            }
        }

        for (FallingObjects stn : falling_stone) {

            stn.transpose(delta, time_passed);

            if (stn.playerTouched(patrick)) {
                falling_stone.removeValue(stn, true);
            }
        }

        for (FallingObjects life : falling_life) {
            life.transpose(delta, time_passed);

            if (life.playerTouchedLife(patrick)) {
                falling_life.removeValue(life, true);
            }
        }

        // make patrick fall
        patrick.fall(delta, time_passed, floors, spikefloors, tramfloors,woodfloors);
        patrick.fall(delta, floors, spikefloors, tramfloors, ebat, time_passed);


        // ADD / DELETE GAME OBJECTS
        // add new floors
        // use time to control add floor
        if (floor_time > (1000 / (850 + time_passed))) {
            if (time_passed > randomizer_tramfloor) {
                addTramFloor();
                randomizer_tramfloor += MathUtils.random(3, 5); // add the obstacles time
                floor_time = 0;
            } else if (time_passed > randomizer_spikefloor) {
                addSpikeFloor();
                randomizer_spikefloor += MathUtils.random(5, 8);
                floor_time = 0;
            } else if (time_passed > randomizer_woodfloor) {
                addWoodFloor();
                randomizer_woodfloor += MathUtils.random(5, 8);
                floor_time = 0;
            } else {
                addFloor();
                floor_time = 0;
            }
        }

        if (time_passed > randomizer_objects) {
            int a = MathUtils.random(1, 10);
            if (a <= 3) {
                addGlass();
            } else if (a <= 6) {
                addStone();
            } else {
                addLife();
            }
            randomizer_objects += MathUtils.random(8, 15); // add the object time
        }

        // the obstacle will be added in the range of (15, 20) of the time passed
        // every 15-20 s will add one rescue
        if (time_passed > randomizer_obstacle) {
            if (MathUtils.random(1, 5) <= 2) {
                if (random <= 3) {
                    rescue_backlog = true;
                } else if (random <= 7) {
                    medicine_backlog = true;
                } else {
                    obstacle_backlog = true;
                }
                addEnemies();
                randomizer_obstacle += MathUtils.random(8, 12); // add the obstacles time
                random = MathUtils.random(1, 10);
            }
        }

        if (rescue_backlog) {
            for (Floor floor : floors) {
                if (floor.getY() < 0) {
                    rescue_backlog = false;
                    addRescue();
                    break;
                }
            }
        } else if (obstacle_backlog) {
            for (Floor floor : floors) {
                if (floor.getY() < 0) {
                    obstacle_backlog = false;
                    addObstacles();
                    break;
                }
            }
        } else if (medicine_backlog) {
            for (Floor floor : floors) {
                if (floor.getY() < 0) {
                    medicine_backlog = false;
                    addMedicine();
                    break;
                }
            }
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
