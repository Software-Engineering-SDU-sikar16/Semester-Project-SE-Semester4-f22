package dk.sdu.mmmi.cbse.osgimap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Map.TileMapHelper;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;


public class MapPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // call functionality to add the map to the game, so it is visible
        gameData.assetManager.setLoader(TiledMap.class, new TmxMapLoader()); // set the loader for the map
        gameData.loadAsset("../assets/maps/map1.tmx", TiledMap.class); // load the map
        gameData.assetManager.finishLoading(); // finish loading the map

        gameData.TileMapHelper = new TileMapHelper(); // create a new helper for the map
        gameData.orthogonalTiledMapRenderer = gameData.TileMapHelper.setupMap(gameData, "../assets/maps/map1.tmx"); // setup the map

        gameData.EnemiesSpawnPosition = new Vector2(gameData.TileMapHelper.Start.x, gameData.TileMapHelper.Start.y); //set the start position to the tile position.
    }


    @Override
    public void stop(GameData gameData, World world) {
        // call functionality to remove the map from the game, so it is no longer visible
        // GameData.assetManager.unload("../assets/maps/map1.tmx");
    }
}
