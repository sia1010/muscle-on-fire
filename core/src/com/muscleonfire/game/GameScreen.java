package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    enum Obstacle {
        NULL,
        FIRE,
        RESCUE,
        MEDICINE,
        MYSTERY
    }
    enum State{
        READY,
        RUNNING,
        OVER
    }

    // VARIABLE DECLARATIONS
     private final MuscleOnFire game; //setscreen,batch,camera,font are included in game class

    // Game Objects
    private Player patrick;
    private Sidewall sidewall;
    private Array<Wallpaper> wallpapers = new Array<Wallpaper>();
    private FallingObjects fallingBuilding;
    private Score score;
    private Array<Floor> floors = new Array<Floor>(); // Floor = data type Floor(class)
    private Floor latestFloor;
    private Array<Obstacles> mysteries = new Array<Obstacles>();
    private Array<Obstacles> rescues = new Array<Obstacles>();
    private Array<Obstacles> medicines = new Array<Obstacles>();
    private Array<Obstacles> fires = new Array<Obstacles>();
    private Array<FallingObjects> falling_glass =new Array<FallingObjects>();
    private Array<FallingObjects> falling_stone =new Array<FallingObjects>();
    private Array<FallingObjects> falling_life =new Array<FallingObjects>();
    private Array<FallingObjects> falling_slime =new Array<FallingObjects>();
    private Array<Slime> onfloor_slime=new Array<Slime>();
    private Array<Slime> die_slime=new Array<Slime>();
    private Array<Bat> ebat = new Array<Bat>();

    int i = 0;
    // Audio
    private Musics musics = new Musics();


    // Game Data Values
    private float time_passed;
    private float randomizer_obstacle;
    private float randomizer_spikefloor;
    private float randomizer_tramfloor;
    private float randomizer_rightroll;
    private float randomizer_leftroll;
    private float randomizer_objects;
    private float floor_time = 0;
    private State gameState;
    private Obstacle next_obstacle = Obstacle.NULL;


    // FUNCTIONS

    private void initialFloor(){
        for (int i = 0; i < 4; i++){
            addFloor(Floor.FloorID.floor);
            floors.peek().object.y += i * 120;
        }
    }
    private void initialWallpaper(){
        for (int i = 0; i < 8; i++){
            addWallpaper();
            wallpapers.peek().object.y += i * 120;
        }
    }
    private void addFloor(Floor.FloorID id){
        // add a new floor
        Floor floor = new Floor();
        floor.spawn(id);

        // add the floor into the floors array
        floors.add(floor);
    }

    private void addWallpaper(){
        Wallpaper wallpaper = new Wallpaper();
        wallpaper.spawn();

        //add wallpaper to wallpapers array?
        wallpapers.add(wallpaper);
    }

    private void addEnemies(){

        Bat enemy = new Bat();
        enemy.spawn();

        // add the floor into the floors array
        ebat.add(enemy);
    }
    private void addFire(){
        // add a new obstacles
        Obstacles fire = new Obstacles();
        fire.spawnFire(floors);

        // add the fire into the fire array
        fires.add(fire);
    }
    private void addRescue(){
        // add a new obstacles
        Obstacles rescue = new Obstacles();
        rescue.spawnRescue(floors);

        // add the rescue into the rescues array
        rescues.add(rescue);
    }
    private void addMedicine(){
        // add a new obstacles
        Obstacles medicine = new Obstacles();
        medicine.spawnMedicine(floors);

        // add the medicine into the medicines array
        medicines.add(medicine);
    }

    private void addMystery(){
        // add a new obstacles
        Obstacles mystery = new Obstacles();
        mystery.spawnMysteryBox(floors);

        // add the mystery into the mystery array
        mysteries.add(mystery);
    }
    private void addGlass(){
        FallingObjects glass=new FallingObjects();
        glass.falling_glass_spawn();

        falling_glass.add(glass);
    }
    private void addStone(){
        FallingObjects stone=new FallingObjects();
        stone.falling_stone_spawn();

        falling_stone.add(stone);
    }
    private void addLife(){
        FallingObjects life=new FallingObjects();
        life.falling_life_spawn();

        falling_life.add(life);
    }

    private void addSlime(){
        FallingObjects slime=new FallingObjects();
        slime.falling_slime_spawn(floors);

        falling_slime.add(slime);
    }

    private void addOnFloorSlime(GameObject slime){
        Slime onfloorSlime=new Slime();
        onfloorSlime.onfloor_spawn(slime);

        onfloor_slime.add(onfloorSlime);
    }
    private void addDieSlime(GameObject slime){
        Slime dieSlime=new Slime();
        dieSlime.dieSlime_spawn(slime);

        die_slime.add(dieSlime);
    }

    private void drawAllObjects(float delta){
        // draw Sidewalls
        game.batch.draw(sidewall.getTexture(), sidewall.getX(), sidewall.getY());

        game.batch.setColor(1,1,1,0.3f);
        for (Wallpaper wallpaper : wallpapers){
            game.batch.draw(wallpaper.getTexture(), wallpaper.getX(),wallpaper.getY());
        }
        game.batch.setColor(1,1,1,1);

        // draw score items
        score.drawScore(game, delta);

        // draw all the floors
        for (Floor floor : floors) { // for each floor(data type Floor) in floors(array) draw the floor
            floor.draw(this.game.batch, time_passed);
        }

        // draw bat_enemy
        for (Bat enemy : ebat) {
            game.batch.draw(enemy.getBatmanfly().getKeyFrame(time_passed, true), enemy.getX(), enemy.getY());
            //game.batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
        }

        // draw all the rescue
        for (Obstacles rescue : rescues) {
            game.batch.draw(rescue.getRescueAni().getKeyFrame(time_passed, true), rescue.getX(), rescue.getY());
            game.batch.draw(rescue.getTextureHelpBox(), rescue.getHelpX(), rescue.getHelpY());
        }

        // draw all the medicine
        for (Obstacles medicine : medicines) {
            game.batch.draw(medicine.getTexture(), medicine.getX(), medicine.getY());
        }

        // draw all mystery boxes
        for (Obstacles mystery : mysteries) {
            game.batch.draw(mystery.getTexture(), mystery.getX(), mystery.getY());
        }

        // draw all the fires
        for (Obstacles fire : fires) {
            game.batch.draw(fire.getFireAnim().getKeyFrame(time_passed, true), fire.getX(), fire.getY());
        }

        // draw all the falling glass
        for(FallingObjects gls : falling_glass ){
            game.batch.draw(gls.getTexture(), gls.getX(), gls.getY());
        }

        // draw all the falling stone
        for(FallingObjects stn : falling_stone ){
            game.batch.draw(stn.getTexture(), stn.getX(),stn.getY());
        }

        // draw all the falling life
        for(FallingObjects life : falling_life ){
            game.batch.draw(life.getTexture(), life.getX(),life.getY());
        }

        // draw all the falling slime
        for(FallingObjects sli : falling_slime ){
            game.batch.draw(sli.getTexture(), sli.getX(),sli.getY());
        }

        for(Slime onfloor_slime : onfloor_slime ){
            game.batch.draw(onfloor_slime.getTexture(), onfloor_slime.getX(),onfloor_slime.getY());
        }

        for(Slime die_slime : die_slime ){
            game.batch.draw(die_slime.getTexture(), die_slime.getX(),die_slime.getY());
        }

        // draw patrick
        patrick.drawPlayer(this.game.batch, time_passed);

        //draw falling building
        game.batch.draw(fallingBuilding.getTexture(), fallingBuilding.getX(), fallingBuilding.getY());

        // draw hearts
        patrick.getHealthPoint().drawHearts(this.game.batch, delta);

        // draw all the buttons
        patrick.getControls().drawButtons(this.game.batch, this.game.font);
    }


    // SCREEN METHODS
    public GameScreen(final MuscleOnFire game) {

        //plays music
        musics.gamePlay();

        this.game = game;

        // spawn patrick
        patrick = new Player();
        patrick.spawn();

        // initialising the Sidewalls
        sidewall = new Sidewall();
        sidewall.spawn();

        //initialising the falling building
        fallingBuilding = new FallingObjects();
        fallingBuilding.falling_building_spawn();

        latestFloor = new Floor();
        latestFloor.spawn(Floor.FloorID.floor);

        // add first floor
        initialFloor();
        initialWallpaper();



        // set randomizer obstacle
        randomizer_obstacle = MathUtils.random(15, 20);

        // set randomizer falling_objects
        randomizer_objects = MathUtils.random(8,13);

        // set game state as READY
        gameState = State.READY;

        // time
        time_passed = 0;

        // open High Score File
        score = new Score(game);
        score.openHighScoreFile();
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

        // add score
        score.addScore(delta);

        latestFloor.transpose(delta, time_passed);


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
            musics.gameStop();
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
        patrick.getControls().getInputs(this.game.camera, patrick);

        if (gameState == State.READY) {
            // press to start
            if (patrick.getControls().getScreenButton().onReleased(this.game.camera)) {
                gameState = State.RUNNING;
            }
        }
        if (gameState == State.RUNNING) {
            // player movement (next frame)
            patrick.processControls(delta, game.camera);
            patrick.jump(delta, floors);

        }
        if (gameState == State.OVER) {
            // press to continue to game over screen
            if (patrick.getControls().getScreenButton().onReleased(this.game.camera)) {
                this.game.setScreen(new GameOver(this.game, score));

            }
        }

        // UPDATE GAME OBJECTS
        // check if patrick fall beyond the screen,if yes then game over
        if (patrick.updateGameOver()) {
            gameState = State.OVER;

            if (i == 0){
                Sounds.over();
                i++;
            }
        }
        // update everything to behave as intended, which depends on what happens
        // transpose slowly moves things up towards the top of the screen
        patrick.transpose(delta, time_passed);

        for (Wallpaper wallpaper : wallpapers){
            wallpaper.transpose(delta, wallpapers.peek().getY());
        }

        for (Floor floor : floors) {
            floor.transpose(delta, time_passed);
            floor.touched(patrick, delta);
        }

        for (Obstacles res : rescues) {
            res.playerTouchedRescue(patrick, delta, score);
            res.transposeRescue(delta, time_passed);
        }

        for (Obstacles medicine : medicines) {
            medicine.transpose(delta, time_passed);
            if (medicine.playerTouchedMedicine(patrick)) {
                medicines.removeValue(medicine, true);
            }
        }

        for (Obstacles mystery : mysteries) {
            mystery.transpose(delta, time_passed);
            if (mystery.playerTouchedMysteryBox(patrick, score)) {
                mysteries.removeValue(mystery, true);
            }
        }

        for (Obstacles fire : fires) {
            fire.transpose(delta, time_passed);
            fire.playerTouchedFire(patrick, delta);
        }

        for (Bat enemy : ebat) {
            if (enemy.isKilled()) {
                if(!enemy.isGivenScore()){
                    score.upScore(500);
                    enemy.setGivenScore(true);
                }
                enemy.transpose(delta, time_passed);
            } else if (enemy.playerTouched(patrick, delta)) {
                ebat.removeValue(enemy, true);
            } else {
                enemy.checkDirection();
                enemy.move(delta);
            }

            if (enemy.object.y < -200 || enemy.object.y > 800) {
                ebat.removeValue(enemy, true);
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

        for (FallingObjects slime : falling_slime) {
            slime.transpose(delta, time_passed);
            if (slime.playerTouched(patrick)) {
                falling_slime.removeValue(slime, true);
            }
            if (slime.isTouchingFloor(floors) && slime.object.y < 400) {
                addOnFloorSlime(slime);
                falling_slime.removeValue(slime, true);
            }
        }

        for (Slime slime : onfloor_slime) {
            if(slime.isOnFloor()) {
                slime.transpose(delta, time_passed);
            }

            slime.checkMovingDirection();
            slime.move(delta);
            slime.fall(slime.object,delta,floors,time_passed);

            if(slime.playerTouched(patrick)){
                onfloor_slime.removeValue(slime,true);
                addDieSlime(slime);
            }
        }
        for(Slime slime:die_slime){
            slime.transpose(delta,time_passed);
            slime.fall(slime.object,delta,floors,time_passed);
            float slime_die_time=slime.getTime();
            slime_die_time+=delta;
            slime.setTime(slime_die_time);
            if (slime.getTime() > 2){

                die_slime.removeValue(slime, true);
            }
        }

        // make patrick fall
        patrick.fall(delta, floors, ebat, time_passed);


        // ADD / DELETE GAME OBJECTS
        // add new floors
        // use time to control add floor
        if (latestFloor.getY() > 0) {
            if (time_passed > randomizer_tramfloor) {
                addFloor(Floor.FloorID.trampoline);
                randomizer_tramfloor += MathUtils.random(5, 10); // add the obstacles time
            } else if (time_passed > randomizer_spikefloor) {
                addFloor(Floor.FloorID.spike);
                randomizer_spikefloor += MathUtils.random(5, 10);
            }  else if (time_passed > randomizer_rightroll) {
                addFloor(Floor.FloorID.rollRight);
                randomizer_rightroll += MathUtils.random(10, 15);
                floor_time = 0;
            }else if (time_passed > randomizer_leftroll) {
                addFloor(Floor.FloorID.rollLeft);
                randomizer_leftroll += MathUtils.random(10, 15);
                floor_time = 0;
            } else {
                addFloor(Floor.FloorID.floor);
            }
            latestFloor.object.y = -120;
        }

        // add new wallpapers when old one moves up
        if(wallpapers.peek().getY() > -210 + 120){
            addWallpaper();
        }

        // add a random falling object every 8-15s
        if (time_passed > randomizer_objects) {
            int random = MathUtils.random(1, 10);
            if (random <= 2) {
                addGlass();
            } else if (random <=4) {
                addStone();
            } else if(random<=6){
                addLife();
            }
            else{
                addSlime();
            }
            randomizer_objects += MathUtils.random(8, 15); // add the object time
        }

        // add a random obstacle every 8-12s
        // when obstacle is added, an enemy bat is also added
        if (time_passed > randomizer_obstacle) {
            int random = MathUtils.random(1, 10);
            if (random <= 3) {
                next_obstacle = Obstacle.RESCUE;
            } else if (random <= 5) {
                next_obstacle = Obstacle.MEDICINE;
            } else if (random <= 7) {
                next_obstacle = Obstacle.MYSTERY;
            } else {
                next_obstacle = Obstacle.FIRE;
            }
            randomizer_obstacle += MathUtils.random(8, 12); // add the obstacles time
        }

        if (next_obstacle != Obstacle.NULL) {
            if (floors.peek().getY() < -64) {
                switch (next_obstacle) {
                    case RESCUE:
                        addRescue();
                        break;
                    case FIRE:
                        addFire();
                        break;
                    case MEDICINE:
                        addMedicine();
                        break;
                    case MYSTERY:
                        addMystery();
                }
                addEnemies();
                next_obstacle = Obstacle.NULL;
            }
        }

        // delete floors which are out of screen
        for (Floor floor : floors) {
            if (floor.getY() > 1000) {
                floors.removeValue(floor, true);

            }
        }
        for (Wallpaper wallpaper : wallpapers){
            if (wallpaper.getY() > 1000){
                wallpapers.removeValue(wallpaper, true);
            }
        }

        // delete falling objects which are out of screen
        for (FallingObjects fallingObject : falling_glass) {
            if (fallingObject.getY() < -200) {
                falling_glass.removeValue(fallingObject, true);
            }
        }
        for (FallingObjects fallingObject : falling_stone) {
            if (fallingObject.getY() < -200) {
                falling_stone.removeValue(fallingObject, true);
            }
        }
        for (FallingObjects fallingObject : falling_life) {
            if (fallingObject.getY() < -200) {
                falling_life.removeValue(fallingObject, true);
            }
        }
        for (FallingObjects fallingObject : falling_slime) {
            if (fallingObject.getY() < -200) {
                falling_slime.removeValue(fallingObject, true);
            }
        }
        for (Slime slime : onfloor_slime){
            if (slime.getY() < -200 || slime.getY() > 1000) {
                onfloor_slime.removeValue(slime, true);
            }
        }
        for(Slime slime : die_slime){
            if (slime.getY()  < -200 || slime.getY() > 1000){
                die_slime.removeValue(slime, true);
            }
        }

        // delete obstacles which are out of screen
        for (Obstacles fire : fires){
            if (fire.getY() > 1000){
                fires.removeValue(fire, true);
            }
        }
        for (Obstacles rescue : rescues){
            if (rescue.getY() > 1000){
                rescues.removeValue(rescue, true);
            }
        }
        for (Obstacles medicine : medicines){
            if (medicine.getY() > 1000){
                medicines.removeValue(medicine, true);
            }
        }
        for (Obstacles mystery : mysteries){
            if (mystery.getY() > 1000){
                mysteries.removeValue(mystery, true);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        gameState = State.READY;
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
