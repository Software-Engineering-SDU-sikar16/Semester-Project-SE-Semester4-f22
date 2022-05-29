package dk.sdu.mmmi.cbse.common.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;
import dk.sdu.mmmi.cbse.common.events.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData
{
	
	//Bullets
/*	public static BulletPool BulletPool = new BulletPool(10000, 0);
	public static Array<Bullet> ActiveBullets = new Array<Bullet>();*/
	
	public Resources assets;
	
	public Health UIHealth;
	
	public static ShapeRenderer GlobalShapeRenderer;
	public static dk.sdu.mmmi.cbse.common.data.Map.TileMapHelper TileMapHelper;
	public static dk.sdu.mmmi.cbse.common.data.Map.GameMapGraph GameMapGraph;
	public static GraphPath<Tile> GameMapPath;
	// Fonts
	public BitmapFont BigPauseScreenFont;
	public SpriteBatch GlobalSpriteBatch;
	public BitmapFont ScoreUIFontIcons;
	public BitmapFont ScoreUIFont;
	public BitmapFont PixelFont;


	// Game
	public static int Coins = 2500;
	public static WaveManager waveManager;


	public boolean IsPauseScreenVisible = false;
	private float delta;
	private int displayWidth;
	private int displayHeight;
	private final GameKeys keys = new GameKeys();
	private List<Event> events = new CopyOnWriteArrayList<>();
	public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	public TiledMap tiledMap;
	public static OrthographicCamera camera;
	public MouseOperator mouseOperator;
	public static Sprite MouseTileSelector;
	public Stage UIStage;

	public void addEvent(Event e) {
		events.add(e);
	}

	public static AssetManager assetManager = new AssetManager();

	public void loadAsset(String path, Class type) {
		assetManager.load(path, type);
	}

	public static <T> Array<T> getListOfAssets(Class<T> type, Array<T> array) {
		return assetManager.getAll(type, array);
	}

	public void removeEvent(Event e) {
		events.remove(e);
	}

	public List<Event> getEvents() {
		return events;
	}

	public GameKeys getKeys() {
		return keys;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public float getDelta() {
		return delta;
	}

	public void setDisplayWidth(int width) {
		this.displayWidth = width;
	}

	public int getDisplayWidth() {
		return displayWidth;
	}

	public void setDisplayHeight(int height) {
		this.displayHeight = height;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
		List<Event> r = new ArrayList();
		for (Event event : events) {
			if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
				r.add(event);
			}
		}

		return r;
	}

	public void Initialize() {
		UIStage = new com.badlogic.gdx.scenes.scene2d.Stage();
		Gdx.input.setInputProcessor(UIStage);

		GlobalSpriteBatch = new SpriteBatch();
		MouseTileSelector = new Sprite();
		GlobalShapeRenderer = new ShapeRenderer();
		waveManager = new WaveManager();
		
		UIHealth = new Health(20, displayHeight - 70, 50, 50, 7, 7);
	}
}
	


