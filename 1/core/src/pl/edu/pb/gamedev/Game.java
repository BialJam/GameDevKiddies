package pl.edu.pb.gamedev;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.VisUI.SkinScale;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;

public class Game extends ApplicationAdapter {
    private Texture dropImage;
    private Texture bucketImage;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Music music;

    private Rectangle bucket;
    private Array<Rectangle> raindrops;
    private long lastDropTime;

    // UI:
    private Stage stage;
    private VisLabel label;

    @Override
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

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isTouched()) {
            final Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            bucket.x -= 500 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            bucket.x += 500 * Gdx.graphics.getDeltaTime();
        }
        if (bucket.x < 0) {
            bucket.x = 0;
        }
        if (bucket.x > 800 - 64) {
            bucket.x = 800 - 64;
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) {
            spawnRaindrop();
        }

        final Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            final Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (raindrop.overlaps(bucket)) {
                iter.remove();
                int points = Integer.parseInt(label.getText().toString());
                points++;
                label.setText(String.valueOf(points));
            } else if (raindrop.y + 64 < 0) {
                iter.remove();
                int points = Integer.parseInt(label.getText().toString());
                points--;
                label.setText(String.valueOf(points));
            }
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
        final Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void dispose() {
        dropImage.dispose();
        bucketImage.dispose();
        music.dispose();
        stage.dispose();
    }

}
