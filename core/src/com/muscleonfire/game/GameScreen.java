package com.muscleonfire.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final MuscleOnFire game;
    Player patrick;
    Array<Floor> floors = new Array<Floor>(); // Floor = data type Floor(class)
    float time_passed, lastFloorAddTime;

    public GameScreen(final MuscleOnFire game) {
        this.game = game;

        // spawn patrick
        patrick = new Player();
        patrick.spawn();

        // time
        time_passed = 0;
    }

    @Override
    public void show() {

    }

    void addFloor(){
        // check if 3 second has passed since the last floor was added before adding a new one.
        if (time_passed - lastFloorAddTime > 3){

            // add a new floor
            Floor floor = new Floor();
            floor.spawn();

            // add the floor into the floors array
            floors.add(floor);

            // update the last time a floor has been added to current time
            lastFloorAddTime = time_passed;
        }
    }

    @Override
    public void render(float delta) {
        // render() is called once per frame.
        // delta is the time that has passed/elapsed since the last frame.


        // RECORD ANY GAME CHANGES
        // record time
        time_passed += delta;


        // DISPLAY THE SCREEN
        // clear the screen
        ScreenUtils.clear(1,1,1,1);

        // update camera
        game.camera.update();

        // set the projection area
        game.batch.setProjectionMatrix(game.camera.combined);

        // draw all objects in the area
        game.batch.begin();

        // draw patrick
        game.batch.draw(patrick.getTexture(), patrick.getX(), patrick.getY());

        // draw all the floors
        for (Floor floor : floors) { // for each floor(data type Floor) in floors(array) draw the floor
            game.batch.draw(floor.getTexture(), floor.getX(), floor.getY());
        }

        // stop drawing (for now)
        game.batch.end();


        // PLAYER INPUTS
        // player movement
        patrick.move(delta, this.game, floors);


        // UPDATE GAME OBJECTS
        // move everything up
        patrick.transpose(delta);
        for (Floor floor : floors) {
            floor.transpose(delta);
        }

        // make patrick fall
        patrick.fall(delta, floors);


        // ADD NEW GAME OBJECTS
        // add new floors
        addFloor();
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
