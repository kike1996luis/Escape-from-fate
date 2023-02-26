package com.me.aTester;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
public class Code implements ApplicationListener , InputProcessor{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public static Bag<Layer>[] colLayers;
	private Stage stage;
	private Skin skin;
	private int numLayers = 0;
	
	private int limitLayers = 5;
	private BitmapFont font;
	private ShapeRenderer color;
	private final float timestep = 1 / 60f;
	private final int positioniterations = 3;
	private TextButton layerText;
	private TextButton showLayer;
	private Color color1;
	private JSONDataReader json;
	private int layerS = 0;
	private boolean[] layerSelected;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private boolean deselected;
	
	@SuppressWarnings("unchecked")
	@Override
	public void create() {		
		
		debugRenderer = new Box2DDebugRenderer();
		world = new World(new Vector2(0, 0), true);
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 13, Gdx.graphics.getHeight() / 13);
		
		batch = new SpriteBatch();
		
		colLayers = (Bag<Layer>[]) new Bag[200];
		for (int i = 0; i < colLayers.length; i++) {

			colLayers[i] = new Bag<Layer>();

		}
		json = new JSONDataReader(this);
		json.readJSON("data.json");
		
		limitLayers = numLayers;
		
		color1 = new Color(1, 1, 1, 1);
		color = new ShapeRenderer();
		
		stage = new Stage();
		
		font = new BitmapFont();
		font.setScale(2, 2);
		
		skin = new Skin(Gdx.files.internal(
				"com/skin/uiskin.json"));
		
		TextButton text = new TextButton("Testeador de animaciones", skin);
		TextButton colorOne = new TextButton("Exit", skin);
		TextButton colorTwo = new TextButton("Exit", skin);
		TextButton colorThree = new TextButton("Exit", skin);
		TextButton colorFour = new TextButton("Exit", skin);
		TextButton colorFive = new TextButton("Exit", skin);
		layerText = new TextButton("Mostrando " + numLayers + " Capas", skin);
		showLayer = new TextButton("Capa " + layerS + " seleccionada", skin);
		
