package com.me.EFF;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class drawBody {

	private Array<Body> tmpBodies = new Array<Body>();
	private String id;
	private World world;
	private SpriteBatch batch;
	private Sprite sprite;

	public drawBody(World world, String id, SpriteBatch batch) {

		this.sprite=new Sprite();
		this.id = id;
		this.world = world;
		this.batch = batch;
	}

	public void renderBody() {
		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			if (body.getUserData() != null && body.getUserData() instanceof UserDataWrapper) {
				UserDataWrapper data = (UserDataWrapper) body.getUserData();
				if (data.getID().equals(id)) {
					batch.begin();
					sprite = (Sprite) data.getObject();
					sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
							body.getPosition().y - sprite.getHeight() / 2);
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					sprite.draw(batch);
					batch.end();
				}
			}
		}
	}
}
