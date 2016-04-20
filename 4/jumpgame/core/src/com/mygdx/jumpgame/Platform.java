package com.mygdx.jumpgame;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Platform extends Rectangle{

	private Texture texture;

	
	public Platform (Texture texture){
		this.texture = texture;
		this.height = texture.getHeight();
		this.width = texture.getWidth();
	}
	public void draw (SpriteBatch batch){
		batch.draw(texture, x, y);
	}
}