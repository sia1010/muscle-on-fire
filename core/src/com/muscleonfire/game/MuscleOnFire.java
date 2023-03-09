package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//cheah

public class MuscleOnFire extends Game { //Game like main,can change screen using setscreen(in built method)
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected OrthographicCamera camera;
	protected FreeTypeFontGenerator generator;
	protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	Coins coin;

	public void create() {
		// initialise a SpriteBatch to be assigned to 'batch'
		batch = new SpriteBatch();

		// initialise camera and set camera size to 480 x 800
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);

		// create freetype font generator and assign a .ttf font to it
		generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));

		// create a freetype font parameter to set the properties of the font we want to use
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 30	; // size = 30
		parameter.color = new Color(0,0,0,1); // color = black

		// generate the font needed and assign it to the BitmapFont
		font = generator.generateFont(parameter);

		// set the Screen to the Menu
		this.setScreen(new Menu(this)); //this = me (vb), setscreen=open

		// open Coin File
		coin = new Coins();
		coin.openCoinFile();
	}

	public void render() {
		super.render(); // important!
	}

	//after closing the app
	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