		text.setColor(.1f, .1f, .1f, 1);
		text.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-25);
		layerText.setPosition(Gdx.graphics.getWidth()-145, 75);
		showLayer.setPosition(0, 75);
		
		colorOne.setColor(.1f, .1f, .1f, 1);
		colorOne.setHeight(50);
		colorOne.setWidth(110);
		colorOne.setPosition(20, 10);
		
		colorTwo.setColor(.1f, .1f, .1f, 1);
		colorTwo.setHeight(50);
		colorTwo.setWidth(110);
		colorTwo.setPosition(150, 10);
		
		colorThree.setColor(.1f, .1f, .1f, 1);
		colorThree.setHeight(50);
		colorThree.setWidth(110);
		colorThree.setPosition(275, 10);
		
		colorFour.setColor(.1f, .1f, .1f, 1);
		colorFour.setHeight(50);
		colorFour.setWidth(110);
		colorFour.setPosition(400, 10);
		
		colorFive.setColor(.1f, .1f, .1f, 1);
		colorFive.setHeight(50);
		colorFive.setWidth(110);
		colorFive.setPosition(525, 10);
		
		layerSelected = new boolean[limitLayers];
		layerSelected[0] = true;
		layerS = 0;
		
		colorOne.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				color1.set(1, 1, 1, 1);
			}
		});
		
		colorTwo.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				color1.set(.5f, .5f, .5f, 1);
			}
		});
		
		colorThree.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				color1.set(0f, 0f, 0f, 1);
			}
		});
		
		colorFour.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				color1.set(0f, 1f, 1f, 1);
			}
		});
		
		colorFive.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				color1.set(1f, 0f, 1f, 1);
			}
		});
		
		stage.addActor(text);
		stage.addActor(layerText);
		stage.addActor(showLayer);
		stage.addActor(colorOne);
		stage.addActor(colorTwo);
		stage.addActor(colorThree);
		stage.addActor(colorFour);
		stage.addActor(colorFive);
		
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
		testWhoIsSelected();
		debugRenderer.setDrawContacts(false);
		debugRenderer.setDrawBodies(false);
	}
	
	public void setNumberLayers(int limitLayers) {
		this.limitLayers = limitLayers;
	}

	@Override
	public void dispose() {
		batch.dispose();
		skin.dispose();
		color.dispose();
	}

	@Override
	public void render() {	
		zoom();
		move();
		if(deselected) {
			showLayer.setText("Sin seleccion");
		} else {
			showLayer.setText("Capa " + (layerS+1) + " seleccionada");
		}
		layerText.setText("Mostrando " + numLayers + " capas");
		
		
		Gdx.gl.glClearColor(color1.r, color1.g, color1.b, color1.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined);
		renderSprites();
		
		batch.begin();
		stage.draw();
		stage.act(Gdx.graphics.getRawDeltaTime());
		
		color.begin(ShapeType.Filled);
		color.setColor(1, .5f, 0, 1);
		color.rect(0, 0, Gdx.graphics.getWidth(), 75);
		color.end();
		
		color.begin(ShapeType.Filled);
		color.setColor(1f, 1f, 1f, 1);
		color.rect(25, 12, 100, 50);
		color.end();
		
		color.begin(ShapeType.Filled);
		color.setColor(.5f, .5f, .5f, 1);
		color.rect(150, 12, 100, 50);
		color.end();
		
		color.begin(ShapeType.Filled);
		color.setColor(0f, 0f, 0f, 1);
		color.rect(275, 12, 100, 50);
		color.end();
		
		color.begin(ShapeType.Filled);
		color.setColor(0f, 1f, 1f, 1);
		color.rect(400, 12, 100, 50);
		color.end();
		
		color.begin(ShapeType.Filled);
		color.setColor(1f, 0f, 1f, 1);
		color.rect(525, 12, 100, 50);
		color.end();
		
		batch.end();
		
		batch.setProjectionMatrix(camera.combined);
		world.step(timestep, positioniterations, positioniterations);
		camera.update();
	}

	public void renderSprites() {
		
		for (Bag<Layer> colLayers : colLayers) {
			for (Layer tmpM : colLayers) {
				if(tmpM.getID() < numLayers) {
					tmpM.render();
				}
			}
		}
	}
	
	public void zoom() {
		if(Gdx.input.isKeyPressed(Keys.Q)) {
			camera.rotate(1f);
		}if(Gdx.input.isKeyPressed(Keys.E)) {
			camera.rotate(-1f);
		}
	}
	
	public void move() {
		if(Gdx.input.isKeyPressed(Keys.W)) {
			camera.translate(0, +.3f);
		}if(Gdx.input.isKeyPressed(Keys.A)) {
			camera.translate(-.3f, 0);
		}if(Gdx.input.isKeyPressed(Keys.S)) {
			camera.translate(0, -.3f);
		}if(Gdx.input.isKeyPressed(Keys.D)) {
			camera.translate(+.3f, 0);
		}
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void restart() {
		for (Bag<Layer> colLayers : colLayers) {
			for (Layer tmpM : colLayers) {
				tmpM.restart();
			}
		}
	}
	
	@Override
	public boolean keyDown(int keyCode) {
		
		if (keyCode == Keys.F) {
			if(numLayers < limitLayers) {
				numLayers++;
			}
		}if (keyCode == Keys.G) {
			if(numLayers > 0) {
				numLayers--;
			}
		}if (keyCode == Keys.R) {
			restart();
		}if (keyCode == Keys.SPACE) {
			deselected = false;
			if(layerS<limitLayers-1) {
				layerS++;
				testWhoIsSelected();
			}else {
				layerS = 0;
				testWhoIsSelected();
			}
		}if (keyCode == Keys.SHIFT_LEFT) {
			deselected = true;
			deselectAll();
		}if (keyCode == Keys.ESCAPE) {
			Gdx.app.exit();
		}
		return false;
	}
	
	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		if(amount == 1) {
			camera.zoom = camera.zoom + .05f;
		}if(amount == -1) {
			camera.zoom = camera.zoom - .05f;
		}
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void testWhoIsSelected() {
		int selected = 0;
		for(int i = 0; i < layerSelected.length; i++) {
			if(layerS == i) {
				layerSelected[i] = true;
				selected = i;
			}else {
				layerSelected[i] = false;
			}
		}selectLayer(selected);
	}
	
	public void deselectAll() {
		for (Bag<Layer> colLayers : colLayers) {
			for (Layer tmpM : colLayers) {
				tmpM.deselectLayer();
			}
		}
	}
	
	public void selectLayer(int selected) {
		for (Bag<Layer> colLayers : colLayers) {
			for (Layer tmpM : colLayers) {
				if(tmpM.getID() == selected) {
					tmpM.selectLayer();
				}else {
					tmpM.deselectLayer();
				}
			}
		}
	}

	public void addLayer(String path, float x, float y, float angle, float height, float width, int split, float speed) {
		
		Layer layer = new Layer(numLayers, world, path, x, y, angle, height, width, split, camera, batch, speed);
		colLayers[numLayers].add(layer);
		numLayers++;
	}
}
