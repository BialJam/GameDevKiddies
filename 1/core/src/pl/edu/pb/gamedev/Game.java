package pl.edu.pb.gamedev;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.VisUI.SkinScale;
import com.kotcrab.vis.ui.widget.VisDialog;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

public class Game extends ApplicationAdapter {
	private Texture dropImage;

	private Texture bucketImage;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private Music rainMusic;
	private Sound dropSound;
	private int points;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private boolean gameEnd;
	// UI:
	private Stage stage;
	private VisLabel label;
	
	
	
	
	public void create() {
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		
		
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();

		// UI:
		VisUI.load(SkinScale.X2);
		stage = new Stage();
		final VisTable table = new VisTable(true);
		table.setFillParent(true);
		stage.addActor(table);

		final VisTable upper = new VisTable(true);
		upper.setBackground(VisUI.getSkin().getDrawable("button"));
		table.add(upper).fillX().expandX().height(50).row();

		label = new VisLabel("0");
		upper.add(label);

		table.add(new Actor()).grow();

		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (Gdx.input.isTouched()) {
			final Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			bucket.x -= 1000 * Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			bucket.x += 1000 * Gdx.graphics.getDeltaTime();
		}
		if (bucket.x < 0) {
			bucket.x = 0;
		}
		if (bucket.x > 800 - 64) {
			bucket.x = 800 - 64;
		}

		final Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			final Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
				points++;
				if (points % 10 == 0) {
					final VisDialog motivation = new VisDialog("LEVEL UP");
					motivation.show(stage);
					label.setText(String.valueOf(points));
					motivation.getTitleLabel().addAction(
							Actions.sequence(Actions.delay(0.5f),
									Actions.color(Color.PINK, 1f),
									Actions.rotateBy(180),
									Actions.removeActor(motivation)));
				}
			} else if (raindrop.y + 64 < 0) {
				iter.remove();
				points--;

				if (points < -1) {
					final VisDialog dialog = new VisDialog(
							"NIESTETY PRZEGRAŁEŚ");
					dialog.show(stage);
					gameEnd = true;
					Timer.schedule(new Task() {
						@Override
						public void run() {
							dialog.hide();
							gameEnd = false;
							spawnRaindrop();
							points = 0;
							label.setText("0");
						}

					}, 2.5f);
				}
			}
			label.setText(String.valueOf(points));
		}

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);
		for (final Rectangle drop : raindrops) {
			batch.draw(dropImage, drop.x, drop.y);
		}
		batch.end();
		stage.act();
		stage.draw();
	}

	private void spawnRaindrop() {
		if (gameEnd) {
			return;
		}
		final Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		Timer.schedule(new Task() {
			@Override
			public void run() {
				spawnRaindrop();

			}

		}, Math.max(2f - points / 20f, 0.4f));
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		rainMusic.dispose();
		dropSound.dispose();
		stage.dispose();
		batch.dispose();
	}



}
