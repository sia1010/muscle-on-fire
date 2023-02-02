package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class MuscleOnFire extends Game {
	SpriteBatch batch;
	BitmapFont font;
	OrthographicCamera camera;

	public void create() {
		// initialise a SpriteBatch to be assigned to 'batch'
		batch = new SpriteBatch();

		// initialise camera and set camera size to 480 x 800
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);

		// create freetype font generator and assign a .ttf font to it
		FreeTypeFontGenerator generator;
		generator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraft.ttf"));

		// create a freetype font parameter to set the properties of the font we want to use
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 30; // size = 30
		parameter.color = new Color(0,0,0,1); // color = black

		// generate the font needed and assign it to the BitmapFont
		font = generator.generateFont(parameter);

		// set the Screen to the Menu
		this.setScreen(new Menu(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
