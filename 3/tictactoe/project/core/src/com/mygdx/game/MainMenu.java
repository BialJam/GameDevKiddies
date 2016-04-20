package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MainMenu implements Screen {

	GameScreen game;
	SpriteBatch batch;
	BitmapFont font = new BitmapFont();
	Button button = new Button();
	Label label;

	public MainMenu(GameScreen game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		font.setColor(Color.WHITE);
		batch.begin();
		font.draw(batch, "Click SPACE to start", Gdx.graphics.getWidth() / 2f
				- font.getLineHeight(), Gdx.graphics.getHeight() / 2f);
		batch.end();

		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			((TicTacToe) Gdx.app.getApplicationListener()).setScreen(game);
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.app.log("MainMenu", "show called");
		batch = new SpriteBatch();
	}

	@Override
	public void hide() {
		Gdx.app.log("MainMenu", "hide called");

	}

	@Override
	public void pause() {
		Gdx.app.log("MainMenu", "pause called");
	}

	@Override
	public void resume() {
		Gdx.app.log("MainMenu", "resume called");

	}

	@Override
	public void dispose() {
		batch.dispose();

	}
}