package com.muscleonfire.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.awt.peer.CanvasPeer;
//cheah

public class MuscleOnFire extends Game { //Game like main,can change screen using setscreen(in built method)
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected OrthographicCamera camera;
	protected FreeTypeFontGenerator generator;
	protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;
	protected Coins coin;

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

		if (Settings.settingsFile.exists()) {
			String[] arr = Settings.settingsFile.readString().split("@");
			if(arr[0].equals("follow")){
				Settings.setControlMode(Controls.ControlMode.follow);
				Controls.setControlMode(Controls.ControlMode.follow);
			} else if(arr[0].equals("button")){
				Settings.setControlMode(Controls.ControlMode.button);
				Controls.setControlMode(Controls.ControlMode.button);
			} else if(arr[0].equals("touch")){
				Settings.setControlMode(Controls.ControlMode.touch);
				Controls.setControlMode(Controls.ControlMode.touch);
			}
			Settings.setSettingsVolume(Float.parseFloat(arr[1]), Float.parseFloat(arr[2]));
		} else {
			Controls.setControlMode(Controls.ControlMode.follow);
			Settings.setControlMode(Controls.ControlMode.follow);
			Settings.setSettingsVolume(1.0f, 1.0f);
			Settings.settingsFile.writeString("follow@1.0f@1.0f", false);
		}
	}

	//after closing the app
	public void dispose() {
		batch.dispose();
		font.dispose();
		generator.dispose();
	}

	public float getMusicVolume() {
		Music music = Gdx.audio.newMusic(Gdx.files.internal("BGM.mp3"));

		return music.getVolume();
	}


}
