package com.me.EFF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Weapon {

	private String actualWeapon;
	private final Sounds sounds;
	private int framesfx;
	private int ammo;
	private boolean isDropped;
	private final World world;
	public Body weapon;
	private final FixtureDef fixtura;
	player player;
	private Sprite spritew;
	private int weaponLimit;
	private int timeGrab = 0;
	private final Array<Body> tmpbod = new Array<Body>();
	private final String[] weapondir = { "data/sprites/weapons/ak47.png", "data/sprites/weapons/deagle.png",
			"data/sprites/weapons/minigun.png", "data/sprites/weapons/crowbar.png", "data/sprites/weapons/knife.png",
			"data/sprites/weapons/shotgun.png", "data/sprites/weapons/m4a1.png" };
	private SpriteBatch batch;
	private Bag<Bullet>[] colBullets;
	private final Texture imagen, imagen1;
	private TextureRegion[] blood, bulletD;
	private Animation bloodSplash, bulletDestroyed;
	public String receivedBy;
	private Vector2 initPosition;
	private float initAngle;
	private TextureRegion[] movArrow;
	private Animation animationArrow;
	private boolean collisionWithPlayer = false;
	private Texture imagen2, imagen3;
	private float tiempo = 0;
	private TextureRegion frameActual2;
	private Sprite spriteAux;
	private int timeDropped = 31;
	private UserDataWrapper userW;
	private int tempCol = 0;
	private int timeAux = 35;
	private TextureRegion[] spikes;
	private Animation spikesAnimation;

	@SuppressWarnings("unchecked")
	public Weapon(Sounds sounds, World world, String actualWeapon, player player, SpriteBatch batch) {

		initPosition = new Vector2(0, 0);
		this.receivedBy = "drop"; // Indica quien tiene esta arma, en este caso "drop" significa que nadie la
									// tiene
		this.spriteAux = new Sprite();
		this.imagen = new Texture(Gdx.files.internal("data/sprites/interactions/bloodsplash.png"));
		this.imagen1 = new Texture(Gdx.files.internal("data/sprites/interactions/bulletDestroyed.png"));
		this.imagen3 = new Texture(Gdx.files.internal("data/sprites/interactions/spikes.png"));
		this.batch = batch;
		this.sounds = sounds;
		this.actualWeapon = actualWeapon;
		this.framesfx = 0;
		this.ammo = 999;
		this.world = world;
		this.fixtura = new FixtureDef();
		this.isDropped = false;
		this.player = player;
		this.imagen2 = new Texture("data/sprites/UI/arrow.png");
		this.movArrow = new TextureRegion[10];
		TextureRegion[][] tmp2 = TextureRegion.split(imagen2, imagen2.getWidth() / 5, imagen2.getHeight());
		System.arraycopy(tmp2[0], 0, movArrow, 0, 5);
		for (int i = 9, k = 0; i >= 5; i--) {
			movArrow[i] = movArrow[k];
			k++;
		}
		animationArrow = new Animation(0.10f, movArrow);
		TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		TextureRegion[][] tmp3 = TextureRegion.split(imagen3, imagen3.getWidth() / 5, imagen3.getHeight());
		blood = new TextureRegion[5];
		spikes = new TextureRegion[5];
		System.arraycopy(tmp[0], 0, blood, 0, 5);
		System.arraycopy(tmp3[0], 0, spikes, 0, 5);
		bloodSplash = new Animation(0.09f, blood);
		spikesAnimation = new Animation(0.09f, spikes);
		TextureRegion[][] tmp1 = TextureRegion.split(imagen1, imagen1.getWidth() / 5, imagen1.getHeight());
		bulletD = new TextureRegion[5];
		System.arraycopy(tmp1[0], 0, bulletD, 0, 5);
		bulletDestroyed = new Animation(0.10f, bulletD);

		setWeapon(actualWeapon);
		colBullets = (Bag<Bullet>[]) new Bag[150];
		for (int i = 0; i < colBullets.length; i++) {

			colBullets[i] = new Bag<Bullet>();

		}
		for (Bag<Bullet> colBullets : colBullets) {

			colBullets.add(new Bullet(world, batch, bloodSplash, spikesAnimation, player, bulletDestroyed, actualWeapon,
					sounds));
		}
		userW = new UserDataWrapper(actualWeapon, spritew);
	}

	private void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public void getExplosion() {
		int impulsoX = (int) (Math.random() * 300) + 200;
		int impulsoY = (int) (Math.random() * 300) + 200;
		boolean opc1 = randomBoolean();
		boolean opc2 = randomBoolean();
		if (opc1 && opc2) {
			weapon.setLinearVelocity(impulsoX, impulsoY);
		} else if (!opc1 && opc2) {
			weapon.setLinearVelocity(-impulsoX, impulsoY);
		} else if (opc1 && !opc2) {
			weapon.setLinearVelocity(impulsoX, -impulsoY);
		} else if (!opc1 && !opc2) {
			weapon.setLinearVelocity(-impulsoX, -impulsoY);
		}
	}

	private boolean randomBoolean() {
		return Math.random() < 0.5;
	}

	public void clean() {

		spritew.getTexture().dispose();
		weapon.setUserData("destroy");
	}

	public void dispose() {
		spritew.getTexture().dispose();
		imagen.dispose();
		imagen1.dispose();
		for (Bag<Bullet> colBullets : colBullets) {
			for (Bullet tmpM : colBullets) {
				tmpM.dispose();
			}
		}
		if (spriteAux != null) {
			if (spriteAux.getTexture() != null) {
				spriteAux.getTexture().dispose();
			}
		}
		imagen2.dispose();
		if (weapon != null) {
			weapon.setUserData("destroy");
		}
	}

	public int getWeaponLimit() {
		return weaponLimit;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public boolean isPlayingSound() {

		if (actualWeapon.equals("ak47")) {
			if (sounds.ak47.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("deagle")) {
			if (sounds.deagle.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("minigun")) {
			if (sounds.minigun.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("crowbar")) {
			if (sounds.crowbar.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("knife")) {
			if (sounds.knife.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("punch")) {
			if (sounds.punch.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("shotgun")) {
			if (sounds.shotgun.isPlaying()) {
				return true;
			}

		} else if (actualWeapon.equals("m4a1")) {
			if (sounds.m4a1.isPlaying()) {
				return true;
			}
		}
		return false;
	}

	public void collisionWithEnemy(Enemy enemy) {
		if (getAmmo() != 0 && enemy.getWeapon().equals("punch") && timeAux >= 25) {
			enemy.receiveWeapon(this);
			sounds.grab.play();
		}
	}

	public void collisionWithPlayer() {

		collisionWithPlayer = true;
	}

	public void endContact() {
		collisionWithPlayer = false;
	}

	public boolean isDropped() {
		return isDropped;
	}

	public String getWeapon() {
		return actualWeapon;
	}

	public int getAmmo() {
		return ammo;
	}

	public Vector2 getImpulse() {
		return weapon.getLinearVelocity();
	}

	public void renderArrow() {

		if (collisionWithPlayer && !player.isDeath()) {
			frameActual2 = animationArrow.getKeyFrame(tiempo, true);
			spriteAux.setRegion(frameActual2);
			spriteAux.setSize(5.5f, 5.5f);
			spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
			spriteAux.setPosition(weapon.getPosition().x - spritew.getWidth() / 2,
					weapon.getPosition().y - spritew.getHeight() / 2);
			batch.begin();
			spriteAux.draw(batch);
			batch.end();
			tiempo += Gdx.graphics.getDeltaTime();
		}
	}

	public void stopImpulse() {
		if (isDropped) {
			weapon.setLinearVelocity(0, 0);
			weapon.setAngularVelocity(0);
		}
	}

	public void render() {

		if (timeAux < 25) {
			timeAux++;
		}
		if (timeGrab < 25) {
			timeGrab++;
		}
		if (tempCol < 35) {
			tempCol++;
		}
		if (isDropped) {

			if (timeDropped < 30) {
				timeDropped++;
			}
			userW.setData(actualWeapon, spritew);
			if (weapon != null) {
				weapon.setUserData(userW);
				world.getBodies(tmpbod);
			}
			for (Body body : tmpbod) {
				if (body.getUserData() != null && body.getUserData() instanceof UserDataWrapper) {
					UserDataWrapper data = (UserDataWrapper) body.getUserData();
					if (data.getID().equals(actualWeapon)) {
						batch.begin();
						spritew = (Sprite) data.getObject();
						spritew.setPosition(body.getPosition().x - spritew.getWidth() / 2,
								body.getPosition().y - spritew.getHeight() / 2);
						spritew.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
						spritew.draw(batch);
						batch.end();
					}
				}
			}
			if (collisionWithPlayer && Gdx.input.isKeyPressed(Input.Keys.SPACE) && player.getWeapon().equals("punch")
					&& timeGrab >= 25) {

				collisionWithPlayer = false;
				timeGrab = 0;
				sounds.grab.play();
				player.receiveWeapon(this);
				receivedBy = "player"; // Si el arma es agarrada por el jugador, entonces esta variable indica que esta
										// arma la tiene el jugador principal.
			}
		}
	}

	public void norDropped() {
		isDropped = true;
	}

	public void forcedCollision() {

		if (isDropped == true
				&& (weapon.getLinearVelocity().x > 55
						|| weapon.getLinearVelocity().y > 55 || weapon.getLinearVelocity().x < -55
				|| weapon.getLinearVelocity().y < -55)) {
			
			if(actualWeapon.equals("knife")) {
				sounds.knifehit.play();
				
			}else if(actualWeapon.equals("crowbar")) {
				sounds.cbarhit.play();
				
			}else {
				sounds.puc1.play();
			}
		}
	}

	public void renderBullets() {

		for (Bag<Bullet> colBullets : colBullets) {
			for (Bullet tmpM : colBullets) {
				if (!tmpM.isDestroyed() || tmpM.isBleeding() || tmpM.isParticle() || tmpM.isBoucing()) {
					tmpM.render();
				}
			}
		}
	}

	public void isReceived(String received) {
		setReceivedBy(received);
		this.isDropped = false;
		if (weapon != null) {
			weapon.setUserData("destroy");
		}
	}

	public String receivedBy() {
		return receivedBy;
	}

	public void restart() {

		if (isDropped && weapon != null) {
			setBulletParticlesInactive();
			weapon.setLinearVelocity(0, 0);
			weapon.setAngularVelocity(0);
			sounds.stopSounds();
			collisionWithPlayer = false;
			weapon.setTransform(initPosition, initAngle);
			refill();
		}
	}

	public void refill() {

		this.ammo = weaponLimit;
	}

	private void setBulletParticlesInactive() {

		for (Bag<Bullet> colBullets : colBullets) {
			for (Bullet tmpM : colBullets) {
				tmpM.bleed = false;
				tmpM.particleDestroy = false;
				tmpM.bounceTime = 26;
				tmpM.bounced = false;
			}
		}
	}

	public int getTimeDropped() {
		return timeDropped;
	}

	// Posicion en x, y, el angulo, y si es empujado por una fuerza....
	public void setDropped(float x, float y, float angle, boolean isForced, boolean firstTime) {

		collisionWithPlayer = false;
		timeAux = 0;
		if (isForced && !receivedBy.equals("enemy")) {
			timeDropped = 0;
		} else if (receivedBy.equals("enemy")) {
			timeDropped = 31;
		}
		fixtura.filter.categoryBits = Constants.BIT_ITEM;
		if (!isDropped) {
			setBulletParticlesInactive();
			if (firstTime) {
				initPosition = new Vector2(x, y);
				initAngle = angle;
			}
			receivedBy = "drop";
			isDropped = true;
			if (actualWeapon.equals("ak47")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(1.5f, 0.5f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 35f;
				fixtura.friction = 0;
				fixtura.restitution = 0.5f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(1);
				weapon.setAngularDamping(2);
				weapon.setAngularVelocity(10);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 100);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("deagle")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(0.5f, 0.5f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 30f;
				fixtura.friction = 0;
				fixtura.restitution = 0.5f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(1);
				weapon.setAngularDamping(1);
				weapon.setAngularVelocity(15);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 100);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("minigun")) {
				spritew.setSize(4.8f, 4.8f);
				spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(1.5f, 0.5f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 65f;
				fixtura.friction = 0;
				fixtura.restitution = 0f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(3);
				weapon.setAngularDamping(10);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 20);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("crowbar")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(0.6f, 0.2f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 10f;
				fixtura.friction = 0;
				fixtura.restitution = 0f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(1);
				weapon.setAngularDamping(1);
				weapon.setAngularVelocity(5);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 110);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("knife")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(0.3f, 0.2f, new Vector2(0.2f, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 10f;
				fixtura.friction = 0;
				fixtura.restitution = 0f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(1);
				weapon.setAngularDamping(99);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 110);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("shotgun")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(1.5f, 0.5f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 35f;
				fixtura.friction = 0;
				fixtura.restitution = 0.5f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(2);
				weapon.setAngularDamping(2);
				weapon.setAngularVelocity(6);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 100);
				}
				weapon.setBullet(true);
			}
			if (actualWeapon.equals("m4a1")) {
				BodyDef bod = new BodyDef();
				bod.type = BodyType.DynamicBody;
				bod.position.set(0, 0);
				PolygonShape arma = new PolygonShape();
				arma.setAsBox(1.5f, 0.5f, new Vector2(0, 0), 0);
				arma.setRadius(0);
				fixtura.shape = arma;
				fixtura.density = 35f;
				fixtura.friction = 0;
				fixtura.restitution = 0.5f;
				weapon = world.createBody(bod);
				weapon.createFixture(fixtura).setUserData(this);
				weapon.setLinearDamping(1);
				weapon.setAngularDamping(2);
				weapon.setAngularVelocity(10);
				arma.dispose();
				weapon.setTransform(x, y, angle);
				if (isForced) {
					direccion(x, y, 100);
				}
				weapon.setBullet(true);
			}
		}
	}

	public void stopSound() {
		sounds.ak47.stop();
		sounds.crowbar.stop();
		sounds.punch.stop();
		sounds.knife.stop();
		sounds.m4a1.stop();
		sounds.minigun.stop();
		sounds.deagle.stop();
		sounds.shotgun.stop();
		sounds.empty.stop();
	}

	public void setWeapon(String actualWeapon) {
		this.actualWeapon = actualWeapon;
		if (actualWeapon.equals("ak47")) {
			this.ammo = 32;
			this.spritew = new Sprite(new Texture(weapondir[0]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("deagle")) {
			this.ammo = 12;
			this.spritew = new Sprite(new Texture(weapondir[1]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("minigun")) {
			this.ammo = 125;
			this.spritew = new Sprite(new Texture(weapondir[2]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("crowbar")) {
			this.ammo = 999;
			this.spritew = new Sprite(new Texture(weapondir[3]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("knife")) {
			this.ammo = 999;
			this.spritew = new Sprite(new Texture(weapondir[4]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("punch")) {
			this.ammo = 999;
		} else if (actualWeapon.equals("shotgun")) {
			this.ammo = 8;
			this.spritew = new Sprite(new Texture(weapondir[5]));
			this.spritew.setSize(4.8f, 4.8f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		} else if (actualWeapon.equals("m4a1")) {
			this.ammo = 24;
			this.spritew = new Sprite(new Texture(weapondir[6]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
		}
		this.weaponLimit = ammo;
	}
	
	public void resetSprite() {
		if(!actualWeapon.equals("punch")) {
			spritew.getTexture().dispose();
		}
		if (actualWeapon.equals("ak47")) {
			
			this.spritew = new Sprite(new Texture(weapondir[0]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
			
		} else if (actualWeapon.equals("deagle")) {
			
			this.spritew = new Sprite(new Texture(weapondir[1]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
			
		} else if (actualWeapon.equals("minigun")) {
			
			this.spritew = new Sprite(new Texture(weapondir[2]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);

		} else if (actualWeapon.equals("crowbar")) {
			
			this.spritew = new Sprite(new Texture(weapondir[3]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);

		} else if (actualWeapon.equals("knife")) {
			
			this.spritew = new Sprite(new Texture(weapondir[4]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);
			
		} else if (actualWeapon.equals("shotgun")) {

			this.spritew = new Sprite(new Texture(weapondir[5]));
			this.spritew.setSize(4.8f, 4.8f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);

		} else if (actualWeapon.equals("m4a1")) {
			
			this.spritew = new Sprite(new Texture(weapondir[6]));
			this.spritew.setSize(5.5f, 5.5f);
			this.spritew.setOrigin(spritew.getWidth() / 2, spritew.getHeight() / 2);

		}
	}

	public void disparo(Vector2 position, float grado, int tipo) {
		for (Bag<Bullet> colBullets : colBullets) {

			for (Bullet tmpP : colBullets) {
				if (tmpP.isDestroyed() && !tmpP.isBleeding() && !tmpP.isParticle()) {
					tmpP.createBullet(position, grado, tipo, receivedBy); // El �ltimo par�metro indica quien tiene el
																			// arma
					return;
				}
			}
		}
	}

	public void attack(Body Item) { // Con el Item se verifica si el arma se ataca desde un enemigo, o si es del
									// jugador principal

		if (ammo != 0) {
			if (actualWeapon.equals("ak47")) {
				if (framesfx >= 5) {
					sounds.ak47.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.ak47.play();
					if (Item.getUserData() instanceof player) { // Condiciones, si la tiene el jugador se agarra la
																// posici�n del jugador, y si la tiene el enemigo se
																// agarra la posici�n del enemigo, esto aplica para
																// todas las armas
						disparo(player.getPosition(), player.getGrados(), 1);
					} else if (Item.getUserData() instanceof Enemy) {
						Enemy aux = (Enemy) Item.getUserData();
						disparo(aux.getPosition(), aux.getGrados(), 1);
					}
					if (ammo != 0) {
						ammo -= 1;
					}
				}
				framesfx++;
			} else if (actualWeapon.equals("deagle")) {
				if (framesfx >= 26) {
					sounds.deagle.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.deagle.play();
					if (Item.getUserData() instanceof player) {
						disparo(player.getPosition(), player.getGrados(), 1);
					} else if (Item.getUserData() instanceof Enemy) {
						Enemy aux = (Enemy) Item.getUserData();
						disparo(aux.getPosition(), aux.getGrados(), 1);
					}
					if (ammo != 0) {
						ammo -= 1;
					}
				}
				framesfx++;
			} else if (actualWeapon.equals("minigun")) {
				if (framesfx >= 3) {
					sounds.minigun.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.minigun.play();
					if (Item.getUserData() instanceof player) {
						disparo(player.getPosition(), player.getGrados(), 1);
					} else if (Item.getUserData() instanceof Enemy) {
						Enemy aux = (Enemy) Item.getUserData();
						disparo(aux.getPosition(), aux.getGrados(), 1);
					}
					if (ammo != 0) {
						ammo -= 1;
					}
				}
				framesfx++;
			} else if (actualWeapon.equals("crowbar")) {
				if (framesfx >= 23) {
					sounds.crowbar.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.crowbar.play();
				}
				framesfx++;
			} else if (actualWeapon.equals("knife")) {
				if (framesfx >= 15) {
					sounds.knife.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.knife.play();
				}
				framesfx++;
			} else if (actualWeapon.equals("punch")) {
				if (framesfx >= 15) {
					sounds.punch.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.punch.play();
				}
				framesfx++;
			} else if (actualWeapon.equals("shotgun")) {
				if (framesfx >= 22) {
					sounds.shotgun.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.shotgun.play();
					if (Item.getUserData() instanceof player) {
						disparo(player.getPosition(), player.getGrados(), 1);
						disparo(player.getPosition(), player.getGrados(), 2);
						disparo(player.getPosition(), player.getGrados(), 3);
						disparo(player.getPosition(), player.getGrados(), 4);
						disparo(player.getPosition(), player.getGrados(), 5);
					} else if (Item.getUserData() instanceof Enemy) {
						Enemy aux = (Enemy) Item.getUserData();
						disparo(aux.getPosition(), aux.getGrados(), 1);
						disparo(aux.getPosition(), aux.getGrados(), 2);
						disparo(aux.getPosition(), aux.getGrados(), 3);
						disparo(aux.getPosition(), aux.getGrados(), 4);
						disparo(aux.getPosition(), aux.getGrados(), 5);
					}
					if (ammo != 0) {
						ammo -= 1;
					}
				}
				framesfx++;

			} else if (actualWeapon.equals("m4a1")) {
				if (framesfx >= 5) {
					sounds.m4a1.stop();
					framesfx = 0;
				}
				if (framesfx == 0) {
					sounds.m4a1.play();
					if (Item.getUserData() instanceof player) {
						disparo(player.getPosition(), player.getGrados(), 1);
					} else if (Item.getUserData() instanceof Enemy) {
						Enemy aux = (Enemy) Item.getUserData();
						disparo(aux.getPosition(), aux.getGrados(), 1);
					}
					if (ammo != 0) {
						ammo -= 1;
					}
				}
				framesfx++;
			}
		} else {
			if (framesfx >= 25) {
				sounds.empty.stop();
				framesfx = 0;
			}
			if (framesfx == 0) {
				sounds.empty.play();
			}
			framesfx++;
		}
	}

	public void direccion(float xx, float yy, float speed) {

		float mousex = Gdx.input.getX();
		float mousey = 0;
		if (weapon.getAngle() > 6.1 && weapon.getAngle() < 9.6) {
			mousey = Gdx.input.getY() - 5;
		} else {
			mousey = Gdx.input.getY();
		}
		Vector3 mousePos = new Vector3(mousex, mousey, 0);
		player.camera.unproject(mousePos);
		float velx = mousePos.x - xx;
		float vely = mousePos.y - yy;
		float length = (float) Math.sqrt(velx * velx + vely * vely);
		if (length != 0) {
			velx = velx / length;
			vely = vely / length;
		}
		weapon.setLinearVelocity(velx * speed, vely * speed);
	}

	public void collisionWithEnemy(Body bod) {

		if (tempCol >= 5) {

			tempCol = 0;
			/*Vector2 impulse = bod.getLinearVelocity();
			if (impulse.x == 0 && impulse.y > 0) {
				weapon.setLinearVelocity(impulse.x + 10, weapon.getLinearVelocity().y);
			} else if (impulse.y == 0 && impulse.x > 0) {
				weapon.setLinearVelocity(weapon.getLinearVelocity().x, impulse.y + 10);
			} else if (impulse.x == 0 && impulse.y <= 0) {
				weapon.setLinearVelocity(impulse.x - 10, weapon.getLinearVelocity().y);
			} else if (impulse.y == 0 && impulse.x <= 0) {
				weapon.setLinearVelocity(weapon.getLinearVelocity().x, impulse.y - 10);
			} else if (impulse.y > 0 && impulse.x > 0) { // Diagonal sup derecha
				weapon.setLinearVelocity(weapon.getLinearVelocity().x + 10, impulse.y - 10);
			} else if (impulse.y <= 0 && impulse.x <= 0) { // Diagonal inf izquierda
				weapon.setLinearVelocity(weapon.getLinearVelocity().x - 10, impulse.y + 10);
			} else if (impulse.y > 0 && impulse.x <= 0) { // Diagonal sup izquierda
				weapon.setLinearVelocity(weapon.getLinearVelocity().x - 10, impulse.y - 10);
			} else if (impulse.y <= 0 && impulse.x > 0) { // Diagonal inf derecha
				weapon.setLinearVelocity(weapon.getLinearVelocity().x + 10, impulse.y + 10);
			} else {
				weapon.setLinearVelocity(weapon.getLinearVelocity().x + 20, impulse.y + 20);
			}*/
		}

	}

}
