package com.me.EFF;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import java.util.Random;

import com.badlogic.gdx.Gdx;

public class player {

	private final Vector2 movement = new Vector2();
	private Body playerCircle;
	private Body melee;
	private Body item;
	private Texture imagen, imagen1, imagen2;
	private TextureRegion[] movPlayer;
	private TextureRegion[] movFeet;
	private TextureRegion[] movReKill;
	private TextureRegion[] movReKill1;
	private Animation animationAttack;
	private Animation animationFeet;
	private Animation animationReKill;
	private Animation animationReKill1;
	private float tiempo;
	private TextureRegion frameActual2;
	private drawBody renderP;
	public OrthographicCamera camera;
	private Weapon weapon;
	private int meleesfx;
	private float fatigue = 300;
	private int timefx = 270, countzoom = 0, timecam = 0;
	private final UI ui;
	private int xaux = 0;
	private int yaux = 0;
	private float interpolar;
	private final float TantoExtrano = 0.0174532925199432957f;
	private float supergrado;
	private boolean meleeActivated = false;
	private boolean spriteSelected[];
	private boolean isDeath;
	public boolean Explosion;
	private Sprite spriteAux;
	private Vector2 initPosition;
	private float initAngle;
	private Sprite spritesPlayerStand[] = { new Sprite(new Texture("data/sprites/player/ak47stand.png")),
			new Sprite(new Texture("data/sprites/player/m4a1stand.png")),
			new Sprite(new Texture("data/sprites/player/stand.png")),
			new Sprite(new Texture("data/sprites/player/deaglestand.png")),
			new Sprite(new Texture("data/sprites/player/minigunstand.png")),
			new Sprite(new Texture("data/sprites/player/knifestand.png")),
			new Sprite(new Texture("data/sprites/player/crowbarstand.png")),
			new Sprite(new Texture("data/sprites/player/shotgunstand.png")),
			new Sprite(new Texture("data/sprites/interactions/playerExplode.png")),
			new Sprite(new Texture("data/sprites/player/death.png")),
			new Sprite(new Texture("data/sprites/player/part1Two.png")),
			new Sprite(new Texture("data/sprites/player/knocked.png")),
			new Sprite(new Texture("data/sprites/player/deathByK.png")),
			new Sprite(new Texture("data/sprites/player/deathByK1.png")) };
	private Texture spritesPlayerAttack[] = { new Texture("data/sprites/player/ak47attack.png"),
			new Texture("data/sprites/player/m4a1attack.png"), new Texture("data/sprites/player/attack.png"),
			new Texture("data/sprites/player/deagleattack.png"), new Texture("data/sprites/player/minigunattack.png"),
			new Texture("data/sprites/player/knifeattack.png"), new Texture("data/sprites/player/crowbarattack.png"),
			new Texture("data/sprites/player/shotgunattack.png"),
			new Texture("data/sprites/interactions/playerExplode.png") };
	private boolean alreadyDeath;
	private boolean alreadyDrawn;
	private boolean randomB = false;
	private Weapon weaponAux1;
	private boolean isBleeding;
	private int health = 200;
	private int timeDeath = 0;
	private Sounds sounds;
	private World world;
	private SpriteBatch batch;
	private boolean firstTime;
	private Weapon initWeapon;
	private boolean mutilatedInTwo = false;
	private boolean draw = true;
	private Random rand;
	private UserDataWrapper userW;
	private boolean reKill = false;
	private Vector2 aux = new Vector2();
	private int timeRe = 36;
	private float aux1;
	private boolean boo;
	private int initAmmo = 0;
	private int timeKnock = 251;
	private boolean knockOut = false;
	private boolean drawRe = false;
	private boolean alreadyBleed = false;

	public player(World world, OrthographicCamera camera, Sounds sounds, SpriteBatch batch, UI ui) {

		this.rand = new Random();
		this.spriteAux = new Sprite();
		this.weaponAux1 = new Weapon(sounds, world, "punch", this, batch);
		this.spriteSelected = new boolean[14];
		this.camera = camera;
		this.weapon = new Weapon(sounds, world, "punch", this, batch);
		this.sounds = sounds;
		this.ui = ui;
		this.meleesfx = 0;
		this.firstTime = true;
		this.world = world;
		this.batch = batch;
		this.spriteSelected[2] = true;
	}

