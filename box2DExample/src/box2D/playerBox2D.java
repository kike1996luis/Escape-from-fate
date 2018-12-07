package box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class playerBox2D {

	private Vector2 movement = new Vector2();
	private Body playerCircle;
	private Texture imagen;
	private Texture imagen1;
	private TextureRegion[] movPlayer;
	private TextureRegion[] movFeet;
	private Animation animationAttack;
	private Animation animationFeet;
	private float tiempo;
	private boolean attack;
	private TextureRegion frameActual2;
	private Sprite boxSprite;
	private Array<Body> tmpBodies = new Array<Body>();
	private float PTM = 64;
	private World world;
	public Bag<Body>[] colSprites;
	private int framesfx;
	OrthographicCamera camera;
	float posX = 0, posY = 0;
	float is = 0;
	private multimedia multi;

	public playerBox2D(int x, int y, World world, final OrthographicCamera camera, multimedia multi) {
		this.camera = camera;
		this.multi = multi;
		this.world = world;
		BodyDef bodyDef = new BodyDef(); // Aquí sólo se va a cargar el cuerpo
											// del jugador y su sprite
											// correspondiente
		FixtureDef fixtureDef = new FixtureDef();
		bodyDef.position.set(x / PTM, y / PTM); // PTM significa Pixels to
												// Meters, 64 píxeles es un
												// metro.....
		bodyDef.type = BodyType.DynamicBody;

		imagen = new Texture(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/player/ak47attack.png"));
		new Texture(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/player/ak47stand.png"));
		TextureRegion[][] tmp = TextureRegion.split(imagen, imagen.getWidth() / 5, imagen.getHeight());
		imagen1 = new Texture(Gdx.files.internal(
				"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/player/feet.png"));
		TextureRegion[][] tmp1 = TextureRegion.split(imagen1, imagen1.getWidth() / 5, imagen1.getHeight());
		movPlayer = new TextureRegion[5];
		movFeet = new TextureRegion[5];

		for (int i = 0; i < 5; i++) {
			movFeet[i] = tmp1[0][i];
		}
		for (int i = 0; i < 5; i++) {
			movPlayer[i] = tmp[0][i];
		}
		animationAttack = new Animation(0.07f, movPlayer);
		animationFeet = new Animation(0.10f, movFeet);
		tiempo = 0f;

		// TODO EL CUERPO DEL JUGADOR

		// ball shape
		CircleShape ballShape = new CircleShape();
		ballShape.setPosition(new Vector2(-1.5f, 0f)); // Posición del cuerpo
														// del player en el
														// espacio, NO MODIFICAR
														// ESTO
		ballShape.setRadius(1.5f);
		
		///PolygonShape asd=new PolygonShape();
		//asd.setAsBox(hx, hy);

		// fixture definition
		// el jugador no tiene nada de esto. Así que NO MOVER
		fixtureDef.shape = ballShape;
		fixtureDef.density = 0f; // Peso en kg
		fixtureDef.friction = 0; // Deslice sobre otro cuerpo
		fixtureDef.restitution = 0; // Rebote
		playerCircle = world.createBody(bodyDef); // playerCircle es el cuerpo
													// del jugador
		playerCircle.createFixture(fixtureDef);
		ballShape.dispose();

		// box.applyAngularImpulse(20, true); // Impulso angular

		playerCircle.setLinearDamping(7); // Que el jugador tenga poca fuerza
											// lineal para moverse

	}

	public Vector2 getPosition() {
		return playerCircle.getPosition();
	}

	public float getAngle() {
		return playerCircle.getAngle();
	}

	public void render(SpriteBatch batch) {

		camera.position.set(playerCircle.getPosition().x, playerCircle.getPosition().y, 0); // Pone
																							// la
																							// cámara
																							// en
																							// el
																							// centro
		camera.update();
		playerCircle.applyForceToCenter(movement, true); // Aplica fuerza al
															// centro al jugador
		walk(batch);
		stand(batch);
		attack(batch);
		tiempo += Gdx.graphics.getDeltaTime();
		rotateSprite(); // Aquí cargas la función de rotar
		batch.setProjectionMatrix(camera.combined);

	}

	public void rotateSprite() { // EMILIO AQUÍ VAS A HACER LA FUNCIÓN DE ROTAR

		is += 0.1f; // Puedes probar la rotación activando el .setTransform de
					// aquí abajo

		// Con .seTransform puedes modificar la posición x y,
		// y también colocas el ángulo en el tercer parámetro

		 //playerCircle.setTransform(playerCircle.getPosition().x,
		 //playerCircle.getPosition().y, is);
	}

	public void dispose() {
		boxSprite.getTexture().dispose();
		tmpBodies = new Array<Body>();
	}

	public void walk(SpriteBatch batch) {

		boolean arriba = Gdx.input.isKeyPressed(Keys.W);
		boolean abajo = Gdx.input.isKeyPressed(Keys.S);
		boolean izquierda = Gdx.input.isKeyPressed(Keys.A);
		boolean derecha = Gdx.input.isKeyPressed(Keys.D);
		if (arriba || abajo || izquierda || derecha) {

			batch.begin(); // Aquí se dibujan los pies
			frameActual2 = animationFeet.getKeyFrame(tiempo, true);
			Sprite sprite = new Sprite(frameActual2);
			sprite.setSize(5.5f, 5.5f);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			playerCircle.setUserData(sprite);
			world.getBodies(tmpBodies);
			for (Body body : tmpBodies)
				if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
					Sprite spriteaux = (Sprite) body.getUserData();
					spriteaux.setPosition(body.getPosition().x - sprite.getWidth() / 2,
							body.getPosition().y - sprite.getHeight() / 2);
					spriteaux.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					spriteaux.draw(batch);
				}
			batch.end();
			if (arriba) {
				if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					playerCircle.setLinearVelocity(0, +10);
				} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					playerCircle.setLinearVelocity(0, +60);
				} else {
					playerCircle.setLinearVelocity(0, +30);
				}
				if (derecha) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
						playerCircle.setLinearVelocity(+10, +10);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						playerCircle.setLinearVelocity(+60, +60);
					} else {
						playerCircle.setLinearVelocity(+30, +30);
					}
				}
				if (izquierda) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
						playerCircle.setLinearVelocity(-10, +10);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						playerCircle.setLinearVelocity(-60, +60);
					} else {
						playerCircle.setLinearVelocity(-30, +30);
					}
				}
			} else if (abajo) {
				if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					playerCircle.setLinearVelocity(0, -10);
				} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					playerCircle.setLinearVelocity(0, -60);
				} else {
					playerCircle.setLinearVelocity(0, -30);
				}
				if (izquierda) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
						playerCircle.setLinearVelocity(-10, -10);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						playerCircle.setLinearVelocity(-60, -60);
					} else {
						playerCircle.setLinearVelocity(-30, -30);
					}
				}
				if (derecha) {
					if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
						playerCircle.setLinearVelocity(+10, -10);
					} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						playerCircle.setLinearVelocity(+60, -60);
					} else {
						playerCircle.setLinearVelocity(+30, -30);
					}
				}
			} else if (izquierda) {
				if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					playerCircle.setLinearVelocity(-10, 0);
				} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					playerCircle.setLinearVelocity(-60, 0);
				} else {
					playerCircle.setLinearVelocity(-30, 0);
				}
			} else if (derecha) {
				if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
					playerCircle.setLinearVelocity(+10, 0);
				} else if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					playerCircle.setLinearVelocity(+60, 0);
				} else {
					playerCircle.setLinearVelocity(+30, 0);
				}
			}
		}
	}

	public void attack(SpriteBatch batch) {
		attack = Gdx.input.isTouched();
		if (attack) {
			batch.begin();
			frameActual2 = animationAttack.getKeyFrame(tiempo, true);
			Sprite sprite = new Sprite(frameActual2);
			sprite.setSize(5.5f, 5.5f);
			sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
			sprite.rotate(15);
			sprite.setRotation(15);
			playerCircle.setUserData(sprite);
			world.getBodies(tmpBodies);
			for (Body body : tmpBodies)
				if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
					Sprite spriteaux = (Sprite) body.getUserData();
					spriteaux.setPosition(body.getPosition().x - sprite.getWidth() / 2,
							body.getPosition().y - sprite.getHeight() / 2);
					spriteaux.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					spriteaux.draw(batch);
				}
			batch.end();
			if (framesfx >= 5) { // Cada cinco frames se reinicia el sonido del
									// disparo
				multi.fire.stop();
				framesfx = 0;
			}
			if (framesfx == 0) {
				multi.fire.play();
			}
			framesfx++;
		}
		attack = false;

	}

	public void stand(SpriteBatch batch) {
		attack = Gdx.input.isTouched();
		if (!attack) {

			// Básicamente se crea un sprite temporal donde se agrega al world
			// con las coordenadas, y el tamaño dado
			// Luego en el for each, se busca el sprite dentro del world
			// Y se dibuja, con respecto al centro del círculo y su orientación,
			// finalmente se dibuja con el sprite.draw(batch)

			batch.begin();
			boxSprite = new Sprite(new Texture(
					"C:/Users/HOME/Desktop/videoGameGit/videogameGit/box2DExample-android/assets/data/sprites/player/ak47stand.png"));
			boxSprite.setSize(5.5f, 5.5f);
			boxSprite.setOrigin(boxSprite.getWidth() / 2, boxSprite.getHeight() / 2);
			playerCircle.setUserData(boxSprite);
			world.getBodies(tmpBodies);
			for (Body body : tmpBodies)

				if (body.getUserData() != null && body.getUserData() instanceof Sprite) {

					Sprite sprite = (Sprite) body.getUserData();
					sprite.setPosition((body.getPosition().x - sprite.getWidth() / 2),
							(body.getPosition().y - sprite.getHeight() / 2));
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					sprite.draw(batch);
				}
			batch.end();
		}
	}

}
