package box2D;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

@SuppressWarnings("unused")
public class box2DCode implements ApplicationListener {

	private SpriteBatch batch;
	float x = 0, y = 0;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private float timestep = 1 / 60f;
	private int velocity = 8, positioniterations = 3;
	private playerBox2D player;
	private OrthogonalTiledMapRenderer renderer;
	private AssetManager manager;
	private multimedia multi = new multimedia();

	private maps Map;

	@Override
	public void create() {

		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);
		world = new World(new Vector2(0, 0), true); // Gravedad en x,y
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();

		Pixmap pm = new Pixmap(Gdx.files.internal("sprites/UI/crosshair.png"));
		Gdx.input.setCursorImage(pm, 20, 18);
		pm.dispose();
		Map = new maps(camera, world, debugRenderer, renderer, batch, multi);
		Map.chooseMap(1); // Escoge el mapa, se carga el jugador y todo desde
							// esa clase.
		multi.cargarAssets();
		multi.musica.setLooping(true);
		multi.musica.play();
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
		Map.dispose();
	}

	@Override
	public void render() {
		keys();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Map.render();
		world.step(timestep, positioniterations, positioniterations);// NO
																		// MODIFICAR
		batch.setProjectionMatrix(camera.combined);
		debugRenderer.render(world, camera.combined);

	}

	public void keys() {
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {// ESCAPE para salir
			System.exit(0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.Q)) { // Controla el zoom
															// con Q y E
			camera.zoom += 0.05f;
		} else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			camera.zoom -= 0.05f;
		}
	}

	@Override
	public void resize(int width, int height) { // Mantiene el ratio al
												// maximizar
		camera.viewportWidth = width / 25;
		camera.viewportHeight = height / 25;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
