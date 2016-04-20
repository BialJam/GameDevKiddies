package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TicTacToe extends Game {

	MainMenu menu;
	GameScreen gameScreen;

	SpriteBatch batch;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		menu = new MainMenu(gameScreen);
		setScreen(menu);
	}

}
