package com.mygdx.jumpgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.jumpgame.JumpPlayer.State;

public class GameScreen implements Screen {
	private Music music;
	private Texture playerTexture,rightTexture, leftTexture, platformTexture;
	private JumpPlayer player;
	private Array<Platform> platformArray;
	private OrthographicCamera camera;
	SpriteBatch batch;
	BitmapFont font;
	MainMenu menu;
	private float gravity = -20;
	TextureRegion[] sprites;

	@Override
	public void show() {

		loadData();
		init();
		// TODO Auto-generated method stub

	}

	private void init() {
		batch = new SpriteBatch();
		music.play();
		camera = new OrthographicCamera(480, 800);
		player = new JumpPlayer(playerTexture);
		platformArray = new Array<Platform>();
		for (int i = 1; i < 50; i++) {
			Platform p = new Platform(platformTexture);
			p.x = MathUtils.random(480);
			p.y = 200 * i;
			platformArray.add(p);
		}

	}

	private void loadData() {
		playerTexture = new Texture("walkingSkeleton/player.png");
		rightTexture = new Texture ("walkingSkeleton/playerright.png");
		leftTexture = new Texture ("walkingSkeleton/player.png");
		platformTexture = new Texture("platform.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("jumpJump.mp3"));

	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		for (int i = 0; i < 100000; i++) {
			batch.draw(platformTexture, 500 + platformTexture.getWidth(), i);
			batch.draw(platformTexture, 0 - platformTexture.getWidth(), i);
			batch.draw(platformTexture, i, 0);
		}

		for (Platform p : platformArray) {
			p.draw(batch);
		}
		player.draw(batch);
		batch.end();

	}

	private void update() {
		handleInput();
		camera.update();
		camera.position.set(player.x + player.width / 2, player.y+300, 0);
		player.y += player.jumpVelocity * Gdx.graphics.getDeltaTime();

		if (player.y > 0) {
			player.jumpVelocity += gravity;
		} else {
			player.y = 0;
			player.canJump = true;
			player.jumpVelocity = 0;
		}
		for (Platform p : platformArray) {
			onPlatform(p);
			if (onPlatform(p)) {
				player.canJump = true;
				player.jumpVelocity = 0;
				player.y = (p.y + p.height);
			}
			if (player.x <= 0) {
				player.x += 1;
			}
			if (player.x >= 480 + p.width) {
				player.x -= 1;
			}
		}

	}

	private boolean onPlatform(Platform p) {
		return player.jumpVelocity <= 0 && player.overlaps(p)&& !(player.y <= p.y);
	}

	private void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			playerTexture = leftTexture;
			player.x -= 300 * Gdx.graphics.getDeltaTime();

		} 
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			playerTexture= rightTexture;
			player.x += 300 * Gdx.graphics.getDeltaTime();
			

		}  
		
		if (Gdx.input.isKeyPressed(Keys.SPACE) || (Gdx.input.justTouched())) {
			player.jump();

		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
