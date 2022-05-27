package dk.sdu.mmmi.cbse.osgimap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        //call functionality to add the map to the game, so it is visible
        GameData.assetManager.setLoader(TiledMap.class, new TmxMapLoader());
            GameData.loadAsset("../assets/maps/map1.tmx", TiledMap.class);
            GameData.assetManager.finishLoading();
            



            //TmxMapLoader tmxMapLoader = new TmxMapLoader();
//            gameData.tiledMap = tmxMapLoader.load("../assets/maps/map1.tmx");
//            //timeout for 2 seconds before running the code
        //gameData.orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(gameData.tiledMap);

    }

    @Override
    public void stop(GameData gameData, World world) {
        GameData.assetManager.unload("../assets/maps/map1.tmx");
        //call functionality to remove the map from the game, so ti not visible anymore
        //GameData.assetManager
//        gameData.tiledMap = null;
//        gameData.orthogonalTiledMapRenderer = null;

    }
}
