package com.me.EFF;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Enemy {

	/*
	 * 
	 * Se incluyeron los sonidos hollow y capisci, se incluy� el signo de
	 * exclamaci�n cuando el enemigo ve al jugador y lo persigue
	 * 
	 */
    
    
         Weapon a;
         boolean revisar=false;
        int ojear=0;
                
        int auxiliar,auxiX,auxiY;
      private boolean buscarArma;
      private Bag<Weapon>[] weapons;
      private Bag<Materials>[] materials;

	private int warningT = 100;
	boolean enLaMira;
	boolean regresar;
	int auxDestinoX;
	int auxDestinoY;
	Sprite warning;
	int temporizador2 = 0;
	boolean escuBool = false;
	int auxDestino;
	boolean escuchar = false;
	private boolean boo;
	float varriba = (float) 7.836119;
	float vabajo = (float) 4.670048;
	float vderecha = (float) 6.2739186;
	float vizquierda = (float) 3.1572204;
	private boolean randomB = false;
	float posantx, posanty;
	boolean rest = false;
	player Player;
	int record = -1;
	int ini, fin, pasos, inice, termine;
	String direccion;
	boolean persiguiendo = false, aviso = false;
	busqueda A;
	int temporizador = 0;
	int totaly;
	int totalx;
	Nodo z;
	int p = 0;
	int p1 = 0;
	int p2 = 0;
	int b = 0;
	int p4 = 0;
	int p5 = 0;
	int p6 = 0;
	int b1 = 0;
	private final Vector2 movement = new Vector2();
	private Body enemyCircle;
	private Body melee;
	private Body item;
	private Texture imagen;
	private TextureRegion[] movPlayer;
	private TextureRegion[] movFeet;
	private Animation animationAttack;
	private Animation animationFeet;
	private float tiempo;
	private TextureRegion frameActual2;
	private drawBody renderP;
	public OrthographicCamera camera;
	private Weapon weapon;
	private float aux1;
	private Vector2 aux = new Vector2();
	private int timefx = 270;
	private boolean reKill = false;
	private float interpolar;
	private final float TantoExtrano = 0.0174532925199432957f;
	private float supergrado;
	private boolean spriteSelected[];
	private boolean isDeath;
	public boolean Explosion;
	private Sprite spriteAux;
	private Vector2 initPosition;
	private float initAngle;
	private boolean drawRe = false;
	private Sprite spritesPlayerStand[];
	private Texture spritesPlayerAttack[];
	private boolean alreadyDeath;
	private boolean alreadyDrawn;
	private Weapon weaponAux1;
	private int timeRe = 36;
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
	private int timeKnock = 251;
	private boolean knockOut = false;
	private boolean alreadyBleed = false;
	private String name;
	private boolean meleeB = false;
	private int timeMelee = 42;
	private boolean collisionWithPlayer = false;
	private boolean meleeActivated = false;

	public Enemy(World world, String name, Weapon initWeapon, float x, float y, String angle, int origen, int destino,
			SpriteBatch batch, Sounds sounds, player Player, String behavior, int pasos, Bag<Materials>[] colMaterials, Bag<Weapon>[] colWeapons) {

		
		 this.weapons=colWeapons;        
         this.materials = colMaterials;
		
		this.warning = new Sprite(new Texture(Gdx.files.internal("data/sprites/UI/warning.png")));
		this.name = name;
		this.rand = new Random();
		this.spriteAux = new Sprite();
		this.weaponAux1 = new Weapon(sounds, world, "punch", Player, batch);
		this.spritesPlayerStand = new Sprite[14];
		this.spritesPlayerAttack = new Texture[8];
		this.spriteSelected = new boolean[spritesPlayerStand.length];
		this.weapon = new Weapon(sounds, world, "punch", Player, batch);
		this.sounds = sounds;
		this.firstTime = true;
		escuBool = false;
		this.world = world;
		this.batch = batch;
		this.spriteSelected[2] = true;
		this.Player = Player;
		this.direccion = behavior;
		ini = origen;
		fin = destino;
		this.pasos = pasos;
		if (name.equals("cartman")) {
			this.spritesPlayerStand[0] = new Sprite(new Texture("data/sprites/cartman/ak47stand.png"));
			this.spritesPlayerStand[1] = new Sprite(new Texture("data/sprites/cartman/m4a1stand.png"));
			this.spritesPlayerStand[2] = new Sprite(new Texture("data/sprites/cartman/stand.png"));
			this.spritesPlayerStand[3] = new Sprite(new Texture("data/sprites/cartman/deaglestand.png"));
			this.spritesPlayerStand[4] = new Sprite(new Texture("data/sprites/cartman/minigunstand.png"));
			this.spritesPlayerStand[5] = new Sprite(new Texture("data/sprites/cartman/knifestand.png"));
			this.spritesPlayerStand[6] = new Sprite(new Texture("data/sprites/cartman/crowbarstand.png"));
			this.spritesPlayerStand[7] = new Sprite(new Texture("data/sprites/cartman/shotgunstand.png"));
			this.spritesPlayerStand[8] = new Sprite(new Texture("data/sprites/interactions/playerExplode.png"));
			this.spritesPlayerStand[9] = new Sprite(new Texture("data/sprites/cartman/death.png"));
			this.spritesPlayerStand[10] = new Sprite(new Texture("data/sprites/cartman/part1Two.png"));
			this.spritesPlayerStand[11] = new Sprite(new Texture("data/sprites/cartman/knocked.png"));
			this.spritesPlayerStand[12] = new Sprite(new Texture("data/sprites/cartman/deathByK.png"));
			this.spritesPlayerStand[13] = new Sprite(new Texture("data/sprites/cartman/deathByK1.png"));
			this.spritesPlayerAttack[0] = new Texture("data/sprites/cartman/ak47attack.png");
			this.spritesPlayerAttack[1] = new Texture("data/sprites/cartman/m4a1attack.png");
			this.spritesPlayerAttack[2] = new Texture("data/sprites/cartman/attack.png");
			this.spritesPlayerAttack[3] = new Texture("data/sprites/cartman/deagleattack.png");
			this.spritesPlayerAttack[4] = new Texture("data/sprites/cartman/minigunattack.png");
			this.spritesPlayerAttack[5] = new Texture("data/sprites/cartman/knifeattack.png");
			this.spritesPlayerAttack[6] = new Texture("data/sprites/cartman/crowbarattack.png");
			this.spritesPlayerAttack[7] = new Texture("data/sprites/cartman/shotgunattack.png");

		} else if (name.equals("player")) {
			this.spritesPlayerStand[0] = new Sprite(new Texture("data/sprites/player/ak47stand.png"));
			this.spritesPlayerStand[1] = new Sprite(new Texture("data/sprites/player/m4a1stand.png"));
			this.spritesPlayerStand[2] = new Sprite(new Texture("data/sprites/player/stand.png"));
			this.spritesPlayerStand[3] = new Sprite(new Texture("data/sprites/player/deaglestand.png"));
			this.spritesPlayerStand[4] = new Sprite(new Texture("data/sprites/player/minigunstand.png"));
			this.spritesPlayerStand[5] = new Sprite(new Texture("data/sprites/player/knifestand.png"));
			this.spritesPlayerStand[6] = new Sprite(new Texture("data/sprites/player/crowbarstand.png"));
			this.spritesPlayerStand[7] = new Sprite(new Texture("data/sprites/player/shotgunstand.png"));
			this.spritesPlayerStand[8] = new Sprite(new Texture("data/sprites/interactions/playerExplode.png"));
			this.spritesPlayerStand[9] = new Sprite(new Texture("data/sprites/player/death.png"));
			this.spritesPlayerStand[10] = new Sprite(new Texture("data/sprites/player/part1Two.png"));
			this.spritesPlayerStand[11] = new Sprite(new Texture("data/sprites/player/knocked.png"));
			this.spritesPlayerStand[12] = new Sprite(new Texture("data/sprites/player/deathByK.png"));
			this.spritesPlayerStand[13] = new Sprite(new Texture("data/sprites/player/deathByK1.png"));
			this.spritesPlayerAttack[0] = new Texture("data/sprites/player/ak47attack.png");
			this.spritesPlayerAttack[1] = new Texture("data/sprites/player/m4a1attack.png");
			this.spritesPlayerAttack[2] = new Texture("data/sprites/player/attack.png");
			this.spritesPlayerAttack[3] = new Texture("data/sprites/player/deagleattack.png");
			this.spritesPlayerAttack[4] = new Texture("data/sprites/player/minigunattack.png");
			this.spritesPlayerAttack[5] = new Texture("data/sprites/player/knifeattack.png");
			this.spritesPlayerAttack[6] = new Texture("data/sprites/player/crowbarattack.png");
			this.spritesPlayerAttack[7] = new Texture("data/sprites/player/shotgunattack.png");

		}
		float anglex = 0;
		if (angle.equals("arriba")) {
			anglex = varriba;
		}

		else if (angle.equals("abajo")) {
			anglex = vabajo;
		}

		else if (angle.equals("izquierda")) {
			anglex = vizquierda;
		}

		else if (angle.equals("derecha")) {
			anglex = vderecha;
		}
		
		
		

		supergrado = anglex;
		buildEnemy(x, y, anglex, initWeapon);
	}

	private void deterDeath() {
		if (isDeath) {
			item.setUserData("inactive");
			enemyCircle.setUserData("inactive");
		} else {
			item.setUserData("active");
			enemyCircle.setUserData("active");
		}
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

	public String getName() {
		return name;
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

	public void buildEnemy(float x, float y, float angle, Weapon weapon2) {

		initPosition = new Vector2(x, y);
		initAngle = angle;
		this.initWeapon = weapon2;
		weapon = weapon2;
		if (!firstTime) {
			melee.setUserData("destroy");
			enemyCircle.setUserData("destroy");
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
		if (name.equals("player")) {
			imagen = new Texture(Gdx.files.internal("data/sprites/player/feet.png"));
		} else if (name.equals("cartman")) {
			imagen = new Texture(Gdx.files.internal("data/sprites/cartman/feet.png"));
		}
		TextureRegion[][] tmp1 = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		movPlayer = new TextureRegion[5];
		movFeet = new TextureRegion[5];
		System.arraycopy(tmp1[0], 0, movFeet, 0, 5);
		System.arraycopy(tmp[0], 0, movPlayer, 0, 5);
		animationAttack = new Animation(0.07f, movPlayer);
		animationFeet = new Animation(0.10f, movFeet);
		tiempo = 0f;
		CircleShape ballShape = new CircleShape();
		ballShape.setPosition(new Vector2(-1.2f, 0f));
		ballShape.setRadius(1.3f);
		fixtureDef.shape = ballShape;
		fixtureDef.density = 0f;
		fixtureDef.friction = 0;
		fixtureDef.restitution = 0;
		enemyCircle = world.createBody(bodyDef);
		enemyCircle.createFixture(fixtureDef).setUserData(this);
		ballShape.dispose();
		enemyCircle.setLinearDamping(7);
		this.renderP = new drawBody(world, "ENEMY", batch);
		for (int i = 0; i < spriteSelected.length; i++) {
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
		item.createFixture(fixtureDef).setUserData(new UserDataWrapper("itemdetector", this));
		itemdetector.dispose();
		item.setBullet(true);
		item.setTransform(x, y, angle);
		enemyCircle.setTransform(x, y, angle);
		melee.setTransform(x, y, angle);
		setWeapon();
		alreadyBleed = false;
		meleeB = false;
	}

	public void restart() {

            revisar=false;
                ojear=0;
                
                auxiliar=0;
            
		warningT = 100;
		escuBool = false;
		 buscarArma=false;
         supergrado=initAngle;
         regresar=false;
         temporizador=0;
         temporizador2=0;
         
         escuchar=false;

		record = -1;
		rest = true;
		reKill = false;
		persiguiendo = false;
		meleeActivated = false;
		timeMelee = 42;
		meleeB = false;
		alreadyBleed = false;
		drawRe = false;
		knockOut = false;
		draw = true;
		enemyCircle.setUserData("active");
		initWeapon.refill();
		weapon.setDropped(enemyCircle.getPosition().x, enemyCircle.getPosition().y, interpolar * TantoExtrano, false,
				false);
		weapon.restart();
		weapon = weaponAux1;
		receiveWeapon(initWeapon);
		isDeath = false;
		Explosion = false;
		mutilatedInTwo = false;
		alreadyDeath = false;
		alreadyDrawn = false;
		health = 200;
		timeDeath = 0;
		timeKnock = 251;
		enemyCircle.setTransform(initPosition, initAngle);
		drawRe = false;
	}

	public int getHealth() {
		return health;
	}

	public void rekilled(boolean randB) {
		sounds.splash2.play();
		health = health - 300;
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

		if (impulse.x > 55 || impulse.y > 55 || impulse.x < -55 || impulse.y < -55) {

			if (weaponReceived.equals("knife") || weaponReceived.equals("crowbar")) {

				enemyCircle.setLinearVelocity((enemyCircle.getLinearVelocity().x - impulse.x) - 10,
						(enemyCircle.getLinearVelocity().y - impulse.y) - 10);
				sounds.splash2.play();
				health = health - 300;
				setFalse();
				spriteSelected[12] = true;
				spritesPlayerStand[12].setSize(8f, 8f);
				isBleeding = true;
				isDeath = true;
			} else {
				setKnockOut();
			}
		} else if (weaponReceived.equals("melee")) {

			sounds.splash2.play();
			health = health - 300;
			setFalse();
			spriteSelected[12] = true;
			spritesPlayerStand[12].setSize(8f, 8f);
			isBleeding = true;
			isDeath = true;
		} else if (weaponReceived.equals("punch")) {

			setKnockOut();
		}
	}

	public boolean randomBoolean() {
		return Math.random() < 0.5;
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
			if (!persiguiendo) {
				persiguiendo = true;
			}
			timeKnock = 0;
			knockOut = true;
			setFalse();
			spriteSelected[11] = true;
			spritesPlayerStand[11].setSize(8f, 8f);
			isBleeding = true;
		}
	}

	public boolean getHurt(Bullet bb) { // Solo se lastima y sangra el jugador, si la bala proviene de un arma del
										// enemigo.
		if (!persiguiendo) {
			persiguiendo = true;
		}
		if (bb.bulletFrom().equals("player") || bb.bulletFrom().equals("bouncing") && !isDeath && !knockOut) {
			health = health - bb.getDamage();
			if (health <= 0) { // Si se muere....
				setFalse();
				int randAux = rand.nextInt(4);
				if ((randAux == 3 || randAux == 2) && (bb.getWeaponName().equals("shotgun"))) { // Mutilacion

					sounds.splash1.play();
					sounds.splash2.play();
					sounds.splash3.play();
					spriteSelected[10] = true;
					spritesPlayerStand[10].setSize(8, 8);
					mutilatedInTwo = true;

				} else if (bb.getWeaponName().equals("minigun")) {

					if ((randAux == 3 || randAux == 2)) { // Desmenbracion
						sounds.splash1.play();
						sounds.splash2.play();
						sounds.splash3.play();
						spriteSelected[8] = true;
						spritesPlayerStand[8].setSize(7, 7);
						Explosion = true;
					} else if (randAux == 1) { // Mutilacion

						sounds.splash1.play();
						sounds.splash2.play();
						sounds.splash3.play();
						spriteSelected[10] = true;
						spritesPlayerStand[10].setSize(8, 8);
						mutilatedInTwo = true;
					} else { // Baleado
						spriteSelected[9] = true;
						spritesPlayerStand[9].setSize(8f, 8f);
					}
				} else { // Baleado
					spriteSelected[9] = true;
					spritesPlayerStand[9].setSize(8f, 8f);
				}
				isDeath = true;
			}
			isBleeding = true;
			enemyCircle.setLinearVelocity(bb.shoot.getLinearVelocity()); // Empuja al personaje en direcci�n de la bala
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
		enemyCircle.setTransform(x, y, angle);
		melee.setTransform(x, y, angle);
		item.setTransform(x, y, angle);
	}

	public void getExplosion() {
		sounds.splash1.play();
		isDeath = true;
		setFalse();
		spriteSelected[8] = true;
		spritesPlayerStand[8].setSize(7, 7);
		Explosion = true;
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

	public void dispose() {
		warning.getTexture().dispose();
		for (int i = 0; i < spritesPlayerStand.length; i++) {
			spritesPlayerStand[i].getTexture().dispose();
		}
		for (int i = 0; i < spritesPlayerAttack.length; i++) {
			spritesPlayerAttack[i].dispose();
		}
		imagen.dispose();
		enemyCircle.setUserData("destroy");
		item.setUserData("destroy");
		melee.setUserData("destroy");
	}

	public boolean paintBackwards() {
		if (isDeath || knockOut) {
			return true;
		}
		return false;
	}

	public boolean isMeleeActivated() {
		return meleeActivated;
	}

	public void drawEnemy(boolean isPaused, boolean indicador) {

		boolean attack = false;
		if (!isDeath && !knockOut) {
			attack = indicador;
		}
		int pos = returnActive();
		if (draw) {
			if (isPaused || isDeath) {
				userW.setData("ENEMY", spritesPlayerStand[pos]);
				enemyCircle.setUserData(userW);
				renderP.renderBody();
			} else if (!attack) {

				userW.setData("ENEMY", spritesPlayerStand[pos]);
				enemyCircle.setUserData(userW);
				renderP.renderBody();

			} else if (attack) {
				if (!isDeath && !knockOut) {
					enemyCircle.setUserData(this);
					if ((!Player.isDeath() && !Player.isKnocked() && !weapon.getWeapon().equals("punch")
							&& !weapon.getWeapon().equals("knife") && !weapon.getWeapon().equals("crowbar"))
							|| (meleeB && persiguiendo) && weapon.getAmmo() != 0) {
						weapon.attack(enemyCircle);
						frameActual2 = animationAttack.getKeyFrame(tiempo, true);
						spriteAux.setRegion(frameActual2);
						spriteAux.setSize(5.5f, 5.5f);
						spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
						userW.setData("ENEMY", spriteAux);
						enemyCircle.setUserData(userW);
						renderP.renderBody();
					} else {
						userW.setData("ENEMY", spritesPlayerStand[pos]);
						enemyCircle.setUserData(userW);
						renderP.renderBody();
					}
				}
			}
			if (isDeath) {
				if (isExplosion() || mutilatedInTwo) {
					enemyCircle.setUserData("inactive");
				} else {
					if (timeDeath >= 25) {
						enemyCircle.setUserData("inactive");
					}
					timeDeath++;
				}
			}
		}
	}

	public boolean isBleeding() {
		return isBleeding;
	}

	public boolean getMelee() {
		return meleeB;
	}

	public Body getBody() {
		return enemyCircle;
	}

	public boolean isKnocked() {
		return knockOut;
	}

	public void collisionWithPlayer() {
		if (collisionWithPlayer) {
			collisionWithPlayer = false;
		} else {
			collisionWithPlayer = true;
		}
	}

	public void melee(boolean indicador) { // MELEEEEE

		if (!Player.isDeath() && persiguiendo) {
			collisionWithPlayer = indicador;
			meleeActivated = true;
		}
		if (indicador && timeMelee >= 15 && persiguiendo) {
			timeMelee = 0;
			meleeB = true;
		}
	}

	public Sounds getSounds() {
		return sounds;
	}

	public void renderSignos() {
		if (warningT < 90) { // Signo de exclamaci�n

			if (!isDeath && !knockOut && !Player.isDeath()) {
				warning.setSize(3.5f, 3.5f);
				warning.setOrigin(warning.getWidth() / 2, warning.getHeight() / 2);
				warning.setPosition(enemyCircle.getPosition().x - warning.getWidth() / 2,
						((enemyCircle.getPosition().y - warning.getHeight() / 2) + 4.5f));
				batch.begin();
				warning.draw(batch);
				batch.end();
			}
			warningT++;
		}
	}

	public void render(boolean isPaused, Graph G) {

		enLaMira = false;
		if (timeMelee < 15) {
			timeMelee++;
		} else {
			meleeB = false;
			meleeActivated = false;
		}
		isBleeding = false;
		item.setUserData(new UserDataWrapper("itemdetector", this));
		item.setTransform(enemyCircle.getPosition(), enemyCircle.getAngle());
		if (A == null) {
			this.A = new busqueda(G);
		}
		if (timeKnock < 250 && knockOut) {
			enemyCircle.setLinearVelocity(0, 0);
			timeKnock++;

		} else if (timeKnock == 250) {
			timeKnock++;
			knockOut = false;
			setWeapon();
		}
		if (!isPaused && !isDeath && !knockOut) {

			recorrido();

			if (persiguiendo == true) {
				analizarEnemigo();
			}

			if (isBleeding) {
				isBleeding = false;
			}
			if (!isDeath) {
				enemyCircle.applyForceToCenter(movement, true);
			}
			tiempo += Gdx.graphics.getDeltaTime();
		}
		if (!isPaused) {
			dropWeapon();
		}
		drawEnemy(isPaused, enLaMira); // EL SEGUNDO PARAMETRO ES PARA ATACAR
		// System.out.println("X "+enemyCircle.getPosition().x+" Y
		// "+enemyCircle.getPosition().y);
		deterDeath();
	}

	public String getWeapon() {
		return weapon.getWeapon();
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

	public void receiveWeapon(Weapon newWeapon) {

		newWeapon.isReceived("enemy");
		this.weapon = newWeapon;
		setWeapon();
	}

	public Vector2 getPosition() {
		return enemyCircle.getPosition();
	}

	public float getGrados() {
		return enemyCircle.getAngle();
	}

	public void dropWeapon() {

		if (isDeath || knockOut || weapon.getAmmo() == 0) {
			if (!"punch".equals(weapon.getWeapon())) {

				boolean ind = false;
				if (weapon.getAmmo() == 0) {
					ind = true;
				}
				weapon.setDropped(enemyCircle.getPosition().x, enemyCircle.getPosition().y, interpolar * TantoExtrano,
						true, false);
				weapon = weaponAux1;
				if (ind) {
					setWeapon();
					sounds.drop.play();
				}
			} else {
				weapon.setDropped(enemyCircle.getPosition().x, enemyCircle.getPosition().y, interpolar * TantoExtrano,
						false, false);
				weapon = weaponAux1;
			}
			if (!alreadyDeath && isDeath) {
				alreadyDeath = true;
			}
		}
	}

	public void walk() {
		if (!persiguiendo && !isDeath && !knockOut) {

			boolean arriba = false;
			boolean abajo = false;
			boolean izquierda = false;
			boolean derecha = false;

			posantx = enemyCircle.getPosition().x;
			posanty = enemyCircle.getPosition().y;

			if (record == -1) {
				inice = ini;
				termine = fin;
			}

			if (record == pasos * pasos) {
				record = 0;
				if (ini != inice && termine != fin) {
					inice = ini;
					termine = fin;
				}

				else {
					termine = ini;
					inice = fin;
				}
			}

			if (direccion.equals("vertical")) {
				if (inice < termine) {

					arriba = true;
					rotateEnemy((float) 7.836119);

				}

				if (inice > termine) {

					abajo = true;
					rotateEnemy((float) 4.670048);

				}
				record++;
			}

			if (direccion.equals("horizontal")) {

				if (inice < termine) {
					derecha = true;
					rotateEnemy((float) 6.2739186);

				}

				if (inice > termine) {
					izquierda = true;
					rotateEnemy((float) 3.1572204);

				}
				record++;
			}
			melee.setTransform(enemyCircle.getPosition(), enemyCircle.getAngle());
			item.setTransform(enemyCircle.getPosition(), enemyCircle.getAngle());
			if (arriba || abajo || izquierda || derecha) {

				frameActual2 = animationFeet.getKeyFrame(tiempo, true);
				spriteAux.setRegion(frameActual2);
				spriteAux.setSize(5.5f, 5.5f);
				spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
				userW.setData("ENEMY", spriteAux);
				enemyCircle.setUserData(userW);
				renderP.renderBody();
				if (arriba) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						enemyCircle.setLinearVelocity(0, +7);
					} else {
						enemyCircle.setLinearVelocity(0, +7);
					}
					if (derecha) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							enemyCircle.setLinearVelocity(+7, +7);
						} else {
							enemyCircle.setLinearVelocity(+7, +7);
						}
					}
					if (izquierda) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							enemyCircle.setLinearVelocity(-7, +7);
						} else {
							enemyCircle.setLinearVelocity(-7, +7);
						}
					}
				} else if (abajo) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						enemyCircle.setLinearVelocity(0, -7);
					} else {
						enemyCircle.setLinearVelocity(0, -7);
					}
					if (izquierda) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							enemyCircle.setLinearVelocity(-7, -7);
						} else {
							enemyCircle.setLinearVelocity(-7, -7);
						}
					}
					if (derecha) {
						if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
							enemyCircle.setLinearVelocity(+7, -7);
						} else {
							enemyCircle.setLinearVelocity(+7, -7);
						}
					}
				} else if (izquierda) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						enemyCircle.setLinearVelocity(-7, 0);
					} else {
						enemyCircle.setLinearVelocity(-7, 0);
					}
				} else if (derecha) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || timefx != 270) {
						enemyCircle.setLinearVelocity(+7, 0);
					} else {
						enemyCircle.setLinearVelocity(+7, 0);
					}
				}
			}
		} else if (persiguiendo && !isDeath && !knockOut) {
			frameActual2 = animationFeet.getKeyFrame(tiempo, true);
			spriteAux.setRegion(frameActual2);
			spriteAux.setSize(5.5f, 5.5f);
			spriteAux.setOrigin(spriteAux.getWidth() / 2, spriteAux.getHeight() / 2);
			userW.setData("ENEMY", spriteAux);
			enemyCircle.setUserData(userW);
			renderP.renderBody();
		}
	}

	public boolean isRekilling() {
		return reKill;
	}

	public void perseguir(Nodo k) {

		if (!isDeath) {
			boolean arriba = false;
			boolean abajo = false;
			boolean izquierda = false;
			boolean derecha = false;
			if (k.x < (int) enemyCircle.getPosition().x) {
				izquierda = true;
				rotateEnemy((float) 3.1572204);
				if ((Player.getPosition().y + 1) > enemyCircle.getPosition().y
						&& (Player.getPosition().y - 1) < enemyCircle.getPosition().y
						&& Player.getPosition().x < enemyCircle.getPosition().x && supergrado == 3.1572204) {
				}
			}

			if (k.x > (int) enemyCircle.getPosition().x) {
				derecha = true;
				rotateEnemy((float) 6.2739186);

				if ((Player.getPosition().y + 1) > enemyCircle.getPosition().y
						&& (Player.getPosition().y - 1) < enemyCircle.getPosition().y
						&& Player.getPosition().x > enemyCircle.getPosition().x && supergrado == 6.2739186) {
				}
			}

			if (k.y > (int) enemyCircle.getPosition().y) {
				arriba = true;
				rotateEnemy((float) 7.836119);

				if ((Player.getPosition().x + 1) > enemyCircle.getPosition().x
						&& (Player.getPosition().x - 1) < enemyCircle.getPosition().x
						&& Player.getPosition().y > enemyCircle.getPosition().y && supergrado == 7.836119) {
				}
			}

			if (k.y < (int) enemyCircle.getPosition().y) {
				abajo = true;
				rotateEnemy((float) 4.670048);

				if ((Player.getPosition().x + 1) > enemyCircle.getPosition().x
						&& (Player.getPosition().x - 1) < enemyCircle.getPosition().x
						&& Player.getPosition().y < enemyCircle.getPosition().y && supergrado == 4.670048) {
				}
			}

			melee.setTransform(enemyCircle.getPosition(), enemyCircle.getAngle());
			item.setTransform(enemyCircle.getPosition(), enemyCircle.getAngle());
			if (arriba || abajo || izquierda || derecha) {

				if (arriba) {
					enemyCircle.setLinearVelocity(0, +30);
					if (derecha) {
						enemyCircle.setLinearVelocity(+30, +30);
					}
					if (izquierda) {
						enemyCircle.setLinearVelocity(-30, +30);
					}
				} else if (abajo) {
					enemyCircle.setLinearVelocity(0, -30);
					if (izquierda) {
						enemyCircle.setLinearVelocity(-30, -30);
					}
					if (derecha) {
						enemyCircle.setLinearVelocity(+30, -30);
					}
				} else if (izquierda) {
					enemyCircle.setLinearVelocity(-30, 0);
				} else if (derecha) {
					enemyCircle.setLinearVelocity(+30, 0);
				}
			}
		}
	}

	public void analizarEnemigo() {
            radioArma();
            
            if(buscarArma){
                if (temporizador == 5) {
			temporizador = 0;
		}

		if (temporizador == 0) {
			 p = (int) enemyCircle.getPosition().y;

                        p1 = (int) (A.A.x);
                        p2 = (int) (enemyCircle.getPosition().x);

                        b = p * p1 + p2;

                        p4 = (int) (a.weapon.getPosition().y);
                        p5 = (int) (A.A.x);
                        p6 = (int) (a.weapon.getPosition().x);
                        b1 = p4 * p5 + p6;

                        A.algoritmoBuscaCamino(b, b1,Player); // origen /destinseguir();
            
            }
           }
            
          else  if(!buscarArma){
                
		if (temporizador == 5) {
			temporizador = 0;
                        auxiliar++;
		}

		if (temporizador == 0) {
			p = (int) enemyCircle.getPosition().y;

			p1 = (int) (A.A.x);
			p2 = (int) (enemyCircle.getPosition().x);

			b = p * p1 + p2;

			p4 = (int) (Player.getPosition().y);
			p5 = (int) (A.A.x);
			p6 = (int) (Player.getPosition().x);
			b1 = p4 * p5 + p6;
                        
			A.algoritmoBuscaCamino(b, b1,Player); // origen /destinseguir();
			z = A.recorrido.pop();
                        
                        if(auxiliar==1){
                            auxiX=p2;
                            auxiY=p;
                        }
		}
                vistaEnemigo();
            }
            
                if(auxiliar==3 && auxiY == (int) enemyCircle.getPosition().y &&  auxiX == (int) enemyCircle.getPosition().x && A.recorrido.size()>3){
                    z = A.recorrido.pop();
                }
                
                if(!"punch".equals(getWeapon()) && buscarArma){
                 buscarArma=false;   
                }
                
                if(auxiliar==2){
                    auxiliar=0;
                }
		temporizador++;
                
                if(A.recorrido.size()<2) {
                       enLaMira=true;
                        if( (int)enemyCircle.getPosition().x >  (int) Player.getPosition().x && (int)enemyCircle.getPosition().y ==  (int) Player.getPosition().y){
                           rotateEnemy((float) 3.1572204); // izquierda
                        }
                        
                        else if((int)enemyCircle.getPosition().x <  (int) Player.getPosition().x && (int)enemyCircle.getPosition().y ==  (int) Player.getPosition().y){
                           rotateEnemy((float) 6.2739186); // derecha   
                        }
                        
                        else if(b<b1){
                             rotateEnemy((float) 7.836119); // arribs
                        }
                        
                        else if(b>b1){
                            rotateEnemy((float) 4.670048); // abajo
                        }
                }
                    
                
              if(("punch".equals(getWeapon()) || "knife".equals(getWeapon())) || "crowbar".equals(getWeapon())  || !enLaMira && !"punch".equals(getWeapon()) && !"knife".equals(getWeapon()) && !"crowbar".equals(getWeapon()) ){
		perseguir(z);
                  if (p2 == z.x || p == z.y) {
			if (p2 == z.x && p == z.y) {

				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}

			}

			if ((p2 - 1 == z.x && p == z.y) || (p2 + 1 == z.x && p == z.y) || (p2 - 2 == z.x && p == z.y)
					|| (p2 + 2 == z.x && p == z.y)) {
				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}
			}

			if ((p2 == z.x && p - 1 == z.y) || (p2 == z.x && p + 1 == z.y) || (p2 == z.x && p - 2 == z.y)
					|| (p2 == z.x && p + 2 == z.y)) {
				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}
			}
		}
           }
            
	}

	public void rotateEnemy(float angles) {
		if (angles == (float) 7.836119) {// arriba
			if (supergrado == 0 || supergrado == (float) 6.2739186) {// der a arriba
				supergrado = (float) 7.836119;
				enemyCircle.setTransform(enemyCircle.getPosition().x - 1, enemyCircle.getPosition().y + 1, supergrado);
			} else if (supergrado == (float) 3.1572204) {// izq a arriba
				supergrado = (float) 7.836119;
				enemyCircle.setTransform(enemyCircle.getPosition().x + 1, enemyCircle.getPosition().y + 1, supergrado);
			}

			else if (supergrado == (float) 4.670048) {// abajo a arriba
				supergrado = (float) 7.836119;
				enemyCircle.setTransform(enemyCircle.getPosition().x, enemyCircle.getPosition().y + 2, supergrado);
			}
		}

		else if (angles == (float) 4.670048) {// abajo
			if (supergrado == (float) 6.2739186) {// der a abajo
				supergrado = (float) 4.670048;
				enemyCircle.setTransform(enemyCircle.getPosition().x - 1, enemyCircle.getPosition().y - 1, supergrado);
			} else if (supergrado == (float) 3.1572204) {// izq a abajo
				supergrado = (float) 4.670048;
				enemyCircle.setTransform(enemyCircle.getPosition().x + 1, enemyCircle.getPosition().y - 1, supergrado);
			}

			else if (supergrado == (float) 7.836119) {// arriba abajo
				supergrado = (float) 4.670048;
				enemyCircle.setTransform(enemyCircle.getPosition().x, enemyCircle.getPosition().y - 2, supergrado);
			}
		}

		else if (angles == (float) 6.2739186) {// der
			if (supergrado == (float) 3.1572204) {// izq a der
				supergrado = (float) 6.2739186;
				enemyCircle.setTransform(enemyCircle.getPosition().x + 2, enemyCircle.getPosition().y, supergrado);
			} else if (supergrado == (float) 4.670048) {// abajo a der
				supergrado = (float) 6.2739186;
				enemyCircle.setTransform(enemyCircle.getPosition().x + 1, enemyCircle.getPosition().y + 1, supergrado);
			}

			else if (supergrado == (float) 7.836119) {// arriba der
				supergrado = (float) 6.2739186;
				enemyCircle.setTransform(enemyCircle.getPosition().x + 1, enemyCircle.getPosition().y - 1, supergrado);
			}
		}

		else if (angles == (float) 3.1572204) {// izq
			if (supergrado == (float) 6.2739186) {// der izq
				supergrado = (float) 3.1572204;
				enemyCircle.setTransform(enemyCircle.getPosition().x - 2, enemyCircle.getPosition().y, supergrado);
			} else if (supergrado == (float) 4.670048) {// abajo a izq
				supergrado = (float) 3.1572204;
				enemyCircle.setTransform(enemyCircle.getPosition().x - 1, enemyCircle.getPosition().y + 1, supergrado);
			}

			else if (supergrado == (float) 7.836119) {// arriba izq
				supergrado = (float) 3.1572204;
				enemyCircle.setTransform(enemyCircle.getPosition().x - 1, enemyCircle.getPosition().y - 1, supergrado);
			}
		}
	}

	void recorrido() {
		 escuchar ();
          if(!escuchar && !regresar){
            if(direccion.equals("quiet") && !persiguiendo){
                
                if (((Player.getPosition().y - enemyCircle.getPosition().y) > -3
						&& (Player.getPosition().y - enemyCircle.getPosition().y) < 3)) {
					
                                    if (Player.getPosition().x >= enemyCircle.getPosition().x && (enemyCircle.getAngle()<7.5f && enemyCircle.getAngle()>5.5f) ||enemyCircle.getAngle()==0 ) {
                                       if( alcanceVista( (int)Player.getPosition().y, (int) enemyCircle.getPosition().x, (int) Player.getPosition().x ,"horizontal"  )){          
                                        persiguiendo = true;
                                       }
           
				     }

					if (Player.getPosition().x <= enemyCircle.getPosition().x && (enemyCircle.getAngle()<4.3f && enemyCircle.getAngle()>2f) ) {
                                            if(alcanceVista((int)Player.getPosition().y,(int)Player.getPosition().x,(int)enemyCircle.getPosition().x,"horizontal")){
                                              persiguiendo = true;
                                           }
           				}
				}
                
                      if (((Player.getPosition().x - enemyCircle.getPosition().x) > -5
						&& (Player.getPosition().x - enemyCircle.getPosition().x) < 5)) {

					if ((Player.getPosition().y > enemyCircle.getPosition().y) && (enemyCircle.getAngle()<2f && enemyCircle.getAngle()>0f)) {
                                              if(alcanceVista((int)Player.getPosition().x,(int)enemyCircle.getPosition().y,(int)Player.getPosition().y,"vertical")){
                                                 persiguiendo = true;
                                              }
           				}
                                        
                                        if ((Player.getPosition().y > enemyCircle.getPosition().y) && (enemyCircle.getAngle()<8f && enemyCircle.getAngle()>7f)) {
	                                       
                                            if(alcanceVista((int)Player.getPosition().x,(int)enemyCircle.getPosition().y,(int)Player.getPosition().y,"vertical")){
                                            persiguiendo = true;
                                            }       
                                          }
                                        
                                        

					if (Player.getPosition().y < enemyCircle.getPosition().y && (enemyCircle.getAngle()<5.5f && enemyCircle.getAngle()>3.2f)) {
                                        
                                             if(alcanceVista((int)Player.getPosition().x,(int)Player.getPosition().y,(int)enemyCircle.getPosition().y,"vertical")){
                                                        persiguiendo = true;
                                                        
                                             }
                                            }
				}    
                
            }
        
            if (direccion.equals("horizontal") && !persiguiendo) {
				
				walk();
				if (((Player.getPosition().y - enemyCircle.getPosition().y) > -3
						&& (Player.getPosition().y - enemyCircle.getPosition().y) < 3)) {
					
                                    if (Player.getPosition().x >= enemyCircle.getPosition().x && (enemyCircle.getAngle()<7.5f && enemyCircle.getAngle()>5.5f) || enemyCircle.getAngle()==0 ) {
					if(alcanceVista((int)Player.getPosition().y,(int)enemyCircle.getPosition().x,(int)Player.getPosition().x,"horizontal")){	
                                            persiguiendo = true;
                                        }

					}

					if (Player.getPosition().x <= enemyCircle.getPosition().x && (enemyCircle.getAngle()<4.3f && enemyCircle.getAngle()>2f) ) {
                                        if(alcanceVista((int)Player.getPosition().y,(int)Player.getPosition().x,(int)enemyCircle.getPosition().x,"horizontal")){
                                            persiguiendo = true;
                                            }

					}
				}
			}

			if (direccion.equals("vertical") && !persiguiendo) {
                            walk();
				if (((Player.getPosition().x - enemyCircle.getPosition().x) > -5
						&& (Player.getPosition().x - enemyCircle.getPosition().x) < 5)) {

				       if ((Player.getPosition().y > enemyCircle.getPosition().y) && (enemyCircle.getAngle()<2f && enemyCircle.getAngle()>0f)) {
					 if( alcanceVista( (int)Player.getPosition().x,(int) Player.getPosition().y, (int)enemyCircle.getPosition().y, "vertical" )){ 	
                                            persiguiendo = true;
                                        }

				}
                                        
                                        if ((Player.getPosition().y > enemyCircle.getPosition().y) && (enemyCircle.getAngle()<8f && enemyCircle.getAngle()>7f)) {
                                          if( alcanceVista( (int)Player.getPosition().x, (int) enemyCircle.getPosition().y, (int) Player.getPosition().y,"vertical")){
                                            persiguiendo = true;
                                          }

					}
                                        
                                        

					if (Player.getPosition().y < enemyCircle.getPosition().y && (enemyCircle.getAngle()<5.5f && enemyCircle.getAngle()>3.2f)) {
					 if( alcanceVista( (int)Player.getPosition().x,(int) Player.getPosition().y, (int)enemyCircle.getPosition().y, "vertical" )){ 	
                                            persiguiendo = true;
                                         }
					}
				}

			}
           }
	}

	 public void escuchar (){
           boolean indicador=false;
            
           if(!"punch".equals(Player.getWeapon()) && !"knife".equals(Player.getWeapon()) && !persiguiendo && Gdx.input.isButtonPressed(Input.Buttons.LEFT) && ((int) (Player.getPosition().y)* (int) (A.A.x)+(int) (Player.getPosition().x)) !=b1){
           
               if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) &&  !Player.isDeath() || !escuchar){
                   revisar=false;
                   if(!regresar && A.A.y==95 && A.A.x == 119 ){
                       int a=0,a1=0,b=0,b1=0;
                       if(enemyCircle.getPosition().x <= Player.getPosition().x){
                           a=(int)enemyCircle.getPosition().x;
                           a1=(int)Player.getPosition().x;
                       }
                       else if (enemyCircle.getPosition().x > Player.getPosition().x){
                           a=(int)Player.getPosition().x;
                           a1=(int)enemyCircle.getPosition().x;
                       }
                       
                       if(enemyCircle.getPosition().y <= Player.getPosition().y){
                           b=(int)enemyCircle.getPosition().y;
                           b1= (int) Player.getPosition().y;
                       }
                       
                        else if (enemyCircle.getPosition().y > Player.getPosition().y){
                           b=(int)Player.getPosition().y;
                           b1=(int) enemyCircle.getPosition().y;
                       }
                       
                       for(int i=a;i<a1;i++){
                           if(A.A.mundo[b][i]==1 && A.A.mundo[b][i+1]==1 && A.A.mundo[b][i+2]==1 && A.A.mundo[b][i+3]==1 && A.A.mundo[b][i+4]==1){
                               indicador=true;
                               continue;
                          }
                          
                       }
                       
                       for(int i=b;i<b1;i++){
                           if(A.A.mundo[i][a]==1 && A.A.mundo[i+1][a]==1 && A.A.mundo[i+2][a]==1 && A.A.mundo[i+3][a]==1 && A.A.mundo[i+4][a]==1){
                               indicador=true;
                               continue;
                           }
                           
                       }
                       
                   }
                   
                   if(!indicador){
			p = (int) enemyCircle.getPosition().y;

			p1 = (int) (A.A.x);
			p2 = (int) (enemyCircle.getPosition().x);

			b = p * p1 + p2;

			p4 = (int) (Player.getPosition().y);
			p5 = (int) (A.A.x);
			p6 = (int) (Player.getPosition().x);
			b1 = p4 * p5 + p6;
                        auxDestinoX = (int) (Player.getPosition().x);
                        auxDestinoY =(int) (Player.getPosition().y);
                        

			A.algoritmoBuscaCamino(b, b1, Player); // origen /destinseguir();
			z = A.recorrido.pop();
                        regresar=false;
                        escuchar=true;
                 }
               }
           }
                
           
           
                
               if(escuchar || regresar){
                 
                   if(auxDestinoX==z.x && auxDestinoY==z.y){
                      
                       if(ojear<600 && !regresar){
                           revisar();
                           revisar=true;
                       }
                       
                      if(regresar){
                          restart();
                      }
                      
                      if(ojear==0 && revisar){
                        revisar=false; 
                        p4 = (int) initPosition.y;
			p5 = (int) (A.A.x);
			p6 = (int) initPosition.x;
                        auxDestinoX=p6;
                        auxDestinoY=p4;
			b = p4 * p5 + p6;
                        auxDestino=b; 
                          
                        //auxDestinoX = p2;
                        //auxDestinoY = p;
                        A.algoritmoBuscaCamino(b1, b,Player);
                        escuchar=false;  
                        regresar=true;
                       }
                  }
                
                vigia();
               
                if(!revisar && !persiguiendo){
		perseguir(z);
               
                if ((int)enemyCircle.getPosition().x == z.x || (int)enemyCircle.getPosition().y == z.y) {
			if ((int)enemyCircle.getPosition().x == z.x && (int)enemyCircle.getPosition().y == z.y) {

				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}

			}

                        else if (((int)enemyCircle.getPosition().x - 1 == z.x && (int)enemyCircle.getPosition().y == z.y) || ((int)enemyCircle.getPosition().x + 1 == z.x && (int)enemyCircle.getPosition().y == z.y) || ((int)enemyCircle.getPosition().x - 2 == z.x && (int)enemyCircle.getPosition().y == z.y)
					|| ((int)enemyCircle.getPosition().x + 2 == z.x && (int)enemyCircle.getPosition().y == z.y)) {
				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}
			}

                        else if (((int)enemyCircle.getPosition().x == z.x && (int)enemyCircle.getPosition().y - 1 == z.y) || ((int)enemyCircle.getPosition().x == z.x && (int)enemyCircle.getPosition().y + 1 == z.y) || ((int)enemyCircle.getPosition().x == z.x && (int)enemyCircle.getPosition().y - 2 == z.y)
					|| ((int)enemyCircle.getPosition().x == z.x && (int)enemyCircle.getPosition().y + 2 == z.y)) {
				if (!A.recorrido.isEmpty()) {
					z = A.recorrido.pop();
				}
			}
		}
             }
           }
        }

	  public void vistaEnemigo(){//VISTA DEL ENEMIGO PARA DISPARAR SI LO TIENE EN LA MIRA
            
            if(supergrado==(float)7.836119 && ((Player.getPosition().x - enemyCircle.getPosition().x) > -1 && (Player.getPosition().x - enemyCircle.getPosition().x) < 1) && Player.getPosition().y >= enemyCircle.getPosition().y){ //ARRIBA
	       if (alcanceVista((int) enemyCircle.getPosition().x,(int) enemyCircle.getPosition().y,(int) Player.getPosition().y, "vertical")){
                enLaMira=true;
               }
            }
					
                                        
            if(supergrado==(float)4.670048 && ((Player.getPosition().x - enemyCircle.getPosition().x) > -1 && (Player.getPosition().x - enemyCircle.getPosition().x) < 1) && Player.getPosition().y <= enemyCircle.getPosition().y){//ABAJO
                if (alcanceVista((int) enemyCircle.getPosition().x, (int) Player.getPosition().y,(int) enemyCircle.getPosition().y,"vertical")){
                enLaMira=true;
               }
                
            }
					
                                            
            if(supergrado==(float)6.2739186 && ((Player.getPosition().y - enemyCircle.getPosition().y) > -1 && (Player.getPosition().y - enemyCircle.getPosition().y) < 1) && Player.getPosition().x >= enemyCircle.getPosition().x){//DERECHA
                if (alcanceVista((int) enemyCircle.getPosition().y,(int) enemyCircle.getPosition().x, (int) Player.getPosition().x,"horizontal")){
                enLaMira=true;
               }
            }
                                            
            if(supergrado==(float)3.1572204 && ((Player.getPosition().y - enemyCircle.getPosition().y) > -1 && (Player.getPosition().y - enemyCircle.getPosition().y) < 1) && Player.getPosition().x <= enemyCircle.getPosition().x){//IZQUIERDA
               if (alcanceVista((int) enemyCircle.getPosition().y, (int) Player.getPosition().x,(int) enemyCircle.getPosition().x,"horizontal")){
                enLaMira=true;
               }
            }
            
            
        }

	public boolean alcanceVista(int constante, int punto1, int punto2, String vista) {

		if(vista.equals("horizontal")){
            for(int i=punto1;i<=punto2;i++){
               
                if(A.A.mundo[constante][i]==1){
                    return false;
                }
                
                if(A.A.mundo[constante][i]==3){
                   
                   for (Bag<Materials> materials : materials) {

			for (Materials tmpM : materials) {
                            if("door".equals(tmpM.getName()) && ( tmpM.getPosition().y - constante < 5 && tmpM.getPosition().y - constante > -5) && ( tmpM.getPosition().x - i < 5 && tmpM.getPosition().x - i > -5)  ){
                              if(tmpM.angleBody > -0.9 && tmpM.angleBody < 0.9){
                                return false;
                              }
                               if(tmpM.angleBody > 2.4 && tmpM.angleBody < 3.8){
                                return false;
                              }
                            }
                        }
                 }
              
            }
                
            }
            return true;
           }
           
            else{
                
              for(int i=punto1;i<=punto2;i++){
               
               if(A.A.mundo[i][constante]==1){
                    return false;
                }
               
               if(A.A.mundo[i][constante]==3){
                   
              
                  
                   for (Bag<Materials> materials : materials) {

			for (Materials tmpM : materials) {
                            if("door".equals(tmpM.getName()) && ( tmpM.getPosition().x - constante < 5 && tmpM.getPosition().x - constante > -5) && ( tmpM.getPosition().y - i < 5 && tmpM.getPosition().y - i > -5)){
                              if(tmpM.angleBody > 4 && tmpM.angleBody<5.5){
                                return false;
                              }
                              
                              if(tmpM.angleBody > 0.5 && tmpM.angleBody<2.6){
                                 return false; 
                              }
                            }
                        }
                 }
               }
               
              }
              return true;
            }
		}

	public void vigia() {

		if (supergrado == (float) 7.836119
				&& ((Player.getPosition().x - enemyCircle.getPosition().x) > -3
						&& (Player.getPosition().x - enemyCircle.getPosition().x) < 3)
				&& Player.getPosition().y >= enemyCircle.getPosition().y) { // ARRIBA
			if (alcanceVista((int) Player.getPosition().x, (int) enemyCircle.getPosition().y,
					(int) Player.getPosition().y, "vertical")) {
				persiguiendo = true;
				if (!Player.isDeath()) {
					sounds.hollow.play();
					warningT = 0;
				}
			}
		}

		if (supergrado == (float) 4.670048
				&& ((Player.getPosition().x - enemyCircle.getPosition().x) > -3
						&& (Player.getPosition().x - enemyCircle.getPosition().x) < 3)
				&& Player.getPosition().y <= enemyCircle.getPosition().y) {// ABAJO
			if (alcanceVista((int) Player.getPosition().x, (int) Player.getPosition().y,
					(int) enemyCircle.getPosition().y, "vertical")) {
				persiguiendo = true;
				if (!Player.isDeath()) {
					sounds.hollow.play();
					warningT = 0;
				}
			}
		}

		if (supergrado == (float) 6.2739186
				&& ((Player.getPosition().y - enemyCircle.getPosition().y) > -3
						&& (Player.getPosition().y - enemyCircle.getPosition().y) < 3)
				&& Player.getPosition().x >= enemyCircle.getPosition().x) {// DERECHA
			if (alcanceVista((int) Player.getPosition().y, (int) enemyCircle.getPosition().x,
					(int) Player.getPosition().x, "horizontal")) {
				persiguiendo = true;
				if (!Player.isDeath()) {
					sounds.hollow.play();
					warningT = 0;
				}
			}
		}

		if (supergrado == (float) 3.1572204
				&& ((Player.getPosition().y - enemyCircle.getPosition().y) > -3
						&& (Player.getPosition().y - enemyCircle.getPosition().y) < 3)
				&& Player.getPosition().x <= enemyCircle.getPosition().x) {// IZQUIERDA
			if (alcanceVista((int) Player.getPosition().y, (int) Player.getPosition().x,
					(int) enemyCircle.getPosition().x, "horizontal")) {
				persiguiendo = true;
				if (!Player.isDeath()) {
					sounds.hollow.play();
					warningT = 0;
				}
			}
		}
	}
	
	
	public void radioArma(){
        
        if("punch".equals(getWeapon()) && !buscarArma){
             
            for (Bag<Weapon> weapon : weapons) {
                
           if(weapon.isEmpty()){
               break;
           }
            else if(!weapon.isEmpty()){     
	     for (Weapon tmpW : weapon) {

                 if(tmpW.weapon!=null && (tmpW.weapon.getPosition().x - getPosition().x > -5 && tmpW.weapon.getPosition().x - getPosition().x < 5 )  && (tmpW.weapon.getPosition().y - getPosition().y > -5 && tmpW.weapon.getPosition().y - getPosition().y < 5 && tmpW.getAmmo()>0) )   {
                        
                        int pasosJugador= A.recorrido.size();
                         
                        p = (int) enemyCircle.getPosition().y;

                        p1 = (int) (A.A.x);
                        p2 = (int) (enemyCircle.getPosition().x);

                        b = p * p1 + p2;

                        p4 = (int) (tmpW.weapon.getPosition().y);
                        p5 = (int) (A.A.x);
                        p6 = (int) (tmpW.weapon.getPosition().x);
                        b1 = p4 * p5 + p6;

                        A.algoritmoBuscaCamino(b, b1,Player); // origen /destinseguir();
                        
                         if(A.recorrido.size() < pasosJugador ){
                             buscarArma=true;
                             a=tmpW;
                         }
                         
                         
                      }
                    }
           }
     
           if(buscarArma){
               break;
           }
                 
           }
        }
      
    }
	
         public void revisar(){
            
            ojear++;
            if(ojear>=0 && ojear<150){
                rotateEnemy((float)7.836119);
            }
            
            if(ojear>=150 && ojear<300){
                rotateEnemy((float)6.2739186);
            }
            
            if(ojear>=300 &&ojear<450){
                rotateEnemy((float) 4.670048);
            }
            
            if(ojear>=450 && ojear<600){
                rotateEnemy((float) 3.1572204);
               if(ojear==599){ 
                ojear=0;
               }
               
            }
        }
}