	public Sounds getSounds() {
		return sounds;
	}

	public void punchSound() {

		int randAux = rand.nextInt(3);
		if (randAux == 0) {
			sounds.puc1.play();
		} else if (randAux == 1) {
			sounds.puc2.play();
		} else if (randAux == 2) {
			sounds.puc3.play();
		}
	}

	public void buildPlayer(float x, float y, float angle, Weapon initWeapon) {

		initPosition = new Vector2(x, y);
		initAngle = angle;
		this.initWeapon = initWeapon;
		weapon = initWeapon;
		if (!firstTime) {
			melee.setUserData("destroy");
			playerCircle.setUserData("destroy");
			item.setUserData("destroy");
		}
		firstTime = false;
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DynamicBody;
		int pos = returnActive();
		TextureRegion[][] tmp = TextureRegion.split(spritesPlayerAttack[pos], spritesPlayerAttack[pos].getWidth() / 5,
				spritesPlayerAttack[pos].getHeight());
		imagen = new Texture(Gdx.files.internal("data/sprites/player/feet.png"));
		imagen1 = new Texture(Gdx.files.internal("data/sprites/player/rekill.png"));
		imagen2 = new Texture(Gdx.files.internal("data/sprites/player/rekill1.png"));
		TextureRegion[][] tmp1 = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		TextureRegion[][] tmp2 = TextureRegion.split(imagen1, imagen1.getWidth() / 5, imagen1.getHeight());
		TextureRegion[][] tmp3 = TextureRegion.split(imagen2, imagen2.getWidth() / 5, imagen2.getHeight());
		movPlayer = new TextureRegion[5];
		movFeet = new TextureRegion[5];
		movReKill = new TextureRegion[5];
		movReKill1 = new TextureRegion[5];
		System.arraycopy(tmp3[0], 0, movReKill1, 0, 5);
		System.arraycopy(tmp2[0], 0, movReKill, 0, 5);
		System.arraycopy(tmp1[0], 0, movFeet, 0, 5);
		System.arraycopy(tmp[0], 0, movPlayer, 0, 5);
		animationAttack = new Animation(0.07f, movPlayer);
		animationFeet = new Animation(0.10f, movFeet);
		animationReKill = new Animation(0.06f, movReKill);
		animationReKill1 = new Animation(0.14f, movReKill1);
		tiempo = 0f;
		CircleShape ballShape = new CircleShape();
		ballShape.setPosition(new Vector2(-1.2f, 0f));
		ballShape.setRadius(1.3f);
		fixtureDef.shape = ballShape;
		fixtureDef.density = 0f;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
		playerCircle = world.createBody(bodyDef);
		playerCircle.createFixture(fixtureDef).setUserData(this);
		ballShape.dispose();
		playerCircle.setLinearDamping(7);
		ui.changeWeaponUI(weapon.getWeapon());
		this.renderP = new drawBody(world, "PLAYER", batch);
		for (int i = 0; i < spritesPlayerStand.length; i++) {
			spritesPlayerStand[i].setSize(6, 6);
			spritesPlayerStand[i].setOrigin(spritesPlayerStand[i].getWidth() / 2,
					spritesPlayerStand[i].getHeight() / 2);
		}
		PolygonShape meleeshape = new PolygonShape();
		meleeshape.setAsBox(0.6f, 1.4f, new Vector2(0.73f, 0), 0);
		fixtureDef.shape = meleeshape;
		fixtureDef.isSensor = true;
		melee = world.createBody(bodyDef);
		userW = new UserDataWrapper("melee", this);
		melee.createFixture(fixtureDef).setUserData(new UserDataWrapper("melee", this));
		meleeshape.dispose();
		PolygonShape itemdetector = new PolygonShape();
		itemdetector.setAsBox(0.8f, 1.6f, new Vector2(0.73f, 0), 0);
		fixtureDef.shape = itemdetector;
		fixtureDef.isSensor = true;
		item = world.createBody(bodyDef);
		userW.setData("itemdetector", this);
		item.createFixture(fixtureDef).setUserData(new UserDataWrapper("itemdetector", this));
		itemdetector.dispose();
		ui.isDeath = false;
		item.setBullet(true);
	}

