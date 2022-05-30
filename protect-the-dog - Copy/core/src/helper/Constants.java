package helper;

import Algorithms.EnemyQuadTree;
import Entities.AnimatedSprite;
import GamePlay.Bullet;
import GamePlay.BulletPool;
import GamePlay.EnemyManager;
import GamePlay.WaveManager;
import Map.GameMapGraph;
import Map.Tile;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Constants
{
	//~ Application Specific
	public static int GlobalWidth = 0;
	public static int GlobalHeight = 0;

	public static final float PPM = 32.0f;


	//~ Game
	public static boolean IsPauseScreenVisible = false;
	public static boolean IsGamePaused = false;
	public static boolean IsBuildingTurret = false;


	public static BulletPool BulletPool = new BulletPool(10000, 0);
	public static Array<Bullet> ActiveBullets = new Array<Bullet>();
	
	public static EnemyManager EnemyManager;
	
	//~ TileMap
	public static TileMapHelper TileMapHelper;
	
	public static World World;
	
	//~ Camera
	public static OrthographicCamera Camera;
	
	
	// ~ Misc
	public static AnimatedSprite MouseTileSelector;
	
	
	//~ Rendering
	
	public static Viewport Viewport;
	
	public static BitmapFont BigPauseScreenFont;
	public static BitmapFont ScoreUIFont;
	public static BitmapFont PixelFont;
	public static BitmapFont ScoreUIFontIcons;
	
	public static SpriteBatch BigPauseScreenSpriteBatch;
	public static com.badlogic.gdx.scenes.scene2d.Stage Stage;
	
	
	public static Overlays.PauseScreen PauseScreen;
	public static Overlays.GameUIOverlay GameUIOverlay;
	

	
//	public static Overlays.WaveProgressBar WaveProgressBar; // todo fix/add
	
	public static ShapeRenderer shapeRenderer;
	public static SpriteBatch batch;
	public static BitmapFont font;
	public static GameMapGraph GameMapGraph;
	public static GraphPath<Tile> GameMapPath;
	public static int Coins = 2500;
	public static Overlays.Health Health;
	public static EnemyQuadTree EnemyQuadTree;
	
	public static WaveManager WaveManager;
}