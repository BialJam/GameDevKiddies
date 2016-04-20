/*package com.mygdx.jumpgame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator implements ApplicationListener {

    private static final int        FRAME_COLS = 6;         // #1


    Animation walkAnimation;          // #3
    Texture walkSheet;              // #4
    TextureRegion[][] walkFrames;             // #5
    SpriteBatch spriteBatch;            // #6
    TextureRegion currentFrame;

    float stateTime;                                        // #8

    public void create() {
    	walkSheet = new Texture(Gdx.files.internal("skeleton.png")); // #9
        TextureRegion[][] tmp= TextureRegion.split(walkSheet, 64, 128);
        walkFrames = new TextureRegion[FRAME_COLS][0];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            
        	walkFrames[index++] = tmp[i];
}
        walkAnimation = new Animation(0.025f, walkFrames[6]);      // #11
        spriteBatch = new SpriteBatch();                // #12
        stateTime = 0f;                         // #13
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);  // #16
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, 50, 50);             // #17
        spriteBatch.end();
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}

*/
