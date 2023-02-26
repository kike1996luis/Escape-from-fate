package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Explosion {

	private Vector2 vector;
	private Body body;
	private Texture imagen;
	private TextureRegion[] explosionAnimation;
	private Animation animationTime;
	private float tiempo;
	private TextureRegion frameActual;
	private Sprite sprite;
	private SpriteBatch batch;
	private int timeExplosion=0;
	public boolean isStopped;
	private Sounds sounds;

	public Explosion(World world, SpriteBatch batch, Sounds sounds) {
		this.sounds=sounds;
		this.sprite=new Sprite();
		this.isStopped=true;
		this.batch=batch;
		this.imagen = new Texture(Gdx.files.internal(
				"data/sprites/interactions/explosion.png"));
		TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		explosionAnimation = new TextureRegion[5];
		System.arraycopy(tmp[0], 0, explosionAnimation, 0, 5);
		tiempo = 0f;
		animationTime = new Animation(0.05f, explosionAnimation);
		BodyDef bodyDef=new BodyDef();
		FixtureDef fixtureDef=new FixtureDef();
		bodyDef.type = BodyType.DynamicBody;
		CircleShape boom = new CircleShape();
		boom.setPosition(new Vector2(0, 0));
		boom.setRadius(5f);
		fixtureDef.shape = boom;
		fixtureDef.isSensor=true;
		body = world.createBody(bodyDef);
		body.createFixture(fixtureDef).setUserData(this);
		boom.dispose();
		body.setTransform(-999, -999, 0);
		body.setUserData("inactive");
	}
	
	public void dispose() {
		if(imagen != null) {
			imagen.dispose();
		}
		if(sprite.getTexture() != null) {
			sprite.getTexture().dispose();
		}
		body.setUserData("destroy");
	}
	
	public void render() {
		
		if(timeExplosion>5 && isStopped==false) {
			isStopped=true;
			body.setUserData("inactive");
			body.setTransform(-999,  -999, 0);
		}else {

			sprite.setRegion(frameActual);
			sprite.setSize(12, 16);
			sprite.setPosition(vector.x-6, vector.y-7);
			sprite.setOrigin(frameActual.getRegionWidth() / 2,
					frameActual.getRegionHeight() / 2);
			batch.begin();
			sprite.draw(batch);
			batch.end();
			tiempo += Gdx.graphics.getDeltaTime();
			timeExplosion++;
			frameActual = animationTime.getKeyFrame(tiempo, true);
		}
	}
	
	public void createBoom(Vector2 vector) {

		this.vector=vector;
		body.setTransform(vector.x, vector.y, 0);
		frameActual = animationTime.getKeyFrame(tiempo, true);
		isStopped=false;
		this.timeExplosion=0;
		sounds.explosion.play();
		body.setUserData("active");
	}
	
	public boolean isStopped() {
		
		return isStopped;
	}
}
