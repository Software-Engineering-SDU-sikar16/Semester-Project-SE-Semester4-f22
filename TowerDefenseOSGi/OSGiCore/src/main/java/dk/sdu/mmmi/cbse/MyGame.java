package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Bullets.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.components.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.helpers.DrawUtil;
import dk.sdu.mmmi.cbse.common.data.helpers.MouseOperator;
import dk.sdu.mmmi.cbse.common.services.*;
import dk.sdu.mmmi.cbse.core.managers.CustomCursorDrawer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyGame implements ApplicationListener
{
	
	public static final GameData gameData = new GameData();
	private static World world = new World();
	private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
	private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
	private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
	public static MyGame INSTANCE;
	CustomCursorDrawer cursor;
	
	
	private boolean IsInitialized = false;
	
	
	public MyGame()
	{
		init();
		INSTANCE = this;
	}
	
	public void init()
	{
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 960;
		cfg.height = 640;
		cfg.title = "Tower Defense";
		cfg.resizable = false;
		cfg.vSyncEnabled = true;
		cfg.backgroundFPS = 60;
		cfg.foregroundFPS = 60;
		cfg.pauseWhenBackground = true;
		new LwjglApplication(this, cfg);
	}
	
	@Override
	public void create()
	{
		this.gameData.Initialize();
		
		
		System.out.println("Game created");
		for (IGamePluginService gamePluginService : gamePluginList)
		{
			gamePluginService.start(gameData, world);
		}
		
		Array<TiledMap> maps = new Array<TiledMap>();
		GameData.getListOfAssets(TiledMap.class, maps);
		
		for (TiledMap map : maps)
		{
			gameData.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map);
		}
		
		world.OnCreateEntities(gameData);
		

		cursor = new CustomCursorDrawer();
		
		IsInitialized = true;
		
		//OverlayService.CreateAllOverlays();
		//overlayService.onCreate();
//        setScreen(new GameScreen(camera));
	}
	
	@Override
	public void render()
	{
	
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//orthogonalTiledMapRenderer.setView(camera);
		//orthogonalTiledMapRenderer.render();
		//overlayService.onRender();
		//OverlayService.RenderAllOverlays();
		
		Update();
	
		Render();
	}
	
	@Override
	public void resize(int width, int height)
	{
		this.gameData.camera.viewportWidth = width;
		this.gameData.camera.viewportHeight = height;
		this.gameData.camera.update();
		this.gameData.viewport.update(width, height);
	}
	
	@Override
	public void pause()
	{
	
	}
	
	@Override
	public void resume()
	{
	
	}
	
	@Override
	public void dispose()
	{
	
	}
	
	private void Render()
	{
		
		world.OnRenderEntities(gameData);
		
		for (Bullet bullet : gameData.ActiveBullets)
		{
			bullet.OnRender(gameData, world); // update bullet
		}
		
		cursor.render();
		RenderTileSelector(gameData);
		
		
		this.gameData.UIStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		this.gameData.UIStage.draw();
	}
	
	private void Update()
	{
		
	//	gameData.waveManager.OnUpdate(gameData, world);
		
		// always draw the map first with the camera because components are renders synchronously.
//        if(mapService != null) {
//        mapService.updateMap(this.gameData.camera);
//        }
//
//        if (overlayService != null) {
//            overlayService.onUpdate();
//        }
//        GameData.assetManager.update();
		
		// Update
		for (IEntityProcessingService entityProcessorService : entityProcessorList)
		{
			entityProcessorService.process(gameData, world);
		}
		
		// Post Update
		for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList)
		{
			postEntityProcessorService.process(gameData, world);
		}
		
		
		world.OnUpdateEntities(gameData);
		
		for (Entity entity : world.getEntities())
		{
			//sl.process(gameData, new Entity());
			SpriteLoaderPart sl = entity.getPart(SpriteLoaderPart.class);
			if (sl != null) // always check, entity might not have  a spriteloader part!
			{
				sl.OnCreate(gameData, world, entity);
			}
			
		}
		
		
		
		
		
		
		gameData.enemyQuadTree.OnUpdate(gameData, world); // update enemy quadtree
		
		
		
		
		
		// update bullets
		//loop through all our active bullets
		for (Bullet bullet : gameData.ActiveBullets)
		{
			bullet.OnUpdate(gameData, world); // update bullet
		}
		
		// loop through bullets again
		for (Bullet bullet : gameData.ActiveBullets)
		{
			Vector2 bulletPos = bullet.getPosition();
			if (bulletPos.x > gameData.GlobalWidth || bulletPos.x < -50 || bulletPos.y < -50 || bulletPos.y > gameData.GlobalHeight)
			{
				// bullet is off screen so free it and then remove it
				gameData.BulletPool.free(bullet); // place back in pool
				gameData.ActiveBullets.removeValue(bullet, true); // remove bullet from our array so we don't render it anymore
			}
		}
	
		

	}
	
	private void RenderTileSelector(GameData gameData)
	{
		Vector2 TilePos = MouseOperator.GetTilePositionUnderMousePosition(gameData);
		
		gameData.MouseTileSelector.setPosition(TilePos.x, TilePos.y);
		gameData.MouseTileSelector.setSize(gameData.TileMapHelper.TilePixelWidth, gameData.TileMapHelper.TilePixelHeight);
		
		gameData.GlobalSpriteBatch.begin();
		gameData.GlobalSpriteBatch.draw(gameData.MouseTileSelector, gameData.MouseTileSelector.getX(), gameData.MouseTileSelector.getY(), gameData.MouseTileSelector.getWidth(), gameData.MouseTileSelector.getHeight());
		
		Vector2 mousePosition = MouseOperator.GetMouseWorldPosition(gameData);
		if (gameData.Coins < gameData.TurretPriceInCoins)
		{
			DrawUtil.DrawText(gameData.GlobalSpriteBatch, gameData.ScoreUIFont, "" + gameData.TurretPriceInCoins, mousePosition.x + 20, mousePosition.y - 5, new Color(1.0f, 0.2f, 0.2f, 1.0f));
		}
		else
		{
			DrawUtil.DrawText(gameData.GlobalSpriteBatch, gameData.ScoreUIFont, "" + gameData.TurretPriceInCoins, mousePosition.x + 20, mousePosition.y - 5, new Color(1.0f, 1.0f, 1.0f, 1.0f));
		}
		
		gameData.GlobalSpriteBatch.end();
	}
	
	
	public void addEntityProcessingService(IEntityProcessingService eps)
	{
		entityProcessorList.add(eps);
		System.out.println("Added EntityProcessingService: " + eps.getClass().getSimpleName());
	}
	
	public void removeEntityProcessingService(IEntityProcessingService eps)
	{
		entityProcessorList.remove(eps);
		System.out.println("Removed EntityProcessingService: " + eps.getClass().getSimpleName());
	}
	
	public void addPostEntityProcessingService(IPostEntityProcessingService eps)
	{
		postEntityProcessorList.add(eps);
		System.out.println("Added PostEntityProcessingService: " + eps.getClass().getSimpleName());
	}
	
	public void removePostEntityProcessingService(IPostEntityProcessingService eps)
	{
		postEntityProcessorList.remove(eps);
		System.out.println("Removed PostEntityProcessingService: " + eps.getClass().getSimpleName());
	}
	
	public void addGamePluginService(IGamePluginService plugin)
	{
		gamePluginList.add(plugin);
		if (IsInitialized)
		{
			plugin.start(gameData, world);
		}
		System.out.println("Added plugin: " + plugin.getClass().getSimpleName());
	}
	
	public void removeGamePluginService(IGamePluginService plugin)
	{
		gamePluginList.remove(plugin);
		plugin.stop(gameData, world);
		System.out.println("Removed plugin: " + plugin.getClass().getSimpleName());
	}
	
}
