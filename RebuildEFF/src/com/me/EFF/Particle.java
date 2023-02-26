package com.me.EFF;

import java.util.Random;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Particle {

	private Sprite sprite;
	private BodyDef bodyDef;
	private FixtureDef fixtureDef;
	private Body particle;
	private int timeDestroy = 0;
	private String spriteID[];
	private boolean isInactive;
	private Random random;
	private int randomDraw;
	private String id;
	private boolean drawBody = false;
	private SpriteBatch batch;
	private boolean firstTime = false;
	private Array<Body> tmpBodies = new Array<Body>();
	private World world;
	private boolean isNotCreated;

	public Particle(World world, SpriteBatch batch, String id) {

		isNotCreated = true;
		this.world = world;
		this.batch = batch;
		random = new Random();
		this.id = id;
		bodyDef = new BodyDef();
		fixtureDef = new FixtureDef();
		bodyDef.type = BodyType.DynamicBody;
		PolygonShape particle1 = new PolygonShape();
		if (id.equals("particle")) {
			spriteID = new String[13];
			spriteID[0] = new String("data/sprites/particles/part1wood.png");
			spriteID[1] = new String("data/sprites/particles/part2wood.png");
			spriteID[2] = new String("data/sprites/particles/part3wood.png");
			spriteID[3] = new String("data/sprites/particles/part1glass.png");
			spriteID[4] = new String("data/sprites/particles/part2glass.png");
			spriteID[5] = new String("data/sprites/particles/part3glass.png");
			spriteID[6] = new String("data/sprites/particles/part1ebarrel.png");
			spriteID[7] = new String("data/sprites/particles/part2ebarrel.png");
			spriteID[8] = new String("data/sprites/particles/part3ebarrel.png");
			spriteID[9] = new String("data/sprites/particles/part1chair.png");
			spriteID[10] = new String("data/sprites/particles/part2chair.png");
			spriteID[11] = new String("data/sprites/particles/part3chair.png");
			spriteID[12] = new String("data/sprites/particles/part4chair.png");
			particle1.setAsBox(.2f, .2f, new Vector2(0, 0), 0);

		} else if (id.equals("blood")) {

			spriteID = new String[5];
			spriteID[0] = new String("data/sprites/particles/part1blood.png");
			spriteID[1] = new String("data/sprites/particles/part2blood.png");
			spriteID[2] = new String("data/sprites/particles/part3blood.png");
			spriteID[3] = new String("data/sprites/particles/part4blood.png");
			spriteID[4] = new String("data/sprites/particles/part5blood.png");
			particle1.setAsBox(.6f, .6f, new Vector2(0, 0), 0);

		} else if (id.equals("bodyPart")) {

			spriteID = new String[7];
			particle1.setAsBox(.4f, 1f, new Vector2(0, 0), 0);
		}
		fixtureDef.shape = particle1;
		fixtureDef.density = 0f;
		fixtureDef.friction = 10;
		fixtureDef.restitution = 0f;
		fixtureDef.filter.groupIndex = 1;
		particle = world.createBody(bodyDef);
		particle.createFixture(fixtureDef);
		particle.applyAngularImpulse(10, true);
		particle.setAngularDamping(10);
		particle.setLinearDamping(5);
		particle1.dispose();
		isInactive = true;
		particle.setUserData("inactive");
		particle.setTransform(-999, -999, 0);
	}

	public void dispose() {

		if(sprite != null) {
			if(sprite.getTexture() != null) {
				sprite.getTexture().dispose();
			}
		}
		particle.setUserData("destroy");
		
	}

	public boolean isNotCreated() {
		return isNotCreated;
	}
	
	public void createParticle(Vector2 vector, boolean byExplosion, int part, String particleType) {

		particle.setUserData("active");
		isNotCreated = false;
		firstTime = false;
		drawBody = false;
		if(sprite != null) {
			if(sprite.getTexture() != null) {
				sprite.getTexture().dispose();
			}
		}
		int ranSize = (int) (Math.random() * 3) + 5;
		this.timeDestroy = 0;
		int randomAngle = random.nextInt(360);
		randomDraw = random.nextInt(5);
		if (particleType.equals("wood")) {

			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[0]));
			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[1]));
			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[2]));

			}
			sprite.setSize(ranSize, ranSize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

		} else if (particleType.equals("glass")) {
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[3]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[4]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[5]));

			}
			sprite.setSize(ranSize, ranSize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

		} else if (particleType.equals("explosiveBarrel")) {
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[6]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[7]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[8]));

			}
			sprite.setSize(ranSize, ranSize);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

		} else if (particleType.equals("chair")) {
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[9]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[10]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[11]));

			} else if (part == 4) {
				sprite = new Sprite(new Texture(spriteID[12]));

			}
			sprite.setSize(7, 7);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		} else if (particleType.equals("playerparts")) {
			spriteID[0] = new String("data/sprites/player/part1.png");
			spriteID[1] = new String("data/sprites/player/part2.png");
			spriteID[2] = new String("data/sprites/player/part3.png");
			spriteID[3] = new String("data/sprites/player/part4.png");
			spriteID[4] = new String("data/sprites/player/part5.png");
			spriteID[5] = new String("data/sprites/player/part2two.png");
			
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[0]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[1]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[2]));

			} else if (part == 4) {
				sprite = new Sprite(new Texture(spriteID[3]));

			} else if (part == 5) {
				sprite = new Sprite(new Texture(spriteID[4]));

			} else if (part == 6) {
				sprite = new Sprite(new Texture(spriteID[5]));

			}if(part == 6) {
				sprite.setSize(8, 8);
			}else {
				sprite.setSize(7, 7);
			}
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			randomDraw = 50;

		}  else if (particleType.equals("cartmanparts")) {
			spriteID[0] = new String("data/sprites/cartman/part1.png");
			spriteID[1] = new String("data/sprites/cartman/part2.png");
			spriteID[2] = new String("data/sprites/cartman/part3.png");
			spriteID[3] = new String("data/sprites/cartman/part4.png");
			spriteID[4] = new String("data/sprites/cartman/part5.png");
			spriteID[5] = new String("data/sprites/cartman/part2two.png");
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[0]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[1]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[2]));

			} else if (part == 4) {
				sprite = new Sprite(new Texture(spriteID[3]));

			} else if (part == 5) {
				sprite = new Sprite(new Texture(spriteID[4]));

			} else if (part == 6) {
				sprite = new Sprite(new Texture(spriteID[5]));

			}if(part == 6) {
				sprite.setSize(8, 8);
			}else {
				sprite.setSize(7, 7);
			}
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			randomDraw = 50;

		} else if (particleType.equals("blood")) {
			if (part == 1) {
				sprite = new Sprite(new Texture(spriteID[0]));

			} else if (part == 2) {
				sprite = new Sprite(new Texture(spriteID[1]));

			} else if (part == 3) {
				sprite = new Sprite(new Texture(spriteID[2]));

			} else if (part == 4) {
				sprite = new Sprite(new Texture(spriteID[3]));

			} else if (part == 5) {
				sprite = new Sprite(new Texture(spriteID[4]));

			}
			sprite.setSize(7, 7);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			randomDraw = random.nextInt(5);

		}
		particle.setTransform(vector, randomAngle);
		if (byExplosion) {
			applyImpulse(true);
		} else if (!byExplosion && !particleType.equals("blood")) {
			applyImpulse(false);
		} else if (particleType.equals("blood") && !byExplosion) {
			particle.setUserData("inactive");
		}
		isInactive = false;
	}

	private void applyImpulse(boolean byExplosion) {

		if (byExplosion) {

			int impulsoX = (int) (Math.random() * 300) + 200;
			int impulsoY = (int) (Math.random() * 300) + 200;
			boolean opc1 = randomBoolean();
			boolean opc2 = randomBoolean();
			if (opc1 && opc2) {
				particle.setLinearVelocity(impulsoX, impulsoY);
			} else if (!opc1 && opc2) {
				particle.setLinearVelocity(-impulsoX, impulsoY);
			} else if (opc1 && !opc2) {
				particle.setLinearVelocity(impulsoX, -impulsoY);
			} else if (!opc1 && !opc2) {
				particle.setLinearVelocity(-impulsoX, -impulsoY);
			}
		} else {
			int impulsoX = (int) (Math.random() * 20) + 10;
			int impulsoY = (int) (Math.random() * 20) + 10;
			boolean opc1 = randomBoolean();
			boolean opc2 = randomBoolean();
			if (opc1 && opc2) {
				particle.setLinearVelocity(impulsoX, impulsoY);
			} else if (!opc1 && opc2) {
				particle.setLinearVelocity(-impulsoX, impulsoY);
			} else if (opc1 && !opc2) {
			} else if (!opc1 && !opc2) {
				particle.setLinearVelocity(-impulsoX, -impulsoY);
			}
		}
	}

	public void clean() {
		
		if(sprite != null) {
			sprite.setPosition(-999, -999);
		}
		isInactive = true;
		isNotCreated = true;
		particle.setUserData("inactive");
		particle.setTransform(999, 999, 0);
	}
	
	public void getExplosion() {
		applyImpulse(true);
	}

	private boolean randomBoolean() {
		return Math.random() < 0.5;
	}
	
	public void render() {
		if(sprite != null) {
			if(!drawBody ) {
				world.getBodies(tmpBodies);
				particle.setUserData(this);
				for (Body body : tmpBodies) {
					
					if (body.getUserData() != null && body.getUserData() instanceof Particle) {
						
						batch.begin();
						sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
						sprite.setPosition(particle.getPosition().x - sprite.getWidth() / 2,
								particle.getPosition().y - sprite.getHeight() / 2);
						sprite.setRotation(particle.getAngle() * MathUtils.radiansToDegrees);
						sprite.draw(batch);
						batch.end();
					}
				}
			}
			if (id.equals("blood") || id.equals("bodyPart")) {

				if (timeDestroy > randomDraw) {
											//Sangre inactiva
					drawBody = true;
					batch.begin();
					if(firstTime ) {
						
						sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
						sprite.setPosition(particle.getPosition().x - sprite.getWidth() / 2,
								particle.getPosition().y - sprite.getHeight() / 2);
						sprite.setRotation(particle.getAngle() * MathUtils.radiansToDegrees);
					}
					sprite.draw(batch);
					batch.end();
					isInactive = true;
					particle.setTransform(-999, -999, 0);
					particle.setUserData("inactive");
					
				}else if (timeDestroy <= randomDraw) {
					timeDestroy++;
				}
			} else {
				if (timeDestroy > 10) {
					drawBody = true;
					batch.begin();
					if(firstTime ) {
						
						sprite.setPosition(particle.getPosition().x - sprite.getWidth() / 2,
								particle.getPosition().y - sprite.getHeight() / 2);
						sprite.setRotation(particle.getAngle() * MathUtils.radiansToDegrees);
					}
					sprite.draw(batch);
					batch.end();
					isInactive = true;
					particle.setUserData("inactive");
					particle.setTransform(-999, -999, 0);
				}
			}
		}timeDestroy++;
	}
	
	public int timeDestroy() {
		return timeDestroy;
	}

	public boolean isInactive() {
		return isInactive;
	}
	
}
