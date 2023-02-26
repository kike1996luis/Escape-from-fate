package com.me.EFF;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;

public class CodeGame implements Screen, InputProcessor {

	private JSONDataRW mapReader;
	public static Bag<Particle>[] colParticles;
	private final Array<Body> tmpBodies = new Array<Body>();
	public static Bag<Particle>[] colBloodParticles;
	public static Bag<Particle>[] colBodyParticles;
	private Bag<Explosion>[] colExplosions;
	private Bag<Materials>[] colMaterials;
	private Bag<Weapon>[] colWeapons;
	private Bag<Limit>[] colLimits;
	private Bag<Enemy>[] colEnemies;
	private Bag<Item>[] colItems;
	private static OrthographicCamera camera;
	private World world;
	private SpriteBatch batch;
	private Sounds sounds;
	@SuppressWarnings("unused")
	private GameClass parent;
	private UI ui;
	private player Player;
	private int cantM;
	private int cantW;
	private int cantL;
	private int cantE;
	private int cantI;
	private Random rand;
	private Box2DDebugRenderer debugRenderer;
	private boolean firstTime;
	private Texture txR;
	private TextureRegion region;
	private boolean paused;
	private boolean isDebugging;
	private static float unitScale;
	private static AssetManager manager;
	private static TiledMap map;
	private static OrthogonalTiledMapRenderer renderer;
	private final float timestep = 1 / 60f;
	private final int positioniterations = 3;
	private Texture txR1;
	private TextureRegion region1;
	private int nextMap = 0;
	private boolean winLevel = false;
	private int M[][];
	private Graph G;
	String nameScene = null;

