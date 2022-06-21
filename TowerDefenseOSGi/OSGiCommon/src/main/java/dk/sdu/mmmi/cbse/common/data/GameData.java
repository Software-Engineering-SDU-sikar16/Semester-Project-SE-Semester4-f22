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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import dk.sdu.mmmi.cbse.common.data.Algorithms.EnemyQuadTree;
import dk.sdu.mmmi.cbse.common.data.Bullets.Bullet;
import dk.sdu.mmmi.cbse.common.data.GamePlay.BulletPool;
import dk.sdu.mmmi.cbse.common.data.Map.Tile;
import dk.sdu.mmmi.cbse.common.data.Map.TileMapHelper;
import dk.sdu.mmmi.cbse.common.data.helpers.GameKeys;
import dk.sdu.mmmi.cbse.common.data.helpers.MouseOperator;
import dk.sdu.mmmi.cbse.common.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameData {
    public static final boolean DEBUG = false;

    // Enemy Waves
    public static WaveSettings[] AllWaves = new WaveSettings[]
            {
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                    }),
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                    }),
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.elite,
                    }),
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.elite,
                            EnemyType.elite,
                    }),
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.elite,
                            EnemyType.kingkong,
                    }),
                    new WaveSettings(new EnemyType[]{
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.normal,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.boss,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.elite,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                    }),

                    new WaveSettings(new EnemyType[]{
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                            EnemyType.kingkong,
                    }),
            };
    public static BulletPool BulletPool = new BulletPool(10000, 0);
    public static Array<Bullet> ActiveBullets = new Array<Bullet>();
    public static TileMapHelper TileMapHelper;
    // Game
    public static int Coins = 2500;
    public static int GlobalWidth;
    public static int GlobalHeight;
    public static OrthographicCamera camera;
    public static Sprite MouseTileSelector;
    public static AssetManager assetManager = new AssetManager();
    private final GameKeys keys = new GameKeys();
    public int currentWaveIndex = 0;
    public boolean IsWaveStarted = false;
    public Vector2 EnemiesSpawnPosition;
    // Essential
    public Viewport viewport;
    public Resources assets;
    public EnemyQuadTree enemyQuadTree;
    public dk.sdu.mmmi.cbse.common.data.GamePlay.UIHealth UIHealth;
    public ShapeRenderer GlobalShapeRenderer;
    public dk.sdu.mmmi.cbse.common.data.Map.GameMapGraph GameMapGraph;
    public GraphPath<Tile> GameMapPath;
    // Fonts
    public BitmapFont BigPauseScreenFont;
    public SpriteBatch GlobalSpriteBatch;
    public BitmapFont ScoreUIFontIcons;
    public BitmapFont ScoreUIFont;
    public BitmapFont PixelFont;
    public boolean IsPauseScreenVisible = false;
    public int TurretPriceInCoins = 1000;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public TiledMap tiledMap;
    public MouseOperator mouseOperator;
    public Stage UIStage;
    private float delta;
    private List<Event> events = new CopyOnWriteArrayList<>();

    public static <T> Array<T> getListOfAssets(Class<T> type, Array<T> array) {
        return assetManager.getAll(type, array);
    }

    public int GetWaveNumber() {
        return currentWaveIndex;
    }

    public int GetTotalWaves() {
        return AllWaves.length;
    }

    public WaveSettings GetWave() {
        currentWaveIndex = MathUtils.clamp(currentWaveIndex, 0, AllWaves.length);
        return AllWaves[currentWaveIndex];
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void loadAsset(String path, Class type) {
        assetManager.load(path, type);
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

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public int getGlobalWidth() {
        return GlobalWidth;
    }

    public void setGlobalWidth(int width) {
        this.GlobalWidth = width;
    }

    public int getGlobalHeight() {
        return GlobalHeight;
    }

    public void setGlobalHeight(int height) {
        this.GlobalHeight = height;
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
        this.setGlobalWidth(Gdx.graphics.getWidth());
        this.setGlobalHeight(Gdx.graphics.getHeight());

        assets.LoadAllAssets(this);

        viewport = new FitViewport(GlobalWidth, GlobalHeight);
        UIStage = new Stage(viewport);
        Gdx.input.setInputProcessor(UIStage);


        camera = new OrthographicCamera();
        camera.setToOrtho(false, GlobalWidth, GlobalHeight);


        MouseTileSelector = new Sprite(Resources.LoadTexture("../assets/ui/selected_tile.png"));


        GlobalSpriteBatch = new SpriteBatch();
        GlobalShapeRenderer = new ShapeRenderer();

        enemyQuadTree = new EnemyQuadTree(this);
    }
}
	


