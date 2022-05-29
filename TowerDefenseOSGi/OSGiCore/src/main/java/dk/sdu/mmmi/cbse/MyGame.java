package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.*;
import dk.sdu.mmmi.cbse.core.managers.CustomCursorDrawer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyGame implements ApplicationListener {

    public static final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    public static MyGame INSTANCE;
    private int widthScreen, heightScreen;
	;
	CustomCursorDrawer cursor;


    public MyGame(){
        init();
        INSTANCE = this;
    }

    public void init() {
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
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.gameData.setDisplayHeight(this.heightScreen);
        this.gameData.setDisplayWidth(this.widthScreen);
        this.gameData.camera = new OrthographicCamera();
        this.gameData.camera.setToOrtho(false, widthScreen, heightScreen);
		this.gameData.Initialize();





       for (   IGamePluginService gamePluginService : gamePluginList) {
           gamePluginService.start(gameData, world);
        }

        Array<TiledMap> maps = new Array<TiledMap>();
        GameData.getListOfAssets(TiledMap.class, maps);

        for (TiledMap map : maps) {
            gameData.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        }


        for(Entity entity : world.getEntities()) {
            SpriteLoaderPart sl = entity.getPart(SpriteLoaderPart.class);
            sl.createSprite();
        }

		cursor = new CustomCursorDrawer();


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
		update();
	}
	
	@Override
	public void resize(int width, int height)
	{
		this.gameData.camera.viewportWidth = width;
		this.gameData.camera.viewportHeight = height;
		this.gameData.camera.update();
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
	
	private void update()
	{
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
		
		
		for (Entity entity : world.getEntities())
		{
			//sl.process(gameData, new Entity());
			SpriteLoaderPart sl = entity.getPart(SpriteLoaderPart.class);
			sl.process(gameData, entity);
		}
		
		
		
		this.gameData.UIStage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		this.gameData.UIStage.draw();
		cursor.render();
	}
	
	public void addEntityProcessingService(IEntityProcessingService eps)
	{
		entityProcessorList.add(eps);
	}
	
	public void removeEntityProcessingService(IEntityProcessingService eps)
	{
		entityProcessorList.remove(eps);
	}
	
	public void addPostEntityProcessingService(IPostEntityProcessingService eps)
	{
		postEntityProcessorList.add(eps);
	}
	
	public void removePostEntityProcessingService(IPostEntityProcessingService eps)
	{
		postEntityProcessorList.remove(eps);
	}
	
	public void addGamePluginService(IGamePluginService plugin)
	{
		gamePluginList.add(plugin);
		//plugin.start(gameData, world);
	}
	
	public void removeGamePluginService(IGamePluginService plugin)
	{
		gamePluginList.remove(plugin);
		plugin.stop(gameData, world);
	}




//    public void addMapService(IMapService map) {
//        mapService = map;
//    }
//
//    public void removeMapService(IMapService map) {
//        mapService = null;
//    }
//
//    public void addUIService(IOverlayService overlay) {
//        overlayService  = overlay;
//    }
//
//    public void removeUIService(IOverlayService overlay) {
//        overlayService = null;
//    }

}