	public int getTimeReKill() {
		return timeRe;
	}

	public boolean isRekilling() {
		return reKill;
	}

	public boolean isMeleeActivated() {
		return meleeActivated;
	}

	public void reKill(Vector2 position, float angle, boolean boo) {

		if (!reKill && timeRe >= 35) {
			this.boo = boo;
			reKill = true;
			aux = position;
			aux1 = angle;
			timeRe = 0;
			sounds.splash1.play();
			randomB = randomBoolean();
		}
	}

	public boolean randomBoolean() {
		return Math.random() < 0.5;
	}

	public void restart() {

		meleeActivated = false;
		reKill = false;
		alreadyBleed = false;
		knockOut = false;
		drawRe = false;
		timeKnock = 251;
		draw = true;
		ui.isDeath = false;
		weapon.setDropped(playerCircle.getPosition().x, playerCircle.getPosition().y, interpolar * TantoExtrano, false,
				false);
		weapon.restart();
		if (!initWeapon.getWeapon().equals("punch")) {
			initWeapon.setAmmo(initAmmo);
		}
		playerCircle.setUserData("active");
		receiveWeapon(initWeapon);
		isDeath = false;
		Explosion = false;
		mutilatedInTwo = false;
		alreadyDeath = false;
		alreadyDrawn = false;
		health = 200;
		timeDeath = 0;
		timeRe = 36;
		playerCircle.setTransform(initPosition, initAngle);
		ui.changeWeaponUI(initWeapon.getWeapon());
		sounds.stopSounds();
	}

	public int getHealth() {
		return health;
	}

	public boolean getHurt(Bullet bb) { // Solo se lastima y sangra el jugador, si la bala proviene de un arma del
										// enemigo.

		if (bb.bulletFrom().equals("enemy") || bb.bulletFrom().equals("bouncing") && !isDeath && !knockOut) {
			health = health - bb.getDamage();
			if (health <= 0) { // Si se muere....
				sounds.stopSounds();
				setFalse();
				int randAux = rand.nextInt(4);
				if ((randAux == 3 || randAux == 2) && (bb.getWeaponName().equals("shotgun"))) { // Mutilacion

					sounds.death.stop();
					sounds.splash1.play();
					sounds.splash2.play();
					sounds.splash3.play();
					spriteSelected[10] = true;
					spritesPlayerStand[10].setSize(8, 8);
					mutilatedInTwo = true;

				} else if (bb.getWeaponName().equals("minigun")) {

					if ((randAux == 3 || randAux == 2)) { // Desmenbracion
						sounds.death.stop();
						sounds.splash1.play();
						sounds.splash2.play();
						sounds.splash3.play();
						spriteSelected[8] = true;
						spritesPlayerStand[8].setSize(7, 7);
						Explosion = true;
					} else if (randAux == 1) { // Mutilacion

						sounds.death.stop();
						sounds.splash1.play();
						sounds.splash2.play();
						sounds.splash3.play();
						spriteSelected[10] = true;
						spritesPlayerStand[10].setSize(8, 8);
						mutilatedInTwo = true;
					} else { // Baleado
						sounds.death.play();
						spriteSelected[9] = true;
						spritesPlayerStand[9].setSize(8f, 8f);
					}
				} else { // Baleado
					sounds.death.play();
					spriteSelected[9] = true;
					spritesPlayerStand[9].setSize(8f, 8f);
				}
				isDeath = true;
				ui.setDeath();
			}
			isBleeding = true;
			playerCircle.setLinearVelocity(bb.shoot.getLinearVelocity()); // Empuja al personaje en direcci�n de la bala
			if (!sounds.splash1.isPlaying()) {
				sounds.splash1.play();
			} else if (!sounds.splash2.isPlaying()) {
				sounds.splash2.play();
			} else if (!sounds.splash3.isPlaying()) {
				sounds.splash3.play();
			}
			return true;
		}
		return false;
	}

	public void setPosition(float x, float y, float angle, boolean isOrigin) {

		if (isOrigin) {
			initPosition = new Vector2(x, y);
			initAngle = angle;
		}
		playerCircle.setTransform(x, y, angle);
		melee.setTransform(x, y, angle);
		item.setTransform(x, y, angle);
	}

