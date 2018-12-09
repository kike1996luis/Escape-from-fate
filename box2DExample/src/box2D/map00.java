package box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class map00 extends maps { // Esta clase se extiende de la clase maps

	private Bag<Materials>[] colMaterials;
	int cantM;

	@SuppressWarnings("unchecked")
	public map00() {
		
		super(camera, world, debugRenderer, renderer, batch, multi);
		colMaterials = (Bag<Materials>[]) new Bag[999];
		cantM=0;
		for (int i = 0; i < colMaterials.length; i++) {

			colMaterials[i] = new Bag<Materials>();

		}
		Gdx.input.setInputProcessor(new InputController() {

                        @Override
			public boolean scrolled(int amount) {

				camera.rotate(amount * 5);
				return true;
			}
		});

	}

	// Regresa la posici�n del jugador

	public int posX() {
		return 1000;
	}

	public int posY() {
		return 800;
	}

        @Override
	public void dispose(){
            for (Bag<Materials> colMaterial : colMaterials) {
                for (Materials tmpM : colMaterial) {
                    tmpM.dispose();
                }
            }
	}
	
	public void renderMaterials(SpriteBatch batch) {
            for (Bag<Materials> colMaterial : colMaterials) {
                if (colMaterial == null) {
                    break;
                } else {
                    for (Materials tmpM : colMaterial) {
                        tmpM.render(batch);
                    }
                }
            }
	}

	public void addMaterial(Materials material){
		
		colMaterials[cantM].add(material);
		cantM++;
	}
	
	public void buildMap(World world) { // Objetos y l�mites del mapa, NO
										// MODIFICAR.

		// LoadingMaterials
		
		colMaterials[cantM].add(new Materials(50, 45, world, 0, 0));
		colMaterials[cantM].add(new Materials(51, 41, world, 0, 45));
		colMaterials[cantM].add(new Materials(15, 3, world, 1, 20));
		colMaterials[cantM].add(new Materials(10, 5, world, 1, 20));
		colMaterials[cantM].add(new Materials(53, 43, world, 2, 20));
		colMaterials[cantM].add(new Materials(35, 34, world, 4, 0));
		colMaterials[cantM].add(new Materials(48, 11.5f, world, 5, 4f));
		colMaterials[cantM].add(new Materials(67, 39, world, 6, 45f));
		colMaterials[cantM].add(new Materials(62, 44, world, 3, 0));
		colMaterials[cantM].add(new Materials(35, 40, world, 7, 9.5f));
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		// Limites del mapa
		// Body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(2, 0);

		// Shape ground

		ChainShape groundShape = new ChainShape();
		groundShape.createChain(new Vector2[] { new Vector2(0, 0), new Vector2(78, 0) });

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = 0; // Deslice sobre otro cuerpo
		fixtureDef.restitution = 0; // Rebote

		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		// Ground

		// Body definition

		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// Shape ground

		ChainShape groundShape0 = new ChainShape();
		groundShape0.createChain(new Vector2[] { new Vector2(0, 0), new Vector2(5, 0) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		ChainShape groundShape1 = new ChainShape();
		groundShape1.createChain(new Vector2[] { new Vector2(80, 0), new Vector2(80, 48) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape.dispose();

		ChainShape groundShape2 = new ChainShape();
		groundShape2.createChain(new Vector2[] { new Vector2(0, 0), new Vector2(0, 48) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape2.dispose();

		ChainShape groundShape3 = new ChainShape();
		groundShape3.createChain(new Vector2[] { new Vector2(0, 48), new Vector2(80, 48) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape3.dispose();

		ChainShape groundShape4 = new ChainShape();
		groundShape4.createChain(new Vector2[] { new Vector2(0, 40f), new Vector2(12.6f, 40f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape4.dispose();

		ChainShape groundShape5 = new ChainShape();
		groundShape5.createChain(new Vector2[] { new Vector2(0, 11.3f), new Vector2(12.6f, 11.3f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape5.dispose();

		ChainShape groundShape6 = new ChainShape();
		groundShape6.createChain(new Vector2[] { new Vector2(12.6f, 11.3f), new Vector2(12.6f, 40f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape6.dispose();

		ChainShape groundShape7 = new ChainShape();
		groundShape7.createChain(new Vector2[] { new Vector2(22f, 0), new Vector2(22f, 3f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape7.dispose();

		ChainShape groundShape8 = new ChainShape();
		groundShape8.createChain(new Vector2[] { new Vector2(21f, 0), new Vector2(21f, 3f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape8.dispose();

		ChainShape groundShape9 = new ChainShape();
		groundShape9.createChain(new Vector2[] { new Vector2(21f, 3), new Vector2(22f, 3f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape9.dispose();

		ChainShape groundShape10 = new ChainShape();
		groundShape10.createChain(new Vector2[] { new Vector2(21f, 6.5f), new Vector2(22f, 6.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape10.dispose();

		ChainShape groundShape11 = new ChainShape();
		groundShape11.createChain(new Vector2[] { new Vector2(21f, 15.5f), new Vector2(22f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape11.dispose();

		ChainShape groundShape12 = new ChainShape();
		groundShape12.createChain(new Vector2[] { new Vector2(22f, 6.5f), new Vector2(22f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape12.dispose();

		ChainShape groundShape13 = new ChainShape();
		groundShape13.createChain(new Vector2[] { new Vector2(21f, 6.5f), new Vector2(21f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape13.dispose();

		ChainShape groundShape14 = new ChainShape();
		groundShape14.createChain(new Vector2[] { new Vector2(22f, 15.5f), new Vector2(51f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape14.dispose();

		ChainShape groundShape15 = new ChainShape();
		groundShape15.createChain(new Vector2[] { new Vector2(22f, 14.5f), new Vector2(51f, 14.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape15.dispose();

		ChainShape groundShape16 = new ChainShape();
		groundShape16.createChain(new Vector2[] { new Vector2(51f, 4.5f), new Vector2(51f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape16.dispose();

		ChainShape groundShape17 = new ChainShape();
		groundShape17.createChain(new Vector2[] { new Vector2(50f, 3.5f), new Vector2(50f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape17.dispose();

		ChainShape groundShape18 = new ChainShape();
		groundShape18.createChain(new Vector2[] { new Vector2(50f, 3.5f), new Vector2(63.5f, 3.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape18.dispose();

		ChainShape groundShape19 = new ChainShape();
		groundShape19.createChain(new Vector2[] { new Vector2(50f, 4.5f), new Vector2(63.5f, 4.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape19.dispose();

		ChainShape groundShape20 = new ChainShape();
		groundShape20.createChain(new Vector2[] { new Vector2(63.5f, 3.5f), new Vector2(63.5f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape20.dispose();

		ChainShape groundShape21 = new ChainShape();
		groundShape21.createChain(new Vector2[] { new Vector2(62.5f, 3.5f), new Vector2(62.5f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape21.dispose();

		ChainShape groundShape22 = new ChainShape();
		groundShape22.createChain(new Vector2[] { new Vector2(62.5f, 15.5f), new Vector2(70.5f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape22.dispose();

		ChainShape groundShape23 = new ChainShape();
		groundShape23.createChain(new Vector2[] { new Vector2(62.5f, 14.5f), new Vector2(70.5f, 14.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape23.dispose();

		ChainShape groundShape24 = new ChainShape();
		groundShape24.createChain(new Vector2[] { new Vector2(70.5f, 14.5f), new Vector2(70.5f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape24.dispose();

		ChainShape groundShape25 = new ChainShape();
		groundShape25.createChain(new Vector2[] { new Vector2(73.5f, 14.5f), new Vector2(73.5f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape25.dispose();

		ChainShape groundShape26 = new ChainShape();
		groundShape26.createChain(new Vector2[] { new Vector2(73.5f, 14.5f), new Vector2(79f, 14.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape26.dispose();

		ChainShape groundShape27 = new ChainShape();
		groundShape27.createChain(new Vector2[] { new Vector2(73.5f, 15.5f), new Vector2(79f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape27.dispose();

		ChainShape groundShape28 = new ChainShape();
		groundShape28.createChain(new Vector2[] { new Vector2(79f, 0f), new Vector2(79f, 15.5f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape28.dispose();

		ChainShape groundShape29 = new ChainShape();
		groundShape29.createChain(new Vector2[] { new Vector2(65f, 29f), new Vector2(65f, 48f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape29.dispose();

		ChainShape groundShape30 = new ChainShape();
		groundShape30.createChain(new Vector2[] { new Vector2(24.5f, 29f), new Vector2(24.5f, 48f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape30.dispose();

		ChainShape groundShape31 = new ChainShape();
		groundShape31.createChain(new Vector2[] { new Vector2(25.5f, 29f), new Vector2(25.5f, 48f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape31.dispose();

		ChainShape groundShape32 = new ChainShape();
		groundShape32.createChain(new Vector2[] { new Vector2(24.5f, 29f), new Vector2(49.5f, 29f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape32.dispose();

		ChainShape groundShape33 = new ChainShape();
		groundShape33.createChain(new Vector2[] { new Vector2(25.5f, 30f), new Vector2(49.5f, 30f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape33.dispose();

		ChainShape groundShape34 = new ChainShape();
		groundShape34.createChain(new Vector2[] { new Vector2(49.5f, 29f), new Vector2(49.5f, 30f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape34.dispose();

		ChainShape groundShape35 = new ChainShape();
		groundShape35.createChain(new Vector2[] { new Vector2(53, 29f), new Vector2(53, 30f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape35.dispose();

		ChainShape groundShape36 = new ChainShape();
		groundShape36.createChain(new Vector2[] { new Vector2(53, 29f), new Vector2(65, 29f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape36.dispose();

		ChainShape groundShape37 = new ChainShape();
		groundShape37.createChain(new Vector2[] { new Vector2(53, 30f), new Vector2(65, 30f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape37.dispose();

		ChainShape groundShape38 = new ChainShape();
		groundShape38.createChain(new Vector2[] { new Vector2(64, 30f), new Vector2(64, 48f) });
		world.createBody(bodyDef).createFixture(fixtureDef);
		groundShape38.dispose();

		debugRenderer.setDrawBodies(false);
		debugRenderer.setDrawContacts(false);
	}
}
