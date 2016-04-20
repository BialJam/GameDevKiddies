package com.mygdx.jumpgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Jumpgame extends Game {

	BitmapFont font;
	MainMenu menu;
    GameScreen gameScreen;

	public void create() {
		gameScreen = new GameScreen();
		menu = new MainMenu(gameScreen);
		setScreen(menu);
	}

}
