package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
	private static final float WORLD_SIZE = 3f;

	private final Viewport viewport = new FitViewport(WORLD_SIZE, WORLD_SIZE);
	private final SpriteBatch batch;
	private final Texture background, circle, cross, win, lose, tie;

	public GameScreen() {
		batch = new SpriteBatch();
		background = new Texture("stage.png");
		circle = new Texture("circle.png");
		cross = new Texture("cross.png");
		win = new Texture("win.png");
		lose = new Texture("Bez nazwy.png");
		tie = new Texture("tie.png");
		newGame();

	}

	@Override
	public void dispose() {
		batch.dispose();
		background.dispose();
		circle.dispose();
		cross.dispose();
		win.dispose();
		lose.dispose();
		tie.dispose();
	}

	public void update() {
		if (Gdx.input.justTouched() && condition != State.TURN_X
				&& condition != State.TURN_O)
			newGame();
		else if (Gdx.input.justTouched() && condition == State.TURN_X) {
			Vector2 coords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			viewport.unproject(coords);
			float posX = coords.x;
			float posY = coords.y;
			int fieldX = 0, fieldY = 0;
			if (posX > 0 && posX <= 1)
				fieldX = 0;
			if (posX > 1 && posX <= 2)
				fieldX = 1;
			if (posX > 2 && posX <= 3)
				fieldX = 2;
			if (posY > 0 && posY <= 1)
				fieldY = 0;
			if (posY > 1 && posY <= 2)
				fieldY = 1;
			if (posY > 2 && posY <= 3)
				fieldY = 2;
			if (newField[fieldX][fieldY] == Pool.CLEAN) {
				newField[fieldX][fieldY] = Pool.X;
				condition = State.TURN_O;
			}
		} else if (condition == State.TURN_O) {
			boolean found = false;
			do {
				int fieldX = (int) Math.round(Math.random() * 2);
				int fieldY = (int) Math.round(Math.random() * 2);

				if (newField[fieldX][fieldY] == Pool.CLEAN) {
					found = true;
					newField[fieldX][fieldY] = Pool.O;
					condition = State.TURN_X;

				}
			} while (found == false);
		}
	}

	private boolean ifWin() {
		for (int i = 0; i < 3; i++) {
			if (newField[0][i] == Pool.X && newField[1][i] == Pool.X
					&& newField[2][i] == Pool.X) {
				return true;
			} else if (newField[i][0] == Pool.X && newField[i][1] == Pool.X
					&& newField[i][2] == Pool.X) {
				return true;
			}

			else if (newField[0][0] == Pool.X && newField[1][1] == Pool.X
					&& newField[2][2] == Pool.X) {
				return true;
			} else if (newField[2][0] == Pool.X && newField[1][1] == Pool.X
					&& newField[0][2] == Pool.X)
				return true;
		}
		return false;
	}

	private boolean ifLose() {
		for (int i = 0; i < 3; i++) {
			if (newField[0][i] == Pool.O && newField[1][i] == Pool.O
					&& newField[2][i] == Pool.O) {
				return true;
			} else if (newField[i][0] == Pool.O && newField[i][1] == Pool.O
					&& newField[i][2] == Pool.O) {
				return true;
			}

			else if (newField[0][0] == Pool.O && newField[1][1] == Pool.O
					&& newField[2][2] == Pool.O) {
				return true;
			} else if (newField[2][0] == Pool.O && newField[1][1] == Pool.O
					&& newField[0][2] == Pool.O)
				return true;
		}
		return false;
	}

	private boolean ifTie() {
		int count = 0;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (newField[i][j] != Pool.CLEAN)
					count++;
			}
		if (count == 9)
			return true;
		else
			return false;
	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		if (condition == State.TURN_O || condition == State.TURN_X) {
			batch.draw(background, 0, 0, WORLD_SIZE, WORLD_SIZE);
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++) {
					if (newField[i][j] == Pool.X)
						batch.draw(cross, i, j, WORLD_SIZE / 3f,
								WORLD_SIZE / 3f);
					if (newField[i][j] == Pool.O)
						batch.draw(circle, i, j, WORLD_SIZE / 3f,
								WORLD_SIZE / 3f);

				}
		}
		if (ifWin() == true) {
			condition = State.WIN;
			if (TimeUtils.nanoTime() > (10000000 * 10000))
				batch.draw(win, 0, 0, WORLD_SIZE, WORLD_SIZE);
		}

		if (ifLose() == true) {
			condition = State.LOSE;
			batch.draw(lose, 0, 0, WORLD_SIZE, WORLD_SIZE);
		}
		if (ifTie() == true && !ifLose() && !ifWin()) {
			condition = State.TIE;
			batch.draw(tie, 0, 0, WORLD_SIZE, WORLD_SIZE);
		}

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.getCamera().position.set(WORLD_SIZE / 2f, WORLD_SIZE / 2f, 0f);
		viewport.update(width, height, false);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	private enum State {
		WIN, LOSE, TIE, TURN_X, TURN_O
	}

	private State condition;

	private enum Pool {
		X, O, CLEAN;
	}

	private final Pool[][] newField = new Pool[3][3];

	public void cleanField() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				newField[i][j] = Pool.CLEAN;
	}

	private void newGame() {
		cleanField();
		if (Math.round(Math.random()) == 1)
			condition = State.TURN_X;
		else
			condition = State.TURN_O;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