	@SuppressWarnings("unchecked")
	public CodeGame(GameClass parent) {

		rand = new Random();
		firstTime = true;
		debugRenderer = new Box2DDebugRenderer();
		cantM = 0;
		cantW = 0;
		cantL = 0;
		cantE = 0;
		cantI = 0;
		sounds = new Sounds();
		sounds.loadSounds();
		this.parent = parent;
		colMaterials = (Bag<Materials>[]) new Bag[50];
		colParticles = (Bag<Particle>[]) new Bag[200];
		colBloodParticles = (Bag<Particle>[]) new Bag[200];
		colBodyParticles = (Bag<Particle>[]) new Bag[200];
		colExplosions = (Bag<Explosion>[]) new Bag[20];
		colWeapons = (Bag<Weapon>[]) new Bag[50];
		colLimits = (Bag<Limit>[]) new Bag[500];
		colEnemies = (Bag<Enemy>[]) new Bag[30];
		colItems = (Bag<Item>[]) new Bag[50];
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 18);
		world = new World(new Vector2(0, 0), true);
		batch = new SpriteBatch();
		ui = new UI(new Weapon(sounds, world, "punch", Player, batch));
		Player = new player(world, camera, sounds, batch, ui);
		mapReader = new JSONDataRW(this, parent);
		debugRenderer.setDrawContacts(false);
		debugRenderer.setDrawBodies(false);
		setDebug();
	}

	public void chooseLevel(int gameLevel) {

		if (gameLevel == 0) {

			loadAll();
			mapReader.readJSON("data/maps/test.json");
			firstTime = false;
			nameScene = "bin/data/maps/test.txt";

		} else if (gameLevel == 1) {

			loadAll();
			mapReader.readJSON("data/maps/map01.json");
			firstTime = false;
			nameScene = "bin/data/maps/map01.txt";

		} else if (gameLevel == 2) {

			loadAll();
			mapReader.readJSON("data/maps/map02.json");
			firstTime = false;
			nameScene = "bin/data/maps/map02.txt";
		}
		
		 else if (gameLevel == 3) {

				loadAll();
				mapReader.readJSON("data/maps/map03.json");
				firstTime = false;
				nameScene = "bin/data/maps/map03.txt";
			}

		buildGraph(nameScene);

	}

	public void loadAll() {

		winLevel = false;
		cantL = 0;
		cantM = 0;
		cantW = 0;
		cantE = 0;
		cantI = 0;
		Gdx.input.setInputProcessor(this);
		world.setContactListener(new ContactDetection());
		if (firstTime) {
			ui.changeCursor("inGameCursor");
			for (int i = 0; i < colMaterials.length; i++) {

				colMaterials[i] = new Bag<Materials>();

			}
			for (int i = 0; i < colParticles.length; i++) {

				colParticles[i] = new Bag<Particle>();

			}
			for (int i = 0; i < colBodyParticles.length; i++) {

				colBodyParticles[i] = new Bag<Particle>();

			}
			for (int i = 0; i < colBloodParticles.length; i++) {

				colBloodParticles[i] = new Bag<Particle>();

			}
			for (int i = 0; i < colExplosions.length; i++) {

				colExplosions[i] = new Bag<Explosion>();

			}

			for (int i = 0; i < colWeapons.length; i++) {

				colWeapons[i] = new Bag<Weapon>();

			}

			for (int i = 0; i < colLimits.length; i++) {

				colLimits[i] = new Bag<Limit>();

			}

			for (int i = 0; i < colEnemies.length; i++) {

				colEnemies[i] = new Bag<Enemy>();

			}

			for (int i = 0; i < colItems.length; i++) {

				colItems[i] = new Bag<Item>();

			}
			initializateExplosions();
			initializateParticles();
		} else {
			deactivateObjects();
		}
	}

	public int[][] matriz() {
		return M;
	}

	private void buildGraph(String Scene) {

		float mayorX = 0, mayorY = 0;
		for (Bag<Limit> colLimits : colLimits) {

			for (Limit tmpL : colLimits) {

				if (tmpL.getMayorX() > mayorX) {
					mayorX = tmpL.getMayorX();

				}
				if (tmpL.getMayorY() > mayorY) {
					mayorY = tmpL.getMayorY();
				}
			}
		}

		M = new int[(int) mayorY][(int) mayorX];

		File f = new File(Scene);
		Scanner mirar = null;
		int aux;
		int a1 = 0, a2 = 0;

		try {
			mirar = new Scanner(f);
			while (mirar.hasNextLine()) {

				aux = mirar.nextInt();

				if (a2 == (int)mayorX) {
					a1++;
					a2 = 0;
                   
				}

				M[a1][a2] = aux;
				a2++;
			}

		}

		catch (Exception ex) {

		}
                
		G = new Graph((int) mayorY * (int) mayorX, (int) mayorX, (int) mayorY);
		G.crearMundo(M);
	}

	public void buildMaterial(String name, float x, float y, float angle, boolean back, String color) {

		colMaterials[cantM].add(new Materials(x, y, world, name, angle, color, batch, sounds, cantM, back, camera));
		cantM++;
	}

	public void buildItem(String type, float x, float y, float angle, int goalID, String text) {

		colItems[cantI].add(new Item(type, goalID, new Vector2(x, y), angle, world, ui, text, batch, sounds));
		cantI++;
	}

	public void buildWeapon(String name, float x, float y, float angle) {

		Weapon tmpW = new Weapon(sounds, world, name, Player, batch);
		tmpW.setDropped(x, y, angle, false, true);
		colWeapons[cantW].add(tmpW);
		cantW++;
	}

	public void buildLimit(String figure, float x1, float y1, float x2, float y2, float radius, float angle,
			float sizeX, float sizeY, int goalID, boolean isTraspassable) {

		colLimits[cantL].add(new Limit(figure, x1, y1, x2, y2, radius, angle, sizeX, sizeY, world, goalID, isTraspassable, sounds));
		cantL++;
	}

	public void buildEnemy(String name, String weapon, float x, float y, String angle, String behavior, int origen,
			int destino, int pasos) {

		if(!weapon.equals("punch")) {
			Weapon tmpW = new Weapon(sounds, world, weapon, Player, batch);
			Enemy asd = new Enemy(world, name, tmpW, x, y, angle, origen, destino, batch, sounds, Player, behavior, pasos, colMaterials, colWeapons);
			colWeapons[cantW].add(tmpW);
			cantW++;
			colEnemies[cantE].add(asd);
			cantE++;
			asd.receiveWeapon(tmpW);
		}else {
			Weapon tmpW = new Weapon(sounds, world, "punch", Player, batch);
			colEnemies[cantE].add(
					new Enemy(world, name, tmpW, x, y, angle, origen, destino, batch, sounds, Player, behavior, pasos,colMaterials, colWeapons));
			cantE++;
		}
	}

	public void buildMap(String name, int width, int height, String path, String pngpath, String medium, float initialX,
			float initialY, float initialAngle, int nextMap, String backgroundMusic) {
		if (!firstTime) {
			changeMap(name, width, height, path, pngpath, medium, initialX, initialY, initialAngle, nextMap,
					backgroundMusic);
		} else {
			
			Weapon tmpW = new Weapon(sounds, world, "punch", Player, batch);
			Player.buildPlayer(initialX, initialY, initialAngle, tmpW);
			txR = new Texture(pngpath);
			if (!medium.equals("null")) {
				txR1 = new Texture(medium);
				region1 = new TextureRegion(txR1, 0, 0, width, height);
			}
			this.nextMap = nextMap;
			region = new TextureRegion(txR, 0, 0, width, height);
			unitScale = 1 / 10f;
			manager = new AssetManager();
			manager.setLoader(TiledMap.class, new TmxMapLoader());
			manager.load(path, TiledMap.class);
			manager.finishLoading();
			manager.update();
			map = manager.get(path, TiledMap.class);
			renderer = new OrthogonalTiledMapRenderer(map, unitScale);
			map.dispose();
			setBackgroundMusic(backgroundMusic);
		}
	}

	private void changeMap(String name, int width, int height, String path, String pngpath, String medium,
			float initialX, float initialY, float initialAngle, int nextMap, String backgroundMusic) {
		Gdx.app.postRunnable(() -> {

			Player.setPosition(initialX, initialY, initialAngle, true);
			Player.setInitWeapon();
			if (!Player.getWeapon().equals("punch")) {
				colWeapons[cantW].add(Player.getWeaponProperties());
				cantW++;
			}
			ui.changeWeaponUI(Player.getWeapon());
			txR.dispose();
			txR = new Texture(pngpath);
			region.getTexture().dispose();
			region = new TextureRegion(txR, 0, 0, width, height);
			if (!medium.equals("null")) {
				region1.getTexture().dispose();
				txR1.dispose();
				txR1 = new Texture(medium);
				region1 = new TextureRegion(txR1, 0, 0, width, height);
			} else {
				region1.getTexture().dispose();
				region1 = null;
				txR1.dispose();
			}
			this.nextMap = nextMap;
			manager.setLoader(TiledMap.class, new TmxMapLoader());
			manager.load(path, TiledMap.class);
			manager.update();
			manager.finishLoading();
			map = manager.get(path, TiledMap.class);
			renderer.getMap().dispose();
			renderer = new OrthogonalTiledMapRenderer(map, unitScale);
			map.dispose();
			setBackgroundMusic(backgroundMusic);
		});
	}

	private void setBackgroundMusic(String backgroundMusic) {

		if (backgroundMusic.equals("title")) {
			sounds.background0.setLooping(true);
			sounds.background0.play();
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPause(boolean b) {
		paused = b;
	}

	public void togglePause() {
		paused = !paused;
		if (paused) {
			sounds.stopSounds();
		}
	}

	public void setDebug() {
		isDebugging = !isDebugging;
		if (isDebugging) {
			debugRenderer.setDrawContacts(false);
			debugRenderer.setDrawBodies(false);
		} else {
			debugRenderer.setDrawContacts(true);
			debugRenderer.setDrawBodies(true);
		}
	}

	public void deactivateObjects() {

		for (Bag<Materials> colMaterial : colMaterials) {
			for (Materials tmpM : colMaterial) {
				if (tmpM != null) {
					tmpM.dispose();
				}
			}
		}
		for (int i = 0; i < colMaterials.length; i++) {

			colMaterials[i] = new Bag<Materials>();

		}
		for (Bag<Particle> colParticles : colParticles) {
			for (Particle tmpM : colParticles) {
				if (tmpM != null) {
					tmpM.clean();
				}
			}
		}
		for (Bag<Particle> colBodyParticles : colBodyParticles) {
			for (Particle tmpM : colBodyParticles) {
				if (tmpM != null) {
					tmpM.clean();
				}
			}
		}
		for (Bag<Particle> colBloodParticles : colBloodParticles) {
			for (Particle tmpM : colBloodParticles) {
				if (tmpM != null) {
					tmpM.clean();
				}
			}
		}
		for (Bag<Item> colItems : colItems) {
			for (Item tmpM : colItems) {
				if (tmpM != null) {
					tmpM.dispose();
				}
			}
		}
		for (int i = 0; i < colItems.length; i++) {

			colItems[i] = new Bag<Item>();

		}
		for (Bag<Weapon> colWeapons : colWeapons) {
			for (Weapon tmpM : colWeapons) {
				if (tmpM != null && !tmpM.receivedBy.equals("player")) {
					tmpM.dispose();
				}
			}
		}
		for (int i = 0; i < colWeapons.length; i++) {

			colWeapons[i] = new Bag<Weapon>();

		}
		for (Bag<Limit> colLimits : colLimits) {
			for (Limit tmpM : colLimits) {
				if (tmpM != null) {
					tmpM.delete();
				}
			}
		}
		for (int i = 0; i < colLimits.length; i++) {

			colLimits[i] = new Bag<Limit>();

		}
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpM : colEnemies) {
				if (tmpM != null) {
					tmpM.dispose();
				}
			}
		}
		for (int i = 0; i < colEnemies.length; i++) {

			colEnemies[i] = new Bag<Enemy>();

		}
	}

	@Override
	public boolean keyDown(int keyCode) {
		if (keyCode == Keys.P) {
			if (!Player.isDeath()) {
				togglePause();
			}
		}
		if (keyCode == Keys.ESCAPE) {
			Gdx.app.exit();
		}
		if (keyCode == Keys.K) {
			setDebug();
		}
		if (keyCode == Keys.R && !isPaused() && !Player.isRekilling()) {
			restartMap();
		}
		return false;
	}
	
	public void restartMap() {

		for (Bag<Weapon> colWeapons : colWeapons) {

			for (Weapon tmpW : colWeapons) {

				if (tmpW != null) {
					tmpW.restart();
				}
			}
		}
		Player.restart();
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(tmpW != null) {
					tmpW.restart();
				}
			}
		} 
		for (Bag<Materials> colMaterials : colMaterials) {

			for (Materials tmpM : colMaterials) {

				if (tmpM != null) {
					tmpM.restart();
				}
			}
		}
		for (Bag<Particle> colBloodParticles : colBloodParticles) {

			for (Particle tmpW : colBloodParticles) {

				if (tmpW != null) {
					tmpW.clean();
				}
			}
		}
		for (Bag<Particle> colBodyParticles : colBodyParticles) {

			for (Particle tmpW : colBodyParticles) {

				if (tmpW != null) {
					tmpW.clean();
				}
			}
		}
		for (Bag<Particle> colParticles : colParticles) {

			for (Particle tmpW : colParticles) {

				if (tmpW != null) {
					tmpW.clean();
				}
			}
		}
		for (Bag<Limit> colLimits : colLimits) {
			for (Limit tmpC : colLimits) {
				if (!tmpC.isActive()) {
					tmpC.setActive();
				}
			}
		}
		for (Bag<Item> colItems : colItems) {
			for (Item tmpI : colItems) {
				if (tmpI != null) {
					tmpI.restart();
				}
			}
		}
		sounds.stopSounds();
	}

	public void testLimits() {
		for (Bag<Limit> colLimits : colLimits) {
			for (Limit tmpI : colLimits) {
				tmpI.testLimits();
				if (tmpI.isTouchingGoal()) {
					winLevel = true;
				}
			}
		}
	}

	public void renderItems() {
		for (Bag<Item> colItems : colItems) {
			for (Item tmpI : colItems) {
				if (tmpI != null) {
					tmpI.render();
				}
			}
		}
	}

	private void testGoals() {
		for (Bag<Item> colItems : colItems) {
			for (Item tmpI : colItems) {
				if (tmpI.isReceived()) {

					for (Bag<Limit> colLimits : colLimits) {

						for (Limit tmpC : colLimits) {

							if (tmpC.getgoalID() == tmpI.goalID()) {
								tmpC.setInactive();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	private void desmenbration(Vector2 pos, String var, int i) {
		if(i == 1) {
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
		}else if(i == 2) {
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, true, 4, "blood");
			addBloodParticles(pos, true, 5, "blood");
			addBloodParticles(pos, true, 1, "blood");
			addBloodParticles(pos, true, 2, "blood");
			addBloodParticles(pos, true, 3, "blood");
			addBloodParticles(pos, false, 4, "blood");
			addBloodParticles(pos, false, 5, "blood");
			addBloodParticles(pos, false, 1, "blood");
			addBloodParticles(pos, false, 2, "blood");
			addBloodParticles(pos, false, 3, "blood");
		}
		if (var.equals("player") && i == 1) { // Explosion

			addBodyParticles(pos, true, 1, "playerparts");
			addBodyParticles(pos, true, 2, "playerparts");
			addBodyParticles(pos, true, 3, "playerparts");
			addBodyParticles(pos, true, 4, "playerparts");
			addBodyParticles(pos, true, 5, "playerparts");
			
		} else if (var.equals("player") && i == 2) { // Mutilaci�n en dos partes

			addBodyParticles(pos, false, 6, "playerparts");
			addBodyParticles(pos, false, 5, "playerparts");
		}
		
		else if (var.equals("cartman") && i == 1) { // Explosion

			addBodyParticles(pos, true, 1, "cartmanparts");
			addBodyParticles(pos, true, 2, "cartmanparts");
			addBodyParticles(pos, true, 3, "cartmanparts");
			addBodyParticles(pos, true, 4, "cartmanparts");
			addBodyParticles(pos, true, 5, "cartmanparts");
		} else if (var.equals("cartman") && i == 2) { // Mutilaci�n en dos partes

			addBodyParticles(pos, false, 6, "cartmanparts");
			addBodyParticles(pos, false, 3, "cartmanparts");
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (winLevel) {
			chooseLevel(3);
		}
		destroyBodies();
		setInactiveBodies();
		setActiveBodies();
		testLimits();
		renderer.setView(camera);
		renderer.render();
		if (!isPaused()) {
			world.step(timestep, positioniterations, positioniterations);
			camera.update();
		}
		renderParticles();
		renderBackwardsMaterials();
		if (Player.isBleeding()) {
			bloodFloor(Player.getPosition());
		}
		enemyBleeding();
		walkEnemy();
		if (!Player.isDeath() && !isPaused()) {
			Player.walk();
		}
		if (region1 != null) {
			batch.begin();
			batch.draw(region1, 0, 0, region1.getRegionWidth() * 1 / 10, region1.getRegionHeight() * 1 / 10);
			batch.end();
			batch.setProjectionMatrix(camera.combined);
		}
		if (Player.paintBackwards()) {
			Player.render(isPaused());
		}enemyPaintBackwards();
		if (Player.isExplosion() && !Player.alreadyDrawn()) {

			desmenbration(Player.getPosition(), "player", 1);
			Player.setDrawn();
		} else if (Player.mutilatedInTwo() && !Player.alreadyDrawn()) {

			desmenbration(Player.getPosition(), "player", 2);
			Player.setDrawn();
		}desmenbrationEnemy();
		renderMaterials();
		renderItems();
		testGoals();
		renderWeapons();
		renderBullets();
		renderEnemy(isPaused());
		if (!Player.paintBackwards()) {
			Player.render(isPaused());
		}
		renderMaterialsUpWards();
		renderExplosions();
		debugRenderer.render(world, camera.combined);
		batch.begin();
		batch.draw(region, 0, 0, region.getRegionWidth() * 1 / 10, region.getRegionHeight() * 1 / 10);
		batch.end();
		batch.setProjectionMatrix(camera.combined);
		renderArrow();
		renderSignos();
		ui.render();
	}

	private void renderSignos() {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				tmpW.renderSignos();
			}
		}
	}
	
	private void desmenbrationEnemy() {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(tmpW.isExplosion() && !tmpW.alreadyDrawn()) {
					desmenbration(tmpW.getPosition(), tmpW.getName(), 1);
					tmpW.setDrawn();
					
				}else if(tmpW.mutilatedInTwo() && !tmpW.alreadyDrawn()) {
					desmenbration(tmpW.getPosition(), tmpW.getName(), 2);
					tmpW.setDrawn();
				}
			}
		}
	}
	
	private void bloodFloor(Vector2 pos) {

		addBloodParticles(pos, false, rand.nextInt(4) + 1, "blood");
	}

	private void addBloodParticles(Vector2 vector, boolean byExplosion, int part, String particleType) {

		for (Bag<Particle> colBloodParticles : colBloodParticles) {

			for (Particle tmpP : colBloodParticles) {

				if (tmpP.isNotCreated()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}
		for (Bag<Particle> colBloodParticles : colBloodParticles) {

			for (Particle tmpP : colBloodParticles) {

				if (tmpP.isInactive()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}
	}

	public void enemyBleeding() {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(tmpW.isBleeding()) {
					bloodFloor(tmpW.getPosition());
				}
			}
		}
	}
	
	public void walkEnemy() {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(!isPaused()) {
					tmpW.walk();
				}
			}
		}
	}
	
	public void enemyPaintBackwards() {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(tmpW.paintBackwards()) {
					tmpW.render(isPaused(), G);
				}
			}
		}
	}
	
	public void renderEnemy(boolean isPaused) {
		for (Bag<Enemy> colEnemies : colEnemies) {
			for (Enemy tmpW : colEnemies) {
				if(!tmpW.paintBackwards() && !tmpW.isDeath()) {
					tmpW.render(isPaused(), G);
				}
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 18;
		camera.viewportHeight = height / 18;
		camera.update();

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		sounds.disposeAssets();
		Player.dispose();
		ui.dispose();
		deactivateObjects();
	}

	public void renderMaterials() {

		for (Bag<Materials> colMaterial : colMaterials) {
			if (colMaterial == null) {
				break;
			} else {
				for (Materials tmpM : colMaterial) {
					if (!tmpM.drawBackwards() && !tmpM.drawUpwards()) {
						tmpM.render(batch);
					}
					if (tmpM.isExplosion()) {

						createExplosion(tmpM.getPosition());
						tmpM.stopExplosion();
					}
					if (tmpM.isBroken() && !tmpM.alreadyPainted) {

						getParticles(tmpM);
						tmpM.setDrawn();
					}
				}
			}
		}
	}

	public void renderMaterialsUpWards() {

		for (Bag<Materials> colMaterial : colMaterials) {
			if (colMaterial == null) {
				break;
			} else {
				for (Materials tmpM : colMaterial) {
					if (tmpM.drawUpwards()) {
						tmpM.render(batch);
					}
				}
			}
		}
	}

	public void renderWeapons() {

		for (Bag<Weapon> colWeapons : colWeapons) {
			for (Weapon tmpW : colWeapons) {
				tmpW.render();
			}
		}
	}

	public void renderArrow() {

		for (Bag<Weapon> colWeapons : colWeapons) {
			for (Weapon tmpW : colWeapons) {
				tmpW.renderArrow();
			}
		}
		for (Bag<Item> colItems : colItems) {
			for (Item tmpI : colItems) {
				tmpI.renderArrow();
			}
		}
	}

	public void renderBullets() {

		for (Bag<Weapon> colWeapons : colWeapons) {
			for (Weapon tmpW : colWeapons) {
				tmpW.renderBullets();
			}
		}
	}

	public void getParticles(Materials tmpM) {
		if (tmpM.getName().equals("littleFlowerVase")) {
			if (tmpM.isExplosion()) {

				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 2, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");

			} else {
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 2, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
			}
		}
		if (tmpM.getName().equals("wallGlass")) {
			if (tmpM.isExplosion()) {

				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 2, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 2, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");
				addParticles(tmpM.getPosition(), true, 3, "glass");
				addParticles(tmpM.getPosition(), true, 1, "glass");

			} else {
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 2, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 2, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
				addParticles(tmpM.getPosition(), false, 3, "glass");
				addParticles(tmpM.getPosition(), false, 1, "glass");
			}
		}
		if (tmpM.getName().equals("chair")) {
			if (tmpM.isExplosion()) {
				addParticles(tmpM.getPosition(), true, 1, "wood");
				addParticles(tmpM.getPosition(), true, 2, "wood");
				addParticles(tmpM.getPosition(), true, 3, "wood");
				addParticles(tmpM.getPosition(), true, 1, "wood");
				addParticles(tmpM.getPosition(), true, 4, "chair");
				addParticles(tmpM.getPosition(), true, 2, "chair");
				addParticles(tmpM.getPosition(), true, 3, "chair");
				addParticles(tmpM.getPosition(), true, 1, "chair");
			} else {
				addParticles(tmpM.getPosition(), false, 1, "wood");
				addParticles(tmpM.getPosition(), false, 2, "wood");
				addParticles(tmpM.getPosition(), false, 3, "wood");
				addParticles(tmpM.getPosition(), false, 1, "wood");
				addParticles(tmpM.getPosition(), false, 4, "chair");
				addParticles(tmpM.getPosition(), false, 2, "chair");
				addParticles(tmpM.getPosition(), false, 3, "chair");
				addParticles(tmpM.getPosition(), false, 1, "chair");
			}
		}
		if (tmpM.getName().equals("explosiveBarrel")) {

			addParticles(tmpM.getPosition(), true, 1, "explosiveBarrel");
			addParticles(tmpM.getPosition(), true, 2, "explosiveBarrel");
			addParticles(tmpM.getPosition(), true, 3, "explosiveBarrel");
			addParticles(tmpM.getPosition(), true, 1, "explosiveBarrel");
			addParticles(tmpM.getPosition(), true, 2, "explosiveBarrel");
			addParticles(tmpM.getPosition(), true, 3, "explosiveBarrel");
		}

	}

	private void addParticles(Vector2 vector, boolean byExplosion, int part, String particleType) {

		for (Bag<Particle> colParticles : colParticles) {

			for (Particle tmpP : colParticles) {
				if (tmpP.isNotCreated()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}for (Bag<Particle> colParticles : colParticles) {

			for (Particle tmpP : colParticles) {
				if (tmpP.isInactive()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}
	}

	private void createExplosion(Vector2 vector) {

		for (Bag<Explosion> colExplosions : colExplosions) {

			for (Explosion tmpE : colExplosions) {
				if (tmpE.isStopped()) {
					tmpE.createBoom(vector);
					return;
				}
			}
		}
	}

	private void addBodyParticles(Vector2 vector, boolean byExplosion, int part, String particleType) {

		for (Bag<Particle> colBodyParticles : colBodyParticles) {

			for (Particle tmpP : colBodyParticles) {

				if (tmpP.isNotCreated()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}for (Bag<Particle> colBodyParticles : colBodyParticles) {

			for (Particle tmpP : colBodyParticles) {

				if (tmpP.isInactive()) {
					tmpP.createParticle(vector, byExplosion, part, particleType);
					return;
				}
			}
		}
	}

	public void renderBackwardsMaterials() {

		for (Bag<Materials> colMaterial : colMaterials) {

			if (colMaterial == null) {
				break;
			} else {
				for (Materials tmpM : colMaterial) {

					if (tmpM.drawBackwards()) {
						tmpM.render(batch);
					}
				}
			}
		}
	}

	public void renderExplosions() {

		for (Bag<Explosion> colExplosions : colExplosions) {
			for (Explosion tmpM : colExplosions) {
				if (!tmpM.isStopped) {
					tmpM.render();
				}
			}
		}
	}

	public void renderParticles() {

		for (Bag<Particle> colBloodParticles : colBloodParticles) {
			for (Particle tmpP : colBloodParticles) {
				tmpP.render();
			}
		}
		for (Bag<Particle> colParticles : colParticles) {
			for (Particle tmpP : colParticles) {
				tmpP.render();
			}
		}
		for (Bag<Particle> colBodyParticles : colBodyParticles) {
			for (Particle tmpP : colBodyParticles) {
				tmpP.render();
			}
		}
	}

	private void initializateExplosions() {
		for (Bag<Explosion> colExplosions : colExplosions) {

			colExplosions.add(new Explosion(world, batch, sounds));
		}
	}

	private void initializateParticles() {
		for (Bag<Particle> colParticles : colParticles) {

			colParticles.add(new Particle(world, batch, "particle"));
		}
		for (Bag<Particle> colBloodParticles : colBloodParticles) {

			colBloodParticles.add(new Particle(world, batch, "blood"));
		}
		for (Bag<Particle> colBodyParticles : colBodyParticles) {

			colBodyParticles.add(new Particle(world, batch, "bodyPart"));
		}
	}

	private void destroyBodies() {

		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			if ((body.getUserData() != null && body.getUserData().equals("destroy"))) {
				world.destroyBody(body);
			}
		}
	}

	private void setInactiveBodies() {

		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			if ((body.getUserData() != null && body.getUserData().equals("inactive"))) {
				body.setActive(false);
				body.setAwake(false);
			}
		}
	}

	private void setActiveBodies() {

		world.getBodies(tmpBodies);
		for (Body body : tmpBodies) {
			if ((body.getUserData() != null && body.getUserData().equals("active"))) {
				body.setActive(true);
				body.setAwake(true);
			}
		}
	}
}