	public void getExplosion() {
		sounds.stopSounds();
		sounds.splash1.play();
		isDeath = true;
		setFalse();
		spriteSelected[8] = true;
		spritesPlayerStand[8].setSize(7, 7);
		Explosion = true;
		ui.setDeath();
	}

	public boolean isExplosion() {
		return Explosion;
	}

	public boolean mutilatedInTwo() {
		return mutilatedInTwo;
	}

	public void setDrawn() {
		alreadyDrawn = true;
	}

	public boolean alreadyDrawn() {
		return alreadyDrawn;
	}

	public boolean isDeath() {
		return isDeath;
	}

	public void walk() {

		if (!reKill && !knockOut) {
			boolean arriba = Gdx.input.isKeyPressed(Keys.W);
			boolean abajo = Gdx.input.isKeyPressed(Keys.S);
			boolean izquierda = Gdx.input.isKeyPressed(Keys.A);
			boolean derecha = Gdx.input.isKeyPressed(Keys.D);
			melee.setTransform(playerCircle.getPosition(), playerCircle.getAngle());
			item.setTransform(playerCircle.getPosition(), playerCircle.getAngle());
			if (!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || (!arriba && !abajo && !izquierda && !derecha)) {
				sounds.running.stop();
			}
			if (arriba || abajo || izquierda || derecha) {

				frameActual2 = animationFeet.getKeyFrame(tiempo, true);
				spriteAux.setRegion(frameActual2);
				spriteAux.setSize(5.5f, 5.5f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				userW.setData("PLAYER", spriteAux);
				playerCircle.setUserData(userW);
				if (!sounds.footsteps.isPlaying()) {
					sounds.footsteps.play();
				} else if (!sounds.running.isPlaying() && Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					sounds.running.play();
				}
				renderP.renderBody();
				if (arriba) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						playerCircle.setLinearVelocity(0, +5);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
						playerCircle.setLinearVelocity(0, +70);
					} else {
						playerCircle.setLinearVelocity(0, +30);
					}
					if (derecha) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							playerCircle.setLinearVelocity(+5, +5);
						} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
							playerCircle.setLinearVelocity(+70, +70);
						} else {
							playerCircle.setLinearVelocity(+30, +30);
						}
					}
					if (izquierda) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							playerCircle.setLinearVelocity(-5, +5);
						} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
							playerCircle.setLinearVelocity(-70, +70);
						} else {
							playerCircle.setLinearVelocity(-30, +30);
						}
					}
				} else if (abajo) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						playerCircle.setLinearVelocity(0, -5);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
						playerCircle.setLinearVelocity(0, -70);
					} else {
						playerCircle.setLinearVelocity(0, -30);
					}
					if (izquierda) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							playerCircle.setLinearVelocity(-5, -5);
						} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
							playerCircle.setLinearVelocity(-70, -70);
						} else {
							playerCircle.setLinearVelocity(-30, -30);
						}
					}
					if (derecha) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							playerCircle.setLinearVelocity(+5, -5);
						} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
							playerCircle.setLinearVelocity(+70, -70);
						} else {
							playerCircle.setLinearVelocity(+30, -30);
						}
					}
				} else if (izquierda) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						playerCircle.setLinearVelocity(-5, 0);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
						playerCircle.setLinearVelocity(-70, 0);
					} else {
						playerCircle.setLinearVelocity(-30, 0);
					}
				} else if (derecha) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						playerCircle.setLinearVelocity(+5, 0);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && fatigue > 2 && timefx == 270) {
						playerCircle.setLinearVelocity(+70, 0);
					} else {
						playerCircle.setLinearVelocity(+30, 0);
					}
				}
			} else {
				sounds.footsteps.stop();
			}
		}
	}

	public void rotateSprite() {

		if (!reKill && !knockOut) {
			xaux = Gdx.input.getX();
			yaux = Gdx.graphics.getHeight() - Gdx.input.getY();
			interpolar = (float) ((Math.atan2(xaux - Gdx.graphics.getWidth() / 2,
					-(yaux - Gdx.graphics.getHeight() / 2)) * 180.0d / Math.PI) + 30.0f);
			interpolar = interpolar + 250;
			supergrado = interpolar * TantoExtrano;
			playerCircle.setTransform(playerCircle.getPosition().x, playerCircle.getPosition().y, supergrado);
		}
	}

	public void dispose() {
		for (int i = 0; i < spritesPlayerStand.length; i++) {
			spritesPlayerStand[i].getTexture().dispose();
		}
		for (int i = 0; i < spritesPlayerAttack.length; i++) {
			spritesPlayerAttack[i].dispose();
		}
		imagen.dispose();
		playerCircle.setUserData("destroy");
		item.setUserData("destroy");
		melee.setUserData("destroy");
	}

	public boolean paintBackwards() {
		if (isDeath || knockOut) {
			return true;
		}
		return false;
	}

	private void normalizecamera() {

		if ((!Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) && timecam != 0 && !reKill) || timefx != 270 || knockOut) {

			if (timecam > 0) {
				timecam--;
				camera.rotate(-1 * 3);
				camera.update();
			} else if (timecam < 0) {
				timecam++;
				camera.rotate(1 * 3);
				camera.update();
			}
		}
	}

	public void drawPlayer(boolean isPaused) {

		boolean attack = Gdx.input.isButtonPressed(Buttons.LEFT);
		int pos = returnActive();
		if (!reKill) {
			if (draw) {
				if (isPaused || isDeath) {
					userW.setData("PLAYER", spritesPlayerStand[pos]);
					playerCircle.setUserData(userW);
					renderP.renderBody();
				} else if (!attack && !knockOut) {

					userW.setData("PLAYER", spritesPlayerStand[pos]);
					playerCircle.setUserData(userW);
					renderP.renderBody();

				} else {
					if (!isDeath && !knockOut) {
						playerCircle.setUserData(this);
						weapon.attack(playerCircle);
						if (weapon.getAmmo() != 0 && weapon.isPlayingSound()) {
							frameActual2 = animationAttack.getKeyFrame(tiempo, true);
							spriteAux.setRegion(frameActual2);
							spriteAux.setSize(5.5f, 5.5f);
							spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
							userW.setData("PLAYER", spriteAux);
							playerCircle.setUserData(userW);
							renderP.renderBody();
						} else {
							userW.setData("PLAYER", spritesPlayerStand[pos]);
							playerCircle.setUserData(userW);
							renderP.renderBody();
						}
					}
				}
				if (isDeath) {
					if (isExplosion() || mutilatedInTwo) {
						playerCircle.setUserData("inactive");
					} else {
						if (timeDeath >= 25) {
							playerCircle.setUserData("inactive");
						}
						timeDeath++;
					}
				}
			}
		} else {

			if (boo) {
				frameActual2 = animationReKill.getKeyFrame(tiempo, true);
				spriteAux.setRegion(frameActual2);
				spriteAux.setSize(5.5f, 5.5f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				userW.setData("PLAYER", spriteAux);
				playerCircle.setUserData(userW);
				renderP.renderBody();
			} else {
				frameActual2 = animationReKill1.getKeyFrame(tiempo, true);
				spriteAux.setRegion(frameActual2);
				spriteAux.setSize(5.5f, 5.5f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				userW.setData("PLAYER", spriteAux);
				playerCircle.setUserData(userW);
				renderP.renderBody();
			}

		}
		if (knockOut) {
			userW.setData("PLAYER", spritesPlayerStand[pos]);
			playerCircle.setUserData(userW);
			renderP.renderBody();
		}
	}

	public boolean isBleeding() {
		return isBleeding;
	}

	public void rekilled(boolean randB) {
		sounds.splash2.play();
		health = health - 600;
		isDeath = true;
		ui.setDeath();
		if (!drawRe) {
			setFalse();
			if (randB) {

				spriteSelected[12] = true;
				spritesPlayerStand[12].setSize(8f, 8f);
				drawRe = true;
				alreadyBleed = false;
			} else {
				spriteSelected[13] = true;
				spritesPlayerStand[13].setSize(8f, 8f);
				drawRe = true;
				alreadyBleed = true;
			}
		}
		if (!alreadyBleed) {
			isBleeding = true;
		}
		isDeath = true;
		timeKnock = 251;
	}

	public void knockOut(String weaponReceived, Vector2 impulse) {

		sounds.stopSounds();
		if (impulse.x > 55 || impulse.y > 55 || impulse.x < -55 || impulse.y < -55) {

			if (weaponReceived.equals("knife") || weaponReceived.equals("crowbar")) {

				playerCircle.setLinearVelocity((playerCircle.getLinearVelocity().x - impulse.x) - 10,
						(playerCircle.getLinearVelocity().y - impulse.y) - 10);
				sounds.splash2.play();
				health = health - 600;
				isBleeding = true;
				if (health <= 0) {
					setFalse();
					spriteSelected[12] = true;
					spritesPlayerStand[12].setSize(8f, 8f);
					isDeath = true;
					ui.setDeath();
				}
			} else {
				setKnockOut();
			}
		} else if (weaponReceived.equals("melee")) {

			sounds.splash2.play();
			health = health - 600;
			isBleeding = true;
			if (health <= 0) {
				setFalse();
				spriteSelected[12] = true;
				spritesPlayerStand[12].setSize(8f, 8f);
				isDeath = true;
				ui.setDeath();
			}
		} else if (weaponReceived.equals("punch")) {

			setKnockOut();
		}
	}

	public void setKnockOut() {

		int randAux = rand.nextInt(8);
		if (randAux == 0) {
			sounds.pch1.play();
		} else if (randAux == 1) {
			sounds.pch2.play();
		} else if (randAux == 2) {
			sounds.pch3.play();
		} else if (randAux == 3) {
			sounds.pch4.play();
		} else if (randAux == 4) {
			sounds.pch5.play();
		} else if (randAux == 5) {
			sounds.pch6.play();
		} else if (randAux == 6) {
			sounds.pch7.play();
		} else if (randAux == 7) {
			sounds.pch8.play();
		}
		if (timeKnock > 250) {
			timeKnock = 0;
			knockOut = true;
			setFalse();
			spriteSelected[11] = true;
			spritesPlayerStand[11].setSize(8f, 8f);
			isBleeding = true;
		}
	}

	public boolean isKnocked() {
		return knockOut;
	}

	public void melee() {
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && (weapon.getWeapon().equals("crowbar")
				|| weapon.getWeapon().equals("knife") || weapon.getWeapon().equals("punch"))) {
			if (!knockOut && !reKill && !isDeath) {
				melee.setUserData("active");
				meleeActivated = true;
				if (meleesfx >= 15) {
					melee.setUserData("inactive");
					meleeActivated = false;
					if (meleesfx >= 30) {
						meleesfx = 0;
					}
				}
				meleesfx++;
			}
		} else if (!(Gdx.input.isButtonPressed(Buttons.LEFT))) {
			melee.setUserData("inactive");
			meleeActivated = false;
			meleesfx = 0;
		}
	}

	public void render(boolean isPaused) {

		if (isBleeding) {
			isBleeding = false;
		}
		if (timeKnock < 250 && knockOut) {
			playerCircle.setLinearVelocity(0, 0);
			timeKnock++;

		} else if (timeKnock == 250) {
			timeKnock++;
			knockOut = false;
			setWeapon();
		}
		if (reKill && timeRe < 35 && !knockOut) {

			if (boo) {
				isBleeding = true;
				if (aux1 > 7 && aux1 < 8) {
					item.setTransform(aux.x - 2f, aux.y + .1f, aux1);
					playerCircle.setTransform(aux.x - 2f, aux.y + .1f, aux1);
					melee.setTransform(aux.x - 2f, aux.y + .1f, aux1);
				} else if (aux1 > 4 && aux1 < 5) {
					item.setTransform(aux.x - .2f, aux.y - 1.7f, aux1);
					playerCircle.setTransform(aux.x - .2f, aux.y - 1.7f, aux1);
					melee.setTransform(aux.x - .2f, aux.y - 1.7f, aux1);
				} else if (aux1 > 3 && aux1 < 4) {
					item.setTransform(aux.x - 1.7f, aux.y - 2f, aux1);
					playerCircle.setTransform(aux.x - 1.7f, aux.y - 2f, aux1);
					melee.setTransform(aux.x - 1.7f, aux.y - 2f, aux1);
				} else if (aux1 >= 160 && aux1 <= 170) {
					item.setTransform(aux.x - 2.7f, aux.y - 2f, aux1);
					playerCircle.setTransform(aux.x - 2.7f, aux.y - 2f, aux1);
					melee.setTransform(aux.x - 2.7f, aux.y - 2f, aux1);
				} else {
					item.setTransform(aux.x, aux.y, aux1);
					playerCircle.setTransform(aux.x, aux.y, aux1);
					melee.setTransform(aux.x, aux.y, aux1);

				}
			} else {
				if (aux1 > 7 && aux1 < 8) { // arriba
					item.setTransform(aux.x - 2f, aux.y + 1.5f, aux1);
					playerCircle.setTransform(aux.x - 2f, aux.y + 1.5f, aux1);
					melee.setTransform(aux.x - 2f, aux.y + 1.5f, aux1);
				} else if (aux1 > 4 && aux1 < 5) { // abajo
					item.setTransform(aux.x - .2f, aux.y - 3.5f, aux1);
					playerCircle.setTransform(aux.x - .2f, aux.y - 3.5f, aux1);
					melee.setTransform(aux.x - .2f, aux.y - 3.5f, aux1);
				} else if (aux1 > 3 && aux1 < 4) { // izquierda
					item.setTransform(aux.x - 3.5f, aux.y - 2f, aux1);
					playerCircle.setTransform(aux.x - 3.5f, aux.y - 2f, aux1);
					melee.setTransform(aux.x - 3.5f, aux.y - 2f, aux1);
				} else if (aux1 >= 160 && aux1 <= 170) {
					item.setTransform(aux.x - 3.7f, aux.y - 1.5f, aux1);
					playerCircle.setTransform(aux.x - 3.7f, aux.y - 1.5f, aux1);
					melee.setTransform(aux.x - 3.7f, aux.y - 1.5f, aux1);
				} else { // derecha
					item.setTransform(aux.x + 1.5f, aux.y, aux1);
					playerCircle.setTransform(aux.x + 1.5f, aux.y, aux1);
					melee.setTransform(aux.x + 1.5f, aux.y, aux1);

				}
			}
			timeRe++;
		} else {
			reKill = false;
			isBleeding = false;
		}
		item.setUserData(new UserDataWrapper("itemdetector", this));
		camera.position.set(playerCircle.getPosition().x, playerCircle.getPosition().y, 0);
		camera.update();
		if (!isPaused) {

			playerUI();
			melee();
			ui.setfB(fatigue);
			if (!isDeath) {
				playerCircle.applyForceToCenter(movement, true);
				rotateSprite();
			}
			fatigue();
			normalizecamera();
			dropWeapon();
			tiempo += Gdx.graphics.getDeltaTime();
			zoom();
		}
		drawPlayer(isPaused);
		item.setUserData("active");
	}

	public String getWeapon() {
		return weapon.getWeapon();
	}

	public void playerUI() {
		ui.setZoomUI();
		if (isDeath) {
			ui.setAmmo(999, 999);
		} else {
			ui.setAmmo(weapon.getAmmo(), weapon.getWeaponLimit());
		}
	}

	private void setFalse() {
		for (int i = 0; i < spriteSelected.length; i++) {
			spriteSelected[i] = false;
		}
	}

	private void setWeapon() {
		setFalse();
		String nameWeapon = weapon.getWeapon();
		if (nameWeapon.equals("ak47")) {

			spriteSelected[0] = true;

		} else if (nameWeapon.equals("knife")) {

			spriteSelected[5] = true;

		} else if (nameWeapon.equals("crowbar")) {

			spriteSelected[6] = true;

		} else if (nameWeapon.equals("punch")) {

			spriteSelected[2] = true;

		} else if (nameWeapon.equals("m4a1")) {

			spriteSelected[1] = true;

		} else if (nameWeapon.equals("minigun")) {

			spriteSelected[4] = true;

		} else if (nameWeapon.equals("deagle")) {

			spriteSelected[3] = true;

		} else if (nameWeapon.equals("shotgun")) {

			spriteSelected[7] = true;
		}
		int pos = returnActive();
		TextureRegion[][] tmp = TextureRegion.split(spritesPlayerAttack[pos], spritesPlayerAttack[pos].getWidth() / 5,
				spritesPlayerAttack[pos].getHeight());
		System.arraycopy(tmp[0], 0, movPlayer, 0, 5);
	}

	private int returnActive() {
		for (int i = 0; i < spriteSelected.length; i++) {

			if (spriteSelected[i]) {
				return i;
			}
		}
		return 2;
	}

	public void setInitWeapon() {
		initAmmo = weapon.getAmmo();
		weapon.isReceived("player");
		weapon.resetSprite();
		initWeapon = weapon;
	}

	public int getWeaponAmmo() {
		return weapon.getAmmo();
	}

	public int getWeaponInitAmmo() {
		return initWeapon.getAmmo();
	}

	public Weapon getWeaponProperties() {
		return weapon;
	}

	public void receiveWeapon(Weapon newWeapon) {

		if (!reKill) {
			newWeapon.isReceived("player");
			this.weapon = newWeapon;
			setWeapon();
			ui.changeWeaponUI(weapon.getWeapon());
		}
	}

	private void rekillcamera() {

		if (randomB && reKill && timecam <= 4) {
			timecam++;
			camera.rotate(1 * 3);
			camera.update();
		} else if (!randomB && reKill && timecam >= -4) {
			timecam--;
			camera.rotate(-1 * 3);
			camera.update();
		}
	}

	private void sprintcamera() {

		if (!reKill) {
			if (((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.S)) && timecam <= 4)) {
				timecam++;
				camera.rotate(1 * 3);
				camera.update();
			} else if (((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.W)) && timecam >= -4)) {
				timecam--;
				camera.rotate(-1 * 3);
				camera.update();
			}
		}
	}

	public Vector2 getPosition() {
		return playerCircle.getPosition();
	}

	public float getGrados() {
		return supergrado;
	}

	public void fatigue() {

		if (timefx != 270) {
			sounds.running.stop();
			if (!sounds.tired.isPlaying() && !isDeath) {
				sounds.tired.play();
			}
			timefx++;
		} else {
			if (fatigue > 0 && (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT) || reKill) && !isDeath && !knockOut) {
				if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.S)
						|| Gdx.input.isKeyPressed(Keys.D)) || reKill) {
					if (!reKill) {
						fatigue -= 1.5f;
					}
					rekillcamera();
					sprintcamera();
				}
				if (sounds.tired.isPlaying()) {
					sounds.tired.stop();
				}
			}
			if (fatigue < 300) {
				fatigue += .5f;
			}
			if (fatigue <= 0) {
				timefx = 0;
			}
		}
	}

	public void zoom() {

		if (reKill) {
			if (timeRe <= 1) {
				camera.zoom -= 0.15f;
				countzoom--;
			} else if (timeRe == 34) {
				countzoom++;
				camera.zoom += 0.15f;
			}
		} else {
			if (Gdx.input.isKeyPressed(Keys.V) && countzoom < 15) {
				camera.zoom += 0.05f;
				countzoom++;
			} else if (!Gdx.input.isKeyPressed(Keys.V) && countzoom >= 0) {
				countzoom--;
				camera.zoom -= 0.05f;
			}
		}
	}

	public void dropWeapon() {

		if (Gdx.input.isButtonPressed(Buttons.RIGHT) || isDeath || knockOut || reKill) {
			if (!"punch".equals(weapon.getWeapon())) {
				weapon.stopSound();
				if (!isDeath) {
					if (!knockOut) {
						weapon.setDropped(playerCircle.getPosition().x, playerCircle.getPosition().y,
								interpolar * TantoExtrano, true, false);
						sounds.drop.play();
						weapon = weaponAux1;
						setWeapon();
					} else if (knockOut || reKill) {
						weapon.setDropped(playerCircle.getPosition().x, playerCircle.getPosition().y,
								interpolar * TantoExtrano, false, false);
						weapon = weaponAux1;
					}
					ui.changeWeaponUI(weapon.getWeapon());
				} else if (!alreadyDeath) {
					weapon.setDropped(playerCircle.getPosition().x, playerCircle.getPosition().y,
							interpolar * TantoExtrano, false, false);
					ui.changeWeaponUI("punch");
					alreadyDeath = true;
				}
			}
		}
	}
}
