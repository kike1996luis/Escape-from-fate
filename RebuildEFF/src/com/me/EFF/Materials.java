package com.me.EFF;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Materials {
	
	int bandera=0;
    float xBody,yBody,angleBody;
    boolean indi;

	private World world;
	private float x, y, sizeX, sizeY;
	private Body body;
	private Sprite sprite;
	private float angle;
	private boolean isBreakable;
	private boolean isPunchable;
	private FixtureDef fixtureDef;
	private String nameMaterial;
	private int bulletsReceived;
	private boolean isExplosion;
	public boolean isStatic;
	private Random rand;
	private String[] spriteBroken = { new String("data/sprites/materials/chairBroken.png"),
			new String("data/sprites/materials/explosiveBarrelBroken.png"),
			new String("data/sprites/materials/littleFlowerVaseBroken.png"),
			new String("data/sprites/materials/wallGlassBroken.png") };
	private final String[] materialdir = { "data/sprites/materials/chair.png",
			"data/sprites/materials/rotableChair.png", "data/sprites/materials/circularTable.png",
			"data/sprites/materials/bed.png", "data/sprites/materials/sofa.png", "data/sprites/materials/desk.png",
			"data/sprites/materials/mediumDesk.png", "data/sprites/materials/bigDesk.png",
			"data/sprites/materials/fallenTable.png", "data/sprites/materials/miniSofa.png",
			"data/sprites/materials/glassTable.png", "data/sprites/materials/littleFlowerVase.png",
			"data/sprites/materials/explosiveBarrel.png", "data/sprites/materials/doubleSofa.png",
			"data/sprites/materials/door.png", "data/sprites/materials/wheelChair.png",
			"data/sprites/materials/wallGlass.png", "data/sprites/materials/littleTable.png",
			"data/sprites/materials/largeTable.png", "data/sprites/materials/door2.png",
			"data/sprites/materials/ironWall.png", "data/sprites/materials/ironWall1.png" };
	private boolean isBroken;
	private drawBody renderM;
	private Sounds sounds;
	public boolean alreadyPainted;
	private boolean drawBackwards = false;
	private boolean alreadyInactive;
	private int timeBroken;
	private int ID;
	private Vector2 initPosition;
	private float initAngle;
	private boolean back;
	private boolean drawUpWards = false;
	private UserDataWrapper userW;
	private int tempCol = 36;
	private OrthographicCamera camera;

	public Materials(float posX, float posY, World world, String opcMaterial, float angle, String color,
			SpriteBatch batch, Sounds sounds, int ID, boolean back, OrthographicCamera camera) {

		this.camera = camera;
		this.rand = new Random();
		this.back = back;
		this.initPosition = new Vector2(posX, posY);
		this.initAngle = angle;
		this.sounds = sounds;
		this.ID = ID;
		this.x = posX;
		this.y = posY;
		this.angle = angle;
		this.world = world;
		fixtureDef = new FixtureDef();
		if (sprite != null) {
			if (sprite.getTexture() != null) {
				sprite.getTexture().dispose();
			}
		}
		if (opcMaterial.equals("chair")) {

			this.sprite = new Sprite(new Texture(materialdir[0]));
			buildMaterial("chair");
			this.nameMaterial = "chair";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = true;

		} else if (opcMaterial.equals("rotableChair")) {

			if (color.equals("green")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/rotableChairG.png"));
			} else if (color.equals("black")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/rotableChairB.png"));
			} else if (color.equals("blue")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/rotableChairBL.png"));
			} else if (color.equals("red")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/rotableChairR.png"));
			} else {
				this.sprite = new Sprite(new Texture(materialdir[1]));
			}
			buildMaterial("rotableChair");
			this.nameMaterial = "rotableChair";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = true;

		} else if (opcMaterial.equals("circularTable")) {
			this.sprite = new Sprite(new Texture(materialdir[2]));
			buildMaterial("circularTable");
			this.nameMaterial = "circularTable";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("bed")) {

			if (color.equals("green")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/bedG.png"));
			} else if (color.equals("red")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/bedR.png"));
			} else {
				this.sprite = new Sprite(new Texture(materialdir[3]));
			}
			buildMaterial("bed");
			this.nameMaterial = "bed";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = false;

		} else if (opcMaterial.equals("sofa")) {
			if (color.equals("red")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/sofaR.png"));
			} else if (color.equals("blue dark")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/sofaBD.png"));
			} else if (color.equals("black")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/sofaBL.png"));
			} else if (color.equals("green")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/sofaG.png"));
			} else if (color.equals("purple")) {
				this.sprite = new Sprite(new Texture("data/sprites/materials/sofaP.png"));
			} else {
				this.sprite = new Sprite(new Texture(materialdir[4]));
			}

			buildMaterial("sofa");
			this.nameMaterial = "sofa";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = true;

		} else if (opcMaterial.equals("desk")) {
			this.sprite = new Sprite(new Texture(materialdir[5]));
			buildMaterial("desk");
			this.nameMaterial = "desk";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = false;

		} else if (opcMaterial.equals("mediumDesk")) {
			this.sprite = new Sprite(new Texture(materialdir[6]));
			buildMaterial("mediumDesk");
			this.nameMaterial = "mediumDesk";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("bigDesk")) {
			this.sprite = new Sprite(new Texture(materialdir[7]));
			buildMaterial("bigDesk");
			this.nameMaterial = "bigDesk";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("fallenTable")) {
			this.sprite = new Sprite(new Texture(materialdir[8]));
			buildMaterial("fallenTable");
			this.nameMaterial = "fallenTable";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isPunchable = true;

		} else if (opcMaterial.equals("miniSofa")) {
			this.sprite = new Sprite(new Texture(materialdir[9]));
			buildMaterial("miniSofa");
			this.nameMaterial = "miniSofa";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isPunchable = true;

		} else if (opcMaterial.equals("glassTable")) {
			this.sprite = new Sprite(new Texture(materialdir[10]));
			buildMaterial("glassTable");
			this.nameMaterial = "glassTable";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("littleFlowerVase")) {
			this.sprite = new Sprite(new Texture(materialdir[11]));
			buildMaterial("littleFlowerVase");
			this.nameMaterial = "littleFlowerVase";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = true;

		} else if (opcMaterial.equals("explosiveBarrel")) {
			this.sprite = new Sprite(new Texture(materialdir[12]));
			buildMaterial("explosiveBarrel");
			this.nameMaterial = "explosiveBarrel";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = true;

		} else if (opcMaterial.equals("doubleSofa")) {
			this.sprite = new Sprite(new Texture(materialdir[13]));
			buildMaterial("doubleSofa");
			this.nameMaterial = "doubleSofa";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = false;

		} else if (opcMaterial.equals("door")) {
			this.sprite = new Sprite(new Texture(materialdir[14]));
			buildMaterial("door");
			this.nameMaterial = "door";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = true;

		} else if (opcMaterial.equals("wheelChair")) {
			this.sprite = new Sprite(new Texture(materialdir[15]));
			buildMaterial("wheelChair");
			this.nameMaterial = "wheelChair";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = false;
			isPunchable = true;

		} else if (opcMaterial.equals("wallGlass")) {
			this.sprite = new Sprite(new Texture(materialdir[16]));
			buildMaterial("wallGlass");
			this.nameMaterial = "wallGlass";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = true;

		} else if (opcMaterial.equals("littleTable")) {
			this.sprite = new Sprite(new Texture(materialdir[17]));
			buildMaterial("littleTable");
			this.nameMaterial = "littleTable";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("largeTable")) {
			this.sprite = new Sprite(new Texture(materialdir[18]));
			buildMaterial("largeTable");
			this.nameMaterial = "largeTable";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("door2")) {
			this.sprite = new Sprite(new Texture(materialdir[19]));
			buildMaterial("door2");
			this.nameMaterial = "door2";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = true;

		} else if (opcMaterial.equals("ironWall")) {
			this.sprite = new Sprite(new Texture(materialdir[20]));
			buildMaterial("ironWall");
			this.nameMaterial = "ironWall";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		} else if (opcMaterial.equals("ironWall1")) {
			this.sprite = new Sprite(new Texture(materialdir[21]));
			buildMaterial("ironWall1");
			this.nameMaterial = "ironWall1";
			this.renderM = new drawBody(world, nameMaterial, batch);
			isStatic = true;
			isPunchable = false;

		}
		userW = new UserDataWrapper(nameMaterial, sprite);
	}

	public void setDeactivated() {

	}

	public boolean drawBackwards() {
		return drawBackwards;
	}

	public String typeAngle() {
		if(angle == 90) {
			return "vertical";
		}else if(angle == 0) {
			return "horizontal";
		}return "null";
	}
	
	public boolean isTraspassable() {
		if(fixtureDef.filter.categoryBits == Constants.BIT_TRASPASABLE) {
			return true;
		}
		return false;
	}
	
	public void collisionWithMelee() {

		if (fixtureDef.filter.categoryBits != Constants.BIT_TRASPASABLE) {
			int randAux = rand.nextInt(3);
			if (randAux == 0) {
				sounds.puc1.play();
			} else if (randAux == 1) {
				sounds.puc2.play();
			} else if (randAux == 2) {
				sounds.puc3.play();
			}
			if (!isBroken && isPunchable) {
				bulletsReceived++;
				breakObject();
				impulso(body.getPosition().x, body.getPosition().y, 20f, camera);
			}
		}
	}

	public void collisionWithEnemy(Body bod) {

		if (tempCol >= 5) {
			tempCol = 0;
			boolean rand = randomBoolean();
			Vector2 impulse = bod.getLinearVelocity();
			if (impulse.x == 0 && impulse.y > 0) {
				if (rand) {
					body.setLinearVelocity(impulse.x + 30, body.getLinearVelocity().y);
				} else {
					body.setLinearVelocity(impulse.x - 30, body.getLinearVelocity().y);
				}
			} else if (impulse.y == 0 && impulse.x > 0) {
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x, impulse.y + 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x, impulse.y - 30);
				}
			} else if (impulse.x == 0 && impulse.y <= 0) {
				if (rand) {
					body.setLinearVelocity(impulse.x - 30, body.getLinearVelocity().y);
				} else {
					body.setLinearVelocity(impulse.x + 30, body.getLinearVelocity().y);
				}
			} else if (impulse.y == 0 && impulse.x <= 0) {
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x, impulse.y - 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x, impulse.y + 30);
				}
			} else if (impulse.y > 0 && impulse.x > 0) { // Diagonal sup derecha
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x + 30, impulse.y - 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x - 30, impulse.y + 30);
				}
			} else if (impulse.y <= 0 && impulse.x <= 0) { // Diagonal inf izquierda
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x - 30, impulse.y + 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x + 30, impulse.y - 30);
				}
			} else if (impulse.y > 0 && impulse.x <= 0) { // Diagonal sup izquierda
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x - 30, impulse.y - 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x + 30, impulse.y + 30);
				}
			} else if (impulse.y <= 0 && impulse.x > 0) { // Diagonal inf derecha
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x + 30, impulse.y + 30);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x - 30, impulse.y - 30);
				}
			} else {
				if (rand) {
					body.setLinearVelocity(body.getLinearVelocity().x + 60, impulse.y + 60);
				} else {
					body.setLinearVelocity(body.getLinearVelocity().x - 60, impulse.y - 60);
				}
			}
		}
	}

	public void impulso(float xx, float yy, float speed, OrthographicCamera camera) {

		float mousex = Gdx.input.getX();
		float mousey = 0;
		if (body.getAngle() > 6.1 && body.getAngle() < 9.6) {
			mousey = Gdx.input.getY() - 5;
		} else {
			mousey = Gdx.input.getY();
		}
		Vector3 mousePos = new Vector3(mousex, mousey, 0);
		camera.unproject(mousePos);
		float velx = mousePos.x - xx;
		float vely = mousePos.y - yy;
		float length = (float) Math.sqrt(velx * velx + vely * vely);
		if (length != 0) {
			velx = velx / length;
			vely = vely / length;
		}
		body.setLinearVelocity(velx * speed, vely * speed);
	}

	public void collisionWithBullet(Bullet bull) {
		if (!nameMaterial.equals("littleFlowerVase") && !nameMaterial.equals("glassTable")
				&& !nameMaterial.equals("bisDesk") && !nameMaterial.equals("mediumDesk") && !nameMaterial.equals("desk")
				&& !nameMaterial.equals("bed") && !nameMaterial.equals("circularTable")
				&& !nameMaterial.equals("wallGlass") && !nameMaterial.equals("ironWall")
				&& !nameMaterial.equals("ironWall1")) {
			Vector2 vec = bull.shoot.getLinearVelocity();
			body.setLinearVelocity(vec);
		}
		if (nameMaterial.equals("miniSofa") || nameMaterial.equals("sofa") || nameMaterial.equals("sofaBD")
				|| nameMaterial.equals("sofaBL") || nameMaterial.equals("sofaG") || nameMaterial.equals("sofaP")
				|| nameMaterial.equals("sofaR") || nameMaterial.equals("rotableChair")
				|| nameMaterial.equals("rotableChairB") || nameMaterial.equals("rotableChairBL")
				|| nameMaterial.equals("rotableChairG") || nameMaterial.equals("rotableChairR")
				|| nameMaterial.equals("wallGlass") || nameMaterial.equals("wheelChair")
				|| nameMaterial.equals("doubleSofa")) {
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
		} else if (nameMaterial.equals("ironWall") || nameMaterial.equals("ironWall1")) {

			int random = rand.nextInt(5);
			if (random == 0) {
				sounds.bs1.play();
			} else if (random == 1) {
				sounds.bs2.play();
			} else if (random == 2) {
				sounds.bs3.play();
			} else if (random == 3) {
				sounds.bs4.play();
			} else if (random == 4) {
				sounds.bs5.play();
			}
		} else if (nameMaterial.equals("explosiveBarrel")) {
			int random = rand.nextInt(5);
			if (random == 0) {
				sounds.bs1.play();
			} else if (random == 1) {
				sounds.bs2.play();
			} else if (random == 2) {
				sounds.bs3.play();
			} else if (random == 3) {
				sounds.bs4.play();
			} else if (random == 4) {
				sounds.bs5.play();
			}
		} else if (nameMaterial.equals("littleFlowerVase") || nameMaterial.equals("chair")
				|| nameMaterial.equals("fallenTable") || nameMaterial.equals("door")
				|| nameMaterial.equals("largeTable") || nameMaterial.equals("littleTable")) {
			int random = rand.nextInt(5);
			if (random == 0) {
				sounds.bw1.play();
			} else if (random == 1) {
				sounds.bw2.play();
			} else if (random == 2) {
				sounds.bw3.play();
			}
		}
		if (!isBroken && isBreakable) {
			bulletsReceived++;
			breakObject();
		}
	}

	public void collisionWithWeapon(Weapon weap) {
		Vector2 vec = weap.weapon.getLinearVelocity();
		if (vec.x > 55 || vec.y > 55 || vec.x < -55 || vec.y < -55) {
			if (!isBroken) {
				bulletsReceived++;
				breakObject();
			}
		}

	}

	public void getExplosion() {

		applyImpulse();
		bulletsReceived += 50;
		breakObject();
	}

	private void applyImpulse() {
		int impulsoX = (int) (Math.random() * 300) + 200;
		int impulsoY = (int) (Math.random() * 300) + 200;
		boolean opc1 = randomBoolean();
		boolean opc2 = randomBoolean();
		if (opc1 && opc2) {
			body.setLinearVelocity(impulsoX, impulsoY);
		} else if (!opc1 && opc2) {
			body.setLinearVelocity(-impulsoX, impulsoY);
		} else if (opc1 && !opc2) {
			body.setLinearVelocity(impulsoX, -impulsoY);
		} else if (!opc1 && !opc2) {
			body.setLinearVelocity(-impulsoX, -impulsoY);
		}
	}

	private boolean randomBoolean() {
		return Math.random() < 0.5;
	}

	public String getName() {
		return nameMaterial;
	}

	public boolean isBreakable() {
		return isBreakable;
	}

	public void restart() {

		body.setLinearVelocity(0, 0);
		body.setAngularVelocity(0);
		if (nameMaterial.equals("door") || nameMaterial.equals("door2")) {
			sprite.getTexture().dispose();
			body.setUserData("destroy");
			if (nameMaterial.equals("door")) {
				buildMaterial("door");
				this.sprite = new Sprite(new Texture(materialdir[14]));
			} else {
				buildMaterial("door2");
				this.sprite = new Sprite(new Texture(materialdir[19]));
			}
		} else if (nameMaterial.equals("doubleSofa")) {
			sprite.getTexture().dispose();
			body.setUserData("destroy");
			buildMaterial("doubleSofa");
			this.sprite = new Sprite(new Texture(materialdir[13]));
		}
		sounds.stopSounds();
		if (!isStatic) {
			body.setTransform(initPosition, initAngle);
		}
		if (isBroken && isBreakable) {

			drawBackwards = false;
			alreadyPainted = false;
			if (alreadyInactive) {
				alreadyInactive = false;
				body.setUserData("active");
			}
			isBroken = false;
			timeBroken = 0;
			bulletsReceived = 0;
			sprite.getTexture().dispose();
			if (nameMaterial.equals("chair")) {

				this.sprite = new Sprite(new Texture(materialdir[0]));

			} else if (nameMaterial.equals("explosiveBarrel")) {

				this.sprite = new Sprite(new Texture(materialdir[12]));

			} else if (nameMaterial.equals("littleFlowerVase")) {

				this.sprite = new Sprite(new Texture(materialdir[11]));

			} else if (nameMaterial.equals("wallGlass")) {

				this.sprite = new Sprite(new Texture(materialdir[16]));
			}
		}
	}

	private void breakObject() { // Metodo para cuando colisione la bala, el material se rompa...
		if (isBreakable && !isBroken) { // Si es un objeto rompible, se carga el sprite del objeto roto...

			if (nameMaterial.equals("chair") && bulletsReceived >= 1) {
				drawBackwards = true;
				sprite = new Sprite(new Texture(spriteBroken[0]));
				isBroken = true; // Indicas que el material está roto
				sounds.break1.play();

			} else if (nameMaterial.equals("explosiveBarrel") && bulletsReceived >= 1) {

				drawBackwards = true;
				sprite = new Sprite(new Texture(spriteBroken[1]));
				isBroken = true; // Indicas que el material está roto
				isExplosion = true;
				body.setUserData("inactive");

			} else if (nameMaterial.equals("littleFlowerVase") && bulletsReceived >= 1) {

				sprite = new Sprite(new Texture(spriteBroken[2]));
				isBroken = true; // Indicas que el material está roto
				sounds.break2.play();

			} else if (nameMaterial.equals("wallGlass") && bulletsReceived >= 1) {

				sprite = new Sprite(new Texture(spriteBroken[3]));
				isBroken = true; // Indicas que el material está roto
				sounds.break2.play();

			}
		}
	}

	public void setDrawn() {
		alreadyPainted = true;
	}

	public boolean isExplosion() {
		return isExplosion;
	}

	public boolean isBroken() {
		return isBroken;
	}

	public void setAngle() {
		body.setTransform(x, y, angle * MathUtils.degreesToRadians);
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

	public boolean drawUpwards() {
		return drawUpWards;
	}

	private void buildMaterial(String name) {
		this.nameMaterial = name;
		BodyDef bodyDef = new BodyDef();
		drawBackwards = false;
		drawUpWards = false;
		fixtureDef.filter.categoryBits = Constants.BIT_MATERIAL;
		if (back) {
			isStatic = true;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
		}
		if (name.equals("chair")) {

			isBreakable = true; // Este objeto es rompible
			sizeX = 5.5f;
			sizeY = 5.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape chair = new PolygonShape();
			chair.setAsBox(1.3f, 1.1f, new Vector2(0, 0), 0);
			fixtureDef.shape = chair;
			fixtureDef.density = 2.5f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(5);
			body.setLinearDamping(5);
			chair.dispose();

		} else if (name.equals("ironWall")) {
			isBreakable = false;
			sizeX = 5f;
			sizeY = 4.7f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape ironWall = new PolygonShape();
			ironWall.setAsBox(1.6f, .4f, new Vector2(0, -.3f), 0);
			fixtureDef.shape = ironWall;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			ironWall.dispose();
			drawUpWards = true;

		} else if (name.equals("ironWall1")) {
			isBreakable = false;
			sizeX = 5f;
			sizeY = 4.7f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape ironWall1 = new PolygonShape();
			ironWall1.setAsBox(1.6f, .4f, new Vector2(0, -.3f), 0);
			fixtureDef.shape = ironWall1;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			ironWall1.dispose();
			drawUpWards = true;

		} else if (name.equals("rotableChair")) {

			sizeX = 5.5f;
			sizeY = 5.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape rotableChair = new PolygonShape();
			rotableChair.setAsBox(1f, 1f, new Vector2(0, 0), 0);
			fixtureDef.shape = rotableChair;
			fixtureDef.density = 1.5f;
			fixtureDef.friction = 5;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(0.2f);
			body.setLinearDamping(2);
			rotableChair.dispose();

		} else if (name.equals("circularTable")) {

			sizeX = 13.5f;
			sizeY = 13.5f;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			bodyDef.type = BodyType.StaticBody;
			CircleShape circularTable = new CircleShape();
			circularTable.setPosition(new Vector2(0, 0));
			circularTable.setRadius(4.1f);
			fixtureDef.shape = circularTable;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			circularTable.dispose();

		} else if (name.equals("bed")) {

			sizeX = 6.5f;
			sizeY = 6.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape bed = new PolygonShape();
			bed.setAsBox(1.5f, 3f, new Vector2(0, 0), 0);
			fixtureDef.shape = bed;
			fixtureDef.density = 55f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			bed.dispose();

		} else if (name.equals("sofa")) {

			sizeX = 8f;
			sizeY = 8f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape sofa = new PolygonShape();
			sofa.setAsBox(1.5f, 4f, new Vector2(0, 0), 0);
			fixtureDef.shape = sofa;
			fixtureDef.density = 25f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			sofa.dispose();

		} else if (name.equals("desk")) {

			sizeX = 8.8f;
			sizeY = 8.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape desk = new PolygonShape();
			desk.setAsBox(1.4f, 3.2f, new Vector2(0, 0), 0);
			fixtureDef.shape = desk;
			fixtureDef.density = 3f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			desk.dispose();

		} else if (name.equals("mediumDesk")) {

			sizeX = 18.5f;
			sizeY = 18.5f;
			bodyDef.type = BodyType.StaticBody;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			PolygonShape mediumDesk = new PolygonShape();
			mediumDesk.setAsBox(2f, 9f, new Vector2(0, 0), 0);
			fixtureDef.shape = mediumDesk;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			mediumDesk.dispose();

		} else if (name.equals("bigDesk")) {

			sizeX = 50.5f;
			sizeY = 50.5f;
			bodyDef.type = BodyType.StaticBody;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			PolygonShape bigDesk = new PolygonShape();
			bigDesk.setAsBox(1.7f, 12.5f, new Vector2(4.3f, 1), 0);
			fixtureDef.shape = bigDesk;
			body = world.createBody(bodyDef);
			body.setTransform(x, y, angle);
			body.createFixture(fixtureDef).setUserData(this);
			bigDesk.dispose();
			PolygonShape bigDesk1 = new PolygonShape();
			bigDesk1.setAsBox(5.1f, 1.5f, new Vector2(-1f, -11.5f), 0);
			fixtureDef.shape = bigDesk1;
			world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			bigDesk1.dispose();
			PolygonShape bigDesk2 = new PolygonShape();
			bigDesk2.setAsBox(5.1f, 1.5f, new Vector2(-1f, 13.5f), 0);
			fixtureDef.shape = bigDesk2;
			world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			bigDesk2.dispose();
		} else if (name.equals("fallenTable")) {

			sizeX = 8.5f;
			sizeY = 8.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape fallenTable = new PolygonShape();
			fallenTable.setAsBox(0.3f, 2.8f, new Vector2(-1.6f, 0.2f), 0);
			fixtureDef.shape = fallenTable;
			fixtureDef.density = 10f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.setAngularDamping(20);
			body.setLinearDamping(80);
			fixtureDef.shape = fallenTable;
			body.setTransform(x, y, angle);
			body.createFixture(fixtureDef).setUserData(this);
			fallenTable.dispose();
			PolygonShape fallenTable1 = new PolygonShape();
			fallenTable1.setAsBox(1.5f, 0.2f, new Vector2(-0.3f, -2.5f), 0);
			fixtureDef.shape = fallenTable1;
			body.setAngularDamping(20);
			body.setLinearDamping(8);
			body.createFixture(fixtureDef).setUserData(this);
			fallenTable1.dispose();
			PolygonShape fallenTable2 = new PolygonShape();
			fallenTable2.setAsBox(1.5f, 0.2f, new Vector2(-0.3f, 3f), 0);
			fixtureDef.shape = fallenTable1;
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(20);
			body.setLinearDamping(8);
			fallenTable2.dispose();
			PolygonShape fallenTable3 = new PolygonShape();
			fallenTable3.setAsBox(1f, 0.2f, new Vector2(-0.5f, 2.3f), 0);
			fixtureDef.shape = fallenTable3;
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(20);
			body.setLinearDamping(8);
			fallenTable3.dispose();
			PolygonShape fallenTable4 = new PolygonShape();
			fallenTable4.setAsBox(1f, 0.2f, new Vector2(-0.5f, -1.7f), 0);
			fixtureDef.shape = fallenTable4;
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(20);
			body.setLinearDamping(8);
			fallenTable4.dispose();

		} else if (name.equals("miniSofa")) {
			sizeX = 5.5f;
			sizeY = 5.5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape miniSofa = new PolygonShape();
			miniSofa.setAsBox(1.8f, 1.5f, new Vector2(0, 0), 0);
			fixtureDef.shape = miniSofa;
			fixtureDef.density = 3f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			miniSofa.dispose();

		} else if (name.equals("glassTable")) {
			sizeX = 8f;
			sizeY = 8f;
			bodyDef.type = BodyType.StaticBody;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			PolygonShape glassTable = new PolygonShape();
			glassTable.setAsBox(2f, 3.7f, new Vector2(0, 0), 0);
			fixtureDef.shape = glassTable;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			glassTable.dispose();

		} else if (name.equals("littleFlowerVase")) {
			isBreakable = true; // Este objeto es rompible
			sizeX = 5.5f;
			sizeY = 5.5f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape littleFlowerVase = new PolygonShape();
			littleFlowerVase.setAsBox(1.3f, 1.3f, new Vector2(-.3f, .3f), 0);
			fixtureDef.shape = littleFlowerVase;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			littleFlowerVase.dispose();

		} else if (name.equals("explosiveBarrel")) {
			isBreakable = true; // Este objeto es rompible
			sizeX = 5f;
			sizeY = 5f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			CircleShape explosiveBarrel = new CircleShape();
			explosiveBarrel.setPosition(new Vector2(-0.1f, -0.1f));
			explosiveBarrel.setRadius(1.3f);
			fixtureDef.shape = explosiveBarrel;
			fixtureDef.density = 1f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(10);
			body.setLinearDamping(20);
			explosiveBarrel.dispose();

		} else if (name.equals("doubleSofa")) {
			sizeX = 10.5f;
			sizeY = 10.5f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape doubleSofa = new PolygonShape();
			doubleSofa.setAsBox(1.9f, 5f, new Vector2(-1.2f, 0), 0);
			fixtureDef.shape = doubleSofa;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			doubleSofa.dispose();
			body.setTransform(x, y, 0);
			PolygonShape doubleSofa1 = new PolygonShape();
			doubleSofa1.setAsBox(2.4f, 1.7f, new Vector2(0.5f, -3.2f), 0);
			fixtureDef.shape = doubleSofa1;
			body.createFixture(fixtureDef).setUserData(this);
			doubleSofa1.dispose();
			isStatic = true;

		} else if (name.equals("door")) {
			drawBackwards = false;
			sizeX = 5f;
			sizeY = 4.7f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape door = new PolygonShape();
			door.setAsBox(.2f, 1.5f, new Vector2(0, 0.2f), 0);
			fixtureDef.shape = door;
			fixtureDef.density = 10f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(0);
			body.setLinearDamping(1);
			door.dispose();
			body.setTransform(x, y, 0);
			CircleShape door1 = new CircleShape();
			bodyDef.type = BodyType.StaticBody;
			door1.setPosition(new Vector2(0f, -2f));
			door1.setRadius(0.45f);
			fixtureDef.shape = door1;
			body.createFixture(fixtureDef).setUserData(this);
			door1.dispose();
			drawUpWards = true;

		} else if (name.equals("wheelChair")) {
			isBreakable = false; // Este objeto es rompible
			sizeX = 7f;
			sizeY = 7f;
			if (isStatic) {
				bodyDef.type = BodyType.StaticBody;
			} else {
				bodyDef.type = BodyType.DynamicBody;
			}
			PolygonShape wheelChair = new PolygonShape();
			wheelChair.setAsBox(1.8f, 1.5f, new Vector2(0, 0), 0);
			fixtureDef.shape = wheelChair;
			fixtureDef.density = 3f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(15);
			body.setLinearDamping(5);
			wheelChair.dispose();

		} else if (name.equals("wallGlass")) {
			isBreakable = true;
			sizeX = 5f;
			sizeY = 4.7f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape wallGlass = new PolygonShape();
			wallGlass.setAsBox(1.5f, .4f, new Vector2(0, -.3f), 0);
			fixtureDef.shape = wallGlass;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			wallGlass.dispose();
			drawUpWards = true;

		} else if (name.equals("littleTable")) {
			isBreakable = false;
			sizeX = 13f;
			sizeY = 13f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape littleTable = new PolygonShape();

			littleTable.setAsBox(1.5f, 2f, new Vector2(0, -.3f), 0);
			fixtureDef.shape = littleTable;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			littleTable.dispose();

		} else if (name.equals("largeTable")) {
			isBreakable = false;
			sizeX = 13f;
			sizeY = 13f;
			bodyDef.type = BodyType.StaticBody;
			fixtureDef.filter.categoryBits = Constants.BIT_TRASPASABLE;
			PolygonShape largeTable = new PolygonShape();
			largeTable.setAsBox(1.3f, 4.5f, new Vector2(0, -.6f), 0);
			fixtureDef.shape = largeTable;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			largeTable.dispose();

		} else if (name.equals("door2")) {
			drawBackwards = false;
			sizeX = 5f;
			sizeY = 4.7f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape door = new PolygonShape();
			door.setAsBox(.2f, 1.5f, new Vector2(0, 0.2f), 0);
			fixtureDef.shape = door;
			fixtureDef.density = 10f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef).setUserData(this);
			body.setAngularDamping(0);
			body.setLinearDamping(1);
			door.dispose();
			body.setTransform(x, y, 0);
			CircleShape door1 = new CircleShape();
			bodyDef.type = BodyType.StaticBody;
			door1.setPosition(new Vector2(0f, -2f));
			door1.setRadius(0.45f);
			fixtureDef.shape = door1;
			body.createFixture(fixtureDef).setUserData(this);
			door1.dispose();
			drawUpWards = true;

		}
		setAngle();
	}

	public void stopExplosion() {
		isExplosion = false;
	}

	public void dispose() {
		sprite.getTexture().dispose();
		body.setUserData("destroy");
	}

	public int getID() {
		return ID;
	}

	public void render(SpriteBatch batch) {
		

        if(!indi){
            xBody = body.getPosition().x;
            yBody = body.getPosition().y;
            
            if(bandera==30){
            indi=true;
            }
            bandera++;
        }
    
        angleBody= body.getAngle();

		sprite.setSize(sizeX, sizeY);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		userW.setData(nameMaterial, sprite);
		body.setUserData(userW);
		renderM.renderBody();
		if (isBroken && !alreadyInactive) {
			if (timeBroken > 50 && nameMaterial.equals("chair")) {

				body.setUserData("inactive");
				alreadyInactive = true;
			} else if (nameMaterial.equals("explosiveBarrel")) {

				body.setUserData("inactive");
				alreadyInactive = true;
			} else if (timeBroken <= 50 && nameMaterial.equals("chair")) {
				timeBroken++;
			}
		}
		if (tempCol < 35) {
			tempCol++;
		}
	}
	
	public Vector2 getinitPosition(){
        return initPosition;
    }
	
}
