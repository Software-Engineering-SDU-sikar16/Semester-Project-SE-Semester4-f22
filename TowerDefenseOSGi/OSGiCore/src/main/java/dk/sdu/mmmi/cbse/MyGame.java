package dk.sdu.mmmi.cbse;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
//import com.badlogic.gdx.physics.box2d.World;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpriteLoaderPart;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IMapService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyGame implements ApplicationListener {

//    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private static World world = new World();
    private static final List<IEntityProcessingService> entityProcessorList = new CopyOnWriteArrayList<>();
    private static final List<IGamePluginService> gamePluginList = new CopyOnWriteArrayList<>();
    private IMapService mapService;
    private static List<IPostEntityProcessingService> postEntityProcessorList = new CopyOnWriteArrayList<>();
    public static MyGame INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;

    public MyGame(){
        init();
        INSTANCE = this;
    }

    public void init() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width = 960;
        cfg.height = 640;
        cfg.title = "Protect the dog";
        cfg.resizable = false;
        cfg.vSyncEnabled = true;
        cfg.backgroundFPS = 60;
        cfg.foregroundFPS = 60;

        new LwjglApplication(this, cfg);
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.gameData.setDisplayHeight(this.heightScreen);
        this.gameData.setDisplayWidth(this.widthScreen);
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, widthScreen, heightScreen);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        mapService.createMap();
        for (   IGamePluginService gamePluginService : gamePluginList) {
            gamePluginService.start(gameData, world);
        }

        for(Entity entity : world.getEntities()) {
            SpriteLoaderPart sl = entity.getPart(SpriteLoaderPart.class);
            sl.createSprite();
        }
//        setScreen(new GameScreen(camera));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //orthogonalTiledMapRenderer.setView(camera);
        //orthogonalTiledMapRenderer.render();
        update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void update() {
        // always draw the map first with the camera because components are renders synchronously.
        if(mapService != null) {
        mapService.updateMap(camera);
        }
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessorList) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : postEntityProcessorList) {
            postEntityProcessorService.process(gameData, world);
        }
        for(Entity entity : world.getEntities()) {
                                                                        //sl.process(gameData, new Entity());
          SpriteLoaderPart sl = entity.getPart(SpriteLoaderPart.class);
            sl.process(gameData, entity);
       }
    }

    public void addEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.add(eps);
    }

    public void removeEntityProcessingService(IEntityProcessingService eps) {
        entityProcessorList.remove(eps);
    }

    public void addPostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.add(eps);
    }

    public void removePostEntityProcessingService(IPostEntityProcessingService eps) {
        postEntityProcessorList.remove(eps);
    }

    public void addGamePluginService(IGamePluginService plugin) {
        gamePluginList.add(plugin);
    }

    public void removeGamePluginService(IGamePluginService plugin) {
        gamePluginList.remove(plugin);
        plugin.stop(gameData, world);
    }

    public void addMapService(IMapService map) {
        mapService = map;
    }

    public void removeMapService(IMapService map) {
        mapService = null;
    }

}
