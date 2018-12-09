package box2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Materials {

	private World world;
	private float x, y, sizeX, sizeY;
	private Body body;
	private Sprite sprite;
	private Array<Body> tmpBodies = new Array<Body>();
	private float angle;

	private final String[] materialdir = {
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/chair.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/rotableChair.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/circularTable.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/bed.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/sofa.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/desk.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/mediumDesk.png",
			"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/materials/bigDesk.png",
			"sprites/materials/gsharp/", "sprites/materials/a/", "sprites/materials/asharp/", "sprites/materials/b/" };

	public Materials(float posX, float posY, World world, int opcMaterial, float angle) {
		this.x = posX;
		this.y = posY;
		this.angle = angle;
		this.world = world;
		switch (opcMaterial) {
		case 0:
			this.sprite = new Sprite(new Texture(materialdir[0]));
			buildMaterial("chair");
			break;
		case 1:
			this.sprite = new Sprite(new Texture(materialdir[1]));
			buildMaterial("rotableChair");
			break;
		case 2:
			this.sprite = new Sprite(new Texture(materialdir[2]));
			buildMaterial("circularTable");
			break;
		case 3:
			this.sprite = new Sprite(new Texture(materialdir[3]));
			buildMaterial("bed");
			break;
		case 4:
			this.sprite = new Sprite(new Texture(materialdir[4]));
			buildMaterial("sofa");
			break;
		case 5:
			this.sprite = new Sprite(new Texture(materialdir[5]));
			buildMaterial("desk");
			break;
		case 6:
			this.sprite = new Sprite(new Texture(materialdir[6]));
			buildMaterial("mediumDesk");
			break;
		case 7:
			this.sprite = new Sprite(new Texture(materialdir[7]));
			buildMaterial("bigDesk");
			break;

		}
	}

        public void setAngle(){
            body.setTransform(body.getPosition(), body.getAngle() * (angle * MathUtils.degreesToRadians));
        }
        
	public Vector2 getPosition() {
		return body.getPosition();
	}

	private void buildMaterial(String name) {
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		if (name.equals("chair")) {

			sizeX = 5.5f;
			sizeY = 5.5f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape chair = new PolygonShape();
			chair.setAsBox(1.3f, 1.1f, new Vector2(0, 0), 0);
			fixtureDef.shape = chair;
			fixtureDef.density = 2.5f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			body.setAngularDamping(5);
			body.setLinearDamping(5);
			chair.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("rotableChair")) {

			sizeX = 5.5f;
			sizeY = 5.5f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape rotableChair = new PolygonShape();
			rotableChair.setAsBox(1f, 1f, new Vector2(0, 0), 0);
			fixtureDef.shape = rotableChair;
			fixtureDef.density = 1.5f;
			fixtureDef.friction = 5;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			body.setAngularDamping(0.2f);
			body.setLinearDamping(2);
			rotableChair.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("circularTable")) {

			sizeX = 13.5f;
			sizeY = 13.5f;
			bodyDef.type = BodyType.StaticBody;
			CircleShape circularTable = new CircleShape();
			circularTable.setPosition(new Vector2(0, 0));
			circularTable.setRadius(4.1f);
			fixtureDef.shape = circularTable;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			circularTable.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("bed")) {

			sizeX = 6.5f;
			sizeY = 6.5f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape bed = new PolygonShape();
			bed.setAsBox(1.5f, 3f, new Vector2(0, 0), 0);
			fixtureDef.shape = bed;
			fixtureDef.density = 55f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			bed.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("sofa")) {

			sizeX = 9f;
			sizeY = 8.5f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape sofa = new PolygonShape();
			sofa.setAsBox(1.5f, 4f, new Vector2(0, 0), 0);
			fixtureDef.shape = sofa;
			fixtureDef.density = 25f;
			fixtureDef.friction = 0;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			sofa.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("desk")) {

			sizeX = 8.8f;
			sizeY = 8.5f;
			bodyDef.type = BodyType.DynamicBody;
			PolygonShape desk = new PolygonShape();
			desk.setAsBox(1.4f, 3.2f, new Vector2(0, 0), 0);
			fixtureDef.shape = desk;
			fixtureDef.density = 3f;
			fixtureDef.friction = 10;
			fixtureDef.restitution = 0f;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			body.setAngularDamping(40);
			body.setLinearDamping(20);
			desk.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("mediumDesk")) {

			sizeX = 18.5f;
			sizeY = 18.5f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape mediumDesk = new PolygonShape();
			mediumDesk.setAsBox(2f, 9f, new Vector2(0, 0), 0);
			fixtureDef.shape = mediumDesk;
			body = world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			mediumDesk.dispose();
			body.setTransform(x, y, body.getAngle() * (angle * MathUtils.degreesToRadians));

		} else if (name.equals("bigDesk")) {

			sizeX = 25.5f;
			sizeY = 25.5f;
			bodyDef.type = BodyType.StaticBody;
			PolygonShape bigDesk = new PolygonShape();
			bigDesk.setAsBox(0.8f, 9f, new Vector2(0.4f, 0), 0);
			fixtureDef.shape = bigDesk;
			body = world.createBody(bodyDef);
			body.setTransform(x, y, angle);
			body.createFixture(fixtureDef);
			bigDesk.dispose();
			PolygonShape bigDesk1 = new PolygonShape();
			bigDesk1.setAsBox(3.1f, 1f, new Vector2(-3.7f, -8f), 0);
			fixtureDef.shape = bigDesk1;
			world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			bigDesk1.dispose();
			PolygonShape bigDesk2 = new PolygonShape();
			bigDesk2.setAsBox(3.1f, 1f, new Vector2(-3.7f, 8), 0);
			fixtureDef.shape = bigDesk2;
			world.createBody(bodyDef);
			body.createFixture(fixtureDef);
			bigDesk2.dispose();
		}setAngle();
	}

	public void dispose() {
		sprite.getTexture().dispose();
		tmpBodies = new Array<Body>();
	}

	public void render(SpriteBatch batch) {
		batch.begin();
		sprite.setSize(sizeX, sizeY);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		body.setUserData(sprite);
		world.getBodies(tmpBodies);
            for (Body bodyaux : tmpBodies) {
                if (bodyaux.getUserData() != null && bodyaux.getUserData() instanceof Sprite) {
                    
                    Sprite spriteaux = (Sprite) bodyaux.getUserData();
                    spriteaux.setPosition((bodyaux.getPosition().x - spriteaux.getWidth() / 2),
                            (bodyaux.getPosition().y - spriteaux.getHeight() / 2));
                    spriteaux.setRotation(bodyaux.getAngle() * MathUtils.radiansToDegrees);
                    spriteaux.draw(batch);
                    bodyaux = null;
                }
            }
		batch.end();
	}
}
