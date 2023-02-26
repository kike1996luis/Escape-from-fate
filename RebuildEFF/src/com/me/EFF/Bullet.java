package com.me.EFF;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Bullet {
	private World world;
	public Body shoot;
	private boolean isDestroyed;
	private Sprite spritew;
	private Array<Body> tmpBodies = new Array<Body>();
	private SpriteBatch batch;
	private Animation bloodSplash;
	public boolean bleed;
	private int bloodTime = 0;
	private Vector2 vectorPart;
	private TextureRegion frameActual2, frameActual;
	private float tiempo;
	private player jugador;
	private String bulletFrom;
	private Animation bulletDestroyed;
	public boolean particleDestroy;
	private int bulletDestroyedTime;
	private String nameWeapon;
	private Random rand;
	private Sprite spriteAux, spriteAux1, spriteAux2;
	private int damage;
	private boolean partFirstTime;
	private boolean bloodFirstTime;
	private int timeDestroy;
	private UserDataWrapper userW;
	private float grades = 0;
	private int tipobala = 0;
	private Sounds sounds;
	public boolean bounced;
	private Animation spikesAnimation;
	private boolean spikesFirstTime;
	public int bounceTime;
	private boolean isBouncing;
	private String typeMaterial;

	public Bullet(World world, SpriteBatch batch, Animation bloodSplash, Animation spikesAnimation, player player,
			Animation bulletDestroyed, String nameWeapon, Sounds sounds) {

		this.isBouncing = false;
		this.bounceTime = 21;
		this.spikesFirstTime = false;
		this.spikesAnimation = spikesAnimation;
		this.bounced = false;
		this.sounds = sounds;
		this.vectorPart = new Vector2(999, 999);
		this.spriteAux = new Sprite();
		this.spriteAux1 = new Sprite();
		this.spriteAux2 = new Sprite();
		this.rand = new Random();
		this.nameWeapon = nameWeapon;
		this.bulletDestroyed = bulletDestroyed;
		jugador = player;
		this.bloodSplash = bloodSplash;
		this.batch = batch;
		this.world = world;
		this.isDestroyed = true;
		if (nameWeapon.equals("minigun")) {
			spritew = new Sprite(new Texture("data/sprites/interactions/bulletminigun.png"));
		} else if (nameWeapon.equals("ak47")) {
			spritew = new Sprite(new Texture("data/sprites/interactions/bulletak47.png"));
		} else if (nameWeapon.equals("m4a1")) {
			spritew = new Sprite(new Texture("data/sprites/interactions/bulletm4a1.png"));
		} else if (nameWeapon.equals("deagle")) {
			spritew = new Sprite(new Texture("data/sprites/interactions/bulletdeagle.png"));
		} else if (nameWeapon.equals("shotgun")) {
			spritew = new Sprite(new Texture("data/sprites/interactions/bulletshotgun.png"));
		}
		this.userW = new UserDataWrapper("bullet", spritew);
	}

	public String getWeaponName() {
		return nameWeapon;
	}

	public int getDamage() {
		if (nameWeapon.equals("minigun")) {
			this.damage = rand.nextInt(50) + 35;
		} else if (nameWeapon.equals("ak47")) {
			this.damage = rand.nextInt(25) + 70;
		} else if (nameWeapon.equals("m4a1")) {
			this.damage = rand.nextInt(25) + 75;
		} else if (nameWeapon.equals("deagle")) {
			this.damage = rand.nextInt(25) + 100;
		} else if (nameWeapon.equals("shotgun")) {
			this.damage = rand.nextInt(50) + 35;
		}
		return damage;
	}

	public void getHurt() {

		bleed = true;
		bloodTime = 0;
		bloodFirstTime = true;
	}

	public void dispose() {
		if (spritew != null) {
			if (spritew.getTexture() != null) {
				spritew.getTexture().dispose();
			}
		}
	}

	public String bulletFrom() {
		return bulletFrom;
	}

	public void createBullet(Vector2 vector, float grades, int tipobala, String bulletFrom) {

		this.isBouncing = false;
		this.bounceTime = 21;
		this.spikesFirstTime = false;
		this.bounced = false;
		this.tipobala = tipobala;
		vectorPart.set(999, 999);
		this.grades = grades;
		timeDestroy = 0;
		particleDestroy = false;
		bleed = false;
		if (nameWeapon.equals("minigun")) { // Se genera un float aleatorio y se carga en varios puntos.

			float random = rand.nextFloat();
			this.bulletFrom = bulletFrom; // Indica de donde proviene la bala, puede ser de un arma enemiga o del
											// jugador
			isDestroyed = false;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape balita = new PolygonShape();
			balita.setAsBox(0.03f, 1, new Vector2(-.9f, -1.3f), 0);
			balita.setRadius(0);
			FixtureDef fixtura = new FixtureDef();
			fixtura.shape = balita;
			fixtura.density = 0f;
			fixtura.friction = 0;
			fixtura.restitution = 20;
			fixtura.isSensor = true;
			shoot = world.createBody(bodyDef);
			shoot.createFixture(fixtura).setUserData(this);
			balita.dispose();
			shoot.setTransform(new Vector2((vector.x) + random - .5f, (vector.y) + random - .5f), grades + 1.6f);
			direccion(vector.x, vector.y);
		} else if (nameWeapon.equals("ak47")) { // Se genera un entero del 0 al 2, junto con otro número del 0 al 3. El
												// primero es para calcular la dirección. Mientras que el segundo es
												// para elegir las 3 opciones posibles que se pueden elegir...

			int random = rand.nextInt(2);
			int opc = rand.nextInt(3);
			this.bulletFrom = bulletFrom;
			isDestroyed = false;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape balita = new PolygonShape();
			balita.setAsBox(0.03f, 1, new Vector2(-.5f, -1.3f), 0);
			balita.setRadius(0);
			FixtureDef fixtura = new FixtureDef();
			fixtura.shape = balita;
			fixtura.density = 0f;
			fixtura.friction = 0;
			fixtura.restitution = 20;
			fixtura.isSensor = true;
			shoot = world.createBody(bodyDef);
			shoot.createFixture(fixtura).setUserData(this);
			balita.dispose();
			shoot.setTransform(new Vector2((vector.x), (vector.y)), grades + 1.6f);
			if (opc == 0) {
				direccion(vector.x + random, vector.y + random);
			} else if (opc == 1) {
				direccion(vector.x - random, vector.y - random);
			} else if (opc == 2) {
				direccion(vector.x + random, vector.y - random);
			} else if (opc == 3) {
				direccion(vector.x - random, vector.y + random);
			}

		} else if (nameWeapon.equals("m4a1")) { // Lo mismo que el ak47, solo que esta vez es más preciso, se usa
												// flotantes en vez de enteros.

			float random = rand.nextFloat();
			int opc = rand.nextInt(3);
			this.bulletFrom = bulletFrom;
			isDestroyed = false;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape balita = new PolygonShape();
			balita.setAsBox(0.03f, 1, new Vector2(-.4f, -1.4f), 0);
			balita.setRadius(0);
			FixtureDef fixtura = new FixtureDef();
			fixtura.shape = balita;
			fixtura.density = 0f;
			fixtura.friction = 0;
			fixtura.restitution = 20;
			fixtura.isSensor = true;
			shoot = world.createBody(bodyDef);
			shoot.createFixture(fixtura).setUserData(this);
			balita.dispose();
			shoot.setTransform(new Vector2((vector.x), (vector.y)), grades + 1.6f);
			if (opc == 0) {
				direccion(vector.x + random, vector.y + random);
			} else if (opc == 1) {
				direccion(vector.x - random, vector.y - random);
			} else if (opc == 2) {
				direccion(vector.x + random, vector.y - random);
			} else if (opc == 3) {
				direccion(vector.x - random, vector.y + random);
			}

		} else if (nameWeapon.equals("deagle")) { // Dispara exactamente en el lugar del cursor.

			this.bulletFrom = bulletFrom;
			isDestroyed = false;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape balita = new PolygonShape();
			balita.setAsBox(0.03f, 1, new Vector2(-.3f, -1.4f), 0);
			balita.setRadius(0);
			FixtureDef fixtura = new FixtureDef();
			fixtura.shape = balita;
			fixtura.density = 0f;
			fixtura.friction = 0;
			fixtura.restitution = 20;
			fixtura.isSensor = true;
			shoot = world.createBody(bodyDef);
			shoot.createFixture(fixtura).setUserData(this);
			balita.dispose();
			shoot.setTransform(new Vector2((vector.x), (vector.y)), grades + 1.6f);
			direccion(vector.x, vector.y);
		} else if (nameWeapon.equals("shotgun")) {

			this.bulletFrom = bulletFrom;
			isDestroyed = false;
			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape balita = new PolygonShape();
			balita.setAsBox(0.03f, 1, new Vector2(-.35f, -1.3f), 0);
			balita.setRadius(0);
			FixtureDef fixtura = new FixtureDef();
			fixtura.shape = balita;
			fixtura.density = 0f;
			fixtura.friction = 0;
			fixtura.restitution = 20;
			fixtura.isSensor = true;
			shoot = world.createBody(bodyDef);
			shoot.createFixture(fixtura).setUserData(this);
			balita.dispose();
			shoot.setTransform(new Vector2((vector.x), (vector.y)), grades + 1.6f);
			if (tipobala == 1) {
				direccion(vector.x, vector.y);
			}
			if (tipobala == 2) {
				direccion(vector.x + 1f, vector.y + 1f);
			}
			if (tipobala == 3) {
				direccion(vector.x + 2f, vector.y + 2f);
			}
			if (tipobala == 4) {
				direccion(vector.x - 1f, vector.y - 1f);
			}
			if (tipobala == 5) {
				direccion(vector.x - 2f, vector.y - 2f);
			}
		}
		spritew.setSize(5f, 5f);
		spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
	}

	public void direccion(float xx, float yy) {

		if (bulletFrom.equals("player")) {
			float mousex = Gdx.input.getX();
			float mousey = 0;
			if (shoot.getAngle() > 6.1 && shoot.getAngle() < 9.6) { // Para mayor precisión, se evalúa el ángulo de la
																	// bala
																	// y se acomoda la dirección de la bala con respecto
																	// al
																	// cursor para que sea más preciso.
				mousey = Gdx.input.getY() - 5;
			} else {
				mousey = Gdx.input.getY();
			}
			Vector3 mousePos = new Vector3(mousex, mousey, 0);
			jugador.camera.unproject(mousePos);
			float speed = 60f;
			float velx = mousePos.x - xx;
			float vely = mousePos.y - yy;
			float length = (float) Math.sqrt(velx * velx + vely * vely);
			if (length != 0) {
				velx = velx / length;
				vely = vely / length;
			}
			shoot.setLinearVelocity(velx * speed, vely * speed);
		} else if (bulletFrom.equals("enemy")) {
			float speed = 60f;
			int temp = 0;
			if(nameWeapon.equals("shotgun")) {
				
				if (tipobala == 2) {
					temp = 10;
				} else if (tipobala == 3) {
					temp = 20;
				} else if (tipobala == 4) {
					temp = -10;
				} else if (tipobala == 5) {
					temp = -20;
				} if (grades >= 4 && grades <= 5) {
					shoot.setLinearVelocity(temp, -speed);
				} else if (grades == 0) {
					shoot.setLinearVelocity(speed, temp);
				} else if (grades >= 3 && grades < 4) {
					shoot.setLinearVelocity(-speed, temp);
				} else if (grades >= 7 && grades <= 8) {
					shoot.setLinearVelocity(temp, speed);
				} else {
					shoot.setLinearVelocity(speed, temp);
				}
			}
			
			else {
				
				if (grades >= 4 && grades <= 5) {
					shoot.setLinearVelocity(xx, yy * -speed);
				} else if (grades == 0) {
					shoot.setLinearVelocity(xx * speed, yy);
				} else if (grades >= 3 && grades < 4) {
					shoot.setLinearVelocity(xx * -speed, yy);
				} else if (grades >= 7 && grades <= 8) {
					shoot.setLinearVelocity(xx, yy * speed);
				} else {
					shoot.setLinearVelocity(xx * speed, yy * temp);
				}
			}
		}
	}

	public boolean isBoucing() {
		if (bounceTime <= 20) {
			return true;
		} else {
			return false;
		}
	}

	public void render() {

		if (bounceTime <= 20) {
			batch.begin();
			frameActual2 = spikesAnimation.getKeyFrame(tiempo, true);
			spriteAux2.setRegion(frameActual2);
			if (spikesFirstTime) {
				float tmp = rand.nextFloat();
				spriteAux2.setSize(tmp + .7f, tmp + .7f);
				spriteAux2.setOrigin(spriteAux2.getWidth() / 2, spriteAux2.getHeight() / 2);
				spriteAux2 = positionSpriteBullet(spriteAux2);
				spriteAux2.setRotation(vectorPart.angle());
				spikesFirstTime = false;
			}
			spriteAux2.draw(batch);
			batch.end();
			bounceTime++;
		}
		if (bounced) {
			vectorPart = shoot.getLinearVelocity();
			/*if(typeMaterial.equals("horizontal")) {
				if (vectorPart.y > -60f && vectorPart.y <= -51f) {
					shoot.setTransform(shoot.getPosition().x + 1.5f, shoot.getPosition().y + 3f,
							shoot.getAngle() + (180 * MathUtils.degreesToRadians));
				} else if (vectorPart.y >= 51f && vectorPart.y <= 60f) {
					shoot.setTransform(shoot.getPosition().x - 1.5f, shoot.getPosition().y - 3f,
							shoot.getAngle() + (180 * MathUtils.degreesToRadians));
				} else if (vectorPart.x < 0 && vectorPart.y < 0) {
					shoot.setTransform(shoot.getPosition().x, shoot.getPosition().y + 2f,
							shoot.getAngle() + (90 * MathUtils.degreesToRadians));
				} else if (vectorPart.x > 0 && vectorPart.y < 0) {
					shoot.setTransform(shoot.getPosition().x + 2.6f, shoot.getPosition().y + 2.1f,
							shoot.getAngle() + (260 * MathUtils.degreesToRadians));
				} else if (vectorPart.x > 0 && vectorPart.y > 0) {
					shoot.setTransform(shoot.getPosition().x, shoot.getPosition().y - 2f,
							shoot.getAngle() + (90 * MathUtils.degreesToRadians));
				} else if (vectorPart.x < 0 && vectorPart.y > 0) {
					shoot.setTransform(shoot.getPosition().x - 2.6f, shoot.getPosition().y - 2.1f,
							shoot.getAngle() + (260 * MathUtils.degreesToRadians));
				}
			} else {
				if (vectorPart.y > -60f && vectorPart.y <= -51f) {
					shoot.setTransform(shoot.getPosition().x + 3f, shoot.getPosition().y + 1.5f,
							shoot.getAngle() + (-180 * MathUtils.degreesToRadians));
				} else if (vectorPart.y >= 51f && vectorPart.y <= 60f) {
					shoot.setTransform(shoot.getPosition().x - 3f, shoot.getPosition().y - 1.5f,
							shoot.getAngle() + (-180 * MathUtils.degreesToRadians));
				} else if (vectorPart.x < 0 && vectorPart.y < 0) {
					shoot.setTransform(shoot.getPosition().x + 2f, shoot.getPosition().y,
							shoot.getAngle() + (-90 * MathUtils.degreesToRadians));
				} else if (vectorPart.x > 0 && vectorPart.y < 0) {
					shoot.setTransform(shoot.getPosition().x + 2.1f, shoot.getPosition().y + 2.6f,
							shoot.getAngle() + (-260 * MathUtils.degreesToRadians));
				} else if (vectorPart.x > 0 && vectorPart.y > 0) {
					shoot.setTransform(shoot.getPosition().x -2f, shoot.getPosition().y,
							shoot.getAngle() + (-90 * MathUtils.degreesToRadians));
				} else if (vectorPart.x < 0 && vectorPart.y > 0) {
					shoot.setTransform(shoot.getPosition().x - 2.1f, shoot.getPosition().y - 2.6f,
							shoot.getAngle() + (-260 * MathUtils.degreesToRadians));
				}
			}*/
			bounced = false;
		}
		if (bleed && bloodTime < 25) {
			batch.begin();
			frameActual2 = bloodSplash.getKeyFrame(tiempo, true);
			spriteAux1.setRegion(frameActual2);
			if (bloodFirstTime) {
				spriteAux1.setSize(7f, 7f);
				spriteAux1.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				spriteAux1 = positionSpriteBlood(spriteAux1);
				spriteAux1.setRotation(vectorPart.angle());
				bloodFirstTime = false;
			}
			spriteAux1.draw(batch);
			batch.end();
			bloodTime++;
		} else if (bloodTime >= 15) {
			bleed = false;
		}

		if (particleDestroy && bulletDestroyedTime < 20) { // Partícula de polvo que ocurre cuando se destruye una bala

			batch.begin();
			frameActual = bulletDestroyed.getKeyFrame(tiempo, true);
			spriteAux.setRegion(frameActual);
			if (partFirstTime) {

				float tmp = rand.nextFloat();
				spriteAux.setSize(tmp + .7f, tmp + .7f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				spriteAux = positionSpriteBullet(spriteAux);
				spriteAux.setRotation(vectorPart.angle());
				partFirstTime = false;
			}
			spriteAux.draw(batch);
			batch.end();
			bulletDestroyedTime++;
		} else if (bulletDestroyedTime >= 20) {
			particleDestroy = false;
		}
		if (!isDestroyed && timeDestroy < 100) { // Sprite de la bala.

			userW.setObject(spritew);
			shoot.setUserData(userW);
			world.getBodies(tmpBodies);
			for (Body body : tmpBodies) {
				if (body.getUserData() != null && body.getUserData() instanceof UserDataWrapper) {
					UserDataWrapper data = (UserDataWrapper) body.getUserData();
					if (data.getID().equals("bullet")) {
						batch.begin();
						spritew = (Sprite) data.getObject();
						spritew.setPosition(body.getPosition().x - spritew.getWidth() / 2,
								body.getPosition().y - spritew.getHeight() / 2);
						spritew.setRotation((body.getAngle() * MathUtils.radiansToDegrees) - 90);
						spritew.draw(batch);
						batch.end();
						body = null;
					}
				}
			}
			timeDestroy++;
			shoot.setUserData(this);
			testMoving();
		} else if (timeDestroy >= 100) {
			setInactive();
		}
		tiempo += Gdx.graphics.getDeltaTime();
	}

	private Sprite positionSpriteBlood(Sprite spriteAux1) {

		if (shoot.getAngle() < 4.8f && shoot.getAngle() > 3.87f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 1,
					(shoot.getPosition().y - spritew.getHeight() / 2) - .3f);
		} else if (shoot.getAngle() <= 3.87f && shoot.getAngle() > 3.2f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2),
					(shoot.getPosition().y - spritew.getHeight() / 2));
		} else if (shoot.getAngle() < 5.8f && shoot.getAngle() >= 3.87f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) - 1,
					(shoot.getPosition().y - spritew.getHeight() / 2) - 1);
		} else if (shoot.getAngle() < 6.8f && shoot.getAngle() >= 5.8f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2),
					(shoot.getPosition().y - spritew.getHeight() / 2) - 2);
		} else if (shoot.getAngle() < 7.8f && shoot.getAngle() >= 6.8f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) - .5f,
					(shoot.getPosition().y - spritew.getHeight() / 2) - 1);
		} else if (shoot.getAngle() < 8.8f && shoot.getAngle() >= 7.8f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2),
					(shoot.getPosition().y - spritew.getHeight() / 2) - 1f);
		} else if (shoot.getAngle() < 10f && shoot.getAngle() >= 8.8f) {
			spriteAux1.setPosition((shoot.getPosition().x - spritew.getWidth() / 2),
					(shoot.getPosition().y - spritew.getHeight() / 2));
		}
		return spriteAux1;
	}

	private Sprite positionSpriteBullet(Sprite spriteAux) { // Posiciona el sprite de polvo en la bala, según el ángulo
															// donde
		// se encontraba la última posición de la bala antes de
		// destruirse.

		if (shoot.getAngle() < 4.8f && shoot.getAngle() > 3.87f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + .3f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 2.5f);
		} else if (shoot.getAngle() < 3.89f && shoot.getAngle() > 3.34f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2),
					(shoot.getPosition().y - spritew.getHeight() / 2) + 3.5f);
		} else if (shoot.getAngle() > 8.77f && shoot.getAngle() < 9.70f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 2.5f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 3.5f);
		} else if (shoot.getAngle() > 7.91f && shoot.getAngle() < 8.77f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 2.5f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 2.5f);
		} else if (shoot.getAngle() > 6.68f && shoot.getAngle() < 7.91f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 2.5f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 1.5f);
		} else if (shoot.getAngle() > 5.84f && shoot.getAngle() < 6.68f) {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 1.7f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 1f);
		} else {
			spriteAux.setPosition((shoot.getPosition().x - spritew.getWidth() / 2) + 1f,
					(shoot.getPosition().y - spritew.getHeight() / 2) + 1.4f);
		}
		return spriteAux;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public boolean isBleeding() {
		return bleed;
	}

	public boolean isParticle() {
		return particleDestroy;
	}

	private void testMoving() {
		if (shoot.getLinearVelocity() == new Vector2(0, 0) && timeDestroy > 5) {
			setInactive();
		}
	}

	public void setInactive() {
		shoot.setUserData("destroy");
		this.isDestroyed = true;
	}

	public boolean isBouncing() {
		return isBouncing;
	}

	public void impacto(Fixture item, boolean sound, boolean bounce, String typeMaterial) {

		if (bounce) {

			bulletFrom = "bouncing";
			isBouncing = true;
			bounced = true;
			bounceTime = 0;
			spikesFirstTime = true;
			vectorPart = shoot.getLinearVelocity();
			this.typeMaterial = typeMaterial;
			if (typeMaterial.equals("horizontal")) {
				if ((vectorPart.x > 0 && vectorPart.y < 0) || (vectorPart.x > 0 && vectorPart.y > 0)
						|| (vectorPart.x < 0 && vectorPart.y < 0) || (vectorPart.x < 0 && vectorPart.y > 0)) {
					vectorPart = new Vector2(vectorPart.x, vectorPart.y * -1);
				}
			} else if (typeMaterial.equals("vertical")){
				if ((vectorPart.x > 0 && vectorPart.y < 0) || (vectorPart.x < 0 && vectorPart.y < 0)
						|| (vectorPart.x > 0 && vectorPart.y > 0) || (vectorPart.x < 0 && vectorPart.y > 0)) {
					vectorPart = new Vector2(vectorPart.x * -1, vectorPart.y);
				}
			}
			shoot.setLinearVelocity(vectorPart.x, vectorPart.y);

		} else {
			vectorPart = shoot.getPosition();
			vectorPart.setAngle(shoot.getAngle() * MathUtils.radiansToDegrees);
			shoot.setUserData("destroy");
			this.isDestroyed = true;
			if (!(item.getUserData() instanceof player) && !(item.getUserData() instanceof Enemy)) {
				particleDestroy = true; // Si esto está prendido, se activa la animación de polvo.
				bulletDestroyedTime = 0;
				partFirstTime = true;
				if (sound) {

					int random = rand.nextInt(10);
					if (random == 0) {
						sounds.bn1.play();
					} else if (random == 1) {
						sounds.bn2.play();
					} else if (random == 2) {
						sounds.bn3.play();
					} else if (random == 3) {
						sounds.bn4.play();
					} else if (random == 4) {
						sounds.bn5.play();
					} else if (random == 5) {
						sounds.bn6.play();
					} else if (random == 6) {
						sounds.bn7.play();
					} else if (random == 7) {
						sounds.bn8.play();
					} else if (random == 8) {
						sounds.bn9.play();
					} else if (random == 9) {
						sounds.bn10.play();
					}
				}
			}
		}
	}
}
