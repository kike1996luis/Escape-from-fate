package box2D;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class maps {

	protected static OrthographicCamera camera;
	protected static World world;
	protected static Box2DDebugRenderer debugRenderer;
	private AssetManager manager;
	private TiledMap map;

	protected static OrthogonalTiledMapRenderer renderer;
	float unitScale;
	private playerBox2D player;
	protected static SpriteBatch batch;
	protected static multimedia multi;

	public maps(OrthographicCamera camera, World world, Box2DDebugRenderer debugRenderer,
			OrthogonalTiledMapRenderer renderer, SpriteBatch batch, multimedia multi) {

		// En esta clase se eligen los mapas, y se manda a construirlos

		unitScale = 1 / 10f;
		maps.batch = batch;
		maps.multi = multi;
		maps.debugRenderer = debugRenderer;
		maps.world = world;
		maps.camera = camera;
		camera.zoom += 0.1f;
	}

	public void dispose() {
		player.dispose();
	}

	public void chooseMap(int opcMap) { // Se escogen los mapas aquí por
										// parámetro
		switch (opcMap) {

		case 1: // Dependiendo del mapa que se escoja, se carga la posición del
				// jugador en él

			manager = new AssetManager();
			manager.setLoader(TiledMap.class, new TmxMapLoader());
			manager.load("maps/test.tmx", TiledMap.class);
			manager.finishLoading();
			map = manager.get("maps/test.tmx", TiledMap.class);
			renderer = new OrthogonalTiledMapRenderer(map, unitScale);
			debugRenderer.setDrawContacts(false);
			map00 test = new map00();
			player = new playerBox2D(test.posX(), test.posY(), world, camera, multi);
			test.buildMap(world);
			break;
		}
	}

	public void render() { // Se renderiza el jugador y las cámaras
		renderer.setView(camera);
		renderer.render();
		player.render(batch);
	}
}
